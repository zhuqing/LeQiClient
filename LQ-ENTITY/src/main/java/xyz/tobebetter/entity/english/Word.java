/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
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
     * 单词的过去式
     */
    private String wordPast;
    /**
     * 单词的完成式
     */
    private String wordDone;
    /**
     * 比较级
     */
    private String worder;
    /**
     * 最高级
     */
    private String wordest;

    /**
     * 单词的意思，json格式
     */
    private String means;

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
    private String amAudionPath;
    // private String userId;
    /**
     * tts发音
     */
    private String ttsAudioPath;

    /**
     * 美音音标
     */
    private String phAm;
    /**
     * 英音的音标
     */
    private String phEn;

    public static Word icibaJsontoWord(String json) {
        Word word = new Word();
        word.setDetail(json);

        JSONObject jsonObject = new JSONObject(json);

        if (!jsonObject.has("exchange")||!jsonObject.has("word_name")) {
            return word;
        }
        
        word.setWord(jsonObject.getString("word_name"));
        JSONObject exchange = jsonObject.getJSONObject("exchange");
        
        if (exchange.get("word_done") instanceof JSONArray) {
            word.setWordDone(exchange.getJSONArray("word_done").get(0).toString());
        }
        if (exchange.get("word_past") instanceof JSONArray) {
            word.setWordPast(exchange.getJSONArray("word_past").get(0).toString());
        }
       
         
        if (exchange.get("word_er") instanceof JSONArray) {
            word.setWorder(exchange.getJSONArray("word_er").get(0).toString());
        }
        if (exchange.get("word_est") instanceof JSONArray) {
            word.setWordest(exchange.getJSONArray("word_est").get(0).toString());
        }

        JSONArray symbols = jsonObject.getJSONArray("symbols");
        if (symbols.length() == 0) {
            return word;
        }
        JSONObject symbol = symbols.getJSONObject(0);

        word.setPhEn(symbol.getString("ph_en"));
        word.setPhAm(symbol.getString("ph_am"));

        word.setAmAudionPath(symbol.getString("ph_am_mp3"));
        word.setEnAudioPath(symbol.getString("ph_en_mp3"));
        word.setTtsAudioPath(symbol.getString("ph_tts_mp3"));
        word.setMeans(symbol.getJSONArray("parts").toString());

        return word;
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
     * @return the wordPast
     */
    public String getWordPast() {
        return wordPast;
    }

    /**
     * @param wordPast the wordPast to set
     */
    public void setWordPast(String wordPast) {
        this.wordPast = wordPast;
    }

    /**
     * @return the wordDone
     */
    public String getWordDone() {
        return wordDone;
    }

    /**
     * @param wordDone the wordDone to set
     */
    public void setWordDone(String wordDone) {
        this.wordDone = wordDone;
    }

    /**
     * @return the worder
     */
    public String getWorder() {
        return worder;
    }

    /**
     * @param worder the worder to set
     */
    public void setWorder(String worder) {
        this.worder = worder;
    }

    /**
     * @return the wordest
     */
    public String getWordest() {
        return wordest;
    }

    /**
     * @param wordest the wordest to set
     */
    public void setWordest(String wordest) {
        this.wordest = wordest;
    }

    /**
     * @return the means
     */
    public String getMeans() {
        return means;
    }

    /**
     * @param means the means to set
     */
    public void setMeans(String means) {
        this.means = means;
    }

    /**
     * @return the ttsAudioPath
     */
    public String getTtsAudioPath() {
        return ttsAudioPath;
    }

    /**
     * @param ttsAudioPath the ttsAudioPath to set
     */
    public void setTtsAudioPath(String ttsAudioPath) {
        this.ttsAudioPath = ttsAudioPath;
    }

    /**
     * @return the phAm
     */
    public String getPhAm() {
        return phAm;
    }

    /**
     * @param phAm the phAm to set
     */
    public void setPhAm(String phAm) {
        this.phAm = phAm;
    }

    /**
     * @return the phEn
     */
    public String getPhEn() {
        return phEn;
    }

    /**
     * @param phEn the phEn to set
     */
    public void setPhEn(String phEn) {
        this.phEn = phEn;
    }

    /**
     * @return the amAudionPath
     */
    public String getAmAudionPath() {
        return amAudionPath;
    }

    /**
     * @param amAudionPath the amAudionPath to set
     */
    public void setAmAudionPath(String amAudionPath) {
        this.amAudionPath = amAudionPath;
    }

    @Override
    public String toString() {
        return "Word{" + "word=" + word + ", wordPast=" + wordPast + ", wordDone=" + wordDone + ", worder=" + worder + ", wordest=" + wordest + ", means=" + means + ", detail=" + detail + ", enAudioPath=" + enAudioPath + ", amAudionPath=" + amAudionPath + ", ttsAudioPath=" + ttsAudioPath + ", phAm=" + phAm + ", phEn=" + phEn + '}';
    }

}
