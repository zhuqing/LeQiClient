/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.observation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author duyi
 */
public abstract class LQChangeListener<T> implements ChangeListener<T>{
    
    public abstract ObservableValue getObservableValue();
    
    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue){
        System.err.println(getObservableValue().hashCode() + "=========================");
    }
}
