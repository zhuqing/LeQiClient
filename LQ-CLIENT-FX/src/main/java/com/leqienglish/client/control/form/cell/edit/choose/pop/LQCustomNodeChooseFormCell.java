/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.choose.pop;


import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.control.pop.query.LQQueryPopup;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * 自定义弹出框节点的FormCell
 *
 * @author zhuqing
 */
public class LQCustomNodeChooseFormCell<S, T> extends LQEditableFormCell<S, T, LQQueryPopup> {

    /**
     * 自定义节点回调
     */
    private Callback<S, Node> customNodeCallback;
    /**
     * 根据输入文本查询查询
     */
    private Consumer<String> queryConsumer;

    /**
     * 点击提交按钮触发
     */
    private Consumer<S> commit;

    /**
     * 弹出框中显示的内容
     */
    private BorderPane content;

    /**
     * 查询弹出框组件
     */
    private LQQueryPopup popup;

    @Override
    protected LQQueryPopup createEditGraghic() {
        if (getPopup() == null) {

            popup = new LQQueryPopup() {
                @Override
                protected void query(String text) {
                    if (getQueryConsumer() != null) {
                        getQueryConsumer().accept(text);
                    }
                }

                @Override
                public Node getContent() {
                    if (getCustomNodeCallback() != null) {
                        content.setCenter(getCustomNodeCallback().call(getItem()));
                    }
                    return super.getContent(); //To change body of generated methods, choose Tools | Templates.
                }

            };
            content = createContent();
            getPopup().setContent(getContent());
        }
        return getPopup();
    }

    protected BorderPane createContent() {

        HBox bottomActionBox = new HBox();
        Button submit = new Button("提交");
        JavaFxObservable.actionEventsOf(submit)
                .filter((a) -> getCommit() != null && getPopup() != null)
                .subscribe((a) -> {
                    getCommit().accept(getItem());
                    getPopup().close();
                });
        Button cancel = new Button("取消");
        JavaFxObservable.actionEventsOf(cancel)
                .filter((a) -> getPopup() != null)
                .subscribe((c) -> getPopup().close());
        bottomActionBox.getChildren().addAll(submit, cancel);
        BorderPane pane = new BorderPane();

        pane.setBottom(bottomActionBox);
        return pane;
    }

    @Override
    public LQFormCell clone() {
        LQCustomNodeChooseFormCell cell = (LQCustomNodeChooseFormCell) super.clone();
        cell.setCommit(commit);
        cell.setCustomNodeCallback(customNodeCallback);
        cell.setQueryConsumer(queryConsumer);

        return cell;
    }

    /**
     * @return the customNodeCallback
     */
    public Callback<S, Node> getCustomNodeCallback() {
        return customNodeCallback;
    }

    /**
     * @param customNodeCallback the customNodeCallback to set
     */
    public void setCustomNodeCallback(Callback<S, Node> customNodeCallback) {
        this.customNodeCallback = customNodeCallback;
    }

    /**
     * @return the queryConsumer
     */
    public Consumer<String> getQueryConsumer() {
        return queryConsumer;
    }

    /**
     * @param queryConsumer the queryConsumer to set
     */
    public void setQueryConsumer(Consumer<String> queryConsumer) {
        this.queryConsumer = queryConsumer;
    }

    /**
     * @return the content
     */
    public Node getContent() {
        return content;
    }

    /**
     * @return the commit
     */
    public Consumer<S> getCommit() {
        return commit;
    }

    /**
     * @param submit the commit to set
     */
    public void setCommit(Consumer<S> submit) {
        this.commit = submit;
    }

    /**
     * @return the popup
     */
    public LQQueryPopup getPopup() {
        return popup;
    }

}
