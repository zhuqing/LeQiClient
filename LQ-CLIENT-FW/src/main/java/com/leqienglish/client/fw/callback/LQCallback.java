/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.callback;

import com.leqienglish.client.fw.app.AbstractApplication;
import javafx.util.Callback;

/**
 *
 * @author zhuleqi
 */
public class LQCallback<P,R> implements Callback<P,R>{

    private String beanId;
    
    @Override
    public R call(P p) {
        if(this.getBeanId() == null){
            return null;
        }
        
        Callback<P,R> call =  (Callback<P,R>) AbstractApplication.getContext().getBean(this.getBeanId());
        
        return call.call(p);
    }

    /**
     * @return the beanId
     */
    public String getBeanId() {
        return beanId;
    }

    /**
     * @param beanId the beanId to set
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }
    
    
    
}
