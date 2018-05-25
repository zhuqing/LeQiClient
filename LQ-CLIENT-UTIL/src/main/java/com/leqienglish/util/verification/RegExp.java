/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.verification;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.leqienglish.util.verification.VerificationResult.VerificationLV;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author duyi
 */
@JsonInclude(Include.NON_NULL)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegExp {

    /**
     * 正则表达式数组
     *
     * @return
     */
    String value() default "";

    /**
     * 错误级别
     *
     * @return
     */
    VerificationLV verificationLV() default VerificationLV.ERROR;

    /**
     * 错误消息
     *
     * @return
     */
    String message() default "数据有问题";
}
