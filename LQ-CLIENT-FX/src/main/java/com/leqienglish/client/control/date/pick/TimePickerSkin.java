/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date.pick;



import com.leqienglish.client.control.buttonpane.ButtonPane.ButtonSet;
import com.leqienglish.client.control.buttonpane.ToggleButtonPane;
import com.leqienglish.client.control.buttonpane.ToggleButtonPaneBuilder;
import com.leqienglish.client.control.skin.LQSkin;
import com.leqienglish.client.util.lock.LockUtil;
import com.leqienglish.client.util.textinput.TextInputControlUtil;
import com.leqienglish.util.date.DateUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间面板的皮肤类
 *
 * @author niuben
 * @version 2013-6-28
 */
public final class TimePickerSkin extends LQSkin<TimePicker, TimePickerBehavior> {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TimePickerSkin.class);
    /**
     * 时间面板的布局面板
     */
    private BorderPane timeBPn;

    /**
     * 日历工具类
     */
    private final Calendar calendar = Calendar.getInstance();
//    /**
//     * ButtonPane构建工具
//     */
//    private ToggleButtonPaneBuilder buttonPaneBuilder;

    private ToggleButtonPane hourBtnPn;

    private ToggleButtonPane minuteBtnPn;

    private Label label;

    private TextField hhText;

    private TextField mmText;

    private TextField ssText;

    private HBox timeBox;

    private final String HOURS = "hours";

    /**
     * 初始化同步日历变量工具
     */
    {
        if (getSkinnable().getValue() != null) {
            calendar.setTime(getSkinnable().getValue().getTime());
        }

    }

    /**
     * 构造方法
     *
     * @param c
     */
    public TimePickerSkin(TimePicker c) {
        super(c, new TimePickerBehavior(c));
    }

    @Override
    protected void showSkin() {
        getChildren().add(timeBPn);
    }

    @Override
    protected void initSkin() {
        super.initSkin();
        timeBPn = new BorderPane();
//        buttonPaneBuilder = new ToggleButtonPaneBuilder();
        //初始化界面顶部的时间显示
        if (getSkinnable().getValue() != null) {
            initTimeTip(getSkinnable().getValue().getTime());
        }

        //初始化界面中部的时间按钮面板
        HBox centerHBx = new HBox();

        String format = getSkinnable().getFormat();
        if (format.contains("H")) {
            initHoursPn(centerHBx);
        }
        if (format.contains("m")) {
            initMinutePn(centerHBx);
        }

        centerHBx.getStyleClass().add("TimePicker-CenterHBox");
        timeBPn.setCenter(centerHBx);

        //时间面板控件date属性改变监听
        getSkinnable().valueProperty().addListener(new ChangeListener<Calendar>() {
            @Override
            public void changed(ObservableValue<? extends Calendar> observable, Calendar oldValue, Calendar newValue) {
                if (newValue == null) {
                    return;
                }
                if (calendar.getTimeInMillis() != newValue.getTimeInMillis()) {
                    calendar.setTime(newValue.getTime());
                }

                initTimeTip(newValue.getTime());
                updateSelectHourAndMinu(newValue);
            }
        });
    }

    /**
     * 当前时间显示(布局顶端)
     *
     * @param date 需要显示的时间
     */
    private void initTimeTip(Date date) {

        if (LockUtil.isLocked(this)) {
            return;
        }
        String timeString = "";
        if (this.getSkinnable().isShowCalendar()) {
            timeString = new SimpleDateFormat(DateUtil.YYYYMMDD).format(date);
        }
        getLabel().setText("当前时间：" + timeString + " ");
        getTimeBox().getChildren().clear();
        getTimeBox().getChildren().add(getLabel());
        if (getSkinnable().getFormat().contains("hh") || getSkinnable().getFormat().contains("HH")) {

            getHhText().setText(date.getHours() + "");
            registLoseFocus(getHhText(), Calendar.HOUR_OF_DAY);
            registEnterEventHandler(getHhText(), () -> {
                if (getTimeBox().getChildren().contains(getMmText())) {
                    getMmText().requestFocus();
                }
            });
            TextInputControlUtil.focusAndSelectAll(getHhText());
            TextInputControlUtil.mouseClickAndSelectAll(getHhText());
            getHhText().setMaxWidth(30);
            getTimeBox().getChildren().addAll(getHhText());
        }

        if (getSkinnable().getFormat().contains("mm")) {
            getMmText().setText(date.getMinutes() + "");
            getMmText().setMaxWidth(30);
            TextInputControlUtil.focusAndSelectAll(getMmText());
            TextInputControlUtil.mouseClickAndSelectAll(getMmText());

            registLoseFocus(getMmText(), Calendar.MINUTE);
            registEnterEventHandler(getMmText(), () -> {
                if (getTimeBox().getChildren().contains(getSsText())) {
                    getSsText().requestFocus();
                }
            });
            getTimeBox().getChildren().addAll(createSpliteLable(":"), getMmText());
        }

        if (getSkinnable().getFormat().contains("ss")) {

            getSsText().setMaxWidth(30);
            TextInputControlUtil.focusAndSelectAll(getSsText());
            TextInputControlUtil.mouseClickAndSelectAll(getSsText());
            getSsText().setText(date.getSeconds() + "");

            registLoseFocus(getSsText(), Calendar.SECOND);
            registEnterEventHandler(getSsText(), () -> {
                getSkinnable().setFinished(true);
            });
            getTimeBox().getChildren().addAll(createSpliteLable(":"), getSsText());
        }

        timeBPn.setTop(getTimeBox());

    }

    private void registLoseFocus(TextField textField, int type) {
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (newValue) {
                    return;
                }
                LockUtil.lock(this);
                modifyTime(textField.getText(), type);
                LockUtil.unLock(this);

            }

        });
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

    private Label createSpliteLable(String split) {
        Label s = new Label(split);
        s.setMaxWidth(10);
        return s;
    }

    /**
     * 时钟面板构建
     *
     * @param centerHBx BorderPane中center部分的布局
     */
    private void initHoursPn(HBox centerHBx) {
        BorderPane hourBPn = new BorderPane();
        Label lable = createLable("小时", "orderPane_top_labe_hour", hourBPn.widthProperty());
        hourBPn.setTop(lable);

        String[] horesContents = buildBtnContents(24, 1);
        List<ButtonSet> horesListBs = buildBtnSetList(horesContents, 4, 6, "buttonTime");
        refreshButtonEditable(horesListBs, HOURS);
        hourBtnPn = buildButtonPane(new ToggleButtonPaneBuilder(), horesListBs, Calendar.HOUR_OF_DAY, "hourPane");
        hourBPn.setCenter(hourBtnPn);

        hourBPn.getStyleClass().add("TimePicker-CenterHour");
        centerHBx.getChildren().add(hourBPn);

    }

    /**
     * 分钟面板构建
     *
     * @param centerHBx BorderPane中center部分的布局
     */
    private void initMinutePn(HBox centerHBx) {

        BorderPane minuteBPn = new BorderPane();
        Label lable = createLable("分钟", "orderPane_top_labe_minute", minuteBPn.widthProperty());
        minuteBPn.setTop(lable);
        String[] minuteContents = buildBtnContents(12, 5);
        List<ButtonSet> minuteListBs = buildBtnSetList(minuteContents, 2, 6, "buttonTime");
        refreshButtonEditable(minuteListBs, "");
        minuteBtnPn = buildButtonPane(new ToggleButtonPaneBuilder(), minuteListBs, Calendar.MINUTE, "minutePane");
        minuteBtnPn.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (getSkinnable().getSelectMin() == null) {
                        minuteBtnPn.getButtonObservableList().get(0).requestFocus();
                    } else {
                        getSkinnable().getSelectMin().requestFocus();
                    }
                }
            }
        });
        minuteBPn.setCenter(minuteBtnPn);

        minuteBPn.getStyleClass().add("TimePicker-CenterMinute");
        centerHBx.getChildren().add(minuteBPn);

    }

    private void updateSelectHourAndMinu(Calendar newValue) {

        List<ToggleButton> minBtn = minuteBtnPn.getButtonObservableList();
        refreshButtonEditable(minBtn, "");
        getSkinnable().setSelectMin(null);
//        ToggleGroup group =  new ToggleGroup();
        for (int i = 0; i < minBtn.size(); i++) {
            ToggleButton toggleButton = minBtn.get(i);
            toggleButton.setSelected(false);
//            toggleButton.setToggleGroup(group);
            if (Integer.parseInt(toggleButton.getText()) == newValue.get(Calendar.MINUTE)) {
                getSkinnable().setSelectMin(toggleButton);
                toggleButton.setSelected(true);
            }
        }
//        ToggleGroup hourgroup =  new ToggleGroup();
        List<ToggleButton> hoursBtn = hourBtnPn.getButtonObservableList();
        refreshButtonEditable(hoursBtn, HOURS);
        for (int i = 0; i < hoursBtn.size(); i++) {
            ToggleButton toggleButton = hoursBtn.get(i);
//            toggleButton.setToggleGroup(hourgroup);
            if (Integer.parseInt(toggleButton.getText()) == newValue.get(Calendar.HOUR_OF_DAY)) {
                getSkinnable().setSelectHour(toggleButton);
                toggleButton.setSelected(true);
                registEnterEventHandler(toggleButton, () -> {
                    minuteBtnPn.requestFocus();
                });
            }
        }

    }

    /**
     * 创建label
     *
     * @param text 显示文本
     * @param style 样式
     * @param widhtProperty 被绑定宽度
     * @return 创建完成的label
     */
    private Label createLable(String text, String style, ReadOnlyDoubleProperty widhtProperty) {
        Label lbl = new Label(text);
        lbl.getStyleClass().add(style);
        //  lbl.prefWidthProperty().bind(widhtProperty);

        return lbl;
    }

    /**
     * 创建按钮面板，用于时钟选择、分钟选择
     *
     * @param btnSetList 按钮面板配置信息
     * @param calendarField Calendar类中时间类型常量
     * @param tipString 面板名称
     * @param btnPnSytle 按钮样式
     * @return 按钮面板
     */
    private ToggleButtonPane buildButtonPane(ToggleButtonPaneBuilder buttonPaneBuilder,
            final List<ButtonSet> btnSetList,
            final int calendarField,
            final String btnPnSytle) {

        ToggleButtonPane btnPn = buttonPaneBuilder.buttonType(new ToggleButton())
                .cellHight(25d)
                .cellWidth(25d)
                .buttonSetList(btnSetList)
                .buttonEventHander((EventHandler) (Event t) -> {
                    modifyTime(((ToggleButton) t.getSource()).getText(), calendarField);
                    if (Calendar.MINUTE == calendarField) {
                        getSkinnable().setFinished(true);
                    }
                }).build();

        LOGGER.debug("ButtonPane构建完成，配置为：" + btnSetList);
        btnPn.getStyleClass().add(btnPnSytle);
        btnPn.setToggleGroup(new ToggleGroup());
        BorderPane.setAlignment(btnPn, Pos.CENTER);
        return btnPn;
    }

    /**
     * 构造时钟/分钟按钮显示内容的数组
     *
     * @param length 按钮数目
     * @param tick 值相邻按钮的差值(类似刻度)
     * @return 时钟/分钟数组
     */
    public static String[] buildBtnContents(int length, int tick) {
        if (length < 0) {
            throw new ExceptionInInitializerError("时间按钮数不能小于0");
        }
        if (tick < 1) {
            throw new ExceptionInInitializerError("时间刻度不能小于1");
        }
        String[] contents = new String[length];
        for (int i = 0; i < contents.length; i++) {
            String content = i * tick + "";
            if (content.length() < 2) {
                content = "0" + content;
            }
            contents[i] = content;
        }
        LOGGER.debug("时间面板中构造时间数组：len:" + contents.length);
        return contents;
    }

    /**
     * 初始化按钮配置
     *
     * @param contents 按钮显示内容
     * @param col 按钮列
     * @param row 按钮行
     * @param styleid 样式id
     * @return 按钮配置集合
     */
    public static List<ButtonSet> buildBtnSetList(String[] contents, int col, int row, String styleid) {
        if (col < 1 || row < 1) {
            throw new ExceptionInInitializerError("时间面板行或者列不能小于1");
        }
        List<ButtonSet> buttonSetList = new ArrayList<>();
        ToggleButtonPane tmpBtnPn = new ToggleButtonPane();
        LOGGER.debug("面板按钮配置信息开始->row:" + row + "\tcol:" + col + "\tlen:" + contents.length);
        for (int i = 0; i < row; i++) {
            //循环列 
            for (int j = 0; j < col; j++) {
                int index = i * col + j;
                if (index >= contents.length) {
                    continue;
                }
                String content = contents[index];
                ButtonSet buttonSet = tmpBtnPn.new ButtonSet(i, j, 1, 1, content, content, "Button");
                //循环出来的值 要传过去的参数是第几行，第几列，合并几行，合并几列，id，value，styleid 
                buttonSet.setStyleid(styleid);
                buttonSetList.add(buttonSet);
            }
        }
        LOGGER.debug("面板按钮配置信息完成->ButtonSetList:" + buttonSetList + "\tlen:" + buttonSetList.size());
        return buttonSetList;
    }

    /**
     * 修改时间属性值并显示到时间输入域
     *
     * @param valueString 数字字符串
     * @param calendarField Calendar常量字段
     * @param tip 提示类型(时钟|分钟)
     */
    private void modifyTime(String valueString, int calendarField) {
        String tip;
        if (calendarField == Calendar.HOUR_OF_DAY) {
            tip = "时钟";
        } else if (calendarField == Calendar.MINUTE) {
            tip = "分钟";
        } else if (calendarField == Calendar.SECOND) {
            tip = "秒钟";
        } else {
            LOGGER.debug("方法参数错误，calendarField参数只能是Calendar.HOUR_OF_DAY、Calendar.MINUTE或者Calendar.SECOND");
            return;
        }
        if (valueString == null || valueString.trim().equals("")) {
            LOGGER.debug(tip + "数值不能为空");
            return;
        }
        try {
            int value = Integer.parseInt(valueString.trim());
            if (value < 0) {
                LOGGER.debug(tip + "不能小于0");
                return;
            }
            //使用日历工具将数字转换为日期，并为Control中日期属性date赋值
            calendar.set(calendarField, value);
            this.getSkinnable().setCurrentDateType(calendarField);
            Calendar cal = (Calendar) calendar.clone();
            getSkinnable().setValue(cal);

        } catch (NumberFormatException e) {
            LOGGER.error("类型转换错误，" + tip + "[" + valueString + "]不是数值", e);
        }
    }

    public HBox getTimeBox() {
        if (timeBox == null) {
            timeBox = new HBox();
        }
        return timeBox;
    }

    public Label getLabel() {
        if (label == null) {
            label = new Label();
        }
        return label;
    }

    public TextField getHhText() {
        if (hhText == null) {
            hhText = new TextField();
        }
        return hhText;
    }

    public TextField getMmText() {
        if (mmText == null) {
            mmText = new TextField();
        }
        return mmText;
    }

    public TextField getSsText() {
        if (ssText == null) {
            ssText = new TextField();
        }
        return ssText;
    }

    private void refreshButtonEditable(List buttonSets, String type) {
        Calendar startYear = getSkinnable().getYear_start();
        Calendar endYear = getSkinnable().getYear_end();
        if (startYear == null && endYear == null) {
            return;
        }
        for (Object button : buttonSets) {
            if (button instanceof ButtonSet) {
                ((ButtonSet) button).setDisable(false);
            } else {
                ((ToggleButton) button).setDisable(false);
            }

        }
        Calendar currCalendar = getSkinnable().getValue();
        if (startYear != null) {
            if (currCalendar.get(Calendar.YEAR) == startYear.get(Calendar.YEAR)
                    && currCalendar.get(Calendar.MONTH) == startYear.get(Calendar.MONTH)
                    && currCalendar.get(Calendar.DATE) == startYear.get(Calendar.DATE)) {
                int limit_start = 0;
                if (type.equals(HOURS)) {
                    limit_start = startYear.get(Calendar.HOUR_OF_DAY);
                } else if (currCalendar.get(Calendar.HOUR_OF_DAY) == startYear.get(Calendar.HOUR_OF_DAY)) {
                    limit_start = startYear.get(Calendar.MINUTE);
                }
                for (Object button : buttonSets) {
                    if (button instanceof ButtonSet) {
                        ButtonSet bs = (ButtonSet) button;
                        if (Integer.parseInt(bs.getButtonValue()) < limit_start) {
                            bs.setDisable(true);
                        }
                    } else {
                        ToggleButton toggleButton = (ToggleButton) button;
                        if (Integer.parseInt(toggleButton.getText()) < limit_start) {
                            toggleButton.setDisable(true);
                        }
                    }

                }

            }
        }
        if (endYear != null) {
            if (currCalendar.get(Calendar.YEAR) == endYear.get(Calendar.YEAR)
                    && currCalendar.get(Calendar.MONTH) == endYear.get(Calendar.MONTH)
                    && currCalendar.get(Calendar.DATE) == endYear.get(Calendar.DATE)) {
                int limit_end = 0;
                if (type.equals(HOURS)) {
                    limit_end = endYear.get(Calendar.HOUR_OF_DAY);
                } else if (currCalendar.get(Calendar.HOUR_OF_DAY) == endYear.get(Calendar.HOUR_OF_DAY)) {
                    limit_end = endYear.get(Calendar.MINUTE);
                } else {
                    limit_end = 60;
                }
                for (Object button : buttonSets) {
                    if (button instanceof ButtonSet) {
                        ButtonSet bs = (ButtonSet) button;
                        if (Integer.parseInt(bs.getButtonValue()) > limit_end) {
                            bs.setDisable(true);
                        }
                    } else {
                        ToggleButton toggleButton = (ToggleButton) button;
                        if (Integer.parseInt(toggleButton.getText()) > limit_end) {
                            toggleButton.setDisable(true);
                        }
                    }
                }
            }
        }
    }
}
