/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.pop.query;


import com.leqienglish.client.control.view.table.LQTableView;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author zhuqing
 */
public class LQQueryTablePopup<T> extends LQQueryPopup<T, LQTableView> {

    private Callback<String, List<T>> queryCallback;

    private Callback<T, Boolean> commitCallback;

    @Override
    protected void query(String text) {
        if (this.getQueryCallback() == null || this.getContent() == null) {
            return;
        }

        List<T> datas = this.getQueryCallback().call(text);
        this.getContent().getItems().setAll(datas);
        this.getContent().getSelectionModel().select(0);
    }

    @Override
    public void setContent(LQTableView content) {
        if (content != null) {
            addEnterEvent(content);
            addTableRowDoubleClick(content);
            content.setPrefHeight(200);
        }

        super.setContent(content);
    }

    /**
     * 给table增加回车事件
     *
     * @param hipTableView
     */
    private void addEnterEvent(LQTableView hipTableView) {
        hipTableView.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() != KeyCode.ENTER) {
                    return;
                }
                callCommitCallback(hipTableView);

            }
        });
    }

    /**
     * 给显示的Table增加行点击事件
     *
     * @param hipTableView
     */
    private void addTableRowDoubleClick(LQTableView hipTableView) {
        if (hipTableView == null) {
            return;
        }

        hipTableView.setRowMouseEventHandler(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() != 2) {
                    return;
                }

                if (event.getButton() != MouseButton.PRIMARY) {
                    return;
                }

                callCommitCallback(hipTableView);

            }
        });
    }

    /**
     * 调用提交回调
     *
     * @param hipTableView
     */
    private void callCommitCallback(LQTableView hipTableView) {
        if (!getHipPopUp().isShowing()) {
            return;
        }
        if (getCommitCallback() == null) {
            return;
        }

        if (hipTableView.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        try {
            if (getCommitCallback().call((T) hipTableView.getSelectionModel().getSelectedItem())) {
                getHipPopUp().hide();
            }
        } catch (Exception ex) {
            getHipPopUp().hide();
        }
    }

    /**
     * @return the queryCallback
     */
    public Callback<String, List<T>> getQueryCallback() {
        return queryCallback;
    }

    /**
     * @param queryCallback the queryCallback to set
     */
    public void setQueryCallback(Callback<String, List<T>> queryCallback) {
        this.queryCallback = queryCallback;
    }

    /**
     * @return the commitCallback
     */
    public Callback<T, Boolean> getCommitCallback() {
        return commitCallback;
    }

    /**
     * @param commitCallback the commitCallback to set
     */
    public void setCommitCallback(Callback<T, Boolean> commitCallback) {
        this.commitCallback = commitCallback;
    }

}
