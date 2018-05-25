/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.phoneticize;

import com.leqienglish.client.util.phoneticize.PhoneticizeUtil;
import com.leqienglish.client.util.thread.DelayRunner;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.util.List;
import java.util.function.Consumer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 * @author zhuqing
 */
public final class LQPhoneticize extends HBox {

    /**
     * 文本
     */
    private StringProperty text;
    /**
     * 拼音
     */
    private StringProperty phoneticize;
    /**
     * 拼音码的最大长度，默认为10
     */
    private Integer phoneticizeMaxLength = 10;

    private TextField textField;
    private TextField phoneticizeTextField;

    private Consumer commit;

    private boolean couldCommit = false;

    private final ChangeListener<String> textChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            getTextField().setText(newValue);
        }
    };

    private final ChangeListener<String> phoneticizeChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            getPhoneticizeTextField().setText(newValue);
        }
    };

    private final ChangeListener<String> phoneticizeTextFieldChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            setPhoneticize(newValue);
        }
    };

    public LQPhoneticize() {
        this.getChildren().addAll(this.getTextField(), this.getPhoneticizeTextField());
        this.phoneticizeProperty().addListener(phoneticizeChangeListener);
        this.textProperty().addListener(textChangeListener);

        JavaFxObservable.changesOf(this.getTextField().textProperty())
                .filter((f) -> getTextField().isFocused())
                .subscribe((ch) -> getPinYinHeader(ch.getNewVal()));

        /*
         **
         * 拼音文本改变后提交修改
         */
        JavaFxObservable.changesOf(this.getPhoneticizeTextField().textProperty())
                .subscribe((ch) -> commit());

        addKeyEvent(getTextField());
        addKeyEvent(getPhoneticizeTextField());

        addFocusEvent(getTextField());
        addFocusEvent(getPhoneticizeTextField());
        this.getPhoneticizeTextField().textProperty().addListener(phoneticizeTextFieldChangeListener);
    }

    private void addKeyEvent(TextField node) {
        JavaFxObservable.eventsOf(node, KeyEvent.KEY_PRESSED)
                .filter((keyEvent) -> keyEvent.getCode() == KeyCode.ENTER)
                .subscribe((key) -> commit());
    }

    private void addFocusEvent(TextField node) {
        JavaFxObservable.nullableValuesOf(node.focusedProperty())
                .filter((f) -> !f.orElse(Boolean.TRUE))
                .subscribe((f) -> commit());
    }

    private void commit() {
        if (this.getCommit() == null) {
            return;
        }

        this.getCommit().accept(null);
    }

    private void getPinYinHeader(String text) {
        setText(text);
        /**
         * 文本改变couldCommit设置为true，表示可以提交新生成的拼音
         */
        couldCommit = true;
        DelayRunner.delayRunner(this.getTextField(), DelayRunner.NORMAL, () -> {

            String pinyi = PhoneticizeUtil.getFirstPinYinHeadChar(text, this.getPhoneticizeMaxLength());

            this.getPhoneticizeTextField().setText(pinyi);

        });

    }

    /**
     * @return the text
     */
    public String getText() {
        return textProperty().getValue();
    }

    /**
     * @return the text
     */
    public StringProperty textProperty() {
        if (text == null) {
            text = new SimpleStringProperty();
        }
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.textProperty().setValue(text);
    }

    /**
     * @return the phoneticize
     */
    public StringProperty phoneticizeProperty() {
        if (phoneticize == null) {
            phoneticize = new SimpleStringProperty();
        }
        return phoneticize;
    }

    /**
     * @param phoneticize the phoneticize to set
     */
    public void setPhoneticize(String phoneticize) {
        this.phoneticizeProperty().setValue(phoneticize);
    }

    /**
     * @return the phoneticize
     */
    public String getPhoneticize() {
        return phoneticizeProperty().getValue();
    }

    /**
     * @return the textField
     */
    public TextField getTextField() {
        if (textField == null) {
            this.textField = new TextField();
            HBox.setHgrow(textField, Priority.ALWAYS);
        }
        return textField;
    }

    /**
     * @param textField the textField to set
     */
    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    /**
     * @return the phoneticizeTextField
     */
    public TextField getPhoneticizeTextField() {
        if (phoneticizeTextField == null) {
            this.phoneticizeTextField = new TextField();
        }
        return phoneticizeTextField;
    }

    /**
     * @param phoneticizeTextField the phoneticizeTextField to set
     */
    public void setPhoneticizeTextField(TextField phoneticizeTextField) {
        this.phoneticizeTextField = phoneticizeTextField;
    }

    /**
     * 拼音码的最大长度
     *
     * @return the phoneticizeMaxLength
     */
    public Integer getPhoneticizeMaxLength() {
        return phoneticizeMaxLength;
    }

    /**
     * 拼音码的最大长度
     *
     * @param phoneticizeMaxLength the phoneticizeMaxLength to set
     */
    public void setPhoneticizeMaxLength(Integer phoneticizeMaxLength) {
        this.phoneticizeMaxLength = phoneticizeMaxLength;
    }

    /**
     * @return the commit
     */
    public Consumer getCommit() {
        return commit;
    }

    /**
     * @param commit the commit to set
     */
    public void setCommit(Consumer commit) {
        this.commit = commit;
    }
}
