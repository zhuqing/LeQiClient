/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.user.content;

import xyz.tobebetter.entity.Entity;

/**
 *用户和内容的关系表，用户订阅的内容
 * @author zhuqing
 */
public class UserAndContent extends Entity{
    
    private String userId;
    /**
     * contentId
     */
    private String contentId;
    
    /**
     * w完成率0-100
     */
    private Integer finishedPercent;
    
    
    
    

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
     * @return the contentId
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * @param contentId the contentId to set
     */
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    /**
     * @return the finishedPercent
     */
    public Integer getFinishedPercent() {
        return finishedPercent;
    }

    /**
     * @param finishedPercent the finishedPercent to set
     */
    public void setFinishedPercent(Integer finishedPercent) {
        this.finishedPercent = finishedPercent;
    }

  

    
}
