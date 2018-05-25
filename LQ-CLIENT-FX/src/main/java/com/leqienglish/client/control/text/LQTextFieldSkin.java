/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.text;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

/**
 *
 * @author zhuqing
 */
public class LQTextFieldSkin extends TextFieldSkin {

    public LQTextFieldSkin(TextField textField) {
        super(textField);
    }

    @Override
    protected void handleInputMethodEvent(InputMethodEvent event) {
        String text = this.getSkinnable().getText();
        super.handleInputMethodEvent(event);

        LQTextField hipTextField = (LQTextField) this.getSkinnable();
        if (hipTextField.isUnableReplace()) {
            this.getSkinnable().setText(text);
            hipTextField.setUnableReplace(false);
        }
    }

}
