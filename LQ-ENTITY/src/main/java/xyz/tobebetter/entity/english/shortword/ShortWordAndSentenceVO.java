package xyz.tobebetter.entity.english.shortword;



/**
 * Created by zhuleqi on 2018/11/13.
 */
public class ShortWordAndSentenceVO extends ShortWordAndSentence {
    private String shortWord;
    private String english;
    private String chinese;

    public String getShortWord() {
        return shortWord;
    }

    public void setShortWord(String shortWord) {
        this.shortWord = shortWord;
    }


    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}
