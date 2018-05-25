/**
 * 
 */

package com.leqienglish.client.control.buttonpane;

import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;
import javafx.util.Builder;


/**
 * ButtonPane的Builder类。
 * 
 * @author duyi
 * @version 2013-7-2
 * @param <B>
 */
public class ButtonPaneBuilder<B extends ButtonPaneBuilder<B>> implements Builder<ButtonPane>{
    /**
     * 按钮类别
     */
    protected ButtonBase buttonType;
    /**
     * Button配置列表
     */
    protected List buttonSetList;
    /**
     * buttonon的Action事件处理
     */
    protected EventHandler buttonEventHander;
    /**
     * 最小单元格高度
     */
    protected double cellHight;
    /**
     * 最小单元格宽度
     */
    protected double cellWidth;
    
    /**
     * 按钮样式
     */
    private List<String> btnStyleClass;
    
    /**
     * 构造方法
     */
    public ButtonPaneBuilder(){
        
    }
    /**
     * 配置按钮类型
     * @param buttonType 按钮类型
     */
    public ButtonPaneBuilder<B> buttonType(ButtonBase buttonType){
        this.buttonType = buttonType;
        return this;
    }
    /**
     * 设置按钮配置信息列表
     * @param buttonSetList 配置信息列表
     */
    public ButtonPaneBuilder<B> buttonSetList(List buttonSetList){
        this.buttonSetList = buttonSetList;
        return this;
    }
    /**
     * 设置按钮事件处理
     * @param buttonEventHander 事件处理
     */
    public ButtonPaneBuilder<B> buttonEventHander(EventHandler buttonEventHander){
        this.buttonEventHander = buttonEventHander;
        return this;
    }
    /**
     * 配置单元高度
     * @param cellHight 单元高度
     */
    public ButtonPaneBuilder<B> cellHight(double cellHight){
        this.cellHight = cellHight;
        return this;
    }
    /**
     * 配置单元宽度
     * @param cellWidth 单元宽度
     * @return 
     */
    public ButtonPaneBuilder<B> cellWidth(double cellWidth){
        this.cellWidth = cellWidth;
        return this;
    }
    /**
     * 配置单元样式
     * @param styleClass
     * @return 
     */
    public ButtonPaneBuilder<B> addBtnStyleClass(String styleClass){
        this.btnStyleClass.add(styleClass);
        return this;
    }
    
    /**
     * 实例化ButtonPane
     * @return ButtonPane
     */
    @Override
    public ButtonPane build() {
        ButtonPane<ButtonBase> buttonPane = new ButtonPane(cellHight,cellWidth,buttonSetList,buttonType,buttonEventHander,btnStyleClass);
        buttonPane.btnList.size();
        return buttonPane;
    }
}