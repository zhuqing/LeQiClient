/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.timestemp;

import com.leqienglish.client.control.audio.AudioPlay;
import com.sun.javafx.scene.control.behavior.BehaviorBase;

/**
 *
 * @author zhuqing
 * @param <C>
 */
public class TimeStempBehavior<C extends TimeStemp> extends BehaviorBase<C> {
   
    public TimeStempBehavior(C control) {
        super(control,null);
    }
   
}
