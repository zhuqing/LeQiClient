/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.shortword.sentence.uf;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.sentence.Sentence;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.entity.english.shortword.ShortWordAndSentenceVO;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("ShortWordAndSentenceModel")
public class ShortWordAndSentenceModel extends FXMLModel {

    /**
     * 已经生成的单词列表
     */
    private ObservableList<Sentence> sentences;
    
    private ObjectProperty<ShortWord> currentData;

    private ObservableList<ShortWordAndSentenceVO> shortWordAndSentenceVOS;
            

     public ShortWordAndSentenceModel() {
        setFxmlPath("/com/leqi/client/word/shortword/sentence/uf/ShortWordAndSentence.fxml");

    }
     
    @Override
    @Resource(name = "ShortWordAndSentenceController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); 
    }
   
    /**
     * @return the sentences
     */
    public ObservableList<Sentence> getSentences() {
        if(sentences == null){
            this.sentences = FXCollections.observableArrayList();
        }
        return sentences;
    }

    /**
     * @param sentences the sentences to set
     */
    public void setSentences(List<Sentence> sentences) {
        this.getSentences().setAll(sentences);
    }

    /**
     * @return the currentData
     */
    public ShortWord getCurrentData() {
        return this.currentDataProperty().getValue();
    }
    
       /**
     * @return the currentData
     */
    public ObjectProperty<ShortWord> currentDataProperty() {
        if(this.currentData == null){
            this.currentData = new SimpleObjectProperty<ShortWord>();
        }
        return currentData;
    }

    /**
     * @param currentData the currentData to set
     */
    public void setCurrentData(ShortWord currentData) {
        this.currentDataProperty().setValue(currentData);
    }

    public ObservableList<ShortWordAndSentenceVO> getShortWordAndSentenceVOS() {
        if(this.shortWordAndSentenceVOS == null){
            this.shortWordAndSentenceVOS = FXCollections.observableArrayList();
        }
        return shortWordAndSentenceVOS;
    }

    public void setShortWordAndSentenceVOS(List<ShortWordAndSentenceVO> shortWordAndSentenceVOS) {
       this.getShortWordAndSentenceVOS().setAll(shortWordAndSentenceVOS);
    }
}
