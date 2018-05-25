/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.textinput;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author zhuqing
 */
public class TextInputControlUtil {

    public static void focusAndSelectAll(TextInputControl textInput) {
        if (textInput == null) {
            return;
        }
        JavaFxObservable.changesOf(textInput.focusedProperty())
                .filter((c) -> c.getNewVal())
                .subscribe((c) -> textInput.selectAll());
    }
    
    public static void mouseClickAndSelectAll(TextInputControl textInput) {
        if (textInput == null) {
            return;
        }
        JavaFxObservable.eventsOf(textInput,MouseEvent.MOUSE_RELEASED)
                .subscribe((c) -> textInput.selectAll());
    }
}
