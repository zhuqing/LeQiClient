/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.verification;


import com.leqienglish.util.date.DateUtil;
import com.leqienglish.util.verification.VerificationResult.VerificationLV;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 校验工具
 *
 * @author duyi
 */
public class VerificationUtil {

    public static Set<VerificationResult> verification(Object obj) {
        Set<VerificationResult> verificationResultSet = new HashSet<>();
        if (obj == null) {
            VerificationResult verificationResult = new VerificationResult(VerificationLV.ERROR);
            verificationResult.setMessage("对象不可为空!!!");
            verificationResultSet.add(verificationResult);
            return verificationResultSet;
        }
        Class objClaz = obj.getClass();
        PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(objClaz);
        for (PropertyDescriptor property : properties) {
            String propertyName = property.getName();
            if ("class".equals(propertyName)) {
                continue;
            }
            VerificationResult verificationResult = verification(obj, objClaz, propertyName);
            if (verificationResult == null) {
                continue;
            }
            verificationResultSet.add(verificationResult);
        }
        return verificationResultSet;
    }

    public static VerificationResult verification(Object obj, Class claz, String propertyName) {
        try {
            Object propertyValue = PropertyUtils.getProperty(obj, propertyName);
            Field field = claz.getDeclaredField(propertyName);
            NonEmpty nonEmpty = field.getAnnotation(NonEmpty.class);
            if (nonEmpty != null && nonEmpty.message() != null && !nonEmpty.message().isEmpty()) {
                if (propertyValue == null || propertyValue.toString().isEmpty()) {
                    VerificationResult verificationResult = new VerificationResult(VerificationLV.ERROR);
                    verificationResult.setMessage(nonEmpty.message());
                    return verificationResult;
                }
            }
            if (propertyValue == null) {
                return null;
            }
            RegExp regExp = field.getAnnotation(RegExp.class);
            if (regExp != null && regExp.value() != null && !regExp.value().isEmpty()) {
                Pattern pattern = Pattern.compile(regExp.value());
                Matcher matcher = pattern.matcher(getStrValue(propertyValue));
                boolean matcherResult = matcher.matches();
                if (!matcherResult) {
                    VerificationResult verificationResult = new VerificationResult(regExp.verificationLV());
                    verificationResult.setMessage(regExp.message());
                    return verificationResult;
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public static VerificationResult verificationNonEmpty(Object obj, Class claz, String propertyName) {
        try {
            Object propertyValue = PropertyUtils.getProperty(obj, propertyName);
            if (propertyValue == null || propertyValue.toString().isEmpty()) {
                VerificationResult verificationResult = new VerificationResult(VerificationLV.ERROR);
                verificationResult.setMessage(propertyName + "不可为空!!!");
                return verificationResult;
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(VerificationUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static VerificationResult verification(Object obj, Class claz, String propertyName, String regExp, VerificationLV verificationLV, String message) {
        try {
            Object propertyValue = PropertyUtils.getProperty(obj, propertyName);
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(getStrValue(propertyValue));
            boolean matcherResult = matcher.matches();
            if (!matcherResult) {
                VerificationResult verificationResult = new VerificationResult(verificationLV);
                verificationResult.setMessage(message);
                return verificationResult;
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(VerificationUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static CharSequence getStrValue(Object propertyValue) {
        if (propertyValue instanceof Date) {
            String dateStr = DateUtil.toDateFmt((Date) propertyValue, DateUtil.YYYYMMDDHHMMSS);
            return dateStr;
        }
        return propertyValue.toString();
    }
}
