/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english.word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import xyz.tobebetter.entity.Entity;
import xyz.tobebetter.entity.english.Word;

/**
 *[{"part":"adj.","means":["\u597d\u7684","\u4f18\u79c0\u7684","\u6709\u76ca\u7684","\u6f02\u4eae\u7684\uff0c\u5065\u5168\u7684"]},
 * {"part":"n.","means":["\u597d\u5904\uff0c\u5229\u76ca","\u5584\u826f","\u5584\u884c","\u597d\u4eba"]},
 * {"part":"adv.","means":["\u540cwell"]}]
 * @author zhuleqi
 */
public class WordMean extends Entity{
    /**
     * 单词Id
     */
    private String wordId;
    /**
     * 单词
     */
    private String word;
    /**
     * 词性
     */
    private String wordType;
    /**
     * 单词的中文意思。
     */
    private String means;
    
    public static List<WordMean> toWordMeans(String json,Word word){
        JSONArray arr = new JSONArray(json);
        if(arr == null || arr.length() == 0){
            return Collections.EMPTY_LIST;
        }
        List<WordMean> wordMeans = new ArrayList<>(arr.length());
        
        for(int i = 0 ; i < arr.length() ; i++){
            WordMean wordMean = new WordMean();
           JSONObject jo = arr.getJSONObject(i);
           wordMean.setWordType(jo.getString("part"));
           
           Object means = jo.get("means");
           StringBuilder sb = new StringBuilder();
           if(means instanceof JSONArray){
               JSONArray meanArray = (JSONArray) means;
               meanArray.forEach((m)->sb.append(m.toString()).append(","));
           }
           wordMean.setMeans(sb.substring(0, sb.length()-1));
           wordMeans.add(wordMean);
           
        }
        
        return wordMeans;
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

    /**
     * @return the wordType
     */
    public String getWordType() {
        return wordType;
    }

    /**
     * @param wordType the wordType to set
     */
    public void setWordType(String wordType) {
        this.wordType = wordType;
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

    @Override
    public String toString() {
        return "WordMean{" + "wordId=" + wordId + ", word=" + word + ", wordType=" + wordType + ", means=" + means + '}';
    }
    
    
}
