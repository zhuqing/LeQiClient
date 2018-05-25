/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyi
 */
public class PropertyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtil.class);

    private static Map<String, Properties> propertyMap = new HashMap<>();

    public static String getPropertyValue(String propertyUrl, String propertyKey) {
        Properties property = getPropertyFile(propertyUrl);
        if (property == null) {
            return null;
        }
        return property.getProperty(propertyKey);
    }

    public static Set getAllProperty(String propertyUrl) {
        Properties property = getPropertyFile(propertyUrl);
        if (property == null) {
            return null;
        }
        return property.keySet();
    }

    private static Properties getPropertyFile(String propertyUrl) {
        if (propertyMap.containsKey(propertyUrl)) {
            return propertyMap.get(propertyUrl);
        }
        try {
            InputStream is = PropertyUtil.class.getResourceAsStream(propertyUrl);
            Properties properties = new Properties();
            properties.load(is);
            is.close();
            propertyMap.put(propertyUrl, properties);
            return properties;
        } catch (IOException ex) {
            LOGGER.error("加载配置文件失败！！！", ex);
            return null;
        }
    }

}
