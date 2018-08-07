/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english.segment;

import xyz.tobebetter.entity.Entity;

/**
 * 单词和段的关联
 *
 * @author zhuqing
 */
public class WordAndSegment extends Entity {
    
    /**
     * 关联的单词，减少查询次数
     */
    private String word;

    /**
     * 单词所在的段Id
     */
    private String segmentId;
    
    private String contentId;
    /**
     * 文章标题
     */
    private String contentTitle;
    /**
     * 单词Id
     */
    private String wordId;

    /**
     * 单词所在的句子
     */
    private Integer scentenceIndex;
    
    /**
     * 句子
     */
    private String scentence;
    
    /**
     * 所在句子音频的开始时间
     */
    
    private Long startTime;
    
    /**
     *  所在句子音频的结束时间
     */
    private Long endTime;
    
    /**
     * 音频路径
     */
    private String audioPath;

    /**
     * 单词所在的段Id
     *
     * @return the segmentId
     */
    public String getSegmentId() {
        return segmentId;
    }

    /**
     * 单词所在的段Id
     *
     * @param segmentId the segmentId to set
     */
    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    /**
     * 单词Id
     *
     * @return the wordId
     */
    public String getWordId() {
        return wordId;
    }

    /**
     * 单词Id
     *
     * @param wordId the wordId to set
     */
    public void setWordId(String wordId) {
        this.wordId = wordId;
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

    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * @param word the word to set
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * @return the startTime
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the scentenceIndex
     */
    public Integer getScentenceIndex() {
        return scentenceIndex;
    }

    /**
     * @param scentenceIndex the scentenceIndex to set
     */
    public void setScentenceIndex(Integer scentenceIndex) {
        this.scentenceIndex = scentenceIndex;
    }

    /**
     * @return the scentence
     */
    public String getScentence() {
        return scentence;
    }

    /**
     * @param scentence the scentence to set
     */
    public void setScentence(String scentence) {
        this.scentence = scentence;
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
     * @return the contentTitle
     */
    public String getContentTitle() {
        return contentTitle;
    }

    /**
     * @param contentTitle the contentTitle to set
     */
    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

}
