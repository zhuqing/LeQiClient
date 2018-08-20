/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.user.word;

import xyz.tobebetter.entity.Entity;

/**
 *用户添加的单词
 * @author zhuqing
 */
public class UserAndWord extends Entity{
    private String userId;
    private String wordId;
    
    /**
     * 背诵次数
     */
    private Integer reciteCount;

    /**
     * 背诵状态0：未背，1，正在背，2，已背
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

    /**
     * @return the reciteCount
     */
    public Integer getReciteCount() {
        return reciteCount;
    }

    /**
     * @param reciteCount the reciteCount to set
     */
    public void setReciteCount(Integer reciteCount) {
        this.reciteCount = reciteCount;
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
