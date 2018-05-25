/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date;



import com.leqienglish.client.util.thread.DelayRunner;
import com.leqienglish.util.date.DateUtil;
import com.leqienglish.util.text.TextUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * 时间输入域
 *
 * @author zhuqing
 */
public class DateTimeTextField extends TextField {

    private final static String YEAR = "yyyy";

    private final static String MONTH = "MM";
    private final static String DAY = "dd";
    private final static String HOUR = "HH";
    private final static String MINUTE = "mm";
    private final static String SECOND = "ss";

    private int selectIndex = 0;
    /**
     * 日期格式
     */
    private StringProperty dateFormate;

    /**
     * 需要输入的格式的集合
     */
    private List<String> inputDatas;

    private String[] strs;

    /**
     * 当前时间
     */
    private ObjectProperty<LocalDateTime> dateTime;

    public DateTimeTextField() {
        init();
    }

    private void init() {
        inputDatas = createInputDatas();
        initListener();
//        registFocus();
    }

    private List<String> createInputDatas() {
        List<String> inputDatas = new ArrayList<>();
        if (this.getDateFormate() == null || this.getDateFormate().isEmpty()) {
            return inputDatas;
        }

        if (this.getDateFormate().contains(YEAR)) {
            inputDatas.add(YEAR);
        }
        if (this.getDateFormate().contains(MONTH)) {
            inputDatas.add(MONTH);
        }
        if (this.getDateFormate().contains(DAY)) {
            inputDatas.add(DAY);
        }
        if (this.getDateFormate().contains(HOUR)) {
            inputDatas.add(HOUR);
        }
        if (this.getDateFormate().contains(MINUTE)) {
            inputDatas.add(MINUTE);
        }
        if (this.getDateFormate().contains(SECOND)) {
            inputDatas.add(SECOND);
        }
        return inputDatas;
    }

    private void initListener() {
        this.dateFormateProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                inputDatas = createInputDatas();
            }
        }
        );

        this.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (!newValue) {
                    commitDate();
                } else {
                    selectIndex = 0;
                    DelayRunner.runLater(() -> select(DateTimeTextField.this.getText()), 500L);
                }
            }
        });

        this.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null || newValue.isEmpty()) {
                    setDateTime(null);
                }
            }
        });

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                updateSelectIndex(getCaretPosition());
                select(DateTimeTextField.this.getText());
            }
        });

        JavaFxObservable.eventsOf(this, KeyEvent.KEY_PRESSED)
                .filter((e) -> e.getCode() == KeyCode.ENTER)
                .subscribe((e) -> {
                    selectIndex++;
                    if (select(DateTimeTextField.this.getText())) {
                        e.consume();
                    }
                });

    }

    private boolean select(String date) {
        if (date == null || date.isEmpty()) {
            return false;
        }
        strs = TextUtil.splitDate(date);
        if (strs == null || strs.length == 0) {
            return true;
        }

        if (this.selectIndex >= strs.length) {
            return false;
        }

        String selected = strs[this.selectIndex];

        int start = TextUtil.textAllLength(strs, this.selectIndex) + selectIndex;
        DateTimeTextField.this.selectRange(start, start + selected.length());

        return true;

    }

    /**
     * 提交时间
     */
    private void commitDate() {
        if (getText().isEmpty()) {
            this.setDateTime(null);
            return;
        }
        double time = DateUtil.toDoubleTime(getText(), getDateFormate());

        this.setDateTime(DateUtil.toLocalDateTime(DateUtil.toDate(time)));

    }

    /**
     * 日期格式
     *
     * @return the dateFormate
     */
    public String getDateFormate() {
        return dateFormateProperty().getValue();
    }

    /**
     * 日期格式
     *
     * @return the dateFormate
     */
    public StringProperty dateFormateProperty() {
        if (dateFormate == null) {
            dateFormate = new SimpleStringProperty(DateUtil.YYYYMMDDHHMMSS);
        }
        return dateFormate;
    }

    /**
     * 日期格式
     *
     * @param dateFormate the dateFormate to set
     */
    public void setDateFormate(String dateFormate) {
        this.dateFormateProperty().setValue(dateFormate);
    }

    /**
     * 当前时间
     *
     * @return the dateTime
     */
    public LocalDateTime getDateTime() {
        return dateTimeProperty().getValue();
    }

    /**
     * 当前时间
     *
     * @return the dateTime
     */
    public ObjectProperty<LocalDateTime> dateTimeProperty() {
        if (dateTime == null) {
            dateTime = new SimpleObjectProperty<LocalDateTime>();
        }
        return dateTime;
    }

    /**
     * 当前时间
     *
     * @param dateTime the dateTime to set
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTimeProperty().setValue(dateTime);
    }

    private void updateSelectIndex(int index) {
        if (index == 0) {
            selectIndex = 0;
        } else {
            if (strs == null || strs.length == 0) {
                return;
            }
            int currIndex = 0;
            for (int i = 0; i < strs.length; i++) {
                currIndex++;
                currIndex += strs[i].length();
                if (index < currIndex) {
                    selectIndex = i;
                    break;
                }
            }
        }
    }
}
