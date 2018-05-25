/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date;


import com.leqienglish.client.control.pop.LQPopup;
import com.leqienglish.client.control.skin.LQSkin;
import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;
import javafx.stage.WindowEvent;

/**
 *
 * @author zhuqing
 */
public abstract class LQPopupFieldSkin<C extends Control, BB extends BehaviorBase<C>> extends LQSkin<C, BB> {

    /**
     * 弹出框
     */
    private LQPopup lqPopup;
    private Region owner;
    private Region popContent;
    
    public LQPopupFieldSkin(C control, BB behavior) {
        super(control, behavior);
    }
    
    @Override
    protected void showSkin() {
        super.showSkin(); //To change body of generated methods, choose Tools | Templates.
        this.getChildren().add(getOwner());
    }
    
    @Override
    protected void initSkin() {
        super.initSkin(); //To change body of generated methods, choose Tools | Templates.
        this.setPopContent(createPopupContent());
        this.setOwner(createPopupOwner());
        
        createPop();
        
    }
    
    private void createPop() {
        
        this.lqPopup = new LQPopup();
        this.getLQPopup().setAutoFix(true);
        this.getLQPopup().setAutoHide(true);
        this.getLQPopup().setConsumeAutoHidingEvents(false);
        
        this.getLQPopup().getContent().add(getPopContent());
        this.getLQPopup().showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.err.println("showEventHandler:" + newValue);
                if (newValue) {
                    showEventHandler();
                } else {
                    //hiddenEventHandler();
                }
            }
        });
        
        this.getLQPopup().setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                hiddenEventHandler();
            }
        });
        
        this.getSkinnable().getScene().addEventHandler(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
//                System.err.println("SCROLL_STARTED:" + event);
            }
        });
        
        getPopContent().addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
//                System.err.println("MOUSE_EXITED:" + event);
                
            }
        });
        
        getPopContent().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
//                System.err.println("MOUSE_ENTERED:" + event);
                
            }
        });
        
    }
    
    protected abstract Region createPopupContent();
    
    protected abstract Region createPopupOwner();
    
    protected void showV() {
        this.getLQPopup().showV(this.getOwner());
    }
    
    protected void showV(Region owner) {
        if (this.getLQPopup() == null || owner == null || getPopContent() == null) {
            return;
        }
        if (this.getLQPopup().isShowing()) {
            return;
        }
        if (owner == null) {
            owner = this.getOwner();
        }
        this.getLQPopup().showV(owner);
        
    }
    
    protected void hidden() {
        if (this.getLQPopup() == null) {
            return;
        }
        if (this.getLQPopup().isShowing()) {
            this.getLQPopup().hide();
        }
    }

    /**
     * hipPopup显示是触发的
     */
    protected abstract void showEventHandler();

    /**
     * hippopup关闭时触发
     */
    protected abstract void hiddenEventHandler();
    
    protected void autoHiddenEventHandler() {
        System.err.println("autoHiddenEventHandler");
    }

    /**
     * 重新加载并替换Content
     */
    protected void reloadPopupContent() {
        this.setPopContent(createPopupContent());
        this.getLQPopup().getContent().setAll(getPopContent());
    }

    /**
     * @return the owner
     */
    public Region getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Region owner) {
        this.owner = owner;
    }

    /**
     * @return the popContent
     */
    public Region getPopContent() {
        return popContent;
    }

    /**
     * @param popContent the popContent to set
     */
    public void setPopContent(Region popContent) {
        this.popContent = popContent;
    }

    /**
     * 弹出框
     *
     * @return the hipPopup
     */
    public LQPopup getLQPopup() {
        if (lqPopup == null) {
            lqPopup = new LQPopup();
        }
        return lqPopup;
    }
}
