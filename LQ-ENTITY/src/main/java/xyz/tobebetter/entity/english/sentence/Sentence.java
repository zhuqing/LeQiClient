/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english.sentence;

import xyz.tobebetter.entity.Entity;

/**
 *
 * @author zhuleqi
 */
public class Sentence extends Entity{
    /**
     * 英文句子
     */
    private String english;
    /**
     * 对应的中文
     */
    private String chinese;
    /**
     * 音频路径
     */
    private String audioPath;
    /**
     * 音频的开始时间
     */
    private Integer startTime;
    /**
     * 音频的结束时间
     */
    private Integer endTime;
    /**
     * 图片路径
     */
    private String imagePath;

    /**
     * @return the english
     */
    public String getEnglish() {
        return english;
    }

    /**
     * @param english the english to set
     */
    public void setEnglish(String english) {
        this.english = english;
    }

    /**
     * @return the chinese
     */
    public String getChinese() {
        return chinese;
    }

    /**
     * @param chinese the chinese to set
     */
    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

 

    /**
     * @return the startTime
     */
    public Integer getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Integer getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * @return the audioPath
     */
    public String getAudioPath() {
        return audioPath;
    }

    /**
     * @param audioPath the audioPath to set
     */
    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }
    
    
}
