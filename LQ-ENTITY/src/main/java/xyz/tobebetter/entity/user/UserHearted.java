/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.user;

import xyz.tobebetter.entity.Entity;

/**
 *
 * @author zhuleqi
 */
public class UserHearted extends Entity {
    /**
     * 点赞的用户Id
     */
    private String userId;
    /**
     * 点赞的内容ID；
     */
    private String targetId;
    /**
     * 点赞的内容的类型，0:content,1:Segment
     */
    private Integer type;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the targetId
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * @param targetId the targetId to set
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }
    
}
