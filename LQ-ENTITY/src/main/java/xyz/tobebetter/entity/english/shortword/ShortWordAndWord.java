package xyz.tobebetter.entity.english.shortword;

import xyz.tobebetter.entity.Entity;

/**
 *
 * Created by zhuleqi on 2018/11/17.
 */
public class ShortWordAndWord extends Entity{
    private String shortWordId;
    private String wordId;


    public String getShortWordId() {
        return shortWordId;
    }

    public void setShortWordId(String shortWordId) {
        this.shortWordId = shortWordId;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }
}
