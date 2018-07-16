/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.user.word;

/**
 *用户关注的单词
 * @author zhuqing
 */
public class UserAndWord {
    private String userId;
    private String wordId;

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
