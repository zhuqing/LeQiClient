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
public class Word extends Entity {

    /**
     * 单词
     */
    private String word;
    /**
     * 单词详情
     */
    private String detail;

    /**
     * 英音的发音路径
     */
    private String enAudioPath;
    /**
     * 美音的发音路径
     */
    private String anAudionPath;
    // private String userId;

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
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * 英音的发音路径
     *
     * @return the enAudioPath
     */
    public String getEnAudioPath() {
        return enAudioPath;
    }

    /**
     * 英音的发音路径
     *
     * @param enAudioPath the enAudioPath to set
     */
    public void setEnAudioPath(String enAudioPath) {
        this.enAudioPath = enAudioPath;
    }

    /**
     * 美音的发音路径
     *
     * @return the anAudionPath
     */
    public String getAnAudionPath() {
        return anAudionPath;
    }

    /**
     * 美音的发音路径
     *
     * @param anAudionPath the anAudionPath to set
     */
    public void setAnAudionPath(String anAudionPath) {
        this.anAudionPath = anAudionPath;
    }

}
