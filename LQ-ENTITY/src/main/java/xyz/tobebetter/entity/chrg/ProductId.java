/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.chrg;

import xyz.tobebetter.entity.Entity;

/**
 * 系统的所有商品，实体，虚拟（会员）
 *
 * @author zhuqing
 */
public class ProductId extends Entity {

    /**
     * 商品名称
     */
    private String productName;
    /**
     * 价格
     */
    private Double price;
    /**
     * 描述
     */
    private String description;
    /**
     * 类型
     */
    private Integer type;

    /**
     * 商品名称
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 商品名称
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 价格
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 价格
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 描述
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 类型
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * 类型
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }
}
