/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date.pick;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;

/**
 * 通过使用这个日期选择组件，可使选择的日期自定义的格式化并返回给调用者 此日期选择组件，默认获得此 Java<br>
 * 虚拟机实例的当前默认语言环境值：Locale.getDefault()<br>
 * 注意的是在没有传入一个TextField对象时不能调用showCalendar()方法<br>
 * 将选择的日期格式化，请设置dateFormat属性<br>
 * 默认值通过设置 setCalendar(Calendar value)设置<br>
 * <b>示例：</b>
 * TextField dateField=new TextField();//与日历选择组件的绑定的TextField final<br>
 * CalendarPicker cPicker = new CalendarPicker();//构造一个日期选中组件<br>
 * cPicker.setTextfield(dateField);//将dateField设置到cPicker的属性中 <br>
 * Calendar calendar= Calendar.getInstance();//创建一个Java.uitl.Calendar类<br>
 * DateFormat dateformat=new<br>
 * SimpleDateFormat("yyyy-MM-dd");//将选中的日期，以某种格式，进行格式化<br>
 * calendar.set(Calendar.YEAR, 2013); <br>
 * calendar.set(Calendar.MONTH, 11);<br>
 * cPicker.setCalendar(calendar);//设置默认日期<br>
 * cPicker.setDateFormat(dateformat);//将选中的日期，以某种格式，进行格式化<br>
 * //鼠标点击TextField时弹出日历选择组件 dateField.setOnMouseClicked(new<br>
 * EventHandler(MouseEvent)(){<br>
 * Override<br>
 * public void handle(MouseEvent t) { cPicker.showCalendar();//调用日期选择组件 <br>}
 *
 * @author weipengwei
 * @version 2013-07-05 });
 */
public class CalendarPicker<T> extends TimePicker {

    /**
     * 此日期选择组件，获得此 Java 虚拟机实例的当前默认语言环境值：Locale.getDefault()
     */
    volatile private ObjectProperty<Locale> localeObjectProperty = new SimpleObjectProperty<>(this, "locale", Locale.getDefault());
    /**
     * 最大年份,默认是明年，也就是当前年份加1
     */
    private ObjectProperty<Integer> yearEnd = new SimpleObjectProperty<>(this, "yearEnd", Calendar.getInstance().get(Calendar.YEAR) + 10);
    /**
     * 使用者要传入一个textfield，此组件返回的Calendar。要给textfield传入。
     */
    private TextField textfield = null;
    /**
     * 此属性只是给年份的选择上 规定 开始和结束 yearStart，yearEnd
     */
    private ObjectProperty<Integer> yearStart;

    private IntegerProperty currentDateType;
    /**
     * format字符串
     */
    private String format;

    /**
     * 无参的构造函数
     */
    public CalendarPicker() {
        construct();
    }

    /**
     *
     * 加载css样式，初始化数据
     */
    private void construct() {
        // 添加样式并设置Skin类
        this.getStyleClass().add("CalendarPicker");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CalendarPickerSkin(this);
    }

    /**
     * 返回对应的CSS文件路径
     *
     * @return 对应的CSS文件路径
     */
    @Override
    public String getUserAgentStylesheet() {
        return super.getUserAgentStylesheet();
    }

    /**
     * Locale: 根据不同的Locale确定不同的文字 labels, etc 星期 文本
     */
    public ObjectProperty<Locale> localeProperty() {
        return localeObjectProperty;
    }

    /**
     * 获取当前的语言环境
     *
     * @return Locale
     */
    public Locale getLocale() {
        return localeObjectProperty.getValue();
    }

    /**
     * 当前语言环境
     *
     * @param value Locale 当前语言环境
     */
    public void setLocale(Locale value) {
        localeObjectProperty.setValue(value);
    }

    /**
     * 用于把时间格式化
     *
     * @param value Calendar
     * @param dateFormat 格式化
     * @return Calendar被格式化的后的String
     */
    public static String quickFormatCalendar(Calendar value, DateFormat dateFormat) {
        if (value == null || dateFormat == null) {
            return "null";
        }

        // SimpleDateFormat lSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG);
        //lSimpleDateFormat.applyPattern(dateFormat);//原格式化方法
        return value == null ? "null" : dateFormat.format(value.getTime());
    }

    public TextField getTextfield() {
        return textfield;
    }

    public void setTextfield(TextField textfield) {
        this.textfield = textfield;
    }

    /**
     * 格式化类
     *
     * @return 格式化类
     */
    public String getFormat() {
        return this.format;
    }

    /**
     * 设置格式化类
     *
     * @param format 设置格式化类
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 此属性只是给年份的选择上 规定 开始和结束 yearStart，yearEnd
     *
     * @return 开始时间
     */
    public ObjectProperty<Integer> yearStartProperty() {

        return this.yearStart;
    }

    public int getYearStart() {
        return getYear_start().get(Calendar.YEAR);
    }

    /**
     * 此属性只是给年份的选择上 规定 开始和结束 yearStart，yearEnd
     *
     * @param startyear 默认是1958
     */
    public void setYearStart(int startyear) {
        this.yearStart.setValue(startyear);
    }

    /**
     * 最大年份
     *
     * @return 最大年份
     */
    public ObjectProperty<Integer> yearEndProperty() {
        return this.yearEnd;
    }

    /**
     * 返回最大年份
     *
     * @return 返回最大年份
     */
    public int getYearEnd() {
        return getYear_end().get(Calendar.YEAR);
    }

    /**
     * 此属性只是给年份的选择上 规定 开始和结束 yearStart，yearEnd
     *
     * @param setYearEnd 结束年份
     */
    public void setYearEnd(int setYearEnd) {
        this.yearEnd.setValue(setYearEnd);
    }

    /**
     * @return the currentDateType
     */
    public Integer getCurrentDateType() {
        return currentDateTypeProperty().getValue();
    }

    /**
     * @return the currentDateType
     */
    public IntegerProperty currentDateTypeProperty() {
        if (currentDateType == null) {
            currentDateType = new SimpleIntegerProperty();
        }
        return currentDateType;
    }

    /**
     * @param currentDateType the currentDateType to set
     */
    public void setCurrentDateType(Integer currentDateType) {
        this.currentDateTypeProperty().setValue(currentDateType);
    }
}
