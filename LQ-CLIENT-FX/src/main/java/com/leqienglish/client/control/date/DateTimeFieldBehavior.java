/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import java.util.ArrayList;

/**
 *
 * @author zhuqing
 */
public class DateTimeFieldBehavior extends BehaviorBase<DateTimeField> {

    public DateTimeFieldBehavior(DateTimeField control) {
        super(control, new ArrayList<KeyBinding>());
    }

}
