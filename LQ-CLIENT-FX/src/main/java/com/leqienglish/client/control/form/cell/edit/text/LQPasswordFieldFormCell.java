/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.text;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputControl;

/**
 * 
 * @author zhuqing
 * @param <S> 
 */
public class LQPasswordFieldFormCell<S> extends LQTextInputFormCell<S> {

    @Override
    protected TextInputControl createTextField() {
        return new PasswordField();
    }

   
    

   

}
