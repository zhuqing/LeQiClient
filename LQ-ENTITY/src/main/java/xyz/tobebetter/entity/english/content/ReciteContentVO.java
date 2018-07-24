/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english.content;

import xyz.tobebetter.entity.english.Content;

/**
 *正在背诵的文章的实体
 * @author zhuleqi
 */
public class ReciteContentVO extends Content {

    private String userId;

    /**
     * w完成率0-100
     */
    private Integer finishedPercent;

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
}
