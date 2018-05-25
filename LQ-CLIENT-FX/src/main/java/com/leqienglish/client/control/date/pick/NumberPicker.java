/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date.pick;


import com.leqienglish.client.control.date.DisplayStyleEnum;
import com.leqienglish.client.control.pop.LQPopup;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TextInputControl;

/**
 * <p>
 * 数字面板:由0到9和隐藏，返回按钮组成的一个面板，是一个方便录入数字的组件，</p>
 * <p>
 * 一般应用在录入电话号码，和一些基本录入数字的时候，也适用于一些不可键盘录入更改的时候； 比如密码框中只能用数字面板录入 不可以键盘录入
 * </p>
 *
 * @author niuben
 * @version 2013-6-20(review:huangjijun 2014-3-4 9:38:50)
 *
 */
public class NumberPicker extends Control {

    /**
     * 数字选择面板所依附的数字输入域
     */
    private TextInputControl numInput;
    /**
     * 建立一个弹出窗口
     */
    private LQPopup numPnPop;
    /**
     * 数字面板显示类型
     */
    private final ObjectProperty<DisplayStyleEnum> displayStyleEnumProperty = new SimpleObjectProperty<>(DisplayStyleEnum.H);

    public ObjectProperty<DisplayStyleEnum> displayStyleProperty() {
        return displayStyleEnumProperty;
    }

    public DisplayStyleEnum getDisplayStyle() {
        return displayStyleEnumProperty.get();
    }

    public LQPopup getNumHIPPopup() {
        return numPnPop;
    }

    public void setDisplayStyle(DisplayStyleEnum stylePane) {
        this.displayStyleEnumProperty.set(stylePane);
    }

    public TextInputControl getNumInput() {
        return numInput;
    }

    public void setNumInput(TextInputControl numInput) {
        this.numInput = numInput;
    }

    /**
     * 构造方法 加载css中的样式类 对弹出的窗口初始化一些属性，然后加入弹出窗口的数据
     */
    public NumberPicker() {
        setId("num_btnP");
        getStyleClass().add(NumberPicker.class.getSimpleName());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new NumberPickerSkin(this);
    }

    /**
     * 弹出框显示数字面板
     *
     * @param displayStyleEnum
     */
    public void showPopup(DisplayStyleEnum displayStyleEnum) {
        //调用popup中的弹出框并显示
        if (numPnPop == null) {
            initPopup();
        }
        setDisplayStyle(displayStyleEnum);
        numPnPop.showV(numInput);
    }

    /**
     * 数字面板添加到弹出框
     */
    private void initPopup() {
        numPnPop = new LQPopup();
        numPnPop.setAutoFix(true);
        numPnPop.setAutoHide(true);
        numPnPop.setHideOnEscape(true);
        numPnPop.getContent().add(this);
    }

    /**
     * 隐藏数字面板弹出框
     */
    public void hidePopup() {
        if (numPnPop == null) {
            initPopup();
        }
        numPnPop.hide();
    }

}
