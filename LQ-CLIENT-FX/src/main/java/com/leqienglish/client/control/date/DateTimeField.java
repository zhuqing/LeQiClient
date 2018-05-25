/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date;


import com.leqienglish.util.date.DateUtil;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.function.Consumer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author zhuqing
 */
public class DateTimeField extends Control {

    /**
     * 日期格式
     */
    private StringProperty valueFormat;

    /**
     * 显示的日期格式
     */
    private StringProperty displayFormat;
    /**
     * 日期值
     */
    private ObjectProperty<LocalDateTime> value;

    /**
     * 提示文字
     */
    private StringProperty promptText;

    /**
     * 值提交
     */
    private Consumer<LocalDateTime> commitConsumer;
    /**
     * 是否为当前日期的最大日期
     */
    private String valueResetFormat;

    /*
    *限定开始时间
     */
    private LocalDateTime limit_start_time;

    /**
     * 限定结束时间
     */
    private LocalDateTime limit_end_time;

    private StringProperty rebuild;

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DateTimeFieldSkin(this);
    }

    /**
     * @return the format
     */
    public String getValueFormat() {

        return valueFormatProperty().getValue();
    }

    public StringProperty valueFormatProperty() {
        if (valueFormat == null) {
            valueFormat = new SimpleStringProperty("yyyy-MM-dd");
        }
        return valueFormat;
    }

    /**
     * @param format the format to set
     */
    public void setValueFormat(String valueFormat) {
        this.valueFormatProperty().setValue(valueFormat);
    }

    /**
     * @return the value
     */
    public LocalDateTime getValue() {
        return valueProperty().getValue();
    }

    public ObjectProperty<LocalDateTime> valueProperty() {
        if (value == null) {
            this.value = new SimpleObjectProperty<LocalDateTime>();
        }
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(LocalDateTime value) {
        this.valueProperty().setValue(value);
    }

    /**
     * @return the promptText
     */
    public String getPromptText() {
        return promptTextProperty().getValue();
    }

    public StringProperty promptTextProperty() {
        if (promptText == null) {
            this.promptText = new SimpleStringProperty();
        }
        return promptText;
    }

    /**
     * @param promptText the promptText to set
     */
    public void setPromptText(String promptText) {
        this.promptTextProperty().setValue(promptText);
    }

    /**
     * @return the displayFormat
     */
    public String getDisplayFormat() {
        if (displayFormatProperty().getValue() == null) {
            return this.valueFormatProperty().getValue();
        }
        return displayFormatProperty().getValue();
    }

    /**
     * @return the displayFormat
     */
    public StringProperty displayFormatProperty() {
        if (displayFormat == null) {
            this.displayFormat = new SimpleStringProperty(DateUtil.YYYYMMDDHHMMSS);
        }
        return displayFormat;
    }

    /**
     * @param displayFormat the displayFormat to set
     */
    public void setDisplayFormat(String displayFormat) {
        this.displayFormatProperty().setValue(displayFormat);
    }

    public LocalDateTime reSetDate(LocalDateTime value, String valueResetFormat) {
        if (value == null || valueResetFormat == null || valueResetFormat.isEmpty()) {
            return value;
        }
        int[] calendarType = new int[]{Calendar.SECOND, Calendar.MINUTE, Calendar.HOUR_OF_DAY, Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR};
        Calendar clendar = DateUtil.toCalendar(DateUtil.toDateValue(value).getTime());

        String[] dateArr = valueResetFormat.split("-");
        for (int i = dateArr.length - 1; i >= 0; i--) {
            int num = Integer.valueOf(dateArr[i]);
            clendar.set(calendarType[dateArr.length - 1 - i], num);
        }

        return DateUtil.toLocalDateTime(clendar.getTime());
    }

    /**
     * 是否为当前日期的最大日期
     *
     * @return the valueResetFormat
     */
    public String getValueResetFormat() {

        return valueResetFormat;
    }

    /**
     * 是否为当前日期的最大日期
     *
     * @param valueResetFormat the valueResetFormat to set
     */
    public void setValueResetFormat(String valueResetFormat) {
        this.valueResetFormat = valueResetFormat;
    }

    /**
     * 值提交
     *
     * @return the commitConsumer
     */
    public Consumer<LocalDateTime> getCommitConsumer() {
        return commitConsumer;
    }

    /**
     * 值提交
     *
     * @param commitConsumer the commitConsumer to set
     */
    public void setCommitConsumer(Consumer<LocalDateTime> commitConsumer) {
        this.commitConsumer = commitConsumer;
    }

    public LocalDateTime getLimit_start_time() {
        return limit_start_time;
    }

    public void setLimit_start_time(LocalDateTime limit_start_time) {
        this.limit_start_time = limit_start_time;
    }

    public LocalDateTime getLimit_end_time() {
        return limit_end_time;
    }

    public void setLimit_end_time(LocalDateTime limit_end_time) {
        this.limit_end_time = limit_end_time;
    }

    public StringProperty rebuildProperty() {
        if (rebuild == null) {
            rebuild = new SimpleStringProperty();
        }
        return rebuild;
    }

    public String getRebuild() {
        return rebuildProperty().getValue();
    }

    public void setRebuild(String rebuild) {
        rebuildProperty().setValue(rebuild);
    }

}
