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
    /**
     * 单词Id
     */
    private String wordId;

    /**
     * 单词所在的句子
     */
    private Integer index;
    
    /**
     * 所在句子音频的开始时间
     */
    
    private Integer startTime;
    
    /**
     *  所在句子音频的结束时间
     */
    private Integer endTime;
    
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
     * 单词所在的句子
     *
     * @return the index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * 单词所在的句子
     *
     * @param index the index to set
     */
    public void setIndex(Integer index) {
        this.index = index;
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

}
