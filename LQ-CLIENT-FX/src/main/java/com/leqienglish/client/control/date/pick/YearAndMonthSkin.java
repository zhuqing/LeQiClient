/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date.pick;


import com.leqienglish.client.control.buttonpane.ButtonPane;
import com.leqienglish.client.control.buttonpane.ToggleButtonPane;
import com.leqienglish.client.control.buttonpane.ToggleButtonPaneBuilder;
import com.leqienglish.client.control.date.pagebar.NavigationPageBar;
import com.leqienglish.client.control.date.pick.YearAndMonthPanel.Type;
import com.leqienglish.client.control.skin.LQSkin;
import com.leqienglish.client.util.local.LocalUtil;
import com.sun.javafx.scene.control.behavior.BehaviorBase;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * YearAndMonthPanel的Skin
 *
 * @author weipengwei
 *
 *
 */
public class YearAndMonthSkin extends LQSkin<YearAndMonthPanel, BehaviorBase<YearAndMonthPanel>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(YearAndMonthSkin.class);
    /**
     * 时间格式化
     */
    private DateFormat monthFormat;//时间的格式化
    /**
     * 分页控件
     */
    private NavigationPageBar pageBar;//分页控件
    /**
     * 用于year数据，和分页控件的布局
     */
    private VBox vbox;
    /**
     * ToggleButtonPane
     */
    private ToggleButtonPane buttonPane;
    private ToggleButtonPane buttonPane1;//ToggleButton面板，根据buttons的信息，生成相应的ToggleButton
    /**
     * ButtonSet 集合<br>
     * pages的长度就是页数
     */
    private static List<List<ButtonPane.ButtonSet>> pages;
    /**
     * 每一个ButtonPane都是一页
     */
    private List<ToggleButtonPane> buttonpanes;

    /**
     * 构造
     *
     * @param c YearAndMonthPanel
     */
    public YearAndMonthSkin(YearAndMonthPanel c) {
        super(c, new YearAndMonthPanelBehavior(c));
    }

    @Override
    protected void showSkin() {
        Type type = getSkinnable().getType();
        if (type == Type.MONTH) {
            this.getChildren().add(buttonPane);
        } else if (type == Type.YEAR) {
            this.getChildren().add(vbox);
            refreshYear();
        }
        getSkinnable().indexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                refresh();
            }
        });
        //refreshYear();
    }

    @Override
    protected void initSkin() {
        vbox = new VBox();
        pages = new ArrayList<>();
        buttonpanes = new ArrayList<>();
        pageBar = new NavigationPageBar();
        buttonPane = new ToggleButtonPane();
        buttonPane1 = new ToggleButtonPane();
        createNodes();
        //initEventHandelr();
    }

    /**
     * 填充gridpane
     */
    private void createNodes() {
        Type type = getSkinnable().getType();
        //判断是年的面版还是月的面板
        if (type == Type.MONTH) {
            monthFormat = new SimpleDateFormat("MMMM", getSkinnable().getLocale());
            createMonthButtonPane();
        } else if (type == Type.YEAR) {
            initYear();
            createYearButtonPane();
            fenYe();
        }
    }

    /**
     * 构建一个ButtonPane
     */
    private void createMonthButtonPane() {

        List<ButtonPane<ToggleButton>.ButtonSet> buttons = initMonth();
        //创建ToggleButton类型的ButtonPanel,并设置到ToggleGroup中
        ToggleButtonPaneBuilder buttonPaneBuilder = new ToggleButtonPaneBuilder();
        buttonPane = buttonPaneBuilder.buttonEventHander(new EventHandler() {
            @Override
            public void handle(Event t) {
                buttonPane.setToggleGroup(new ToggleGroup());
                if (getSkinnable().getFormat() == null) {
                    getSkinnable().setSelectValue(((ToggleButton) t.getSource()).getText());
                } else {
                    getSkinnable().setSelectValue(Integer.parseInt(((ToggleButton) t.getSource()).getId()));
                }
            }
        })
                .cellHight(22d)
                .cellWidth(30d)
                .buttonSetList(buttons)
                .buttonType(new ToggleButton())
                .build();
        buttonPane.setToggleGroup(new ToggleGroup());
        buttonPane.getBtnStyleClass().add("toggle-button");
        buttonPane.getStyleClass().add("month-pane");
//        this.getChildren().add(buttonPane);
    }

    /**
     * 初始化月份的面板信息
     *
     * @return List<ButtonPane.ButtonSet>
     */
    private List<ButtonPane<ToggleButton>.ButtonSet> initMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        List<ButtonPane<ToggleButton>.ButtonSet> buttons = new ArrayList<>();//ButtonSet集合
        int row = 0;//所在的行
        int col = 0;//所在的列
        for (int i = 0; i < 12; i++) {
            c.set(Calendar.MONTH, i);
            //创建一个buttonSet，它的属性对应一个Button的信息。
            ButtonPane.ButtonSet buttonset = new ButtonPane().new ButtonSet();
            buttonset.setButtonId("" + i);

            if (getSkinnable().getMonthStart() > 0) {
                if (i < getSkinnable().getMonthStart()) {
                    buttonset.setDisable(true);
                }
            } 
            if (getSkinnable().getMonthEnd() > 0) {
                if (i > getSkinnable().getMonthEnd()) {
                    buttonset.setDisable(true);
                }
            }
            try {
                //buttonset.setButtonValue(monthFormat.format(c.getTime()));//自动国际化
                buttonset.setButtonValue(LocalUtil.getLiteral(c.getTime().toString().substring(4, 7).trim()));//读取国际化配置文件
            } catch (IOException ex) {
                LOGGER.error(ex.toString(), ex);
            }
            buttonset.setColSpan(1);
            buttonset.setColumnId(col);
            buttonset.setRowId(row);
            buttonset.setRowSpan(1);
            buttonset.setStyleid("weekday-label");

            //设置3列的GridPanel
            if ((i + 1) % 2 == 0 && i != 0) {
                buttons.add(buttonset);
                row++;
                col = 0;
            } else {
                buttons.add(buttonset);
                col++;
            }
        }
        //返回Buttons集合
        return buttons;
    }

    /**
     * 用于年份的分页显示
     */
    private void fenYe() {
        pageBar.setMaxPage(pages.size());
        pageBar.setNowPage(0);
        pageBar.setIsShowFirstAndLastBtn(false);
        pageBar.setIsShowTotalLabel(false);
        pageBar.setShowPageBtn(0);
        pageBar.setIsShowInput(false);
        pageBar.setIsShowPreAndNextBtn(true);

        final HBox hBox = new HBox();
        hBox.getStyleClass().add("year-buttons");
        vbox.getChildren().addAll(pageBar, hBox);
        pageBar.nowPageProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (oldValue != null) {
                    hBox.getChildren().clear();
                }
                if (newValue != null) {
                    buttonPane1 = buttonpanes.get((int) newValue - 1);
                    buttonPane1.clearSelect();
                    buttonPane1.setToggleGroup(new ToggleGroup());
                    hBox.getChildren().add(buttonPane1);

                }
            }
        });
        vbox.setAlignment(Pos.TOP_CENTER);
//        vbox.setPrefSize(100, 202);

//        vbox.getChildren().add(buttonpanes.get(0));
//        //添加背景颜色
        vbox.getStyleClass().add("year-vbox");
    }

    /**
     * 每一个年选择面板的分页
     *
     * @return List<ButtonPane> 年选择面板的所有分页
     */
    private void createYearButtonPane() {
        //便利每一页
        for (List<ButtonPane.ButtonSet> buttonset : pages) {
            //一页中的ButtonSets
            List<ButtonPane.ButtonSet> buttons = buttonset;

            ToggleButtonPaneBuilder buttonPaneBuilder = new ToggleButtonPaneBuilder();
            buttonPane1 = buttonPaneBuilder.buttonSetList(buttons)
                    .buttonType(new ToggleButton())
                    .cellHight(21d)
                    .cellWidth(35d)
                    .buttonEventHander(new EventHandler() {
                        @Override
                        public void handle(Event t) {
                            buttonPane1.setToggleGroup(new ToggleGroup());
                            ToggleButton toggle = (ToggleButton) t.getSource();
                            getSkinnable().setSelectValue(Integer.parseInt(toggle.getText()));
                        }
                    })
                    .build();
            //buttonPane1.setToggleGroup(new ToggleGroup());
            buttonPane1.getBtnStyleClass().add("toggle-button");
            buttonpanes.add(buttonPane1);
        }
    }

    /**
     * 初始化每一个ButtonSet 每一个List<ButtonPane.ButtonSet>是一个分页数据 初始化所有的分页数据
     */
    private void initYear() {
        List<ButtonPane.ButtonSet> buttons = new ArrayList<>();//ButtonSet集合

        //根据column,row 确定Button的位置
        int column = 0;
        int row = 0;

        int yearstart = getSkinnable().getYearStart();
        int yearend = getSkinnable().getYearEnd();
        int yiye = (getSkinnable().getColumnSize()) * getSkinnable().getRowSize();//一页有多少条数据

        int count = (yearend - yearstart + 1) % yiye == 0 ? (yearend - yearstart + 1) / yiye : (yearend - yearstart + 1) / yiye + 1;//有多少个GridPane

        int currentcount = 0;
        int curentNum = 0;
        //循环网格面板，确定有多少个面板，用于分页显示。
        for (int i = yearstart; i <= yearend; i++) {
            final ButtonPane.ButtonSet button = new ButtonPane<ToggleButton>().new ButtonSet();//设置ButtonSet值
            buttons.add(button);
            curentNum++;
            button.setButtonId("" + i);
            button.setButtonValue("" + i);
            button.setColSpan(1);
            button.setColumnId(column);
            button.setRowId(row);
            button.setRowSpan(1);
            button.setStyleid("weekday-label");
            //但column==getColumnSize时换行
            if ((column + 1) % getSkinnable().getColumnSize() == 0 && column != 0) {
                row++;
                column = 0;
            } else {
                column++;
            }
            //当页满时，或者便利的集合的最后一个时
            if (curentNum % yiye == 0 || ((currentcount + 1) == count && i == yearend)) {
                row = 0;
                column = 0;
                currentcount++;//当前页加1
                pages.add(buttons);
                buttons = new ArrayList<>();
            }
        }

    }

    private void refresh() {
        Type type = getSkinnable().getType();
        if (type == Type.MONTH) {
            refreshMonth();
        } else if (type == Type.YEAR) {
            refreshYear();
        }

    }

    private void refreshMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH);//默认被选中的是当前月份。
        if (getSkinnable().getIndex() != null) {
            month = getSkinnable().getIndex();
        }
        //TODO duyi 获取可监听List
        buttonPane.getSelectedIndex().set(month);
    }

    /**
     * 刷新年份选择板，使之给定年份默认选中
     */
    private void refreshYear() {
        int onePageSize = (getSkinnable().getColumnSize()) * getSkinnable().getRowSize();
        int index = getSkinnable().getIndex();
        int currentPage = (index + 1) % onePageSize == 0 ? (index + 1) / onePageSize : (index + 1) / onePageSize + 1;
        pageBar.setNowPage(currentPage);
    }
}
