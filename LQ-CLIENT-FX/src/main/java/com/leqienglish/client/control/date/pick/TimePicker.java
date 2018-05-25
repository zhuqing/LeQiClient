/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date.pick;

import java.util.Calendar;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 时间面板: 一般用在由小时和分钟组成的时候可以用时间面板来修改和录入， 时间面板有获取当前时间，可以作为想录入的参考时间，有时会和日期面板结合在一起录入，
 * 记录一下哪年哪月哪日的几点几分，这里的几点几分就可以用时间面板。
 * </p>
 *
 * @author niuben
 * @version 2013-6-28
 */
public class TimePicker extends Control {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TimePicker.class);

    /**
     * 操作是否完成
     */
    private BooleanProperty finished;

    /**
     * 是否显示日历
     */
    private Boolean showCalendar = false;
    /**
     * 选择的日期、时间
     */
    private final ObjectProperty<Calendar> value = new SimpleObjectProperty<>(this, "value", Calendar.getInstance());

    private String format;

    /**
     * 当前提交的数据类型
     */
    private IntegerProperty currentDateType;

    private Node selectDay;

    private Node selectMin;

    private Node selectHour;

    /**
     * 限定开始日期
     */
    private Calendar year_start;
    /**
     * 限定结束日期
     */
    private Calendar year_end;

    public ObservableValue<Calendar> valueProperty() {
        return value;
    }

    public Calendar getValue() {
        return value.getValue();
    }

    public void setValue(Calendar cal) {
        this.value.setValue(cal);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 构造方法
     *
     * @param timePicker 时间显示格式
     */
    public TimePicker(String timePicker) {
        // getStyleClass().add(TimePicker.class.getSimpleName());
        // this.format = format;
        this.getStyleClass().add(timePicker);
    }

    public TimePicker() {
        // this.getStyleClass().add("TimePicker");
        // getStyleClass().add(TimePicker.class.getSimpleName());
    }

    @Override
    protected Skin<?> createDefaultSkin() {

        return new TimePickerSkin(this);
    }

    /**
     * 重写getUserAgentStylesheet方法 加载css文件
     *
     * @return URL
     */
    @Override
    public String getUserAgentStylesheet() {
        return super.getUserAgentStylesheet(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 当前提交的数据类型
     *
     * @return the currentDateType
     */
    public Integer getCurrentDateType() {
        return currentDateTypeProperty().getValue();
    }

    /**
     * 当前提交的数据类型
     *
     * @return the currentDateType
     */
    public IntegerProperty currentDateTypeProperty() {
        if (currentDateType == null) {
            currentDateType = new SimpleIntegerProperty();
        }
        return currentDateType;
    }

    /**
     * 当前提交的数据类型
     *
     * @param currentDateType the currentDateType to set
     */
    public void setCurrentDateType(Integer currentDateType) {
        this.currentDateTypeProperty().setValue(currentDateType);
    }

    /**
     * 操作是否完成
     *
     * @return the finished
     */
    public Boolean getFinished() {
        return finishedProperty().getValue();
    }

    /**
     * 操作是否完成
     *
     * @return the finished
     */
    public BooleanProperty finishedProperty() {
        if (finished == null) {
            finished = new SimpleBooleanProperty();
        }
        return finished;
    }

    /**
     * 操作是否完成
     *
     * @param finished the finished to set
     */
    public void setFinished(Boolean finished) {
        this.finishedProperty().setValue(false);
        this.finishedProperty().setValue(finished);
    }

    /**
     * 是否显示日历
     *
     * @return the showCalendar
     */
    public Boolean isShowCalendar() {

        return showCalendar;
    }

    /**
     * 是否显示日历
     *
     * @param showCalendar the showCalendar to set
     */
    public void setShowCalendar(Boolean showCalendar) {
        this.showCalendar = showCalendar;
    }

    public Node getSelectDay() {
        if (selectDay == null) {
            selectDay = new Label();
        }
        return selectDay;
    }

    public void setSelectDay(Node selectDay) {
        this.selectDay = selectDay;
    }

    public Node getSelectMin() {
        return selectMin;
    }

    public void setSelectMin(Node selectMin) {
        this.selectMin = selectMin;
    }

    public Node getSelectHour() {
        if (selectHour == null) {
            selectHour = new Label();
        }
        return selectHour;
    }

    public void setSelectHour(Node selectHour) {
        this.selectHour = selectHour;
    }

    public Calendar getYear_start() {
        return year_start;
    }

    public void setYear_start(Calendar year_start) {
        this.year_start = year_start;
    }

    public Calendar getYear_end() {
        return year_end;
    }

    public void setYear_end(Calendar year_end) {
        this.year_end = year_end;
    }
}
