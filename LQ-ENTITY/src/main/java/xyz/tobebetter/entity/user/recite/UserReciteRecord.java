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
public class UserReciteRecord extends Entity{
    private String userId;
    private Long learnTime;
    private Long learnDay;

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
     * @return the learnTime
     */
    public Long getLearnTime() {
        return learnTime;
    }

    /**
     * @param learnTime the learnTime to set
     */
    public void setLearnTime(Long learnTime) {
        this.learnTime = learnTime;
    }

    /**
     * @return the learnDay
     */
    public Long getLearnDay() {
        return learnDay;
    }

    /**
     * @param learnDay the learnDay to set
     */
    public void setLearnDay(Long learnDay) {
        this.learnDay = learnDay;
    }
    
    
}
