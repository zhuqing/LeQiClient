/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.user.recite;

import xyz.tobebetter.entity.Entity;

/**
 *
 * @author zhuleqi
 */
public class UserReciteRecordItem extends Entity{
    
    private String userId;
    private String contentId;
    private String segmentId;
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
     * @return the segmentId
     */
    public String getSegmentId() {
        return segmentId;
    }

    /**
     * @param segmentId the segmentId to set
     */
    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
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
