/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english.sentence;

import xyz.tobebetter.entity.Entity;

/**
 *单词和句子的关系
 * @author zhuleqi
 */
public class SentenceAndWord extends Entity{
    private String word;
    private String sentenceId;
    private String wordId;

    /**
     * @return the sentenceId
     */
    public String getSentenceId() {
        return sentenceId;
    }

    /**
     * @param sentenceId the sentenceId to set
     */
    public void setSentenceId(String sentenceId) {
        this.sentenceId = sentenceId;
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
