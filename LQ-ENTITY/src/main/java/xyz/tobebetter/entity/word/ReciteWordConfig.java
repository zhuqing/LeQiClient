/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.word;

import java.util.Objects;
import xyz.tobebetter.entity.Entity;

/**
 * 背诵单词的配置
 *
 * @author zhuleqi
 */
public class ReciteWordConfig extends Entity {

    private String userId;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.userId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReciteWordConfig other = (ReciteWordConfig) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.reciteNumberPerDay, other.reciteNumberPerDay)) {
            return false;
        }
        if (!Objects.equals(this.hasReciteNumber, other.hasReciteNumber)) {
            return false;
        }
        if (!Objects.equals(this.myWordsNumber, other.myWordsNumber)) {
            return false;
        }
        return true;
    }
    /**
     * 每日背诵的单词数,默认是10个
     */
    private Integer reciteNumberPerDay = 10;
    /**
     * 背诵的单词数
     */
    private Integer hasReciteNumber;
    /**
     * 我的单词本中的单词数
     */
    private Integer myWordsNumber;

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
     * @return the reciteNumberPerDay
     */
    public Integer getReciteNumberPerDay() {
        return reciteNumberPerDay;
    }

    /**
     * @param reciteNumberPerDay the reciteNumberPerDay to set
     */
    public void setReciteNumberPerDay(Integer reciteNumberPerDay) {
        this.reciteNumberPerDay = reciteNumberPerDay;
    }

    /**
     * @return the hasReciteNumber
     */
    public Integer getHasReciteNumber() {
        return hasReciteNumber;
    }

    /**
     * @param hasReciteNumber the hasReciteNumber to set
     */
    public void setHasReciteNumber(Integer hasReciteNumber) {
        this.hasReciteNumber = hasReciteNumber;
    }

    /**
     * @return the myWordsNumber
     */
    public Integer getMyWordsNumber() {
        return myWordsNumber;
    }

    /**
     * @param myWordsNumber the myWordsNumber to set
     */
    public void setMyWordsNumber(Integer myWordsNumber) {
        this.myWordsNumber = myWordsNumber;
    }
    
    
}
