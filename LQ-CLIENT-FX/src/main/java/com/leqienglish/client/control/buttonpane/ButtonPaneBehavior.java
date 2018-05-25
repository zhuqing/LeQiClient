/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.buttonpane;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.scene.control.ButtonBase;

/**
 * 按钮面板的Behavior类
 *
 * @author duyi
 * @version 2013-6-20
 * @param <T>
 *
 */
public class ButtonPaneBehavior<T extends ButtonBase> extends BehaviorBase<ButtonPane<T>> {

    public ButtonPaneBehavior(ButtonPane<T> c) {
        super(c, null);
    }

}
