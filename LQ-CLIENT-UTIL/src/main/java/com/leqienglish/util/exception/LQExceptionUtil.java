/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.exception;

/**
 *
 * @author zhuqing
 */
public class LQExceptionUtil {

    /**
     * 必须验证
     *
     * @param required
     * @param message
     * @throws Exception
     */
    public static void required(boolean required, String message) throws Exception {
        if (!required) {
            throw new Exception(message);
        }
    }
}
