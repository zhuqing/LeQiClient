/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.entity;

import java.util.List;

/**
 * <select id="queryDrugInfoLikeDrugName" resultMap="BaseResultMap" parameterType="com.bjgoodwill.hip.entity.pham.drug.DrugInfo"
 * >
 * select
 * <include refid="Base_DrugInfo_List" />
 * from DRUG_INFO
 * <where>
 * <if test="actionType != null">
 * AND ACTION_TYPE like CONCAT(#{actionType,jdbcType=VARCHAR}, '%')
 * </if>
 *
            <if test="drugName != null">
 * AND DRUG_NAME like CONCAT(CONCAT('%', #{drugName,jdbcType=VARCHAR}), '%')
 * </if>
 *
             <if test="drugName != null">
 * OR INPUT_PY like CONCAT(CONCAT('%', #{drugName,jdbcType=VARCHAR}), '%')
 * </if>
 *
            <if test="statusCode != null">
 * AND STATUS_CODE=#{statusCode,jdbcType=VARCHAR}
 * </if>
 * </where>
 * </select>
 *
 * @author zhuqing
 */
public class Mapper {
    
    private String daoClassPath;

    private String daoMethodName;
    private String resultMap;
    private String parameterType;
    private String table;
    private String selectField;
    private List<WhereIf> wheres;

    /**
     * @return the daoMethodName
     */
    public String getDaoMethodName() {
        return daoMethodName;
    }

    /**
     * @param daoMethodName the daoMethodName to set
     */
    public void setDaoMethodName(String daoMethodName) {
        this.daoMethodName = daoMethodName;
    }

    /**
     * @return the resultMap
     */
    public String getResultMap() {
        return resultMap;
    }

    /**
     * @param resultMap the resultMap to set
     */
    public void setResultMap(String resultMap) {
        this.resultMap = resultMap;
    }

    /**
     * @return the parameterType
     */
    public String getParameterType() {
        return parameterType;
    }

    /**
     * @param parameterType the parameterType to set
     */
    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    /**
     * @return the table
     */
    public String getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * @return the selectField
     */
    public String getSelectField() {
        return selectField;
    }

    /**
     * @param selectField the selectField to set
     */
    public void setSelectField(String selectField) {
        this.selectField = selectField;
    }

    /**
     * @return the wheres
     */
    public List<WhereIf> getWheres() {
        return wheres;
    }

    /**
     * @param wheres the wheres to set
     */
    public void setWheres(List<WhereIf> wheres) {
        this.wheres = wheres;
    }

    /**
     * @return the daoClassPath
     */
    public String getDaoClassPath() {
        return daoClassPath;
    }

    /**
     * @param daoClassPath the daoClassPath to set
     */
    public void setDaoClassPath(String daoClassPath) {
        this.daoClassPath = daoClassPath;
    }

    
}
