/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.chrg;

import xyz.tobebetter.entity.Entity;

/**
 *
 * @author zhuqing
 */
public class VIPUser extends Entity{
    /**
     * 产品Id
     */
    private String productId;
    /**
     * 用户Id
     */
    private String userId;
    
    /**
     * 结束时间
     */
    private Long  endDate;

    /**
     * 产品Id
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 产品Id
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 用户Id
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户Id
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 结束时间
     * @return the endDate
     */
    public Long getEndDate() {
        return endDate;
    }

    /**
     * 结束时间
     * @param endDate the endDate to set
     */
    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }
}
