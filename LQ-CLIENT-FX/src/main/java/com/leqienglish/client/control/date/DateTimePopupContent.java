/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date;



import com.leqienglish.client.control.date.pick.CalendarPicker;
import com.leqienglish.client.control.date.pick.TimePicker;
import com.leqienglish.client.util.css.CssStyleLoadUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import jidefx.scene.control.field.popup.PopupContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yangzheng
 */
public class DateTimePopupContent extends HBox implements PopupContent<LocalDateTime> {

    /*默认时间格式，只到秒*/
    public static final String DEF_LOCAL_DATE_TIME_FMTSTR = "yyyy-MM-dd HH:mm:ss";
    /*日志*/
    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimePopupContent.class);
    private HBox root;
    /*时间控件*/
    private TimePicker timePicker;
    /**
     * 日历控件
     */
    private CalendarPicker calendarPicker;
    /**
     * 当前处理的日期类型
     */
    private IntegerProperty currentDateType;
    /**
     * 是否关闭
     */
    private BooleanProperty colse;
    /*选择的日期、时间值*/
    private final ObjectProperty<LocalDateTime> value = new SimpleObjectProperty<LocalDateTime>();//艺术

    /*DateTimePopupContent实例集合*/
    private static Map<String, DateTimePopupContent> instance = new HashMap<String, DateTimePopupContent>();

    /**
     * 限定日期开始间隔 示例： 5年 、3月 、5天
     */
    private Calendar year_start;

    private Calendar year_end;

    public static DateTimePopupContent getInstance(String format, LocalDateTime value) {
        if (instance.get(format) == null) {
            synchronized (DateTimePopupContent.class) {
                if (instance.get(format) == null) {
                    instance.put(format, new DateTimePopupContent(format, value));
                }
            }
        }
        return instance.get(format);
    }

    /**
     * 构造
     *
     * @param format
     * @param value
     */
    public DateTimePopupContent(String format, LocalDateTime value) {
        this(format, value, null, null);
    }

    public DateTimePopupContent(String format, LocalDateTime value, Calendar year_start, Calendar year_end) {
        this.year_start = year_start;
        this.year_end = year_end;
        this.value.setValue(value);
        this.root = new HBox();
        try {
            this.getStylesheets().add(CssStyleLoadUtil.getInstance().getUserCssStyle());
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DateTimePopupContent.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.isShowCalendar(format) && this.isShowTimer(format)) {
            calendarPicker = createCalendarPicker();
            initPickValue(calendarPicker, format, value);
            calendarPicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        calendarPicker.getSelectDay().requestFocus();
                    }
                }
            });

            timePicker = createTimerPicker();
            timePicker.setShowCalendar(true);

            initPickValue(timePicker, format, value);
            registEnterEventHandler(calendarPicker, () -> {
                timePicker.getSelectHour().requestFocus();
            });

            root.getChildren().addAll(calendarPicker, timePicker);
        } else if (this.isShowCalendar(format)) {
            //日期控件初始化
            calendarPicker = createCalendarPicker();
            initPickValue(calendarPicker, format, value);
            root.getChildren().add(calendarPicker);
        } else if (this.isShowTimer(format)) {
            //时间控件初始化
            timePicker = createTimerPicker();
            timePicker.setShowCalendar(false);
            initPickValue(timePicker, format, value);
            root.getChildren().add(timePicker);
        }
        addListener();
        getChildren().add(root);
    }

    public void addListener() {
        this.visibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (calendarPicker != null) {
                    calendarPicker.setVisible(newValue);
                }

                if (timePicker != null) {
                    timePicker.setVisible(newValue);

                }
            }
        });
        if (timePicker != null) {
            timePicker.finishedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        setColse(Boolean.FALSE);
                        setColse(newValue);

                    }
                }
            });
        }

        if (calendarPicker != null) {
            calendarPicker.finishedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (timePicker == null && newValue) {
                        setColse(Boolean.FALSE);
                        setColse(newValue);
                    }
                }
            });
        }
    }

    /**
     * 初始化picker的值
     *
     * @param timePicker
     * @param format
     * @param value
     */
    private void initPickValue(TimePicker timePicker, String format, LocalDateTime value) {
        Date dt = localDateTimeToDate(value);
        Calendar cldr = null;
        if (dt == null) {
            cldr = null;
        } else {
            cldr = Calendar.getInstance();
            cldr.setTime(dt);
        }

        timePicker.setValue(cldr);
        timePicker.setFormat(format);
    }

    /**
     * 创建日历
     *
     * @return
     */
    private CalendarPicker createCalendarPicker() {
        CalendarPicker calendar_Picker = new CalendarPicker();
        calendar_Picker.setYear_start(this.year_start);
        calendar_Picker.setYear_end(this.year_end);
        calendar_Picker.valueProperty().addListener(dateChangeListener);
        return calendar_Picker;
    }

    /**
     * 创建事件
     *
     * @return
     */
    private TimePicker createTimerPicker() {
        TimePicker timePicker = new TimePicker("TimePicker");
        timePicker.setYear_start(this.year_start);
        timePicker.setYear_end(this.year_end);
        timePicker.valueProperty().addListener(timeChangeListener);
        return timePicker;
    }

    /**
     * 判断是否显示日历
     *
     * @param format
     * @return
     */
    private final Boolean isShowCalendar(String format) {
        if (format == null) {
            return false;
        }
        if (format.contains("-") || format.contains("dd")) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否显示timer
     *
     * @param format
     * @return
     */
    private final Boolean isShowTimer(String format) {
        if (format.contains(":")) {
            return true;
        }
        return false;
    }

    /**
     * 值属性
     *
     * @return
     */
    @Override
    public ObservableValue<LocalDateTime> valueProperty() {
        return value;
    }

    /**
     * 取值
     *
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime getValue() {
        return value.getValue();
    }

    /**
     * 设置值
     *
     * @param value
     */
    @Override
    public void setValue(LocalDateTime value) {
        /**
         * 设置LocalDateTime值
         */
        this.value.setValue(value);
        /**
         * 转换或默认值
         */
        Date dt = localDateTimeToDate(value);
        Calendar cldr = null;

        if (dt != null) {
            cldr = Calendar.getInstance();
            cldr.setTime(dt);
        }

        /**
         * 设置日期控件值
         */
        //calendarPicker.setCalendar(cldr);
        if (this.timePicker != null) {
            /**
             * 设置时间控件值
             */
            timePicker.setValue(cldr);
        }

        if (this.calendarPicker != null) {
            calendarPicker.setValue(cldr);
        }

    }
    /**
     * 日期控件取值变化响应
     */
    private ChangeListener dateChangeListener = new ChangeListener<Calendar>() {
        @Override
        public void changed(ObservableValue<? extends Calendar> observable, Calendar oldValue, Calendar newValue) {
            if (newValue != null) {
                LocalDateTime ldt = DateToLocalDateTime(newValue.getTime());
                ldt = LocalDateTime.of(ldt.toLocalDate(), value.getValue().toLocalTime());
                setCurrentDateType(calendarPicker.getCurrentDateType());
                value.setValue(ldt);

                if (timePicker != null) {
                    Calendar cldr = Calendar.getInstance();
                    cldr.setTime(localDateTimeToDate(ldt));
                    timePicker.setValue(cldr);

                }
            } else {
                value.setValue(null);
            }
        }

    };

    /**
     * 时间控件取值变化响应
     */
    private ChangeListener timeChangeListener = new ChangeListener<Calendar>() {
        @Override
        public void changed(ObservableValue<? extends Calendar> observable, Calendar oldValue, Calendar newValue) {
            if (newValue != null) {
                LocalDateTime ldt = DateToLocalDateTime(newValue.getTime());
                ldt = LocalDateTime.of(value.getValue().toLocalDate(), ldt.toLocalTime());
                setCurrentDateType(timePicker.getCurrentDateType());
                value.setValue(ldt);

            }
        }

    };

    /**
     * LocalDateTime转Date
     *
     * @param ldt
     * @return Date
     */
    private Date localDateTimeToDate(LocalDateTime ldt) {
        if (ldt == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DEF_LOCAL_DATE_TIME_FMTSTR);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DEF_LOCAL_DATE_TIME_FMTSTR);
        Date dt = null;
        try {
            dt = sdf.parse(ldt.format(dtf));
        } catch (ParseException ex) {
            LOGGER.error(null, ex);
        }
        return dt;
    }

    /**
     * Date转LocalDateTime
     *
     * @param dt
     * @return
     */
    private LocalDateTime DateToLocalDateTime(Date dt) {
        if (dt == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DEF_LOCAL_DATE_TIME_FMTSTR);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DEF_LOCAL_DATE_TIME_FMTSTR);
        return LocalDateTime.parse(sdf.format(dt), dtf);
    }

    /**
     * 当前处理的日期类型
     *
     * @return the currentDateType
     */
    public Integer getCurrentDateType() {
        return currentDateTypeProperty().getValue();
    }

    /**
     * 当前处理的日期类型
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
     * 当前处理的日期类型
     *
     * @param currentDateType the currentDateType to set
     */
    public void setCurrentDateType(Integer currentDateType) {
        this.currentDateTypeProperty().setValue(currentDateType);
    }

    /**
     * 是否关闭
     *
     * @return the colse
     */
    public Boolean isColse() {
        return colseProperty().getValue();
    }

    /**
     * 是否关闭
     *
     * @return the colse
     */
    public BooleanProperty colseProperty() {
        if (colse == null) {
            colse = new SimpleBooleanProperty(Boolean.FALSE);
        }
        return colse;
    }

    /**
     * 是否关闭
     *
     * @param colse the colse to set
     */
    public void setColse(Boolean colse) {
        colseProperty().setValue(colse);
    }

    private void registEnterEventHandler(Node node, Runnable cimmit) {
        node.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> {
            if (e.getCode() != KeyCode.ENTER) {
                return;
            }
            if (cimmit != null) {
                cimmit.run();
            }
        });
    }
}
