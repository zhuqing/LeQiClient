/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english;

import xyz.tobebetter.entity.Entity;

/**
 * 单词和段的关联
 *
 * @author zhuqing
 */
public class WordAndSegment extends Entity {

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

}
