/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date.pick;

import java.util.Calendar;
import java.util.Locale;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;

/**
 * 年和月的选择面板，这个组件年份的选择是可以自定义大小。<br>
 * 默认选中当前的年月份
 *
 * @author weipengwei
 * @version 2013-07-05
 * @param <T>
 */
public class YearAndMonthPanel<T> extends Control {

    /**
     * 格式化字符串
     */
    private String format;

    private int yearEnd;

    private int yearStart;
    
    private int monthStart;
    
    private int monthEnd;
    
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 年份选择的有多少列
     */
    private final ObjectProperty<Integer> columnsize = new SimpleObjectProperty<Integer>(this, "columnsize", 2) {
        @Override
        public void set(Integer t) {
            if (t == null) {
                t = 2;
            }
            super.set(t);
        }
    };
    /**
     * 设置列直接的间距
     */
    public ObjectProperty<Double> hgapProperty = new SimpleObjectProperty<Double>(this, "hgap", new Double(0)) {
        @Override
        public void set(Double t) {
            if (t == null) {
                t = 0.0;
            }
            super.set(t);
        }
    };
    /**
     * 选中的索引
     */
    public ObjectProperty<Integer> indexObjectProperty = new SimpleObjectProperty<Integer>(this, "index") {
        @Override
        public void set(Integer value) {
            if (value == null) {
                return;
            }
            super.set(value);
        }
    };
    /**
     * 默认Java虚拟机的当前语言环境
     */
    private ObjectProperty<Locale> localeObjectProperty = new SimpleObjectProperty<>(this, "locale", Locale.getDefault());
    /**
     * 行的数量
     */
    private ObjectProperty<Integer> rowSize = new SimpleObjectProperty<Integer>(this, "rowSize", 4) {
        @Override
        public void setValue(Integer t) {
            if (t == null) {
                t = 4;
            }
            super.set(t);
        }
    };
    /**
     * 选定的值
     */
    public ObjectProperty<T> selectValueProperty = new SimpleObjectProperty<T>(this, "type") {
        @Override
        public void set(T t) {
            if (t == null) {
                return;
            }
            super.set(t);
        }
    };

    /**
     * 年份，月份选择板的设定
     */
    private final ObjectProperty<Type> typeObjectProperty = new SimpleObjectProperty<Type>(this, "type", Type.MONTH) {
        @Override
        public void set(Type t) {
            if (t == null) {

                t = Type.MONTH;

            }
            super.set(t);
        }
    };
    /**
     * 行之间的间距
     */
    public ObjectProperty<Double> vgapProperty = new SimpleObjectProperty<Double>(this, "vgap", new Double(0)) {
        @Override
        public void set(Double t) {
            if (t == null) {
                t = 0.0;
            }
            super.set(t);
        }
    };

    /**
     * 加载Skin
     */
    public YearAndMonthPanel() {
        this.getStyleClass().add(YearAndMonthPanel.class.getSimpleName());
    }

    /**
     * 面板类型
     *
     * @return 返回面板类型
     */
    public Type getType() {
        return typeObjectProperty.getValue();
    }

    /**
     * 面板类型
     *
     * @param type 设定面板类型
     */
    public void setType(Type type) {
        this.typeObjectProperty.setValue(type);
    }

    /**
     * css文件路径
     *
     * @return css文件路径
     */
    @Override
    public String getUserAgentStylesheet() {
        return super.getUserAgentStylesheet();
    }

    /**
     * 获得选择的值
     *
     * @return 获得选择的值
     */
    public T getSelectValue() {
        return selectValueProperty.getValue();
    }

    /**
     * 设置选择的值
     *
     * @param selectValue 设置选择的值
     */
    public void setSelectValue(T selectValue) {
        this.selectValueProperty.setValue(selectValue);
    }

    /**
     * 列之间的间距
     *
     * @return 返回列之间的间距 默认是0
     */
    public double getHgap() {
        return hgapProperty.getValue();
    }

    /**
     * 列之间的间距
     *
     * @param hgap 设置列之间的间距 默认是0
     */
    public void setHgap(double hgap) {
        hgapProperty.setValue(hgap);
    }

    /**
     * 行之间的间距
     *
     * @return 返回行之间的间距 默认是0
     */
    public double getVgap() {
        return hgapProperty.getValue();
    }

    /**
     * 设置行之间的间距
     *
     * @param hgap 设置行之间的间距 默认是0
     */
    public void setVgap(double hgap) {
        hgapProperty.setValue(hgap);
    }

    /**
     * Locale: the locale is used to determine first-day-of-week, weekday
     * 根据Locale显示不同文字
     *
     * @return Locale
     */
    public ObjectProperty<Locale> localeProperty() {
        return localeObjectProperty;
    }

    /**
     * 返回Java虚拟机的语言环境
     *
     * @return Locale
     */
    public Locale getLocale() {
        return localeObjectProperty.getValue();
    }

    /**
     * 设置不同的语言环境
     *
     * @param value Locale不同的环境
     */
    public void setLocale(Locale value) {
        localeObjectProperty.setValue(value);
    }

    /**
     * 选中的按钮的index
     *
     * @return 选中的按钮的index
     */
    public ObjectProperty<Integer> indexProperty() {
        return this.indexObjectProperty;
    }

    /**
     * 获得当前选中的索引
     *
     * @return Integer 获得当前选中的索引
     */
    public Integer getIndex() {
        return this.indexObjectProperty.getValue();
    }

    /**
     * 设置选中按钮的Index
     *
     * @param value 设置选中按钮的Index
     */
    public void setIndex(Integer value) {
        this.indexObjectProperty.setValue(value);
    }

    /**
     * 获列的大小
     *
     * @return 获列的大小
     */
    public int getColumnSize() {
        return columnsize.getValue();
    }

    /**
     * 设置列的大小
     *
     * @param i 设置列的大小
     */
    public void setColumnSize(int i) {
        columnsize.setValue(i);
    }

    /**
     * 获得行的大小
     *
     * @return 行的大小
     */
    public int getRowSize() {
        return rowSize.getValue();
    }

    /**
     * 设置行的数量
     *
     * @param i 行的大小
     */
    public void setRowSize(int i) {
        rowSize.setValue(i);
    }

    /**
     * 枚举的年份和月份
     */
    public enum Type {

        YEAR, MONTH
    };

    public int getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(int yearEnd) {
        this.yearEnd = yearEnd;
    }

    public int getYearStart() {
        return yearStart;
    }

    public void setYearStart(int yearStart) {
        this.yearStart = yearStart;
    }

    public int getMonthStart() {
        return monthStart;
    }

    public void setMonthStart(int monthStart) {
        this.monthStart = monthStart;
    }

    public int getMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(int monthEnd) {
        this.monthEnd = monthEnd;
    }
    
    
}
