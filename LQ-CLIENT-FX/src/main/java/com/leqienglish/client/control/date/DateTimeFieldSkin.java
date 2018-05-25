/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date;


import com.leqienglish.client.util.focus.IFocusNodes;
import com.leqienglish.util.date.DateTimeUtil;
import com.leqienglish.util.image.ImageRepertory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import jidefx.scene.control.decoration.DecorationPane;
import jidefx.scene.control.decoration.DecorationUtils;
import jidefx.scene.control.decoration.Decorator;

/**
 *
 * @author zhuqing
 */
public class DateTimeFieldSkin extends LQPopupFieldSkin<DateTimeField, DateTimeFieldBehavior> {

    /**
     * 日历面板
     */
    private DateTimePopupContent dateTimeContent;
    /**
     * 日历输入框
     */
    private DateTimeDecorateField dateTimeTextField;

    private Calendar year_end;

    private Calendar year_start;

    private boolean rebuid = false;

    private final ChangeListener<LocalDateTime> textFieldDataTimeChangeListener = new ChangeListener<LocalDateTime>() {
        @Override
        public void changed(ObservableValue<? extends LocalDateTime> observable, LocalDateTime oldValue, LocalDateTime newValue) {
            getSkinnable().setValue(newValue);
            newValue = reBuilderLocalDateTime(newValue);
            getSkinnable().setValue(newValue);
            if (getSkinnable().getCommitConsumer() != null) {
                getSkinnable().getCommitConsumer().accept(getSkinnable().getValue());
            }
        }
    };

    private final ChangeListener<LocalDateTime> hipCalendarPaneValueChangeListener = new ChangeListener<LocalDateTime>() {
        @Override
        public void changed(ObservableValue<? extends LocalDateTime> observable, LocalDateTime oldValue, LocalDateTime newValue) {
            getSkinnable().valueProperty().removeListener(valueWeakChangeListener);
            newValue = reBuilderLocalDateTime(newValue);
            updateDisplay(newValue);
            getSkinnable().setValue(newValue);
            dateTimeContent.setValue(newValue);
            getSkinnable().valueProperty().addListener(valueWeakChangeListener);

        }
    };

    private final ChangeListener<LocalDateTime> valueChangeListener = new ChangeListener<LocalDateTime>() {
        @Override
        public void changed(ObservableValue<? extends LocalDateTime> observable, LocalDateTime oldValue, LocalDateTime newValue) {

            if (dateTimeContent == null) {
                return;
            }
            newValue = reBuilderLocalDateTime(newValue);
            updateDisplay(newValue);
            dateTimeContent.setValue(newValue);

        }
    };

    private final ChangeListener<String> valueFormatChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (dateTimeContent == null) {
                return;
            }
        }
    };

    private final ChangeListener<String> displayFormatChangeListenr = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            updateDisplay(getSkinnable().getValue());
        }
    };

    private final ChangeListener<Boolean> isShowingChangeListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue != null && !newValue) {
                if (getSkinnable().getCommitConsumer() != null) {
                    getSkinnable().getCommitConsumer().accept(getSkinnable().getValue());
                }
            }
        }
    };

    private final ChangeListener<String> rebuildChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!Objects.equals(newValue, oldValue)) {
                rebuid = true;
                initSkin();
            }
        }
    };

    private final ChangeListener<Boolean> closePropertyListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                hidden();
            }
        }
    };

    private LocalDateTime reBuilderLocalDateTime(LocalDateTime dateTime) {
        LocalDateTime start = getSkinnable().getLimit_start_time();
        LocalDateTime end = getSkinnable().getLimit_end_time();
        if (start != null && dateTime != null && start.isAfter(dateTime)) {
            dateTime = start;
        }
        if (end != null && dateTime != null && end.isBefore(dateTime)) {
            dateTime = end;
        }
        return dateTime;
    }

    private final WeakChangeListener<String> displayFormatWeakChangeListenr = new WeakChangeListener<String>(displayFormatChangeListenr);
    private final WeakChangeListener<String> valueFormatWeakChangeListener = new WeakChangeListener<String>(valueFormatChangeListener);
    private final WeakChangeListener<LocalDateTime> hipCalendarPaneValueWeakChangeListener = new WeakChangeListener<LocalDateTime>(hipCalendarPaneValueChangeListener);
    private final WeakChangeListener<LocalDateTime> valueWeakChangeListener = new WeakChangeListener<LocalDateTime>(valueChangeListener);
    private final WeakChangeListener<String> rebuildSkinChangeListener = new WeakChangeListener<>(rebuildChangeListener);

    public DateTimeFieldSkin(DateTimeField control) {
        super(control, new DateTimeFieldBehavior(control));
    }

    @Override
    protected void initSkin() {
        super.initSkin();
        this.initListener();
        updateDisplay(getSkinnable().getValue());
    }

    @Override
    protected void showV(Region owner) {
        this.getLQPopup().showingProperty().removeListener(isShowingChangeListener);
        if (this.dateTimeContent.getValue() == null) {
            this.dateTimeContent.setValue(getSkinnable().reSetDate(getInitCalendarValue(null), getSkinnable().getValueResetFormat()));
        }
        super.showV(owner); //To change body of generated methods, choose Tools | Templates.
        this.getLQPopup().showingProperty().addListener(isShowingChangeListener);
    }

    private void initListener() {
        this.dateTimeContent.valueProperty().removeListener(hipCalendarPaneValueWeakChangeListener);
        this.dateTimeContent.valueProperty().addListener(hipCalendarPaneValueWeakChangeListener);

        this.getSkinnable().valueProperty().removeListener(valueWeakChangeListener);
        this.getSkinnable().valueProperty().addListener(valueWeakChangeListener);

        this.getSkinnable().rebuildProperty().removeListener(rebuildSkinChangeListener);
        this.getSkinnable().rebuildProperty().addListener(rebuildSkinChangeListener);

        this.getSkinnable().valueFormatProperty().removeListener(valueFormatWeakChangeListener);
        this.getSkinnable().valueFormatProperty().addListener(valueFormatWeakChangeListener);

        this.getSkinnable().displayFormatProperty().removeListener(displayFormatWeakChangeListenr);
        this.getSkinnable().displayFormatProperty().addListener(displayFormatWeakChangeListenr);

        this.getLQPopup().showingProperty().removeListener(isShowingChangeListener);
        this.getLQPopup().showingProperty().addListener(isShowingChangeListener);

        this.dateTimeContent.colseProperty().removeListener(closePropertyListener);
        this.dateTimeContent.colseProperty().addListener(closePropertyListener);

    }

    @Override
    protected void hidden() {
        super.hidden(); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * 更新时间文本域的显示
     *
     * @param value
     */
    private void updateDisplay(LocalDateTime value) {
        if (this.dateTimeTextField == null || this.dateTimeTextField.getTextField() == null) {
            return;
        }
        String timeStr = "";
        if (value != null) {
            timeStr = value.format(getDisplayDateTimeFormatter());
        }
        dateTimeTextField.getTextField().dateTimeProperty().removeListener(textFieldDataTimeChangeListener);
        this.dateTimeTextField.getTextField().setText(timeStr);
        this.dateTimeTextField.getTextField().setDateTime(value);
        dateTimeTextField.getTextField().dateTimeProperty().addListener(textFieldDataTimeChangeListener);

    }

    private DateTimeFormatter getDisplayDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(this.getSkinnable().getDisplayFormat());
    }

    @Override
    protected Region createPopupContent() {
        dateTimeContent = new DateTimePopupContent(this.getSkinnable().getValueFormat(), getSkinnable().getValue(), getYear_start(), getYear_end());
        return dateTimeContent;
    }

    @Override
    protected Region createPopupOwner() {
        if (!rebuid || this.dateTimeTextField == null) {
            this.dateTimeTextField = new DateTimeDecorateField(new DateTimeTextField());
            dateTimeTextField.getTextField().prefHeightProperty().bindBidirectional(getSkinnable().prefHeightProperty());
            dateTimeTextField.getTextField().minHeightProperty().bindBidirectional(getSkinnable().minHeightProperty());
            dateTimeTextField.getTextField().prefWidthProperty().bindBidirectional(getSkinnable().prefWidthProperty());
            dateTimeTextField.getTextField().promptTextProperty().bind(this.getSkinnable().promptTextProperty());
            dateTimeTextField.getTextField().dateFormateProperty().bind(this.getSkinnable().displayFormatProperty());
            dateTimeTextField.getTextField().dateTimeProperty().addListener(textFieldDataTimeChangeListener);
        }
        if (rebuid) {
            LocalDateTime dateTime = getSkinnable().reSetDate(getInitCalendarValue(DateTimeUtil.localTimeDateToCalender(dateTimeTextField.getTextField().getDateTime())), getSkinnable().getValueResetFormat());
            dateTimeTextField.getTextField().setDateTime(dateTime);
        }
        return dateTimeTextField;
    }

    @Override
    protected void showEventHandler() {
        if (dateTimeContent != null) {
            dateTimeContent.setVisible(true);
        }
    }

    @Override
    protected void hiddenEventHandler() {
        if (dateTimeContent != null) {
            dateTimeContent.setVisible(false);
        }
    }

    public Calendar getYear_start() {
        if (year_start != null && !rebuid) {
            return year_start;
        }
        year_start = Calendar.getInstance();
        LocalDateTime limit_start_time = getSkinnable().getLimit_start_time();
        if (limit_start_time != null) {
            year_start.set(limit_start_time.getYear(), limit_start_time.getMonthValue() - 1, limit_start_time.getDayOfMonth());
            return year_start;
        }
        year_start.set(Calendar.YEAR, 1900);
        return year_start;
    }

    public Calendar getYear_end() {
        if (year_end != null && !rebuid) {
            return year_end;
        }
        year_end = Calendar.getInstance();
        LocalDateTime limit_end_time = getSkinnable().getLimit_end_time();
        if (limit_end_time != null) {
            year_end.set(limit_end_time.getYear(), limit_end_time.getMonthValue() - 1, limit_end_time.getDayOfMonth());
            return year_end;
        }
        year_end.add(Calendar.YEAR, 20);
        return year_end;
    }

    private LocalDateTime getInitCalendarValue(Calendar curr) {
        if (curr == null) {
            curr = Calendar.getInstance();
        }

        if (curr.get(Calendar.YEAR) < getYear_start().get(Calendar.YEAR)) {
            curr = getYear_start();
        }
        if (curr.get(Calendar.YEAR) > getYear_end().get(Calendar.YEAR)) {
            curr = getYear_end();
        }

        if (curr.get(Calendar.YEAR) == getYear_start().get(Calendar.YEAR) && curr.get(Calendar.MONTH) < getYear_start().get(Calendar.MONTH)) {
            curr = getYear_start();
        }
        if (curr.get(Calendar.YEAR) == getYear_end().get(Calendar.YEAR) && curr.get(Calendar.MONTH) > getYear_end().get(Calendar.MONTH)) {
            curr = getYear_end();
        }

        if (curr.get(Calendar.YEAR) == getYear_start().get(Calendar.YEAR) && curr.get(Calendar.MONTH) == getYear_start().get(Calendar.MONTH) && curr.get(Calendar.DAY_OF_MONTH) < getYear_start().get(Calendar.DAY_OF_MONTH)) {
            curr = getYear_start();
        }
        if (curr.get(Calendar.YEAR) == getYear_end().get(Calendar.YEAR) && curr.get(Calendar.MONTH) == getYear_end().get(Calendar.MONTH) && curr.get(Calendar.DAY_OF_MONTH) > getYear_end().get(Calendar.DAY_OF_MONTH)) {
            curr = getYear_end();
        }
        return DateTimeUtil.toLocalDateTime(curr.getTime());
    }

    /**
     * 带有时间按钮的节点
     */
    public class DateTimeDecorateField extends DecorationPane implements IFocusNodes {

        /**
         * 节点
         */
        private DateTimeTextField textField;

        private Button button;

        public DateTimeDecorateField(DateTimeTextField textField) {
            super(textField);
            this.textField = textField;
            this.initHandler();
        }

        private void initHandler() {
            this.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        textField.requestFocus();
                        textField.selectAll();
                    }
                }
            });
            //回车事件
            this.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.DOWN) {
                        clickEventHandler(keyEvent);
                    }
                }
            });
        }

        @Override
        protected void initializeChildren() {
            textField = (DateTimeTextField) getContent();
            button = new Button();
            button.setGraphic(new ImageView(ImageRepertory.getImage("/img/control/pane/calendar/13.png")));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    clickEventHandler(event);
                }
            });
            button.getStyleClass().clear();
            DecorationUtils.install(getTextField(), new Decorator<>(button, Pos.CENTER_RIGHT, new Point2D(-106, 0)));
        }

        /**
         * 处理事件
         *
         * @param event
         */
        private void clickEventHandler(Event event) {
            showV(textField);
        }

        @Override
        protected void initializeStyle() {
            super.initializeStyle();
        }

        /**
         * @return the textField
         */
        public DateTimeTextField getTextField() {
            return textField;
        }

        @Override
        public List<Node> getFocusNode() {
            return Arrays.asList(this.textField);
        }

    }
}
