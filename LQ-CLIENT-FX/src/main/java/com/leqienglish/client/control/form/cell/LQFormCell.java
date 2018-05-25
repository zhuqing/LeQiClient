/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell;



import java.util.logging.Level;
import java.util.logging.Logger;

import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.util.reflect.AttrbuteReflectUtil;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import java.util.Map;
import javafx.scene.control.Label;
import javafx.util.Callback;

/**
 * 
 * @author zhuqing
 * @param <S>
 * @param <T> 
 */
public class LQFormCell<S, T> extends Label {

    /**
     * formCell所在的FormView
     */
    private LQFormView formView;

    /**
     * 反射实体中的字段名或Map的key值
     */
    private String propertyName;
    /**
     * 提示名称
     */
    private String promptText;
    /**
     * 反射出来的值
     */
    private T value;

    /**
     * 实体
     */
    private S item;

    private Callback<Object, String> toTextCallback;

    public LQFormCell() {
        this.getStyleClass().remove("label");
        this.getStyleClass().add("hip-form-cell");
    }

    public void updateItem(S item, boolean empty) {

        this.setItem(item);
        if (empty) {
            updateValue(null);
        } else {
            updateEntityProperty(item);
        }
    }

    /**
     * 获取属性的类型
     *
     * @param propertyName
     * @return
     */
    protected Class getFieldType(String propertyName) {
        if (this.getItem() == null) {
            return null;
        }
        return AttrbuteReflectUtil.getFiledType(this.getItem(), propertyName);
    }

    protected void updateValue(T t) {
        this.setValue(t);
        this.setText(toText(t));
        if (t instanceof Number) {
            this.getStyleClass().add("number-form-cell");
        }
    }

    protected String toText(T t) {
        String text = "";
        if (t != null) {
            if (this.getToTextCallback() == null) {
                text = t.toString();
            } else {
                text = this.getToTextCallback().call(t);
            }
        }
        return text;
    }

    protected void updateEntityProperty(S newValue) {

        T t = getValue(newValue);
        updateValue(t);

    }

    /**
     * 从实体S中获取值
     *
     * @param s
     * @return
     */
    protected T getValue(S s) {
        if (s instanceof Map) {
            Map map = (Map) s;
            if (map.containsKey(this.getPropertyName())) {
                return (T) map.get(this.getPropertyName());
            }
        } else {

            try {
                return (T) PropertyReflectUtil.getValue(s, getPropertyName());
            } catch (Exception ex) {
                Logger.getLogger(LQFormCell.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * @return the item
     */
    public S getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(S item) {
        this.item = item;
    }

    public String getPromptText() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

    public LQFormCell clone() {
        try {
            LQFormCell cell = this.getClass().newInstance();
            cell.setPromptText(this.getPromptText());
            cell.setPropertyName(this.getPropertyName());
            cell.setToTextCallback(this.getToTextCallback());
            cell.setPrefSize(this.getPrefWidth(), this.getPrefHeight());
            cell.setFocusTraversable(this.isFocusTraversable());
            return cell;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(LQFormCell.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * @return the formView
     */
    public LQFormView getFormView() {
        return formView;
    }

    /**
     * @param formView the formView to set
     */
    public void setFormView(LQFormView formView) {
        this.formView = formView;
    }

}
