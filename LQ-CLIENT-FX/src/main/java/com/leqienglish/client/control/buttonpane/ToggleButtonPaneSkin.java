/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.buttonpane;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyi
 */
public class ToggleButtonPaneSkin extends ButtonPaneSkin<ToggleButton> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToggleButtonPaneSkin.class);

    public ToggleButtonPaneSkin(ToggleButtonPane c) {
        super(c);
    }

    @Override
    protected void initSkin() {
        super.initSkin();
        StringProperty selectedText = ((ToggleButtonPane) getSkinnable()).getSelectedText();
        selectedText.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedByText();
            }
        });
        IntegerProperty selectedIndex = ((ToggleButtonPane) getSkinnable()).getSelectedIndex();
        selectedIndex.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                selectedByIndex();
            }
        });
        LOGGER.debug("配置选中监听事件，根据selectedIndex和selectedText改变按钮选中状态。");
    }

//    /**
//     * 显示按钮 在Application中运行
//     */
//    @Override
//    public void showSkin() {
//        super.showSkin();
//        
//        
//    }
    public void initButtonGroup() {
        ToggleGroup toggleGroup = ((ToggleButtonPane) getSkinnable()).getToggleGroup();
        ObservableList<ToggleButton> toggleBtnList = getSkinnable().getButtonObservableList();
        for (ToggleButton t : toggleBtnList) {
            t.setToggleGroup(toggleGroup);
        }
        LOGGER.debug("为ToggleButton配置toggleGroup");
    }

    public void selectedByIndex() {
        ObservableList<ToggleButton> toggleBtnList = getSkinnable().getButtonObservableList();
        for (ToggleButton t : toggleBtnList) {
            if (t.getText().equals(((ToggleButtonPane) getSkinnable()).getSelectedText())) {
                ((ToggleButton) t).setSelected(true);
            }
        }
        LOGGER.debug("根据selectedIndex配置按钮选中状态。");
    }

    public void selectedByText() {
        ObservableList<ToggleButton> toggleBtnList = getSkinnable().getButtonObservableList();
        ToggleButton toggleBtn = (ToggleButton) toggleBtnList.get(
                ((ToggleButtonPane) getSkinnable()).getSelectedIndex().getValue());
        toggleBtn.setSelected(true);
        LOGGER.debug("根据selectedText配置按钮选中状态。");
    }

}
