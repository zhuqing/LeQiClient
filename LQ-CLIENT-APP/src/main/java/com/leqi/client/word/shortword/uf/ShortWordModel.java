/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.shortword.uf;

import com.leqi.client.word.uf.*;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.entity.word.Word;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("ShortWordModel")
public class ShortWordModel extends FXMLModel {

    /**
     * 已经生成的单词列表
     */
    private ObservableList<ShortWord> words;
    
    private ObjectProperty<ShortWord> editingData;
            

     public ShortWordModel() {
        setFxmlPath("/com/leqi/client/word/shortword/uf/ShortWord.fxml");

    }
     
    @Override
    @Resource(name = "ShortWordController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); 
    }
   
    /**
     * @return the words
     */
    public ObservableList<ShortWord> getWords() {
        if(words == null){
            this.words = FXCollections.observableArrayList();
        }
        return words;
    }

    /**
     * @param words the words to set
     */
    public void setWords(List<ShortWord> words) {
        this.getWords().setAll(words);
    }

    /**
     * @return the editingData
     */
    public ShortWord getEditingData() {
        return this.editingDataProperty().getValue();
    }
    
       /**
     * @return the editingData
     */
    public ObjectProperty<ShortWord> editingDataProperty() {
        if(this.editingData == null){
            this.editingData = new SimpleObjectProperty<ShortWord>();
        }
        return editingData;
    }

    /**
     * @param editingData the editingData to set
     */
    public void setEditingData(ShortWord editingData) {
        this.editingDataProperty().setValue(editingData);
    }
    
}
