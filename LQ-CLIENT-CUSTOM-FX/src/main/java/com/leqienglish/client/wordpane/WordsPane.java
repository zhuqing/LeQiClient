/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.wordpane;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import xyz.tobebetter.entity.english.Word;

/**
 *
 * @author zhuleqi
 */
public class WordsPane extends Control{
  
    
    private ObservableList<Word> words;
    

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
        this.getWords().setAll(words);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new WordsPaneSkin(this);
    }
    
    
            
    
}
