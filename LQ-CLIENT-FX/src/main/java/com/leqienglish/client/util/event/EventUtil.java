/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.event;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 *
 * @author zhuleqi
 */
public class EventUtil {
    public static <T> T getEntityFromButton(ActionEvent event){
        if(event == null){
            return null;
        }
        Button button = (Button) event.getSource();
        if(button == null ){
            return null;
        }
        return (T) button.getUserData();
    }
}
