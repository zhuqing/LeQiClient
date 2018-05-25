/*
 * License GNU LGPL
 * Copyright (C) 2012 Amrullah <amrullah@panemu.com>.
 */
package com.leqienglish.client.util.local;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * （目前客户端工具类未整理）
 *
 * DataGrid工具类，主要声明国际化配置文件、常数、以及公共类型转换方法
 *
 * @author Huangjijun(huangjijun@bjgoodwill.com)
 * @author Amrullah (amrullah@panemu.com)
 */
public class LocalUtil {

    /**
     * 配置属性
     */
    private static Properties prop = null;
    /**
     * 语言文件
     */
    private static Properties appLanguage = null;

    public static Properties getProp() {
        return prop;
    }

    public static void setProp(Properties prop) {
        LocalUtil.prop = prop;
    }

    public static Properties getAppLanguage() {
        if (appLanguage == null) {
            appLanguage = new Properties();
            InputStream is = LocalUtil.class.getResourceAsStream("/property/language.properties");
            try {
                appLanguage.load(is);
            } catch (IOException ex) {
                Logger.getLogger(LocalUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return appLanguage;
    }

    public static void setAppLanguage(Properties appLanguage) {
        LocalUtil.appLanguage = appLanguage;
    }

    /**
     * 通过键名称获取配置文件
     *
     * @param key 键
     * @return 值
     * @throws java.io.IOException
     */
    public static String getHIPConfigValue(String key) throws IOException {
        Properties properties = prop;
        if (properties == null || key == null || key.trim().equals("")) {
            return null;
        }
        String value = properties.getProperty(key);
        if (value == null || value.trim().equals("")) {
            return key;
        }
        return value;
    }

    /**
     * 通过键名称获取本地化信息资源库中对应的值
     *
     * @param key 键
     * @return 值
     * @throws java.io.IOException
     */
    public static String getLiteral(String key) throws IOException {
        Properties literalBundle = getAppLanguage();
        if (literalBundle == null) {
            return key;
        }
        if (key == null || key.trim().equals("")) {
            return key;
        }
        if (literalBundle.containsKey(key)) {
            return literalBundle.getProperty(key);
        }
        return key;

    }

//    /**
//     * 通过键名称和对应的参数数组获取资源库中对应的值 参数被包含在值中
//     *
//     * @param key 键
//     * @param param 参数数组
//     * @return 值
//     * @throws java.io.IOException
//     */
//    public static String getLiteral(String key, Object... param) throws IOException {
//        ResourceBundle literalBundle = appLanguage;
//        if (literalBundle == null) {
//            return key;
//        }
//        if (key == null || key.trim().equals("")) {
//            return key;
//        }
//        if (param == null || param.length == 0) {
//            return key;
//        }
//
//        if (literalBundle.containsKey(key)) {
//            return MessageFormat.format(literalBundle.getString(key), param);
//        }
//
//        return key;
//
//    }
    /**
     * 打开指定文件 在不同OS中
     *
     * @param fileToOpen 需要打开的文件名
     * @throws Exception 打开过程中出现异常
     */
    public static void openFile(String fileToOpen) throws Exception {
        boolean windows = false;
        boolean mac = false;
        String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Windows")) {
            windows = true;
        } else if (os != null && os.toLowerCase().indexOf("mac") != -1) {
            mac = true;
        }
        String cmd;
        if (windows) {
            cmd = "rundll32 url.dll,FileProtocolHandler " + fileToOpen;
            Process p = Runtime.getRuntime().exec(cmd);
        } else if (mac) {
            cmd = "open " + fileToOpen;
            Process p = Runtime.getRuntime().exec(cmd);
        } else {
            cmd = "edit " + fileToOpen;
            Process p = Runtime.getRuntime().exec(cmd);
        }
    }

}
