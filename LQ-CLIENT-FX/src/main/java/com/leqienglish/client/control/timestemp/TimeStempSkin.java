/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.timestemp;

import com.leqienglish.client.control.CustomSkin;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author zhuqing
 */
public class TimeStempSkin extends CustomSkin<TimeStemp, TimeStempBehavior<TimeStemp>> {

    TextArea sourceTextArea = new TextArea();

    TextArea targetTextArea = new TextArea();
    
    private BorderPane root;

    public TimeStempSkin(TimeStemp timeStemp) {
        super(timeStemp, new TimeStempBehavior<TimeStemp>(timeStemp));
    }

    @Override
    protected void showSkin() {
        super.showSkin(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void initSkin() {
        super.initSkin(); //To change body of generated methods, choose Tools | Templates.
       // root 
    }
    
    private void initListener(){
        
    }

}
