

package com.leqienglish.client.control.buttonpane;


import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;

/**
 * ButtonPane的Builder类。
 * 
 * @author duyi
 * @version 2013-7-2
 */
public class ToggleButtonPaneBuilder<B extends ToggleButtonPaneBuilder<B>> extends ButtonPaneBuilder<B> {

    /**
     * 构造方法
     */
    public ToggleButtonPaneBuilder(){
        
    }
    /**
     * 配置按钮类型
     * @param buttonType 按钮类型
     */
    @Override
    public ToggleButtonPaneBuilder<B> buttonType(ButtonBase buttonType){
        this.buttonType = buttonType;
        return this;
    }
    /**
     * 设置按钮配置信息列表
     * @param buttonSetList 配置信息列表
     */
    @Override
    public ToggleButtonPaneBuilder<B> buttonSetList(List buttonSetList){
        this.buttonSetList = buttonSetList;
        return this;
    }
    /**
     * 设置按钮事件处理
     * @param buttonEventHander 事件处理
     */
    @Override
    public ToggleButtonPaneBuilder<B> buttonEventHander(EventHandler buttonEventHander){
        this.buttonEventHander = buttonEventHander;
        return this;
    }
    /**
     * 配置单元高度
     * @param cellHight 单元高度
     */
    @Override
    public ToggleButtonPaneBuilder<B> cellHight(double cellHight){
        this.cellHight = cellHight;
        return this;
    }
    /**
     * 配置单元宽度
     * @param cellWidth 单元宽度
     */
    @Override
    public ToggleButtonPaneBuilder<B> cellWidth(double cellWidth){
        this.cellWidth = cellWidth;
        return this;
    }
    /**
     * 实例化ToggleButtonPane
     * @return ToggleButtonPane
     */
    @Override
    public ToggleButtonPane build() {
        ToggleButtonPane buttonPane = new ToggleButtonPane(cellHight,cellWidth,buttonSetList,buttonEventHander);
        return buttonPane;
    }
}
