/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjgoodwill.hip.util.file.excel;

import com.bjgoodwill.hip.util.date.DateUtil;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.LoggerFactory;

/**
 * 从Excel中读取数据
 *
 * @author duyi
 */
public class ExcelBeansUtil {

    /**
     * 日志
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExcelBeansUtil.class);

    private final Map<String, Object[][]> excelDatas;

    public ExcelBeansUtil(String path) throws IOException {
        excelDatas = ExcelUtil.readExcel(path, (Integer[]) null);
    }

    public Map<String, Object> readExcelSheet(String sheetName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (excelDatas == null) {
            return null;
        }
        Object[][] sheetDatas = excelDatas.get(sheetName);
        if (sheetDatas == null) {
            return null;
        }
        return readExcelSheet(sheetDatas);
    }

    private Map<String, Object> readExcelSheet(Object[][] sheetDatas) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String className = (String) sheetDatas[0][0];
        Class claz = Class.forName(className);
        Map<String, Class> propertyTypeMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        for (int i = 3; i < sheetDatas.length; i++) {
            Object[] objs = sheetDatas[i];
            Object entity = claz.newInstance();
            if (entity == null) {
                continue;
            }
            dataMap.put(objs[0].toString(), entity);
            for (int j = 1; j < objs.length; j++) {
                if (sheetDatas[2][j] == null || "".equals(sheetDatas[2][j])) {
                    continue;
                }
                Object propertyValue = toPropertyValue(entity, objs[j], sheetDatas[2][j].toString(), sheetDatas[1][j], propertyTypeMap);
                PropertyUtils.setProperty(entity, sheetDatas[2][j].toString(), propertyValue);
            }
        }
        return dataMap;
    }

    private Object toPropertyValue(Object entity, Object obj, String propertyName, Object reMark, Map<String, Class> propertyTypeMap) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class propertyType = null;
        if (propertyTypeMap.containsKey(propertyName)) {
            propertyType = propertyTypeMap.get(propertyName);
        } else {
            propertyType = PropertyUtils.getPropertyType(entity, propertyName);
            propertyTypeMap.put(propertyName, propertyType);
        }
        if (obj == null || propertyType == null) {
            return null;
        }
        if (propertyType == String.class) {
            return obj.toString();
        } else if (propertyType == Long.class) {
            return Long.valueOf(obj.toString());
        } else if (propertyType == Integer.class) {
            return Integer.valueOf(obj.toString());
        } else if (propertyType == Short.class) {
            return Short.valueOf(obj.toString());
        } else if (propertyType == Double.class) {
            return Double.valueOf(obj.toString());
        } else if (propertyType == Float.class) {
            return Float.valueOf(obj.toString());
        } else if (propertyType == Date.class) {
            return DateUtil.toDate(obj.toString(), DateUtil.YYYYMMDDHHMMSS);
        }
        System.err.println(reMark + "||" + propertyName + "||" + propertyType + "字段类型配置错误，或未实现该类型的转换！！！");
        return null;
//        String objStr = obj.toString();
//        if ("String".equals(propertyType)) {
//            return objStr;
//        } else if ("Integer".equals(propertyType)) {
//            return Integer.valueOf(objStr);
//        }
//        System.err.println("字段类型配置错误，或未实现该类型的转换！！！");
//        return null;
    }

}
