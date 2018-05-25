/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.listview.cell;

import com.leqienglish.client.control.view.listview.LQListView;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author zhuqing
 */
public class LQListCell<T> extends ListCell<T> {

    private String propertyName;

    private Callback<Object, String> toTextCallback;

    @Override
    public LQListCell clone() throws CloneNotSupportedException {
        try {
            LQListCell listCell = getClass().newInstance();
            listCell.setPropertyName(propertyName);

            return listCell;
        } catch (InstantiationException ex) {
            Logger.getLogger(LQListCell.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LQListCell.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new LQListCell();
    }

    public LQListCell() {
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LQListView listView = (LQListView) getListView();
                if (listView.getCellMouseClickEventHandler() == null) {
                    return;
                }

                listView.getCellMouseClickEventHandler().handle(event);
            }
        });
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        if (empty) {
            this.setText("");
        } else {
            Object value = getValue(item);

            if (this.getToTextCallback() == null) {
                if (value == null) {
                    this.setText("");
                } else {
                    this.setText(value.toString());
                }
            } else {
                this.setText(getToTextCallback().call(value));
            }
        }

    }

    protected Object getValue(T s) {
        if (s == null) {
            return null;
        }
        if (s instanceof Map) {
            Map map = (Map) s;
            if (map.containsKey(this.getPropertyName())) {
                return map.get(this.getPropertyName());
            }
        } else {

            try {
                return PropertyReflectUtil.getValue(s, getPropertyName());
            } catch (Exception ex) {
                // Logger.getLogger(HipFormCell.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    /**
     * @return the propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @param propertyName the propertyName to set
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * @return the toTextCallback
     */
    public Callback<Object, String> getToTextCallback() {
        return toTextCallback;
    }

    /**
     * @param toTextCallback the toTextCallback to set
     */
    public void setToTextCallback(Callback<Object, String> toTextCallback) {
        this.toTextCallback = toTextCallback;
    }
}
