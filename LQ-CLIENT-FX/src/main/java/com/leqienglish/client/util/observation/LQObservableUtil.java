/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.observation;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HIP监听工具
 *
 * @author duyi
 */
public class LQObservableUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(LQObservableUtil.class);

    private static final Map<String, ChangeListener> CHANGE_LISTENER_MAP = new HashMap<>();
    
    private static final Map<String, ListChangeListener> LIST_CHANGE_LISTENER_MAP = new HashMap<>();

    public static <T> void addListener(ObservableValue<T> observableValue, Class claz, ChangeListener<T> changeListener) {
        if (removeListener(observableValue, claz, changeListener)) {
            LOGGER.warn(claz.getName() + "对" + observableValue + "添加了多次监听。先删除旧监听，再新增监听。");
        }
        observableValue.addListener(changeListener);
        CHANGE_LISTENER_MAP.put(claz.getName() + observableValue.hashCode(), changeListener);
    }

    public static <T> Boolean removeListener(ObservableValue<T> observableValue, Class claz, ChangeListener<T> changeListener) {
        ChangeListener oldChangeListener = CHANGE_LISTENER_MAP.get(claz.getName() + observableValue.hashCode());
        observableValue.removeListener(changeListener);
        if (oldChangeListener != null && oldChangeListener.equals(changeListener)) {
            CHANGE_LISTENER_MAP.remove(claz.getName() + observableValue.hashCode());
            return true;
        }
        return false;
    }

    public static <T> void addListener(ObservableList<T> observableList, Class claz, ListChangeListener<T> changeListener) {
        if (removeListener(observableList, claz, changeListener)) {
            LOGGER.warn(claz.getName() + "对" + observableList + "添加了多次监听。先删除旧监听，再新增监听。");
        }
        observableList.addListener(changeListener);
        LIST_CHANGE_LISTENER_MAP.put(claz.getName() + observableList.hashCode(), changeListener);
    }
    
    public static <T> Boolean removeListener(ObservableList<T> observableList, Class claz, ListChangeListener<T> changeListener) {
        ListChangeListener oldChangeListener = LIST_CHANGE_LISTENER_MAP.get(claz.getName() + observableList.hashCode());
        observableList.removeListener(changeListener);
        if (oldChangeListener != null && oldChangeListener.equals(changeListener)) {
            LIST_CHANGE_LISTENER_MAP.remove(claz.getName() + observableList.hashCode());
            return true;
        }
        return false;
    }
}
