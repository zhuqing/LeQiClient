/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date.listspinner;

/**
 * Copyright (c) 2011, JFXtras All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. * Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. * Neither the name of the <organization> nor the names
 * of its contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.leqienglish.client.control.date.pick.YearAndMonthPanel;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * ListSpriner类用于一个从一个集合中选出一个值，就像ComboBox。<br>
 * 这个ListSpriner可以通过传进一个连续的数值,也可以使用一个集合。<br>
 * http://openjdk.java.net/projects/openjfx/ux/spinner/index.html
 *
 * @author weipengwei
 * @version 2013-07-05
 *
 */
public final class ListSpinner<T> extends Control {

    // final static public String ONCYCLE_PROPERTY_ID = "onCycle";
    /**
     * 选中值
     */
    final private ObjectProperty<T> valueObjectProperty = new SimpleObjectProperty<T>(this, "value", null) {
        @Override
        public void set(T value) {
            if (getItems().indexOf(value) < 0) {

                return;// throw new IllegalArgumentException("Value does not exist in the list: " + value);
            }
            super.set(value);
        }
    };
    /**
     * 选中的索引
     */
    final private ObjectProperty<Integer> indexObjectProperty = new SimpleObjectProperty<Integer>(this, "index", null) {
        @Override
        public void set(Integer value) {
            if (value == null) {
                return;//throw new NullPointerException("Null not allowed as the value for index");
            }
            if (value >= getItems().size()) {
                return;//throw new IllegalArgumentException("Index out of bounds: " + value + ", valid values are 0-" + (getItems().size() - 1));
            }
            super.set(value);
        }
    };
    /**
     * 是否循环：集合有start-end-start...循环。默认是false
     */
    final private ObjectProperty<Boolean> cyclicObjectProperty = new SimpleObjectProperty<Boolean>(this, "cyclic", false) {
        @Override
        public void set(Boolean value) {
            if (value == null) {
                value = false;//throw new NullPointerException("Null not allowed as the value for cyclic");;
            }
            super.set(value);
        }
    };

    /**
     * 是否可编辑属性
     */
    final private ObjectProperty<Boolean> editableObjectProperty
            = new SimpleObjectProperty<Boolean>(this, "editable", false) {
        @Override
        public void set(Boolean value) {
            if (value == null) {
                value = false;//throw new NullPointerException("Null not allowed as the value for editable");
            }
            super.set(value);
        }
    };
    /**
     * 用于给值添加一个后缀，默认我空
     */
    final private ObjectProperty<String> postfixObjectProperty = new SimpleObjectProperty<>(this, "postfix", "");
    /**
     * value的前缀，默认为空
     */
    final private ObjectProperty<String> prefixObjectProperty = new SimpleObjectProperty<>(this, "prefix", "");
    /**
     * 值的集合
     */
    final private ObjectProperty<ObservableList<T>> itemsObjectProperty = new SimpleObjectProperty<ObservableList<T>>(this, "items", null) {
        @Override
        public void set(ObservableList<T> value) {
            if (value == null) {
                return;// throw new NullPointerException("Null not allowed as the value for items");
            }
            super.set(value);
        }
    };
    /**
     * cellFactory的回调函数，用于设置value的前缀和后缀
     */
    final private ObjectProperty<Callback<ListSpinner<T>, Node>> cellFactoryObjectProperty = new SimpleObjectProperty<Callback<ListSpinner<T>, Node>>(this, "cellFactory", new DefaultCellFactory());
    /**
     * 字符串转换器
     */
    final private ObjectProperty<StringConverter<T>> stringConverterObjectProperty = new SimpleObjectProperty<StringConverter<T>>(this, "stringConverter", new DefaultStringConverter());

    /**
     * 回调函数
     */
    final private ObjectProperty<Callback<T, Integer>> addCallbackObjectProperty = new SimpleObjectProperty<>(this, "addCallback", null);
    /**
     * 箭头位置属性
     */
    final private ObjectProperty<ArrowPosition> arrowPositionObjectProperty = new SimpleObjectProperty<ArrowPosition>(this, "arrowPosition", ArrowPosition.TRAILING) {
        @Override
        public void set(ArrowPosition value) {
            if (value == null) {
                return;//  throw new NullPointerException("Null not allowed as the value for arrowPosition");
            }
            super.set(value);
        }
    };
    /**
     * 定义循环事件
     */
    final private ObjectProperty<EventHandler<CycleEvent>> iOnCycleObjectProperty = new SimpleObjectProperty<>(null);
    /*
     * 监听list的变化
     * TODO: 
     */
    private ListChangeListener<T> listChangeListener = new ListChangeListener<T>() {
        @Override
        public void onChanged(javafx.collections.ListChangeListener.Change<? extends T> change) {
            // 获得当前的index
            int lIndex = getIndex();

            // 是不是仍然有效
            if (lIndex >= getItems().size()) {
                lIndex = getItems().size() - 1;
                setIndex(lIndex);
                return;
            }
            valueObjectProperty.setValue(getItems().get(lIndex));
        }
    };
    /**
     * 本地化
     */
    volatile private ObjectProperty<Locale> localeObjectProperty = new SimpleObjectProperty<>(this, "locale", Locale.CHINA);
    final private ObjectProperty<Pos> alignmentObjectProperty = new SimpleObjectProperty<Pos>(this, "alignment", Pos.CENTER_LEFT) {
        @Override
        public void set(Pos value) {
            if (value == null) {
                value = Pos.CENTER_LEFT;//throw new NullPointerException("Null not allowed as the value for alignment");
            }
            super.set(value);
        }
    };
    /**
     * selectArrow:选择箭头的监听
     */
    public ObjectProperty<YearAndMonthPanel.Type> selectArrowTypeProperty = new SimpleObjectProperty<YearAndMonthPanel.Type>(this, "selectArrowType", YearAndMonthPanel.Type.MONTH) {
        @Override
        public void set(YearAndMonthPanel.Type t) {
            if (t == null) {
                t = YearAndMonthPanel.Type.MONTH;//throw new NullPointerException("Null not allowed as the value for selectArrowType");
            }
            super.set(t);
        }
    };
    /**
     * 结束年份，默认结束年份为明年
     */
//    private ObjectProperty<Integer> yearEnd = new SimpleObjectProperty<>(this, "yearEnd", new Integer(Calendar.getInstance().get(Calendar.YEAR) + 1));
//    /**
//     * 开始年份默认1958
//     */
//    private ObjectProperty<Integer> yearStart = new SimpleObjectProperty<>(this, "yearStart", new Integer(1958));

    private int yearEnd;

    private int yearStart;

    private int monthStart;

    private int monthEnd;
    /**
     * 箭头方向的属性
     */
    final private ObjectProperty<ArrowDirection> arrowDirectionObjectProperty = new SimpleObjectProperty<ArrowDirection>(this, "arrowDirection", ArrowDirection.HORIZONTAL) {
        @Override
        public void set(ArrowDirection value) {
            if (value == null) {
                value = ArrowDirection.HORIZONTAL;//throw new NullPointerException("Null not allowed as the value for arrowDirection");
            }
            super.set(value);
        }
    };

    /**
     * 这个value是选中的值。
     *
     * @return 这个value是选中的值。
     */
    public ObjectProperty<T> valueProperty() {
        return this.valueObjectProperty;
    }

    /**
     * value值
     *
     * @return value 的值
     */
    public T getValue() {
        return this.valueObjectProperty.getValue();
    }

    /**
     * value 的值
     *
     * @param value 设置选择中的值。用于默认的设置
     */
    public final void setValue(T value) {
        this.valueObjectProperty.setValue(value);
    }

    /**
     * 设置选择中的值。
     *
     * @param value 设置选择中的值。
     * @return 返回当前对象
     */
    public ListSpinner<T> withValue(T value) {
        setValue(value);
        return this;
    }

    /**
     * 索引，每一个值对应的索引
     *
     * @return 索引，每一个值对应的索引
     */
    public ObjectProperty<Integer> indexProperty() {
        return this.indexObjectProperty;
    }

    /**
     * 获得当前选中的索引
     *
     * @return 获得当前选中的索引
     */
    public Integer getIndex() {
        return this.indexObjectProperty.getValue();
    }

    /**
     * 设置选中的索引
     *
     * @param value 索引
     */
    public void setIndex(Integer value) {
        this.indexObjectProperty.setValue(value);
    }

    /**
     * 设置索引，并返回当前对象
     *
     * @param value 具体的值
     * @return 返回当前对象
     */
    public ListSpinner<T> withIndex(Integer value) {
        setIndex(value);
        return this;
    }

    /**
     * 年月的选择是否循环
     *
     * @return 年月的选择是否循环
     */
    public ObjectProperty<Boolean> cyclicProperty() {
        return this.cyclicObjectProperty;
    }

    /**
     * 返回是否循环
     *
     * @return 是否循环
     */
    public Boolean isCyclic() {
        return this.cyclicObjectProperty.getValue();
    }

    /**
     * 设置是否循环
     *
     * @param value 设置是否循环
     */
    public void setCyclic(Boolean value) {
        this.cyclicObjectProperty.setValue(value);
    }

    /**
     * 设置是否循环，返回当前对象
     *
     * @param value 设置是否循环
     * @return 返回当前对象
     */
    public ListSpinner<T> withCyclic(Boolean value) {
        setCyclic(value);
        return this;
    }

    /**
     * 是否和编辑
     *
     * @return 返回可监听对象
     */
    public ObjectProperty<Boolean> editableProperty() {
        return this.editableObjectProperty;
    }

    /**
     * 是否可编辑
     *
     * @return 返回是否可编辑
     */
    public Boolean isEditable() {
        return this.editableObjectProperty.getValue();
    }

    /**
     * 设置是否可编辑
     *
     * @param value 设置是否可编辑
     */
    public void setEditable(Boolean value) {
        this.editableObjectProperty.setValue(value);
    }

    /**
     * 设置是否可编辑，并返回当前对象。
     *
     * @param value 设置是否可编辑
     * @return 当前对象
     */
    public ListSpinner<T> withEditable(Boolean value) {
        setEditable(value);
        return this;
    }

    /**
     * 为value添加后缀
     *
     * @return
     */
    public ObjectProperty<String> postfixProperty() {
        return this.postfixObjectProperty;
    }

    /**
     * 获得后缀
     *
     * @return 返回添加的后缀
     */
    public String getPostfix() {
        return this.postfixObjectProperty.getValue();
    }

    /**
     * 设置后缀
     *
     * @param value 后缀符
     */
    public void setPostfix(String value) {
        this.postfixObjectProperty.setValue(value);
    }

    /**
     * 设置后缀，并返回当前对象
     *
     * @param value 设置后缀
     * @return 并返回当前对象
     */
    public ListSpinner<T> withPostfix(String value) {
        setPostfix(value);
        return this;
    }

    /**
     * 设置value的前缀
     *
     * @return prefix的可监听对象
     */
    public ObjectProperty<String> prefixProperty() {
        return this.prefixObjectProperty;
    }

    /**
     * 获取value的前缀
     *
     * @return 获取value的前缀
     */
    public String getPrefix() {
        return this.prefixObjectProperty.getValue();
    }

    /**
     * 设置value的前缀
     *
     * @param value value的前缀
     */
    public void setPrefix(String value) {
        this.prefixObjectProperty.setValue(value);
    }

    /**
     * 设置value的前缀，并返回当前对象
     *
     * @param value 设置value的前缀
     * @return 并返回当前对象
     */
    public ListSpinner<T> withPrefix(String value) {
        setPrefix(value);
        return this;
    }

    /**
     * 返回可监听的值集合
     *
     * @return 返回可监听的值集合
     */
    public ObjectProperty<ObservableList<T>> itemsProperty() {
        return this.itemsObjectProperty;
    }

    /**
     * 获得这个值集合。
     *
     * @return
     */
    public ObservableList<T> getItems() {
        return this.itemsObjectProperty.getValue();
    }

    /**
     * 设置这value结合
     *
     * @param value value的集合
     */
    public final void setItems(ObservableList<T> value) {
        this.itemsObjectProperty.setValue(value);
    }

    /**
     * 设置值的集合
     *
     * @return 当前对象
     */
    public ListSpinner<T> withItems(ObservableList<T> value) {
        setItems(value);
        return this;
    }

    /**
     * cellFactory的可监听对象
     *
     * @return cellFactory的可监听对象
     */
    public ObjectProperty<Callback<ListSpinner<T>, Node>> cellFactoryProperty() {
        return this.cellFactoryObjectProperty;
    }

    /**
     * 获得Callback对象。
     *
     * @return 获得Callback对象。
     */
    public Callback<ListSpinner<T>, Node> getCellFactory() {
        return this.cellFactoryObjectProperty.getValue();
    }

    /**
     * 设置一个回调函数
     *
     * @param value ListSpinner 的回调函数
     */
    public void setCellFactory(Callback<ListSpinner<T>, Node> value) {
        this.cellFactoryObjectProperty.setValue(value);
    }

    /**
     * 设置一个回调函数，并返回当前对象
     *
     * @param value 一个回调函数
     * @return 当前对象
     */
    public ListSpinner<T> withCellFactory(Callback<ListSpinner<T>, Node> value) {
        setCellFactory(value);
        return this;
    }

    /**
     * StringConverter(T):字符串转换器
     *
     * @return 字符串转换器的可监听对象
     */
    public ObjectProperty<StringConverter<T>> stringConverterProperty() {
        return this.stringConverterObjectProperty;
    }

    /**
     * 获得StringConverter对象
     *
     * @return 获得StringConverter对象
     */
    public StringConverter<T> getStringConverter() {
        return this.stringConverterObjectProperty.getValue();
    }

    /**
     * 字符串转换器
     *
     * @param value 字符串转换器
     */
    public void setStringConverter(StringConverter<T> value) {
        this.stringConverterObjectProperty.setValue(value);
    }

    /**
     * 字符串转换器
     *
     * @param value 字符串转换器
     * @return 当前对象
     */
    public ListSpinner<T> withStringConverter(StringConverter<T> value) {
        setStringConverter(value);
        return this;
    }

    /**
     * 获得选择箭头的类型
     *
     * @return 返回箭头的类型
     */
    public YearAndMonthPanel.Type getselectArrowType() {
        return this.selectArrowTypeProperty.getValue();
    }

    /**
     * 设置箭头的类型
     *
     * @param type 设置箭头的类型
     */
    public void setselectArrowType(YearAndMonthPanel.Type type) {
        this.selectArrowTypeProperty.setValue(type);
    }

    /**
     * ArrowDirection:箭头的方向
     *
     * @return 返回箭头的方向
     */
    public ObjectProperty<ArrowDirection> arrowDirectionProperty() {
        return this.arrowDirectionObjectProperty;
    }

    /**
     * 获得箭头的方向
     *
     * @return 获得箭头的方向
     */
    public ArrowDirection getArrowDirection() {
        return this.arrowDirectionObjectProperty.getValue();
    }

    /**
     * 设置箭头的方向
     *
     * @param value 设置箭头的方向
     */
    public void setArrowDirection(ArrowDirection value) {
        this.arrowDirectionObjectProperty.setValue(value);
    }

    /**
     * 设置箭头的方向
     *
     * @param value
     * @return 当前对象
     */
    public ListSpinner<T> withArrowDirection(ArrowDirection value) {
        setArrowDirection(value);
        return this;
    }

    /**
     * 本地化设置
     *
     * @return 本地化
     */
    public ObjectProperty<Locale> localeProperty() {
        return localeObjectProperty;
    }

    /**
     * 返回Local
     *
     * @return 返回Local
     */
    public Locale getLocale() {
        return localeObjectProperty.getValue();
    }

    /**
     * 设置Locale
     *
     * @param value Locale
     */
    public void setLocale(Locale value) {
        localeObjectProperty.setValue(value);
    }

    /**
     * ArrowPosition:箭头位置
     *
     * @return 箭头位置可监听对象
     */
    public ObjectProperty<ArrowPosition> arrowPositionProperty() {
        return this.arrowPositionObjectProperty;
    }

    /**
     * 返回ArrowPosition
     *
     * @return ArrowPosition
     */
    public ArrowPosition getArrowPosition() {
        return this.arrowPositionObjectProperty.getValue();
    }

    /**
     * 设置ArrowPosition
     *
     * @param value ArrowPosition
     */
    public void setArrowPosition(ArrowPosition value) {
        this.arrowPositionObjectProperty.setValue(value);
    }

    /**
     * 设置箭头的位置，并且返回当前对象
     *
     * @param value 箭头的位置
     * @return 当前对象
     */
    public ListSpinner<T> withArrowPosition(ArrowPosition value) {
        setArrowPosition(value);
        return this;
    }

    /**
     * 仅适用于可编译的时候
     *
     * @return Pos
     */
    public ObjectProperty<Pos> alignmentProperty() {
        return this.alignmentObjectProperty;
    }

    /**
     * 获取对齐方式
     *
     * @return 获取对齐方式
     */
    public Pos isAlignment() {
        return this.alignmentObjectProperty.getValue();
    }

    /**
     * 设置对齐方式
     *
     * @param value 设置对齐方式
     */
    public void setAlignment(Pos value) {
        this.alignmentObjectProperty.setValue(value);
    }

    /**
     * 设置对齐方式
     *
     * @param value
     * @return ListSpinner
     */
    public ListSpinner<T> withAlignment(Pos value) {
        setAlignment(value);
        return this;
    }

    /**
     * 回调函数
     *
     * @return 回调函数
     */
    public ObjectProperty<Callback<T, Integer>> addCallbackProperty() {
        return this.addCallbackObjectProperty;
    }

    /**
     * 返回回调函数
     *
     * @return 回调函数
     */
    public Callback<T, Integer> getAddCallback() {
        return this.addCallbackObjectProperty.getValue();
    }

    /**
     * 设置一个回调函数
     *
     * @param value 回调函数
     */
    public void setAddCallback(Callback<T, Integer> value) {
        this.addCallbackObjectProperty.setValue(value);
    }

    /**
     * 设置一个回调函数 ，并返回当前对象.
     *
     * @param value
     * @return 当前对象.
     */
    public ListSpinner<T> withAddCallback(Callback<T, Integer> value) {
        setAddCallback(value);
        return this;
    }

//    /**
//     * 开始年份 如果是年份的一个选择的话，用于给定年份的开始和结束时间， 如果这个ListSpinner不是用于年份的选择。那么设置这是没有用的
//     *
//     * @return 开始年份
//     */
//    public ObjectProperty<Integer> yearStartProperty() {
//
//        return this.yearStart;
//    }
//
//    /**
//     * 返回开始年份
//     *
//     * @return 返回开始年份
//     */
//    public int getYearStart() {
//        return yearStart.getValue().intValue();
//    }
//
//    /**
//     * 设置开始年份
//     *
//     * @param startyear 设置开始年份
//     */
//    public void setYearStart(int startyear) {
//        this.yearStart.setValue(new Integer(startyear));
//    }
//
//    /**
//     * 结束年份
//     *
//     * @return 结束年份
//     */
//    public ObjectProperty<Integer> yearEndProperty() {
//
//        return this.yearEnd;
//    }
//
//    /**
//     * 返回结束年份
//     *
//     * @return 结束年份
//     */
//    public int getYearEnd() {
//        return yearEnd.getValue();
//    }
//
//    /**
//     * 设置开始年份
//     *
//     * @param startyear 开始年份
//     */
//    public void setYearEnd(int startyear) {
//        this.yearEnd.setValue(startyear);
//    }
    /**
     * 构造
     */
    public ListSpinner() {
        construct();
    }

    /**
     * The item list used to populate the spinner.
     *
     * @param items The item list used to populate the spinner.
     */
    public ListSpinner(ObservableList<T> items) {
        construct();
        setItems(items);
        first();
    }

    /**
     * 传进一个list集合，并且设置一个开始值，这个值是list集合中的一个值。
     *
     * @param items The item list used to populate the spinner.
     * @param startValue The initial value of the spinner (one of the items).
     */
    public ListSpinner(ObservableList<T> items, T startValue) {
        construct();
        setItems(items);
        setValue(startValue);
    }

    /**
     * 传入一个Java.util.list集合。在内部会把这list集合转成observableList。
     *
     * @param list 值集合
     */
    public ListSpinner(java.util.List<T> list) {
        this(FXCollections.observableList(list));
    }

    /**
     * 设置items属性
     *
     * @param list 一次传入多个值
     */
    public ListSpinner(T... list) {
        this(Arrays.asList(list));
    }

    /**
     * 设置都是int类型的值。指定从哪开始到哪结束。
     *
     * @param from 开始值
     * @param to 结束值
     */
    public ListSpinner(int from, int to) {
        this((java.util.List<T>) new ListSpinnerIntegerList(from, to));
    }

    /**
     * 设置一个开始一个开始值，和结束数值，并在之间每隔几个值区一个值
     *
     * @param from 开始值
     * @param to 结束值
     * @param step 间隔数
     */
    public ListSpinner(int from, int to, int step) {
        this((java.util.List<T>) new ListSpinnerIntegerList(from, to, step));
    }

    /*
     *加载skin类，配置监听 
     */
    private void construct() {
        // setup the CSS
        // 设置Skin类
        this.getStyleClass().add(this.getClass().getSimpleName());

        // value的监听
        this.valueObjectProperty.addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> property, T oldValue, T newValue) {
                // get the value of the new index
                int lIdx = getItems().indexOf(newValue);
                // set the value
                if (ListSpinner.equals(indexObjectProperty.getValue(), lIdx) == false) {
                    indexObjectProperty.setValue(lIdx);
                }
            }
        });

        //index的监听
        this.indexObjectProperty.addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> property, Integer oldIndex, Integer newIndex) {
                // get the value of the new index
                T lValue = newIndex < 0 ? null : getItems().get(newIndex);

                // set the value
                if (ListSpinner.equals(valueObjectProperty.getValue(), lValue) == false) {
                    valueObjectProperty.setValue(lValue);
                }
            }
        });

        // items的监听
        this.itemsObjectProperty.addListener(new ChangeListener<ObservableList<T>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<T>> property, ObservableList<T> oldList, ObservableList<T> newList) {
                if (oldList != null) {
                    oldList.removeListener(listChangeListener);
                }
                if (newList != null) {
                    newList.addListener(listChangeListener);
                }
            }
        });
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ListSpinnerCaspianSkin<>(this);
    }

//   /**
//    * 加载css样式
//    * @return 
//    */
//    @Override
//    public String getUserAgentStylesheet() {
//        this.getStyleClass().add("cssbase");
////        return this.getClass().getResource("/css/control/" + this.getClass().getSimpleName() + ".css").toString();
//       return super.getUserAgentStylesheet();
//    }
    // ==================================================================================================================
    // EVENTS
    /**
     * 循环事件监听
     *
     * @return 循环事件
     */
    public ObjectProperty<EventHandler<CycleEvent>> onCycleProperty() {
        return iOnCycleObjectProperty;
    }

    /**
     * 循环
     *
     * @return CycleEvent
     */
    public EventHandler<CycleEvent> getOnCycle() {
        return iOnCycleObjectProperty.getValue();
    }

    /**
     * 设置循环事件
     *
     * @param value 循环事件
     */
    public void setOnCycle(EventHandler<CycleEvent> value) {
        iOnCycleObjectProperty.setValue(value);
    }

    /**
     * 设置循环事件
     *
     * @param value 循环事件
     * @return 设置循环事件
     */
    public ListSpinner<T> withOnCycle(EventHandler<CycleEvent> value) {
        setOnCycle(value);
        return this;
    }

    /**
     * 我们循环的时候的 fire事件
     *
     * @param cycleDirection
     */
    public void fireCycleEvent(CycleDirection cycleDirection) {
        EventHandler<CycleEvent> lCycleEventHandler = getOnCycle();
        if (lCycleEventHandler != null) {

            CycleEvent lCycleEvent = new CycleEvent();
            lCycleEvent.cycleDirection = cycleDirection;
            lCycleEventHandler.handle(lCycleEvent);
        }
    }

    /**
     * 选中第一个
     */
    public final void first() {
        // nothing to do
        if (getItems() == null || getItems().size() == 0) {
            return;
        }
        //设置新值
        indexObjectProperty.setValue(0);
    }

    /**
     * 后退一个
     */
    public void decrement() {
        // nothing to do

        if (getItems() == null || getItems().size() == 0) {
            return;
        }

        // 当前的索引
        int lOldIdx = this.indexObjectProperty.getValue();

        //获得之前的index
        int lIdx = lOldIdx - 1;

        // 如果到了最后一个了
        if (lIdx < 0) {
            // 如果没有没有设置循环
            if (isCyclic() != null && isCyclic().booleanValue() == false) {
                return;
            }
            //如果循环到另
            lIdx = getItems().size() - 1;

            //我们循环完后，通知监听
            fireCycleEvent(CycleDirection.BOTTOM_TO_TOP);
        }

        // 设置新指数(这将更新值)
        indexObjectProperty.setValue(lIdx);
    }

    /**
     * 增加
     */
    public void increment() {
        // nothing to do
        if (getItems() == null || getItems().size() == 0) {
            return;
        }

        //当前的索引
        int lOldIdx = this.indexObjectProperty.getValue();

        //获得下一个
        int lIdx = lOldIdx + 1;

        //如果返回null,没有下一个index(通常 当前索引+ 1)
        if (lIdx >= getItems().size()) {
            // 如果没有没有设置循环
            if (isCyclic() != null && isCyclic().booleanValue() == false) {
                // do nothing
                return;
            }

            // 循环到另一端，获得第一个值
            lIdx = 0;

            // notify listener that we've cycled
            fireCycleEvent(CycleDirection.TOP_TO_BOTTOM);
        }

        //设置一个新值。
        indexObjectProperty.setValue(lIdx);
    }

    /**
     * 获得最后一个方法，如果这个方法是无尽的那么这个方法就会失败
     */
    public void last() {
        // nothing to do
        if (getItems() == null || getItems().size() == 0) {
            return;
        }

        //设置一个新值。
        indexObjectProperty.setValue(getItems().size() - 1);
    }

    /**
     * 真的如果两个值相等,假否则。
     *
     * @param o1
     * @param o2
     * @return True if the two values are equal, false otherwise.
     */
    static public boolean equals(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        if (o1 != null && o2 == null) {
            return false;
        }
        if (o1 == null && o2 != null) {
            return false;
        }
        // TODO: compare arrays if (o1.getClass().isArray() && o2.getClass().isArray()) return Arrays.equals( (Object[])o1, (Object[])o2 );		
        return o1.equals(o2);
    }

    /**
     * 箭头方向的枚举类
     */
    public enum ArrowDirection {

        VERTICAL, HORIZONTAL
    }

    /**
     * 箭头位置枚举类
     */
    public enum ArrowPosition {

        LEADING, TRAILING, SPLIT
    }

    /**
     * 循环的方向枚举类
     */
    static public enum CycleDirection {

        TOP_TO_BOTTOM, BOTTOM_TO_TOP
    }

    /**
     * CycleEvent
     */
    static public class CycleEvent extends Event {

        /**
         * 新索引
         */
        private Object newIdx;
        /**
         * 旧索引
         */
        private Object oldIdx;

        public static final EventType CYClE_EVENT = new EventType<CycleEvent>("CycleEvent");

        /**
         * 构造
         */
        public CycleEvent() {
            super(CYClE_EVENT);
        }

        /**
         * 构造
         *
         * @param source 源
         * @param target 目标target
         */
        public CycleEvent(Object source, EventTarget target) {
            super(source, target, CYClE_EVENT);
        }

        /**
         * 旧索引
         *
         * @return 旧索引
         */
        public Object getOldIdx() {
            return this.oldIdx;
        }

        /**
         * 新索引
         *
         * @return 新索引
         */
        public Object getNewIdx() {
            return this.newIdx;
        }

        /**
         * 向下的循环方式
         *
         * @return boolean
         */
        public boolean cycledDown() {
            return cycleDirection == CycleDirection.TOP_TO_BOTTOM;
        }

        /**
         * 向上的循环方式
         *
         * @return 向上的循环
         */
        public boolean cycledUp() {
            return cycleDirection == CycleDirection.BOTTOM_TO_TOP;
        }

        CycleDirection cycleDirection;
    }
    // ==================================================================================================================
    // CellFactory

    /**
     * Default cell factory
     */
    class DefaultCellFactory implements Callback<ListSpinner<T>, Node> {

        private Label label = null;

        @Override
        public Node call(ListSpinner<T> spinner) {
            // get value
            T lValue = spinner.getValue();

            // label not yet created
            if (this.label == null) {
                this.label = new Label("");
            }
            this.label.setText(lValue == null ? "" : spinner.getPrefix() + getStringConverter().toString(lValue) + spinner.getPostfix());
            return this.label;
        }
    };
    // StringConverter

    /**
     * 一个字符串转换器,做个简单的toString,但不能转换为一个对象
     *
     * @see org.jfxextras.util.StringConverterFactory
     */
    class DefaultStringConverter extends StringConverter<T> {

        @Override
        public T fromString(String string) {
            throw new IllegalStateException("No StringConverter is set. An editable Spinner must have a StringConverter to be able to render and parse the value.");
        }

        @Override
        public String toString(T value) {
            return value == null ? "" : value.toString();
        }
    }

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
