/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.verification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
public @interface NonEmpty {

    /**
     * 正则表达式数组
     *
     * @return
     */
    String message() default "";
}
