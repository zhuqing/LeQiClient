/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.bool;

/**
 *
 * @author zhangyingchuang
 */
public class BooleanUtil {

    public static Boolean getBoolean(Object object) {
        Boolean bool = false;
        if (object == null) {
            return bool;
        }
        switch (object.getClass().getSimpleName()) {
            case "boolean":
            case "Boolean":
                bool = (Boolean) object;
                break;
            default:
                String text = object.toString();
                bool = !(text.isEmpty() || text.equals("0"));
        }
        return bool;
    }
}
