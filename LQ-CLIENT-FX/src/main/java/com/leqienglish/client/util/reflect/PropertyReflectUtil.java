/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author duyi
 */
public class PropertyReflectUtil {

    public static Object getValue(Object obj, String attributeName) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (attributeName == null || attributeName.isEmpty()) {
            return obj;
        }

        if (obj == null) {
            return null;
        }

        if (obj instanceof Map) {
            Map map = (Map) obj;
            return map.get(attributeName);
        }

        return PropertyUtils.getProperty(obj, attributeName);
    }

    public static Object setValue(Object obj, String attributeName, Object value) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (attributeName == null || attributeName.isEmpty()) {
            return value;
        }

        if (obj == null) {
            return null;
        }

        if (obj instanceof Map) {
            Map map = (Map) obj;
            map.put(attributeName, value);
        }

        PropertyUtils.setProperty(obj, attributeName, value);
        return obj;
    }
    
     public static Object setPropertyValue(Object obj, String attributeName, Object value) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Field field = obj.getClass().getDeclaredField(attributeName);
        field.setAccessible(true);
        field.set(obj, value);
        return obj;
    }
}
