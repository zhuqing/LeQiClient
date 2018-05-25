/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.xml.xstream;

import com.thoughtworks.xstream.XStream;

/**
 *
 * @author duyi
 */
public class XStreamUtil {

    /**
     * 用于Java对象与xml之间的转换
     */
    private static XStream xStream;

    public static void initXStream() {
        xStream = new XStream();
    }

    public static XStream getXStream() {
        if (xStream == null) {
            initXStream();
        }
        return xStream;
    }

    public static void omitField(Class claz, String fieldName) {
        getXStream().omitField(claz, fieldName);
    }

    /**
     * xml字符串转换为java对象
     *
     * @param xmlStr
     * @return ComponentConfig对象
     */
    public static Object xmlToBean(String xmlStr) throws Exception {
        if (getXStream() == null) {
            throw new Exception("XStream未初始化");
        }
        return getXStream().fromXML(xmlStr);
    }

    /**
     * java对象转换为xml字符串
     *
     * @param xmlStr
     * @return ComponentConfig对象
     */
    public static String beanToXml(Object obj) throws Exception {
        if (getXStream() == null) {
            throw new Exception("XStream未初始化");
        }
        return getXStream().toXML(obj);
    }

}
