/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date;

import java.time.LocalTime;
import java.util.function.Consumer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 * @author zhuqing
 */
public final class LQDateTimePickerContent extends HBox {

    private final ChangeListener<LocalTime> localTimeChangeListener = new ChangeListener<LocalTime>() {
        @Override
        public void changed(ObservableValue<? extends LocalTime> observable, LocalTime oldValue, LocalTime newValue) {
            updateTime(newValue);
        }
    };

    private Consumer<ActionEvent> submitActionConsumer;

    private Consumer<ActionEvent> cacelActionConsumer;
    private Consumer<ActionEvent> todayActionConsumer;

    private ObjectProperty<LocalTime> localTime;

    /**
     * 小时输入框
     */
    private TextField hourTextField;
    /**
     * 分钟输入框
     */
    private TextField minuteTextField;
    /**
     * 秒输入框
     */
    private TextField secondTextField;
    /**
     * 今天按钮
     */
    private Button todayButton;
    /**
     * 确认按钮
     */
    private Button submit;
    /**
     * 取消按钮
     */
    private Button cancel;

    public LQDateTimePickerContent() {
        Label label = new Label("");
        HBox.setHgrow(label, Priority.ALWAYS);

        this.getChildren().addAll(getTimeBox(), label,
                this.getTodayButton(), this.getCancel(), this.getSubmit());
        this.localTimeProperty().addListener(localTimeChangeListener);
        this.setSpacing(10);

    }

    private HBox getTimeBox() {
        HBox hbox = new HBox();
        hbox.getChildren().addAll(this.getHourTextField(),
                new Label(":"), this.getMinuteTextField(),
                new Label(":"), this.getSecondTextField());
        return hbox;
    }

    public void updateTime(LocalTime localTime) {
        if (localTime == null) {
            this.getHourTextField().setText("");
            this.getMinuteTextField().setText("");
            this.getSecondTextField().setText("");

        } else {
            this.getHourTextField().setText(getString(localTime.getHour()));
            this.getMinuteTextField().setText(getString(localTime.getMinute()));
            this.getSecondTextField().setText(getString(localTime.getSecond()));
        }

    }

    private String getString(int number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return number + "";
        }
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the localTime
     */
    public LocalTime getLocalTime() {

        return localTimeProperty().getValue();
    }

    /**
     * @return the localTime
     */
    public ObjectProperty<LocalTime> localTimeProperty() {
        if (localTime == null) {
            localTime = new SimpleObjectProperty<LocalTime>(LocalTime.now());
        }
        return localTime;
    }

    /**
     * @param localTime the localTime to set
     */
    public void setLocalTime(LocalTime localTime) {
        this.localTimeProperty().setValue(localTime);
    }

    /**
     * @return the hourTextField
     */
    public TextField getHourTextField() {
        if (this.hourTextField == null) {
            this.hourTextField = new TextField();
            this.hourTextField.setMaxWidth(30);
        }
        return hourTextField;
    }

    /**
     * @return the minuteTextField
     */
    public TextField getMinuteTextField() {
        if (this.minuteTextField == null) {
            this.minuteTextField = new TextField();
            this.minuteTextField.setMaxWidth(30);
        }
        return minuteTextField;
    }

    /**
     * @return the secondTextField
     */
    public TextField getSecondTextField() {
        if (this.secondTextField == null) {
            this.secondTextField = new TextField();
            this.secondTextField.setMaxWidth(30);
        }
        return secondTextField;
    }

    /**
     * @return the todayButton
     */
    public Button getTodayButton() {
        if (this.todayButton == null) {
            this.todayButton = new Button("今天");
            this.todayButton.setOnAction(e -> {
                resetLocalTime();
                if (getTodayActionConsumer() != null) {
                    getTodayActionConsumer().accept(e);
                }
            });
        }
        return todayButton;
    }

    /**
     * @return the submit
     */
    public Button getSubmit() {
        if (this.submit == null) {
            this.submit = new Button("确认");
            this.submit.setOnAction(e -> {
                resetLocalTime();
                if (getSubmitActionConsumer() != null) {
                    getSubmitActionConsumer().accept(e);
                }
            });
        }
        return submit;
    }

    private void resetLocalTime() {
        Integer hour = Integer.valueOf(getHourTextField().getText());
        Integer minute = Integer.valueOf(this.getMinuteTextField().getText());
        Integer second = Integer.valueOf(this.getSecondTextField().getText());
        this.setLocalTime(LocalTime.of(hour, minute, second));
    }

    /**
     * @return the cancel
     */
    public Button getCancel() {
        if (this.cancel == null) {
            this.cancel = new Button("取消");
            this.cancel.setOnAction(e -> {
                if (getCacelActionConsumer() != null) {
                    getCacelActionConsumer().accept(e);
                }
            });
        }
        return cancel;
    }

    public Consumer<ActionEvent> getSubmitActionConsumer() {
        return submitActionConsumer;
    }

    public void setSubmitActionConsumer(Consumer<ActionEvent> submitActionConsumer) {
        this.submitActionConsumer = submitActionConsumer;
    }

    public Consumer<ActionEvent> getCacelActionConsumer() {
        return cacelActionConsumer;
    }

    public void setCacelActionConsumer(Consumer<ActionEvent> cacelActionConsumer) {
        this.cacelActionConsumer = cacelActionConsumer;
    }

    public Consumer<ActionEvent> getTodayActionConsumer() {
        return todayActionConsumer;
    }

    public void setTodayActionConsumer(Consumer<ActionEvent> todayActionConsumer) {
        this.todayActionConsumer = todayActionConsumer;
    }

}
