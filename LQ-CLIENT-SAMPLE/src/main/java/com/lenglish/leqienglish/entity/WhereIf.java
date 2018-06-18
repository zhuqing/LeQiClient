/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.entity;

/**
 ** <if test="actionType != null">
 * AND ACTION_TYPE like CONCAT(#{actionType,jdbcType=VARCHAR}, '%')
 * </if>
 * @author zhuqing
 */
public class WhereIf {

    /**
     * 实体属性名
     */
    private String propertyName;
    /**
     * 对应的table字段名
     */
    private String filedName;
    /**
     * 数据的类型
     */
    private String type;

    /**
     * @return the propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @param propertyName the propertyName to set
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * @return the filedName
     */
    public String getFiledName() {
        return filedName;
    }

    /**
     * @param filedName the filedName to set
     */
    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
