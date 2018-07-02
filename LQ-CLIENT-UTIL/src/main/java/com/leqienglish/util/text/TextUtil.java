/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.text;



/**
 *
 * @author zhuqing
 */
public class TextUtil {

    /**
     *
     * 差分时间字符串
     *
     * @param str
     * @return
     */
    public static String[] splitDate(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return str.split("[ :-]");
    }
    
    public static String[] splitWords(String str){
           if (str == null || str.isEmpty()) {
            return null;
        }
        return str.split("[ ';,.\n0-9\\/]");
 
    }

    public static int textAllLength(String[] strArr, int endIndex) {
        int length = 0;
        for (int i = 0; i < endIndex && i < strArr.length; i++) {
            length += strArr[i].length();
        }

        return length;
    }

    /**
     * 填充字符
     *
     * @param source
     * @param fillText
     * @param maxLenth
     * @param fillDir
     * @return
     */
    public static String fillText(String source, String fillText, int maxLenth, FILL_DIRACTION fillDir) {
        if (source == null) {
            return "";
        }
        int fillLength = maxLenth - source.length();
        if (fillLength <= 0) {
            return source;
        }

        StringBuilder fillTextB = new StringBuilder();

        while (fillTextB.length() < fillLength) {
            fillTextB.append(fillText);
        }
        String fillText1 = fillTextB.substring(0, fillLength);
        StringBuilder textSb = new StringBuilder(source);
        if (fillDir == FILL_DIRACTION.HEAD) {
            textSb.insert(0, fillText1);
        } else {
            textSb.append(fillText1);
        }

        return textSb.toString();
    }
    
    /**
     * 填充方向
     */
    public enum FILL_DIRACTION {
        HEAD("头"),
        TAIL("尾");
        
        private String desc;
        
        private FILL_DIRACTION(String desc){
            this.desc = desc;
        }

        /**
         * @return the desc
         */
        public String getDesc() {
            return desc;
        }
    }
}
