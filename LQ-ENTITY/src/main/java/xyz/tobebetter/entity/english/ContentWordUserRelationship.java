/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english;

import xyz.tobebetter.entity.Entity;

/**
 *
 * @author zhuqing
 */
public class ContentWordUserRelationship extends Entity {

    private String contentId;
    private String userId;
    private String wordId;

    public static ContentWordUserRelationship create(String contentId, String userId, String wordId) {
        ContentWordUserRelationship ship = new ContentWordUserRelationship();
        ship.setContentId(contentId);
        ship.setUserId(userId);
        ship.setWordId(wordId);
        return ship;
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
     * @return the wordId
     */
    public String getWordId() {
        return wordId;
    }

    /**
     * @param wordId the wordId to set
     */
    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

}
