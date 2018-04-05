/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author duyi
 */
public class AttrbuteReflectUtil {

    public static Object getValue(Object obj, String attributeName) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (obj == null) {
            return null;
        }
        if (attributeName == null || attributeName.isEmpty()) {
            return obj;
        }

        if (obj instanceof Map) {
            Map map = (Map) obj;
            return map.get(attributeName);
        }
        Class claz = obj.getClass();
        Field field = AttrbuteReflectUtil.getDeclaredField(claz, attributeName);
        if (field == null) {
            return null;
        }
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param clazz对象的类型
     * @param object :
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {

        if (Map.class.isAssignableFrom(clazz)) {
            return null;
        }

        Field field = null;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
            }
        }
        return null;
    }

    /**
     * 获取属性的类型
     *
     * @param <T>
     * @param t
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    public static <T> Class getFiledType(T t, String fieldName) {

        if (Map.class.isAssignableFrom(t.getClass())) {
            Map map = (Map) t;
            if (map.containsKey(fieldName)) {
                return map.get(fieldName).getClass();
            }
            return null;
        }

        try {
            PropertyDescriptor des = PropertyUtils.getPropertyDescriptor(t, fieldName);
            return des.getPropertyType();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(AttrbuteReflectUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
