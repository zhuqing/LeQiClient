/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english.word.user;

import xyz.tobebetter.entity.Entity;

/**
 *用户已经背诵完成的段
 * @author zhuqing
 */
public class UserAndSegment extends Entity{
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 背诵的段Id
     */
    private String segmentId;
    /**
     * 段所在的文章id
     */
    private String contentId;

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


    
}
