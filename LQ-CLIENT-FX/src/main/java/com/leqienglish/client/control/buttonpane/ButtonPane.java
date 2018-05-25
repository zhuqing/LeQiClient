/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.buttonpane;

import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Skin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 按钮面板
 *
 * @author duyi
 * @version 2013-6-20
 * @param <T>
 */
public class ButtonPane<T extends ButtonBase> extends Control {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ButtonPane.class);
    /**
     * 控件使用的网格布局面板
     */
    private GridPane root = new GridPane();

    /**
     * 包含的Button列表
     */
    protected ObservableList<T> btnList = FXCollections.observableArrayList();

    /**
     * 按钮列表显示起始位
     */
    private IntegerProperty showStart = new SimpleIntegerProperty();

    /**
     * 按钮列表显示结束位
     */
    private IntegerProperty showEnd = new SimpleIntegerProperty();

    /**
     * 显示起始位改变，重新渲染显示。
     */
    private ChangeListener<Number> showStartChangeListener = new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            reLayout();
        }
    };

    /**
     * 显示结束位改变，重新渲染显示。
     */
    private ChangeListener<Number> showEndChangeListener = new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            reLayout();
        }
    };

    /**
     * 按钮类别
     */
    private T buttonType;

    /**
     * 按钮点击事件处理方法
     */
    private EventHandler buttonEventHander;

    /**
     * 按钮配置列表
     */
    private List<ButtonSet> buttonSetList;

    /**
     * 按钮样式
     */
    private ObservableList<String> btnStyleClass = FXCollections.observableArrayList();

    /**
     * 最小单元格高度
     */
    private double cellHight = 18d;

    /**
     * 最小单元格宽度
     */
    private double cellWidth = 18d;

    /**
     * 无参构造
     */
    public ButtonPane() {
        root.getStyleClass().add("btnPn_Root");
        this.showStart.set(1);
        this.showEnd.set(0);
        showStart.addListener(showStartChangeListener);
        showEnd.addListener(showEndChangeListener);
    }

    /**
     * 全参构造
     *
     * @param cellHight 单元高度
     * @param cellWidth 单元宽度
     * @param buttonSetList 配置信息列表
     * @param buttonType 按钮类型
     * @param buttonEventHander 事件处理
     * @param btnStyleClass 按钮样式
     */
    public ButtonPane(double cellHight, double cellWidth, List buttonSetList, T buttonType, EventHandler buttonEventHander, List<String> btnStyleClass) {
        this();
        this.cellHight = cellHight;
        this.cellWidth = cellWidth;
        this.setButtonSetList(buttonSetList);
        this.buttonType = buttonType;
        this.buttonEventHander = buttonEventHander;

        if (btnStyleClass != null) {
            this.btnStyleClass.addAll(btnStyleClass);
        }
        this.getStyleClass().add(ButtonPane.class.getSimpleName());
    }

    /**
     * 重写默认皮肤
     *
     * @return
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new ButtonPaneSkin(this);
    }

    /**
     * 根据起始索引和结束索引来刷新显示
     *
     * @param showStart 显示按钮的ButtonSet索引起始
     * @param showEnd 显示按钮的ButtonSet索引结束
     */
    public void show(int showStart, int showEnd) {
        setShowStart(showStart);
        setShowEnd(showEnd);
        if (getSkin() != null) {
            ((ButtonPaneSkin) getSkin()).showSkin();
        }

    }

    /**
     * 不需要重新构造按钮，但需要重新渲染按钮。
     */
    public void reLayout() {
        if (getSkin() != null) {
            ((ButtonPaneSkin) getSkin()).createButtons();
            ((ButtonPaneSkin) getSkin()).showSkin();
            LOGGER.debug("不重新构造按钮，重新渲染ButtonPane");
        }
    }

    public GridPane getRoot() {
        return root;
    }

    public void setRoot(GridPane root) {
        this.root = root;
    }

    /**
     * 获得单元格高度
     *
     * @return 单元格高度
     */
    public double getCellHight() {
        return cellHight;
    }

    /**
     * 配置单元格高度
     *
     * @param cellHight 单元格高度
     */
    public void setCellHight(double cellHight) {
        this.cellHight = cellHight;
    }

    /**
     * 获得单元格宽度
     *
     * @return 单元格宽度
     */
    public double getCellWidth() {
        return cellWidth;
    }

    /**
     * 配置单元格宽度
     *
     * @param cellWidth 单元格宽度
     */
    public void setCellWidth(double cellWidth) {
        this.cellWidth = cellWidth;
    }

    /**
     * 得到按钮配置列表
     *
     * @return 按钮配置列表
     */
    public List<ButtonSet> getButtonSetList() {
        return buttonSetList;
    }

    /**
     * 按钮配置列表
     *
     * @param buttonSetList 按钮配置列表
     */
    public final void setButtonSetList(List<ButtonSet> buttonSetList) {
        this.buttonSetList = buttonSetList;
        if (this.showEnd.get() == 0) {
            this.showEnd.set(buttonSetList.size());
        }
    }

    /**
     * 获取按钮事件处理
     *
     * @return 按钮事件处理
     */
    public EventHandler getButtonEventHander() {
        return buttonEventHander;
    }

    /**
     * 配置按钮事件处理
     *
     * @param buttonEventHander
     */
    public void setButtonEventHander(EventHandler buttonEventHander) {
        this.buttonEventHander = buttonEventHander;
    }

    /**
     * 得到当前按钮面板中包含的Button列表
     *
     * @return Button列表
     */
    public ObservableList<T> getButtonObservableList() {
        return btnList;
    }

    /**
     * 获取button类型
     *
     * @return
     */
    public T getButtonType() {
        return buttonType;
    }

    /**
     * 配置Button类型
     *
     * @param buttonType
     */
    public void setButtonType(T buttonType) {
        this.buttonType = buttonType;
    }

    public int getShowStart() {
        return showStart.getValue();
    }

    public void setShowStart(int showStart) {
        this.showStart.set(showStart);
    }

    public int getShowEnd() {
        return showEnd.getValue();
    }

    public void setShowEnd(int showEnd) {
        this.showEnd.set(showEnd);
    }

    public List<String> getBtnStyleClass() {
        return btnStyleClass;
    }

    public void setBtnStyleClass(List<String> btnStyleClass) {
        this.btnStyleClass.clear();
        this.btnStyleClass.addAll(btnStyleClass);
    }

    /**
     * button配置类
     *
     * @author duyi
     */
    public class ButtonSet {

        /**
         * 行号
         */
        private int rowId;
        /**
         * 列号
         */
        private int columnId;
        /**
         * 合并行
         */
        private int rowSpan;
        /**
         * 合并列
         */
        private int colSpan;
        /**
         * 按钮的id值
         */
        private String buttonId;
        /**
         * 按钮的text值
         */
        private String buttonValue;
        /**
         * 样式id
         */
        private String styleid;

        /**
         * 用于存放用户想存放的数据值
         */
        private Object buttonUserData;

        /**
         * 是否被选中
         */
        private boolean isSelected;

        private boolean disable;

        public ButtonSet() {
        }

        /**
         * 构造方法
         *
         * @param rowId 行号
         * @param columnId 列号
         * @param rowSpan 合并行
         * @param colSpan 合并列
         * @param buttonId 按钮的id值
         * @param buttonValue 按钮的text值
         * @param styleid 样式id
         */
        public ButtonSet(int rowId, int columnId, int rowSpan, int colSpan, String buttonId, String buttonValue, String styleid) {
            this(rowId, columnId, rowSpan, colSpan, buttonId, buttonValue, styleid, false);
        }

        /**
         * @param rowId 行号
         * @param columnId 列号
         * @param rowSpan 合并行
         * @param colSpan 合并列
         * @param buttonId 按钮的id值
         * @param buttonValue 按钮的text值
         * @param styleid 样式id
         * @param disable 设置是否可编辑
         */
        public ButtonSet(int rowId, int columnId, int rowSpan, int colSpan, String buttonId, String buttonValue, String styleid, boolean disable) {
            this.rowId = rowId;
            this.columnId = columnId;
            this.rowSpan = rowSpan;
            this.colSpan = colSpan;
            this.buttonId = buttonId;
            this.buttonValue = buttonValue;
            this.styleid = styleid;
            this.disable = disable;
        }

        public int getRowId() {
            return rowId;
        }

        public void setRowId(int rowId) {
            this.rowId = rowId;
        }

        public int getColumnId() {
            return columnId;
        }

        public void setColumnId(int columnId) {
            this.columnId = columnId;
        }

        public int getRowSpan() {
            return rowSpan;
        }

        public void setRowSpan(int rowSpan) {
            this.rowSpan = rowSpan;
        }

        public int getColSpan() {
            return colSpan;
        }

        public void setColSpan(int colSpan) {
            this.colSpan = colSpan;
        }

        public String getButtonId() {
            return buttonId;
        }

        public void setButtonId(String buttonId) {
            this.buttonId = buttonId;
        }

        public String getButtonValue() {
            return buttonValue;
        }

        public void setButtonValue(String buttonValue) {
            this.buttonValue = buttonValue;
        }

        public String getStyleid() {
            return styleid;
        }

        public void setStyleid(String styleid) {
            this.styleid = styleid;
        }

        /**
         * @return the buttonUserData
         */
        public Object getButtonUserData() {
            return buttonUserData;
        }

        /**
         * @param buttonUserData the buttonUserData to set
         */
        public void setButtonUserData(Object buttonUserData) {
            this.buttonUserData = buttonUserData;
        }

        /**
         * @return the isSelected
         */
        public boolean isIsSelected() {
            return isSelected;
        }

        /**
         * @param isSelected the isSelected to set
         */
        public void setIsSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        public boolean isDisable() {
            return disable;
        }

        public void setDisable(boolean disable) {
            this.disable = disable;
        }
    }
}
