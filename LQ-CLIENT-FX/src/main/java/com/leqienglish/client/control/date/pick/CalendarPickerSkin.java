/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date.pick;


import com.leqienglish.client.control.buttonpane.ToggleButtonPane;
import com.leqienglish.client.control.buttonpane.ToggleButtonPaneBuilder;
import com.leqienglish.client.control.date.listspinner.ListSpinner;
import com.leqienglish.client.control.date.listspinner.ListSpinnerIntegerList;
import com.leqienglish.client.control.skin.LQSkin;
import com.leqienglish.client.util.local.LocalUtil;
import com.leqienglish.client.util.lock.LockUtil;
import com.leqienglish.util.date.DateUtil;
import com.sun.javafx.scene.control.behavior.BehaviorBase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import jdk.nashorn.internal.runtime.JSType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author weipengwei
 */
public class CalendarPickerSkin<T> extends LQSkin<CalendarPicker<T>, BehaviorBase<CalendarPicker<T>>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarPickerSkin.class);

    /**
     * 整个布局面板
     */
    private BorderPane borderPane;
    /**
     * 保存选择的
     */
    private ObjectProperty<Calendar> displayedCalendar;

    private CalendarPane calendarPane;

    private YearAndMonthBox yearAndMonthBox;

    private YearAndMonthPanel yearPanel;

    private YearAndMonthPanel monthPanel;

    private BorderPane monthBPn;

    public Calendar getDisplayedCalendar() {
        return displayedCalendar.getValue();
    }

    public ObjectProperty<Calendar> displayedCalendarProperty() {
        return displayedCalendar;
    }

    /**
     * 构造
     *
     * @param control CalendarPicker
     */
    public CalendarPickerSkin(CalendarPicker control) {
        super(control, new CalendarPickerBehavior<>(control));
    }

    /**
     *
     * @param value
     */
    public void setDisplayedCalendar(Calendar value) {
        Calendar lValue = getDisplayedCalendar();

        // we need to explicitly break out if the YMD are equals (because we generate new Calendars which is because Calendars are mutable - bah), else we end up in an endless loop
        if (value != null && lValue != null
                && value.get(Calendar.YEAR) == lValue.get(Calendar.YEAR)
                && value.get(Calendar.MONTH) == lValue.get(Calendar.MONTH)
                && value.get(Calendar.DATE) == lValue.get(Calendar.DATE)) {
            return;
        }

        // set value
        displayedCalendar.setValue(derriveDisplayedCalendar(value));
    }

    private Calendar derriveDisplayedCalendar(Calendar lDisplayedCalendar) {
        // done
        if (lDisplayedCalendar == null) {
            return null;
        }

        // always the 1st of the month, without time
        Calendar lCalendar = Calendar.getInstance(getSkinnable().getLocale());
        lCalendar.set(Calendar.DAY_OF_MONTH, lDisplayedCalendar.get(Calendar.DAY_OF_MONTH));//1
        lCalendar.set(Calendar.MONTH, lDisplayedCalendar.get(Calendar.MONTH));
        lCalendar.set(Calendar.YEAR, lDisplayedCalendar.get(Calendar.YEAR));

        lCalendar.set(Calendar.HOUR_OF_DAY, 0);
        lCalendar.set(Calendar.MINUTE, 0);
        lCalendar.set(Calendar.SECOND, 0);
        lCalendar.set(Calendar.MILLISECOND, 0);

        // done
        return lCalendar;
    }

    @Override
    protected void showSkin() {
        if (getSkinnable().getFormat().contains("d")) {
            calendarPane = new CalendarPane();
        } else {
            yearAndMonthBox = new YearAndMonthBox();
        }

        getSkinnable().valueProperty().addListener(new ChangeListener<Calendar>() {
            @Override
            public void changed(ObservableValue<? extends Calendar> ov, Calendar t, Calendar t1) {
                if (t1 == null) {
                    return;
                }

                if (LockUtil.isLocked(t1)) {
                    return;
                }

                setDisplayedCalendar(t1);
                if (calendarPane != null) {
                    calendarPane.getYearListSpinner().setValue(t1.get(Calendar.YEAR));
                    calendarPane.getMonthListSpinner().setValue(t1.get(Calendar.MONTH) + "");
                }

            }
        });
        displayedCalendarProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                if (getSkinnable().getFormat().contains("d")) {
                    calendarPane.refreshSpinner();
                    calendarPane.refreshDayButtonSet();
                    calendarPane.refreshDayButton();
                }

            }
        });

        getChildren().add(borderPane);
        if (getSkinnable().getFormat().contains("d")) {
            calendarPane.refreshDayButton();
        }
    }

    @Override
    protected void initSkin() {
        super.initSkin();
        borderPane = new BorderPane();
        displayedCalendar = new SimpleObjectProperty<Calendar>(this, "displayedCalendar");
        setDisplayedCalendar(getSkinnable().getValue());

    }

    public class CalendarPane extends GridPane {

        /**
         * 存放ButtonSet集合
         */
        private List<ToggleButtonPane.ButtonSet> lists;
        /**
         * ToggleButtonPane选择板
         */
        private ToggleButtonPane buttonPane = new ToggleButtonPane();
        /**
         * 月份选择组件
         */
        private ListSpinner<String> monthListSpinner;
        /**
         * 年份的选择组件
         */
        private ListSpinner<Integer> yearListSpinner;
        /**
         * buttonPane返回的所有Button
         */
        private ObservableList<ToggleButton> dayButtons;
//       /**
//        * 用于记忆，最后一个选中的日期
//        */
//       private Calendar iLastSelected;
        /**
         * 今天按钮的创建
         */
        private Button todaybtn;
        /**
         * 用于添加年、月、今天组件的面板
         */
        private GridPane lGridPane;
        /**
         * 用于添加周信息
         */
        private GridPane weekdayLabelGridPane;
        /**
         * 用于添加lGridPane、weekdayLabelGridPane、buttonPane面板
         */
        private GridPane baseGridPane;

        /**
         * 创建日期选择的布局 这不布局是Border 加 Gridpane的布局方式 年月，星期标志，是添加到BroderPane的Top 天
         * 是一个Gridpane添加到BorderPane的Center
         */
        public CalendarPane() {

            ///此方法主要是创建弹出面板中显示的日期选择控件
            lGridPane = new GridPane();
            baseGridPane = new GridPane();
            baseGridPane.getStyleClass().add("baseGridPane");
            baseGridPane.add(lGridPane, 0, 0);

            //创建年信息面板      
            lGridPane.add(createYearListSpinner(), 0, 0, 3, 1); // col, row, hspan, vspan
            lGridPane.add(createMonthListSpinner(), 3, 0, 3, 1); // col, row, hspan, vspan 
            //创建Totady按钮
            lGridPane.add(createTodayButton(), 6, 0);
            //创建周信息
            baseGridPane.add(getWeekdayLabels(), 0, 1);
            //创建“天”按钮组
            baseGridPane.add(createDayDate(), 0, 2);

            borderPane.setCenter(baseGridPane);
            // 至此所有的控件添加完成。
            borderPane.getStyleClass().add("calendar-backcolor");

        }

        private ListSpinner<Integer> createYearListSpinner() {
            // year spinner
            yearListSpinner = new ListSpinner<>(new ListSpinnerIntegerList(getSkinnable().getYearStart(), getSkinnable().getYearEnd())).withValue(Calendar.getInstance().get(Calendar.YEAR)).withCyclic(Boolean.TRUE);
            yearListSpinner.setEditable(Boolean.TRUE);
            yearListSpinner.getStyleClass().add("yearListSpinner");
            yearListSpinner.setselectArrowType(YearAndMonthPanel.Type.YEAR);//2013-6-9
            yearListSpinner.setYearStart(getSkinnable().getYearStart());
            yearListSpinner.setYearEnd(getSkinnable().getYearEnd());
            yearListSpinner.setArrowPosition(ListSpinner.ArrowPosition.SPLIT);
            yearListSpinner.setPrefWidth(120);
            yearListSpinner.setStringConverter(new StringConverter<Integer>() {
                @Override
                public String toString(Integer t) {
                    return t.toString();
                }

                @Override
                public Integer fromString(String string) {
                    return Integer.parseInt(string);
                }
            });
            if (getSkinnable().getValue() != null) {
                yearListSpinner.setValue(getSkinnable().getValue().get(Calendar.YEAR));
            }
            // 如果yearListSpinner的value发生改变，刷新
            yearListSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
                @Override
                public void changed(ObservableValue observableValue, Integer oldValue, Integer newValue) {
                    Calendar cal = getSkinnable().getValue();
                    if (LockUtil.isLocked(cal.get(Calendar.YEAR))) {
                        return;
                    }
                    lGridPane.add(createMonthListSpinner(), 3, 0, 3, 1); // col, row, hspan, vspan 
                    saveNewValue(Calendar.YEAR);//新加

                }
            });
            lGridPane.getStyleClass().add("top_gridpane");
            return yearListSpinner;
        }

        private ListSpinner<String> createMonthListSpinner() {
            // month spinner
            List<String> lMonthLabels = getMonthLabels();
            monthListSpinner = new ListSpinner<>(lMonthLabels).withIndex(Calendar.getInstance().get(Calendar.MONTH)).withCyclic(Boolean.TRUE);
            monthListSpinner.getStyleClass().add("monthListSpinner");
            monthListSpinner.setEditable(Boolean.TRUE);
            if (getYearListSpinner() == null) {
            } else {
                int lYear = getYearListSpinner().getValue();
                Calendar limitStartCalendar = getSkinnable().getYear_start();
                Calendar limitEndCalendar = getSkinnable().getYear_end();
                if (lYear == limitStartCalendar.get(Calendar.YEAR) && lYear == limitEndCalendar.get(Calendar.YEAR)) {
                    monthListSpinner.setMonthStart(limitStartCalendar.get(Calendar.MONTH));
                    monthListSpinner.setMonthEnd(limitEndCalendar.get(Calendar.MONTH));
                } else if (lYear == limitStartCalendar.get(Calendar.YEAR)) {
                    monthListSpinner.setMonthStart(limitStartCalendar.get(Calendar.MONTH));
                    monthListSpinner.setMonthEnd(0);
                } else if (lYear == limitEndCalendar.get(Calendar.YEAR)) {
                    monthListSpinner.setMonthStart(0);
                    monthListSpinner.setMonthEnd(limitEndCalendar.get(Calendar.MONTH));
                }
            }
            monthListSpinner.setArrowDirection(ListSpinner.ArrowDirection.HORIZONTAL);
            monthListSpinner.setArrowPosition(ListSpinner.ArrowPosition.SPLIT);
            monthListSpinner.setLocale(getSkinnable().getLocale());
            monthListSpinner.setStringConverter(new StringConverter<String>() {
                @Override
                public String toString(String t) {
                    return t;
                }

                @Override
                public String fromString(String string) {
                    String value = "";
                    if (monthListSpinner.getItems().contains(string)) {
                        return string;
                    }
                    if (JSType.isNumber(string)) {
                        Integer index = JSType.toInteger(string);
                        if (index > 0 && index < 13) {
                            value = monthListSpinner.getItems().get(index - 1);
                        }
                    }
                    return value;
                }
            });

            if (getSkinnable().getValue() != null) {
                monthListSpinner.setValue(getMonthLabels().get(getSkinnable().getValue().get(Calendar.MONTH)));
            }
            //循环事件
            monthListSpinner.setOnCycle(new EventHandler<ListSpinner.CycleEvent>() {
                @Override
                public void handle(ListSpinner.CycleEvent evt) {
                    // if we've cycled down
                    if (evt.cycledDown()) {
                        getMonthListSpinner().first();
                        getYearListSpinner().increment();
                    } else {
                        getMonthListSpinner().last();
                        getYearListSpinner().decrement();
                    }
                }
            });
            //         如果montlistspinner的value改变，就重新刷新
            monthListSpinner.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue arg0, String arg1, String arg2) {
                    Calendar cal = getSkinnable().getValue();
                    if (LockUtil.isLocked(cal.get(Calendar.MONTH))) {
                        return;
                    }
                    saveNewValue(Calendar.MONTH);//新加
                }
            });
            return monthListSpinner;
        }

        private Button createTodayButton() {
            todaybtn = new Button();
            getTodaybtn().setId("todaybtn");
            getTodaybtn().getStyleClass().add("today-button");
            getTodaybtn().setText("今天");
            Calendar cal = Calendar.getInstance();
            if (cal.get(Calendar.YEAR) < getSkinnable().getYear_start().get(Calendar.YEAR)) {
                todaybtn.setDisable(true);
            }
            if (cal.get(Calendar.YEAR) == getSkinnable().getYear_start().get(Calendar.YEAR) && cal.get(Calendar.MONTH) < getSkinnable().getYear_start().get(Calendar.MONTH)) {
                todaybtn.setDisable(true);
            }
            if (cal.get(Calendar.YEAR) == getSkinnable().getYear_start().get(Calendar.YEAR) && cal.get(Calendar.MONTH) == getSkinnable().getYear_start().get(Calendar.MONTH) && cal.get(Calendar.DAY_OF_MONTH) < getSkinnable().getYear_start().get(Calendar.DAY_OF_MONTH)) {
                todaybtn.setDisable(true);
            }
            if (cal.get(Calendar.YEAR) > getSkinnable().getYear_end().get(Calendar.YEAR)) {
                todaybtn.setDisable(true);
            }
            if (cal.get(Calendar.YEAR) == getSkinnable().getYear_end().get(Calendar.YEAR) && cal.get(Calendar.MONTH) > getSkinnable().getYear_end().get(Calendar.MONTH)) {
                todaybtn.setDisable(true);
            }
            if (cal.get(Calendar.YEAR) == getSkinnable().getYear_end().get(Calendar.YEAR) && cal.get(Calendar.MONTH) == getSkinnable().getYear_end().get(Calendar.MONTH) && cal.get(Calendar.DAY_OF_MONTH) > getSkinnable().getYear_end().get(Calendar.DAY_OF_MONTH)) {
                todaybtn.setDisable(true);
            }

            getTodaybtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {

                    LockUtil.lock(cal.get(Calendar.YEAR));
                    LockUtil.lock(cal.get(Calendar.MONTH));
                    getSkinnable().setValue(cal);
                    LockUtil.unLock(cal.get(Calendar.YEAR));
                    LockUtil.unLock(cal.get(Calendar.MONTH));
                }
            });
            return getTodaybtn();
        }

        /**
         * 创建Day的选择板
         */
        private ToggleButtonPane createDayDate() {
            List<ToggleButtonPane.ButtonSet> buttons = initDayDate();
            ToggleButtonPaneBuilder buttonPaneBuilder = new ToggleButtonPaneBuilder();
            int lDaysInMonth = determineDaysInMonth();
            buttonPane = buttonPaneBuilder.buttonSetList(buttons)
                    .buttonType(new ToggleButton())
                    .cellHight(25d)
                    .cellWidth(35d)
                    .buttonEventHander(new EventHandler() {
                        @Override
                        public void handle(Event t) {
                            buttonPane.setToggleGroup(new ToggleGroup());
                            getDayButtons().clear();
                            getDayButtons().addAll(buttonPane.getButtonObservableList());
                            refreshDayButton();
                            ToggleButton lToggleButton = (ToggleButton) t.getSource();
                            toggle(lToggleButton);

                        }
                    })
                    .build();
            buttonPane.setShowEnd(lDaysInMonth);
            buttonPane.getButtonObservableList().addListener((ListChangeListener.Change<? extends ToggleButton> c) -> {
                getDayButtons().setAll(buttonPane.getButtonObservableList());
                if (buttonPane.getButtonObservableList().isEmpty()) {
                    return;
                }
                refreshDayButton();
            });

            buttonPane.getBtnStyleClass().add("toggle-button");

            return buttonPane;
        }

        /**
         * 初始化ButtonSet 集合
         *
         * @return List<ToggleButtonPane.ButtonSet> ButtonSet集合
         */
        private List<ToggleButtonPane.ButtonSet> initDayDate() {
            lists = new ArrayList<>();
            dayButtons = FXCollections.observableArrayList();
            int lFirstOfMonthIdx = determineFirstOfMonthDayOfWeek();
            Calendar calendar = (Calendar) getDisplayedCalendar().clone();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            int limit_month_start = getSkinnable().getYear_start().get(Calendar.MONTH);
            int limit_month_end = getSkinnable().getYear_end().get(Calendar.MONTH);
            int limit_day_start = getSkinnable().getYear_start().get(Calendar.DATE);
            int limit_day_end = getSkinnable().getYear_end().get(Calendar.DATE);

            int day = 1;
            for (int i = lFirstOfMonthIdx; i < 6 * 7; i++) {
                // 创建Button
                ToggleButtonPane.ButtonSet buttonSet = new ToggleButtonPane().new ButtonSet();
                //设置属性
                calendar.set(Calendar.DATE, day);
                //buttonSet.setStyleid("day-button");
                buttonSet.setColumnId(i % 7);    // col, row
                buttonSet.setRowId((i / 7));
                buttonSet.setButtonId((i + 1) + "");
                buttonSet.setRowSpan(1);
                buttonSet.setColSpan(1);
                buttonSet.setButtonValue(day + "");

                if (year == getSkinnable().getYearStart() && month == limit_month_start) {
                    if (day < limit_day_start) {
                        buttonSet.setDisable(true);
                    }
                }
                if (year == getSkinnable().getYearEnd() && month == limit_month_end) {
                    if (day > limit_day_end) {
                        buttonSet.setDisable(true);
                    }
                }

                lists.add(buttonSet);
                //只创建31个ButtonSet
                if (day == 31) {
                    break;
                }
                day++;
            }

            return lists;
        }

        /**
         * 被选中的日期值
         *
         * @param toggleButton 被选择的时间
         */
        private void toggle(ToggleButton toggleButton) {
            getSkinnable().setCurrentDateType(Calendar.DATE);
            Calendar lToggledCalendar = (Calendar) getDisplayedCalendar().clone();
            lToggledCalendar.set(Calendar.YEAR, getDisplayedCalendar().get(Calendar.YEAR));
            lToggledCalendar.set(Calendar.MONTH, getDisplayedCalendar().get(Calendar.MONTH));
            lToggledCalendar.set(Calendar.DATE, Integer.parseInt(toggleButton.getText()));
            getSkinnable().setValue(lToggledCalendar);
            getSkinnable().setFinished(true);

        }

        //todo:当用户切换年份，月份时同样进行数据保存。
        private void saveNewValue(int dateField) {

            getSkinnable().setCurrentDateType(dateField);

            // 获得 spinner values
            int lYear = getYearListSpinner().getValue();
            int lMonth = getMonthListSpinner().getIndex();
            //得到新值

            Calendar lCalendar = (Calendar) getDisplayedCalendar().clone();

            lCalendar.set(Calendar.YEAR, lYear);

            int yearStart = getSkinnable().getYearStart();
            int yearEnd = getSkinnable().getYearEnd();

            int limit_month_start = getSkinnable().getYear_start().get(Calendar.MONTH);
            int limit_month_end = getSkinnable().getYear_end().get(Calendar.MONTH);

            int limit_day_start = getSkinnable().getYear_start().get(Calendar.DATE);

            if (lYear == yearStart && lMonth < limit_month_start) {
                lMonth = limit_month_start;
            }
            if (lYear == yearEnd && lMonth > limit_month_end) {
                lMonth = limit_month_end;
            }

            lCalendar.set(Calendar.MONTH, lMonth);

            if (lYear == getSkinnable().getYearStart() && lMonth == limit_month_start) {
                lCalendar.set(Calendar.DAY_OF_MONTH, limit_day_start);
            } else {
                lCalendar.set(Calendar.DAY_OF_MONTH, 1);
            }
            Calendar lToggledCalendar = (Calendar) lCalendar.clone();
            getSkinnable().setValue(lToggledCalendar);
        }

        /**
         * 给选择的按钮添加样式
         */
        private void refreshDayButton() {
            Calendar lCalendar = (Calendar) getDisplayedCalendar().clone();

            String currentDay = getSkinnable().getValue().get(Calendar.DAY_OF_MONTH) + "";
            for (int i = 0; i < getDayButtons().size(); i++) {
                ToggleButton toggleButton = getDayButtons().get(i);
                lCalendar.set(Calendar.DAY_OF_MONTH, i + 1);
                toggleButton.setSelected(false);
                if (Objects.equals(currentDay, toggleButton.getText())) {
                    toggleButton.setSelected(true);
                    getSkinnable().setSelectDay(toggleButton);
                }

                toggleButton.getStyleClass().remove("today");
                if (isToday(lCalendar)) {
                    toggleButton.getStyleClass().add("today");
                }
            }
        }

        /**
         * 判断传入的日期是否是今天
         */
        protected boolean isToday(Calendar calendar) {
            return DateUtil.isToday(calendar);
        }

        /**
         * 刷新ButtonSet集合
         */
        private void refreshDayButtonSet() {
            int lFirstOfMonthIdx = determineFirstOfMonthDayOfWeek();//月的第一天是周几
            int lDaysInMonth = determineDaysInMonth();//这个月有多少天
            Calendar lCalendar = (Calendar) getDisplayedCalendar().clone();
            int day = 0;

            int year = lCalendar.get(Calendar.YEAR);
            int month = lCalendar.get(Calendar.MONTH);

            int limit_month_start = getSkinnable().getYear_start().get(Calendar.MONTH);
            int limit_month_end = getSkinnable().getYear_end().get(Calendar.MONTH);
            int limit_day_start = getSkinnable().getYear_start().get(Calendar.DATE);
            int limit_day_end = getSkinnable().getYear_end().get(Calendar.DATE);

            for (int i = lFirstOfMonthIdx; i < 6 * 7; i++) {
                // 创建Button
                ToggleButtonPane.ButtonSet buttonSet = lists.get(day);
                buttonSet.setColumnId(i % 7);    // col
                buttonSet.setRowId((i / 7));     //row
                buttonSet.setStyleid("");
                buttonSet.setDisable(false);
                if (!buttonSet.getButtonValue().isEmpty()) {
                    int yearStart = getSkinnable().getYearStart();
                    if (year < yearStart) {
                        buttonSet.setDisable(true);
                    } else if (year == yearStart && month < limit_month_start) {
                        buttonSet.setDisable(true);
                    } else if (year == yearStart && month == limit_month_start && Integer.parseInt(buttonSet.getButtonValue()) < limit_day_start) {
                        buttonSet.setDisable(true);
                    }

                    int yearEnd = getSkinnable().getYearEnd();
                    if (year > yearEnd) {
                        buttonSet.setDisable(true);
                    } else if (year == yearEnd && month > limit_month_end) {
                        buttonSet.setDisable(true);
                    } else if (year == yearEnd && month == limit_month_end && Integer.parseInt(buttonSet.getButtonValue()) > limit_day_end) {
                        buttonSet.setDisable(true);
                    }
                }
//                lCalendar.set(Calendar.DATE, Integer.parseInt(buttonSet.getButtonValue()));
                lists.set(day, buttonSet);
                if ((day + 1) == lDaysInMonth) {
                    break;
                }
                day++;
            }
            buttonPane.setButtonSetList(lists);
            buttonPane.show(1, lDaysInMonth);
        }

        /*
         * 刷新Spinner的值
         */
        private void refreshSpinner() {
            // 得到DisplayedCalendar
            Calendar lCalendar = (Calendar) getDisplayedCalendar();
            //得到月的值集合
            if (getMonthListSpinner() != null) {
                List<String> lMonthLabels = getMonthLabels();
                String lMonthLabel = lMonthLabels.get(lCalendar.get(Calendar.MONTH));
                getMonthListSpinner().setValue(lMonthLabel);
            }
            // set value
            getYearListSpinner().setValue(lCalendar.get(Calendar.YEAR));
        }

        /**
         * 创建一 二 三。。。。日的Label，并放在GridPane中
         */
        protected GridPane getWeekdayLabels() {
            weekdayLabelGridPane = new GridPane();

            weekdayLabelGridPane.getStyleClass().add("weekdayLabel_GridPane");

            Calendar lCalendar = new java.util.GregorianCalendar(2009, 5, 1); // july 5th 2009 is a Sunday
            for (int i = 1; i < 8; i++) {
                Label label = new Label("");
                label.getStyleClass().add("weekday-label");
                // 添加到lGridPane中
                weekdayLabelGridPane.add(label, i - 1, 0);
                lCalendar.set(java.util.Calendar.DATE, i);//2013-6-17
                label.getStyleClass().add(isWeekdayWeekend(lCalendar) ? "weekend" : "weekday");

                try {
                    label.setText(LocalUtil.getLiteral(lCalendar.getTime().toString().substring(0, 3)));
                } catch (IOException ex) {
                    LOGGER.error(ex.toString(), ex);
                }

            }

            return weekdayLabelGridPane;

        }

        /**
         * 获得12个月份的集合
         *
         * @return 获得12个月份的集合
         */
        protected List<String> getMonthLabels() {
            // result
            List<String> lMonthLabels = new ArrayList<String>();
            Calendar lCalendar = new java.util.GregorianCalendar(2009, 0, 1);

            for (int i = 0; i < 12; i++) {
                // next
                lCalendar.set(java.util.Calendar.MONTH, i);
                try {
                    lMonthLabels.add(LocalUtil.getLiteral(lCalendar.getTime().toString().substring(4, 7).trim()));
                } catch (IOException ex) {
                    LOGGER.error("国际化出错", ex);
                }
            }
            // done
            return lMonthLabels;
        }

        protected boolean isWeekdayWeekend(Calendar calendar) {
            int idx = DateUtil.getWeekDay(calendar);
            return (idx == 6 || idx == 7);
        }

        /**
         * 判断这个月的第一天是周几
         */
        protected int determineFirstOfMonthDayOfWeek() {
            int lFirstOfMonthIdx = DateUtil.getFirstWeekDayOfMonth(getDisplayedCalendar()) - 1;
            return lFirstOfMonthIdx;
        }

        /**
         * 判断当前月有多少天
         */
        protected int determineDaysInMonth() {
            // determine the number of days in the month
            Calendar lCalendar = (Calendar) getDisplayedCalendar().clone();
            lCalendar.add(java.util.Calendar.MONTH, 1);
            lCalendar.set(java.util.Calendar.DATE, 1);
            lCalendar.add(java.util.Calendar.DATE, -1);
            return lCalendar.get(java.util.Calendar.DAY_OF_MONTH);
        }

        /**
         * @return the monthListSpinner
         */
        public ListSpinner<String> getMonthListSpinner() {
            return monthListSpinner;
        }

        /**
         * @return the yearListSpinner
         */
        public ListSpinner<Integer> getYearListSpinner() {
            return yearListSpinner;
        }

        /**
         * buttonPane返回的所有Button
         *
         * @return the dayButtons
         */
        public ObservableList<ToggleButton> getDayButtons() {
            return dayButtons;
        }

        /**
         * 今天按钮的创建
         *
         * @return the todaybtn
         */
        public Button getTodaybtn() {
            return todaybtn;
        }

        /**
         * 今天按钮的创建
         *
         * @return the todaybtn
         */
        public ToggleButtonPane getButtonPane() {
            return this.buttonPane;
        }
    }

    public class YearAndMonthBox extends HBox {

        /**
         * 只显示年、月组件信息时的布局
         */
        /**
         * 创建年、月信息面板
         */
        public YearAndMonthBox() {
            BorderPane yearBPn = new BorderPane();
            Label yearLabel = new Label("年份");
            yearLabel.getStyleClass().add("");
            yearBPn.setTop(yearLabel);

            yearPanel = new YearAndMonthPanel();
            yearPanel.setType(YearAndMonthPanel.Type.YEAR);
            yearPanel.setVgap(3);
            yearPanel.setLocale(getSkinnable().getLocale());//如果是 month的画，此属性用于根据本地化初始化数据
            //如果是年份的根据年份的开始--结束
            yearPanel.setIndex(getSkinnable().getValue().get(Calendar.YEAR) - getSkinnable().getYearStart());
            yearPanel.setYearEnd(getSkinnable().getYearEnd());
            yearPanel.setYearStart(getSkinnable().getYearStart());
            //yearPanel.setSelectValue(getSkinnable().getDefaultValue().get(Calendar.YEAR));
            yearPanel.setColumnSize(2);
            yearPanel.setRowSize(10);

            yearPanel.selectValueProperty.addListener(new ChangeListener<T>() {
                @Override
                public void changed(ObservableValue<? extends T> ov, T t, T t1) {
                    if (t1 == null) {
                        return;
                    }
                    monthPanel = rebuilderMonthPanel(monthPanel);
                    monthBPn.setCenter(monthPanel);
                    setDisplayedCalendarYearAndMonth(Calendar.YEAR);
                }
            });
            yearBPn.setCenter(yearPanel);
            this.getChildren().add(yearBPn);
            if (getSkinnable().getFormat().contains("M")) {
                monthBPn = new BorderPane();
                Label monthLable = new Label("月份");
                monthLable.getStyleClass().add("");
                monthBPn.setTop(monthLable);
                monthPanel = rebuilderMonthPanel(monthPanel);
                monthBPn.setCenter(monthPanel);
                this.getChildren().add(monthBPn);
            }
            borderPane.setCenter(this);
        }

        private YearAndMonthPanel rebuilderMonthPanel(YearAndMonthPanel monthPanel) {
            monthPanel = new YearAndMonthPanel();
            monthPanel.setType(YearAndMonthPanel.Type.MONTH);
            monthPanel.setVgap(2);
            monthPanel.setLocale(getSkinnable().getLocale());//如果是 month的画，此属性用于根据本地化初始化数据
            monthPanel.setColumnSize(2);
            monthPanel.setRowSize(10);
            monthPanel.setIndex(getSkinnable().getValue().get(Calendar.MONTH));
            monthPanel.setFormat(getSkinnable().getFormat());
            // 获得 spinner values
            if (yearPanel.getSelectValue() == null) {
                yearPanel.setSelectValue(getSkinnable().getValue().get(Calendar.YEAR));
            }
            int lYear = (int) yearPanel.getSelectValue();
            Calendar limitStartCalendar = getSkinnable().getYear_start();
            Calendar limitEndCalendar = getSkinnable().getYear_end();
            if (lYear == limitStartCalendar.get(Calendar.YEAR) && lYear == limitEndCalendar.get(Calendar.YEAR)) {
                monthPanel.setMonthStart(limitStartCalendar.get(Calendar.MONTH));
                monthPanel.setMonthEnd(limitEndCalendar.get(Calendar.MONTH));
            } else if (lYear == limitStartCalendar.get(Calendar.YEAR)) {
                monthPanel.setMonthStart(limitStartCalendar.get(Calendar.MONTH));
                monthPanel.setMonthEnd(0);
            } else if (lYear == limitEndCalendar.get(Calendar.YEAR)) {
                monthPanel.setMonthStart(0);
                monthPanel.setMonthEnd(limitEndCalendar.get(Calendar.MONTH));
            }
            //monthPanel.setSelectValue(Calendar.getInstance().get(Calendar.MONTH));
            monthPanel.selectValueProperty.addListener(new ChangeListener<T>() {
                @Override
                public void changed(ObservableValue<? extends T> ov, T t, T t1) {
                    //monthPanel.setIndex();
                    setDisplayedCalendarYearAndMonth(Calendar.MONTH);
                }
            });
            return monthPanel;
        }

        /*
         * 重新刷新month spinner 和yearspinner的值。并更新DisplayedCalendar
         */
        private void setDisplayedCalendarYearAndMonth(int dataField) {

            // 获得 spinner values
            if (yearPanel.getSelectValue() == null) {
                yearPanel.setSelectValue(getSkinnable().getValue().get(Calendar.YEAR));
            }
            int lYear = (int) yearPanel.getSelectValue();
            //得到新值
            Calendar lCalendar = (Calendar) getDisplayedCalendar().clone();
            lCalendar.set(Calendar.YEAR, lYear);

            if (getSkinnable().getFormat().contains("M")) {
                if (monthPanel.getSelectValue() == null) {
                    monthPanel.setSelectValue(Calendar.getInstance().get(Calendar.MONTH));
                }
                int lMonth = (int) monthPanel.getSelectValue();
                lCalendar.set(Calendar.MONTH, lMonth);
            }
            getSkinnable().setValue(lCalendar);

            //设置DisplayedCalendar，并刷新
            setDisplayedCalendar(lCalendar);
            getSkinnable().setCurrentDateType(dataField);

        }

    }
}
