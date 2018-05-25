/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.sourceitem;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author zhuqing
 */
public class SourceItemUtil {

    /**
     * 找到SourceItems中SourceItem中value 等于 t的sourceItem
     *
     * @param <T>
     * @param t
     * @param sourceItems
     * @return
     */
    public static <T> SourceItem<T> getSourceItem(T t, List<SourceItem<T>> sourceItems) {
        if (sourceItems == null) {
            return null;
        }
        for (SourceItem<T> sourceItem : sourceItems) {
            if (Objects.equals(t, sourceItem.getValue())) {
                return sourceItem;
            }
        }
        return null;
    }

}
