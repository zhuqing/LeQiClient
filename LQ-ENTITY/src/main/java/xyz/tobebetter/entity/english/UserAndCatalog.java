/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english;

import xyz.tobebetter.entity.Entity;

/**
 * 用户订阅分类的关系实体
 *
 * @author zhuqing
 */
public class UserAndCatalog extends Entity {

    /**
     * 正在背诵
     */
    public final static Integer RECITING_STATUS = 1;

    /**
     * 背诵完成
     */
    public final static Integer OVER_STATUS = 10;
    /**
     * 用户ID
     */       
    private String userId;
    /**
     * 分类Id
     */
    private String catalogId;

    /**
     * 用户ID
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 分类Id
     * @return the catalogId
     */
    public String getCatalogId() {
        return catalogId;
    }

    /**
     * 分类Id
     * @param catalogId the catalogId to set
     */
    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
    
    
}
