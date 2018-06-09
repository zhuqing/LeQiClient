/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.chrg;

import xyz.tobebetter.entity.Entity;

/**
 * 用户费用明细
 *
 * @author zhuqing
 */
public class UserCharge extends Entity {

    /**
     * 购买用户Id
     */
    private String userId;
    /**
     * 费用
     */
    private Double amount;

    /**
     * 个数
     */
    private Integer num;

    /**
     * 商品Id
     */
    private String productId;
    /**
     * 其他信息
     */
    private String description;

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
     * 费用
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 费用
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 个数
     * @return the num
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 个数
     * @param num the num to set
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 商品Id
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 商品Id
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 其他信息
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 其他信息
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
