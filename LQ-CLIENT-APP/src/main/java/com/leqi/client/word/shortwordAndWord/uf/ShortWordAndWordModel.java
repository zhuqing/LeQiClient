/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.shortwordAndWord.uf;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.entity.english.shortword.ShortWordAndWordVO;
import xyz.tobebetter.entity.word.Word;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("ShortWordAndWordModel")
public class ShortWordAndWordModel extends FXMLModel {

    /**
     * 已经生成的单词列表
     */
    private ObservableList<Word> words;

    private ObjectProperty<ShortWord> currentData;

    private ObservableList<ShortWordAndWordVO> shortWordAndWordVOS;


     public ShortWordAndWordModel() {
        setFxmlPath("/com/leqi/client/word/shortwordAndWord/uf/ShortWordAndWord.fxml");

    }
     
    @Override
    @Resource(name = "ShortWordAndWordController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); 
    }
   
    /**
     * @return the words
     */
    public ObservableList<Word> getWords() {
        if(words == null){
            this.words = FXCollections.observableArrayList();
        }
        return words;
    }

    /**
     * @param words the words to set
     */
    public void setWords(List<Word> words) {
        this.getWords().setAll(words);
    }

    /**
     * @return the editingData
     */
    public ShortWord getCurrentData() {
        return this.currentDataProperty().getValue();
    }
    
       /**
     * @return the editingData
     */
    public ObjectProperty<ShortWord> currentDataProperty() {
        if(this.currentData == null){
            this.currentData = new SimpleObjectProperty<ShortWord>();
        }
        return currentData;
    }

    /**
     * @param editingData the editingData to set
     */
    public void setCurrentData(ShortWord editingData) {
        this.currentDataProperty().setValue(editingData);
    }

    public ObservableList<ShortWordAndWordVO> getShortWordAndWordVOS() {
        if(shortWordAndWordVOS == null){
            shortWordAndWordVOS = FXCollections.observableArrayList();
        }
        return shortWordAndWordVOS;
    }

    public void setShortWordAndWordVOS(List<ShortWordAndWordVO> shortWordAndWordVOS) {
        this.getShortWordAndWordVOS().setAll(shortWordAndWordVOS);
    }
}
