/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.uf;

import javafx.fxml.Initializable;
import com.leqienglish.client.fw.app.AbstractApplication;
import com.leqienglish.client.fw.LogFacade;

/**
 *
 * @author duyi
 * @param <T>
 */
public abstract class FXMLController<T extends FXMLModel> extends LogFacade implements Initializable {

    private T model;
    
    public FXMLController() {

    }

    public void setModel(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public FXMLModel getModel(String key) {
        return (FXMLModel) AbstractApplication.getContext().getBean(key);
    }

    public abstract void refresh();

    public void refreshView() {
        System.err.println("刷新业务模型：" + getModel().getClass().getName());
    }

    public boolean hide() {
        return true;
    }

    public boolean close() {
        return true;
    }
}
