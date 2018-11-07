/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.sentence.uf;

import com.leqi.client.book.word.uf.*;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import io.reactivex.internal.operators.observable.ObservableLift;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.sentence.Sentence;
import xyz.tobebetter.entity.word.Word;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("SentenceModel")
public class SentenceModel extends FXMLModel {
    /**
     * 句子
     */
    private ObservableList<Sentence> sentences;
    /**
     * 句子对应的单词
     */
    private ObservableList<Word> words;
    /**
     * 当前编辑的句子
     */
    private ObjectProperty<Sentence> currentSentence;
            

     public SentenceModel() {
        setFxmlPath("/com/leqi/client/book/sentence/uf/Sentence.fxml");

    }
     
    @Override
    @Resource(name = "SentenceController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); 
    }

    /**
     * @return the sentences
     */
    public ObservableList<Sentence> getSentences() {
        if(this.sentences == null){
            this.sentences = FXCollections.observableArrayList();
        }
        return sentences;
    }

    /**
     * @param sentences the sentences to set
     */
    public void setSentences(List<Sentence> sentences) {
        this.getSentences().setAll(sentences) ;
    }

    /**
     * @return the words
     */
    public ObservableList<Word> getWords() {
         if(this.words == null){
            this.words = FXCollections.observableArrayList();
        }
        return words;
    }

    /**
     * @param words the words to set
     */
    public void setWords(List<Word> words) {
        this.getWords().setAll(words) ;
    }
    
     /**
     * @return the currentSentence
     */
    public ObjectProperty<Sentence> currentSentenceProperty() {
        if(this.currentSentence == null){
            this.currentSentence = new SimpleObjectProperty<Sentence>();
        }
        return currentSentence;
    }

    /**
     * @return the currentSentence
     */
    public Sentence getCurrentSentence() {
   
        return currentSentenceProperty().getValue();
    }

    /**
     * @param currentSentence the currentSentence to set
     */
    public void setCurrentSentence(Sentence currentSentence) {
        this.currentSentenceProperty().setValue(currentSentence);
    }
   
}
