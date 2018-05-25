/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.combobox;


import com.leqienglish.client.util.sourceitem.SourceItem;
import com.sun.javafx.scene.control.behavior.BehaviorBase;

/**
 *
 * @author zhuqing
 */
public class LQFilterableComboboxBehavior<S extends SourceItem> extends BehaviorBase<LQFilterableCombobox<S>>{
    
    public LQFilterableComboboxBehavior(LQFilterableCombobox<S> control) {
        super(control, null);
    }
    
}
