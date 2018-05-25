package com.leqienglish.client.control.date.pagebar;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * 分页控制条控件
 *
 * @author duyi
 * @version 1.0 26-三月-2014 10:17:54
 */
public class NavigationPageBar extends Control {

    /**
     * 最大页数 默认为1
     */
    private final IntegerProperty maxPage = new SimpleIntegerProperty(1);
    /**
     * 当前页数 默认为1
     */
    private final IntegerProperty nowPage = new SimpleIntegerProperty(1);

    /**
     * 要显示的页码的数量 默认为5
     */
    private int showPageBtn = 5;
    
  

    /**
     * 是否显示首页跟末页
     */
    private boolean isShowFirstAndLastBtn = true;

    /**
     * 是否显示上一页跟下一页 如果为false isShowFirstAndLastBtn= false
     */
    private boolean isShowPreAndNextBtn = true;
    /**
     * 是否显示页码输入框
     */
    private boolean isShowInput = true;
    /**
     * 是否显示总页码
     */
    private boolean isShowTotalLabel = true;

    public NavigationPageBar() {
         this.getStyleClass().add("pageBar");
    }

    public NavigationPageBar(int nowPage, int maxPage) {
        this.maxPage.setValue(maxPage);
        this.nowPage.setValue(nowPage);
         this.getStyleClass().add("pageBar");
    }
 
    /**
     * 
     * @param nowPage 当前页
     * @param maxPage  总页数
     * @param showNum 分页控制条显示页数
     * @param isShowInput  是否显示 页码输入框
     * @param isShowPreAndNextBtn 是否显示上一页下一页
     * @param isShowFirstAndLastBtn 是否显示首页末页
     * @param isShowPageLabel  是否显示总页数
     */
    public NavigationPageBar(int nowPage, int maxPage, int showNum,  boolean isShowInput, boolean isShowPreAndNextBtn, boolean isShowFirstAndLastBtn, boolean isShowPageLabel) {
        this.maxPage.setValue(maxPage);
        this.nowPage.setValue(nowPage);
        this.showPageBtn = showNum;
        this.isShowFirstAndLastBtn = isShowFirstAndLastBtn;
        this.isShowInput = isShowInput;
        this.isShowPreAndNextBtn = isShowPreAndNextBtn;
        this.isShowTotalLabel = isShowPageLabel;
        if (!isShowPreAndNextBtn) {
            
            this.isShowFirstAndLastBtn = false;
            this.isShowTotalLabel = false;
        }
        this.getStyleClass().add("pageBar");
    }
    
    /**
     * 重写默认皮肤
     *
     * @return
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new NavigationPageBarSkin(this);
        // return null;
    }
    /**
     * @param maxPage the maxPage to set
     */
    public final void setMaxPage(Integer maxPage) {
        this.maxPage.set(maxPage);
    }

    /**
     * @param nowPage the nowPage to set
     */
    public final void setNowPage(Integer nowPage) {
        this.nowPage.setValue(nowPage);
    }

    public final Integer getMaxPage() {
        return maxPage.getValue();
    }

    public final Integer getNowPage() {
        return nowPage.getValue();
    }

    public final IntegerProperty maxPageProperty() {
        return this.maxPage;
    }

    public final IntegerProperty nowPageProperty() {
        return this.nowPage;
    }

    /**
     * @return the showPageBtn
     */
    public int getShowPageBtn() {
        return showPageBtn;
    }

    /**
     * @return the isShowFirstAndLastBtn
     */
    public boolean isIsShowFirstAndLastBtn() {
        return isShowFirstAndLastBtn;
    }

    /**
     * @return the isShowPreAndNextBtn
     */
    public boolean isIsShowPreAndNextBtn() {
        return isShowPreAndNextBtn;
    }
    
     /**
     * @return the isShowPreAndNextBtn
     */
    public boolean isIsShowTotalLabel() {
        return isShowTotalLabel;
    }

    /**
     * @return the isShowInput
     */
    public boolean isIsShowInput() {
        return isShowInput;
    }

    /**
     * 是否显示首页跟末页
     * @param isShowFirstAndLastBtn the isShowFirstAndLastBtn to set
     */
    public void setIsShowFirstAndLastBtn(boolean isShowFirstAndLastBtn) {
        this.isShowFirstAndLastBtn = isShowFirstAndLastBtn;
    }

    /**
     * 是否显示上一页跟下一页 如果为false isShowFirstAndLastBtn= false
     * @param isShowPreAndNextBtn the isShowPreAndNextBtn to set
     */
    public void setIsShowPreAndNextBtn(boolean isShowPreAndNextBtn) {
        this.isShowPreAndNextBtn = isShowPreAndNextBtn;
    }

    /**
     * 是否显示页码输入框
     * @param isShowInput the isShowInput to set
     */
    public void setIsShowInput(boolean isShowInput) {
        this.isShowInput = isShowInput;
    }

    /**
     * 是否显示总页码
     * @param isShowTotalLabel the isShowTotalLabel to set
     */
    public void setIsShowTotalLabel(boolean isShowTotalLabel) {
        this.isShowTotalLabel = isShowTotalLabel;
    }

    /**
     * 要显示的页码的数量 默认为5
     * @param showPageBtn the showPageBtn to set
     */
    public void setShowPageBtn(int showPageBtn) {
        this.showPageBtn = showPageBtn;
    }

}
