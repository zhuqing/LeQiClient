/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.pop.query;


import com.leqienglish.client.control.pop.LQPopup;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author zhuqing
 * @param <T>
 */
public abstract class LQQueryPopup<T, N extends Node> extends QueryBar {

    private LQPopup lqPopUp;

    /**
     * 弹出框内的节点
     */
    private N content;

    public LQQueryPopup() {
        this.setQueryConsumer((text) -> {
            query(text);
            show();
        });
        
        this.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
              if(event.getCode() == KeyCode.ESCAPE){
                  getHipPopUp().hide();
              }
            }
        });
    }

    protected abstract void query(String text);

    public void close() {
        if (this.getHipPopUp().isShowing()) {
            this.getHipPopUp().hide();
        }

    }

    protected void show() {
        if (this.getContent() == null) {
            return;
        }
        if (this.getHipPopUp().isShowing()) {
            return;
        }
        if (!this.getHipPopUp().getContent().contains(this.getContent())) {
            this.getHipPopUp().getContent().setAll(this.getContent());
        }

        this.getHipPopUp().showV(this);
    }

    /**
     * @return the lqPopUp
     */
    public LQPopup getHipPopUp() {
        if (this.lqPopUp == null) {
            this.lqPopUp = new LQPopup();
            this.lqPopUp.setAutoFix(true);
            this.lqPopUp.setAutoHide(true);
           
        }
        return lqPopUp;
    }

    /**
     * @param hipPopUp the lqPopUp to set
     */
    public void setHipPopUp(LQPopup hipPopUp) {
        this.lqPopUp = hipPopUp;
    }

    /**
     * @return the content
     */
    public N getContent() {
        return content;
    }

    /**
     * @param popContent the content to set
     */
    public void setContent(N content) {
        this.content = content;
    }

}
