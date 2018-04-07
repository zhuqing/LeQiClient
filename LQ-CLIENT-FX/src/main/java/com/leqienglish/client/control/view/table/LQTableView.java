/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table;


import com.leqienglish.client.control.dragdrop.DragDelegateI;
import com.leqienglish.client.control.dragdrop.DropDelegateI;
import com.leqienglish.client.control.view.table.row.LQRowFactory;
import com.leqienglish.client.util.reflect.MethodReflectUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @param <S>
 */
public class LQTableView<S> extends TableView<S> {

    private ObservableList<S> tickItems;

    private EventHandler<MouseEvent> rowMouseEventHandler;

    /**
     * 拽数据的代理
     */
    private DragDelegateI dragDelegate;

    /**
     * 拖放数据的代理
     */
    private DropDelegateI dropDelegate;

    /**
     * 需要刷新的数据
     */
    private ObservableList<S> refreshItems;

    /**
     * 行样式回调
     */
    private Callback<S, List<String>> rowStylesCallback;

    private Callback<S, String> rowTipTextCallback;

    public LQTableView() {

        this.setRowFactory(new LQRowFactory<>());
        initListener();
    }

    @Override
    public LQTableView clone() throws CloneNotSupportedException {
        try {
            LQTableView newHipTableView = this.getClass().newInstance();

            for (TableColumn<S, ?> column : this.getColumns()) {

                Method method = MethodReflectUtil.getMethod(column.getClass(), "clone");
                if (method == null) {
                    continue;
                }
                TableColumn clonedNode = (TableColumn) MethodReflectUtil.executeMethod(method, column);
                newHipTableView.getColumns().add(clonedNode);
            }

            return newHipTableView;
        } catch (InstantiationException ex) {
            Logger.getLogger(LQTableView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LQTableView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    private void initListener() {

        this.getItems().addListener(new ListChangeListener<S>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends S> c) {

                List removed = new ArrayList<>();
                for (S s : getTickItems()) {
                    if (!getItems().contains(s)) {
                        removed.add(s);
                    }
                }
                getTickItems().removeAll(removed);

            }
        });

    }

    public ObservableList<S> getTickItems() {
        if (tickItems == null) {
            tickItems = FXCollections.observableArrayList();
        }
        return tickItems;
    }

    public void setTickItems(List<S> tickItems) {
        this.getTickItems().setAll(tickItems);
    }

    public void refreshCustomDataSourceCallback() {
        for (Node node : this.getChildren()) {
           // refreshCustomDataSourceCallback(node);
        }

    }

//    private void refreshCustomDataSourceCallback(Node node) {
//        if (node instanceof HipCustomDataSourceTableCell) {
//            HipCustomDataSourceTableCell cell = (HipCustomDataSourceTableCell) node;
//            cell.refreshCustomDataSourceCallback();
//
//        } else if (node instanceof Parent) {
//            Parent parent = (Parent) node;
//            for (Node item : parent.getChildrenUnmodifiable()) {
//                refreshCustomDataSourceCallback(item);
//            }
//        }
//    }

    public void refresh() {
        ObservableList<S> itemList = this.getItems();
        this.setItems(null);
        this.setItems(itemList);
    }

    /**
     * 需要刷新的数据
     *
     * @return the refreshItems
     */
    public ObservableList<S> getRefreshItems() {
        if (refreshItems == null) {
            refreshItems = FXCollections.observableArrayList();
        }
        return refreshItems;
    }

    /**
     * 需要刷新的数据
     *
     * @param refreshItems the refreshItems to set
     */
    public void setRefreshItems(List<S> refreshItems) {
        this.getRefreshItems().setAll(refreshItems);
    }

    /**
     * @return the rowStylesCallback
     */
    public Callback<S, List<String>> getRowStylesCallback() {
        return rowStylesCallback;
    }

    /**
     * @param rowStylesCallback the rowStylesCallback to set
     */
    public void setRowStylesCallback(Callback<S, List<String>> rowStylesCallback) {
        this.rowStylesCallback = rowStylesCallback;
    }

    /**
     * @return the rowMouseEventHandler
     */
    public EventHandler<MouseEvent> getRowMouseEventHandler() {
        return rowMouseEventHandler;
    }

    /**
     * @param rowMouseEventHandler the rowMouseEventHandler to set
     */
    public void setRowMouseEventHandler(EventHandler<MouseEvent> rowMouseEventHandler) {
        this.rowMouseEventHandler = rowMouseEventHandler;
    }

    /**
     * @return the rowTipTextCallback
     */
    public Callback<S, String> getRowTipTextCallback() {
        return rowTipTextCallback;
    }

    /**
     * @param rowTipTextCallback the rowTipTextCallback to set
     */
    public void setRowTipTextCallback(Callback<S, String> rowTipTextCallback) {
        this.rowTipTextCallback = rowTipTextCallback;
    }

    /**
     * 拽数据的代理
     *
     * @return the dragDelegate
     */
    public DragDelegateI getDragDelegate() {
        return dragDelegate;
    }

    /**
     * 拽数据的代理
     *
     * @param dragDelegate the dragDelegate to set
     */
    public void setDragDelegate(DragDelegateI dragDelegate) {
        this.dragDelegate = dragDelegate;
    }

    /**
     * 拖放数据的代理
     *
     * @return the dropDelegate
     */
    public DropDelegateI getDropDelegate() {
        return dropDelegate;
    }

    /**
     * 拖放数据的代理
     *
     * @param dropDelegate the dropDelegate to set
     */
    public void setDropDelegate(DropDelegateI dropDelegate) {
        this.dropDelegate = dropDelegate;
    }

}
