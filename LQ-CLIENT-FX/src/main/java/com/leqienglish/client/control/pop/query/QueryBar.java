/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.pop.query;


import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell.CommitTypeEnum;
import com.leqienglish.client.util.thread.DelayRunner;
import com.leqienglish.util.image.ImageRepertory;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * 查询框
 *
 * @author zhuqing
 */
public class QueryBar extends StackPane {

    /**
     *
     */
    private TextField textField;
    private ImageView imageView;

    /**
     * 延时时间
     */
    private Long delay;

    private boolean stopQuery = false;
    /**
     * 提交类型
     */
    private CommitTypeEnum commitType;

    private Consumer<String> queryConsumer;

    private static final String IMAGE_URL = "/img/controlbar/genquery1.png";

    public QueryBar() {
        this.getChildren().addAll(this.getTextField(), this.getImageView());
        StackPane.setAlignment(this.getImageView(), Pos.CENTER_RIGHT);
        StackPane.setMargin(this.getImageView(), new Insets(2, 6, 2, 2));
        this.setMaxHeight(30);

    }

    /**
     * @return the textField
     */
    public TextField getTextField() {
        if (this.textField == null) {
            this.textField = new TextField();
            this.textField.prefHeightProperty().bind(this.widthProperty());
            JavaFxObservable.changesOf(textField.textProperty())
                    .filter((c) -> getQueryConsumer() != null && !isStopQuery())
                    .filter((c) -> getCommitType() == commitType.COMMIT_BY_DELAY)
                    .subscribe((c) -> {
                        DelayRunner.delayRunner(textField, this.getDelay(), () -> this.getQueryConsumer().accept(textField.getText()));
                    });
            JavaFxObservable.eventsOf(textField, KeyEvent.KEY_PRESSED)
                    .filter((key) -> getQueryConsumer() != null)
                    .filter((key) -> key.getCode() == KeyCode.ENTER)
                    .filter((c) -> getCommitType() != commitType.COMMIT_BY_DELAY)
                    .subscribe((ea) -> {
                        ea.consume();
                        this.getQueryConsumer().accept(textField.getText());

                    });
            JavaFxObservable.changesOf(this.focusedProperty())
                    .filter((c) -> c.getNewVal())
                    .subscribe((c) -> textField.requestFocus());

            
           
        }
        return textField;
    }

    /**
     * @return the imageView
     */
    public ImageView getImageView() {
        if (this.imageView == null) {
            Image image = ImageRepertory.getImage(IMAGE_URL);
            this.imageView = new ImageView(image);
            JavaFxObservable.eventsOf(imageView, MouseEvent.MOUSE_CLICKED)
                    .filter((ea) -> getQueryConsumer() != null)
                    .filter((ea) -> ea.getClickCount() == 1 && ea.getButton() == MouseButton.PRIMARY)
                    .subscribe((e) -> {
                        getQueryConsumer().accept(getTextField().getText());
                    });
        }
        return imageView;
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
     * @return the preventQuert
     */
    public boolean isStopQuery() {
        return stopQuery;
    }

    /**
     * @param stopQuery the preventQuert to set
     */
    public void setStopQuery(boolean stopQuery) {
        this.stopQuery = stopQuery;
    }

    /**
     * 提交类型
     *
     * @return the commitType
     */
    public CommitTypeEnum getCommitType() {
        if (commitType == null) {
            return CommitTypeEnum.COMMIT_BY_DELAY;
        }
        return commitType;
    }

    /**
     * 提交类型
     *
     * @param commitType the commitType to set
     */
    public void setCommitType(CommitTypeEnum commitType) {
        this.commitType = commitType;
    }

    /**
     * 延时时间
     *
     * @return the delaytime
     */
    public Long getDelay() {
        if (delay == null) {
            return 300L;
        }
        return delay;
    }

    /**
     * 延时时间
     *
     * @param delaytime the delaytime to set
     */
    public void setDelay(Long delaytime) {
        this.delay = delaytime;
    }
}
