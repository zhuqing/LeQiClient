package xyz.tobebetter.entity.english.shortword;

import xyz.tobebetter.entity.Entity;

/**
 * Created by zhuleqi on 2018/11/13.
 */
public class ShortWordAndSentence extends Entity {

    private String sentenceId;
    private String shortWordId;

    public String getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(String sentenceId) {
        this.sentenceId = sentenceId;
    }

    public String getShortWordId() {
        return shortWordId;
    }

    public void setShortWordId(String shortWordId) {
        this.shortWordId = shortWordId;
    }
}
