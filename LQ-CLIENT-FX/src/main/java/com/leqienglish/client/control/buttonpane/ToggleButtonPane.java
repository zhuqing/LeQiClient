

package com.leqienglish.client.control.buttonpane;


import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Skin;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 *
 * 开闭按钮面板
 * @author duyi
 * @version 2013-7-2
 */
public class ToggleButtonPane extends ButtonPane<ToggleButton>{
    /**
     * ToggleButton有组属性，一组内只能有一个按钮处于选中状态。
     */
    private ToggleGroup toggleGroup = new ToggleGroup();
    /**
     * 根据index选中一个按钮
     */
    private IntegerProperty selectedIndex = new SimpleIntegerProperty();
    /**
     * 根据按钮文本选中一个按钮
     */
    private StringProperty selectedText = new SimpleStringProperty();
    
    /**
     * 构造方法
     */
    public ToggleButtonPane() {
        this.getStyleClass().add(ToggleButtonPane.class.getSimpleName());
    }
    
    /**
     * 构造方法
     * @param cellHight 单元高度
     * @param cellWidth 单元宽度
     * @param buttonSetList 配置信息列表
     * @param buttonEventHander 事件处理 
     */
    public ToggleButtonPane(double cellHight,double cellWidth,List buttonSetList,EventHandler buttonEventHander) {
        super(cellHight,cellWidth,buttonSetList,new ToggleButton(),buttonEventHander,null);
        this.getStyleClass().add(ToggleButtonPane.class.getSimpleName());
    }
    
    /**
     * 重新配置选中状态
     * @param index buttonList的索引
     */
    public void setSelect(int index){
        selectedIndex.setValue(index);
    }
    
    /**
     * 重新配置选中状态
     * @param text 根据按钮显示的值选中一个按钮
     */
    public void setSelect(String text){
        selectedText.setValue(text);
    }
    
    /**
     *  清除选中状态
     */
    public void clearSelect(){
        for(ToggleButton toggleButton:btnList){
            if(toggleButton.isSelected()){
                toggleButton.setSelected(false);
            }
        }
    }
    
    /**
     * 重写默认皮肤
     * @return
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new ToggleButtonPaneSkin(this);
    }
    /**
     * 得到当前开闭组
     * @return 
     */
    public ToggleGroup getToggleGroup() {
        return toggleGroup;
    }
    /**
     * 配置开闭组信息
     * @param toggleGroup
     */
    public void setToggleGroup(ToggleGroup toggleGroup) {
        for(ToggleButton toggleButton:btnList){
            toggleButton.setToggleGroup(toggleGroup);
        }
        this.toggleGroup = toggleGroup;
    }

    public IntegerProperty getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(IntegerProperty selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public StringProperty getSelectedText() {
        return selectedText;
    }

    public void setSelectedText(StringProperty selectedText) {
        this.selectedText = selectedText;
    }
    
    
}
