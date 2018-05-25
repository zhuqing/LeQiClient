/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.cascade;


import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.TreeItem;

/**
 *
 * @author zhuqing
 */
public class LQCustomCascade<T> extends LQCascade<T> {

    /**
     * 显示文本所在的字段
     */
    private String textPropertyName;
    /**
     * 值所在的字段
     */
    private String valuePropertyName;

    /**
     *
     * @param values
     */
    @Override
    protected void toText(List<TreeItem<T>> values) {
        if (values == null || values.isEmpty()) {
            this.setText("");
            return;
        }
        List<T> dicts = values.stream().map((TreeItem<T> treeItem) -> treeItem.getValue()).collect(Collectors.toList());
        try {
            String text = (String) PropertyReflectUtil.getValue(dicts.get(dicts.size() - 1), getTextPropertyName());
            this.setText(text);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(LQCustomCascade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected Object getValue(T t) {
        if (t == null) {
            return null;
        }
        try {
            return PropertyReflectUtil.getValue(t, getValuePropertyName());
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(LQCustomCascade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    protected String getShowPropertyName() {
        return getTextPropertyName();
    }

    /**
     * @return the textPropertyName
     */
    public String getTextPropertyName() {
        return textPropertyName;
    }

    /**
     * @param textPropertyName the textPropertyName to set
     */
    public void setTextPropertyName(String textPropertyName) {
        this.textPropertyName = textPropertyName;
    }

    /**
     * @return the valuePropertyName
     */
    public String getValuePropertyName() {
        return valuePropertyName;
    }

    /**
     * @param valuePropertyName the valuePropertyName to set
     */
    public void setValuePropertyName(String valuePropertyName) {
        this.valuePropertyName = valuePropertyName;
    }

    @Override
    protected Map<String, List<TreeItem<T>>> createFilterMap(List<TreeItem<T>> items) {
//        Map<String, TreeItem<T>> map = new HashMap<>();
//        List<TreeItem<T>> leafs = TreeItemUtil.getAllLeaf(items);
//
//        for (TreeItem<T> item : leafs) {
//            String key = getNewKey(getKey(item), map);
//            map.put(key, item);
//        }
//        return map;
        return null;
    }

    private String getNewKey(String key, Map<String, TreeItem<T>> map) {
        do {
            if (!map.containsKey(key)) {
                return key;
            } else {
                key += key;
            }
        } while (true);

    }

    private String getKey(TreeItem<T> item) {
        String text = " ";
        try {
            text = (String) PropertyReflectUtil.getValue(item.getValue(), getTextPropertyName());
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(LQCustomCascade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return text;
    }
}
