/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.info.uf;

import com.leqi.client.word.uf.*;
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
import xyz.tobebetter.entity.english.Word;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("WordInfoModel")
public class WordInfoModel extends FXMLModel {

    /**
     * 单词所在的文章
     */
    private ObjectProperty<Word> word;

    public WordInfoModel() {
        setFxmlPath("/com/leqi/client/word/info/uf/WordInfo.fxml");
    }

    @Override
    @Resource(name = "WordInfoController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController);
    }

  
    /**
     * @return the word
     */
    public Word getWord() {
        return wordProperty().getValue();
    }

    /**
     * @return the word
     */
    public ObjectProperty<Word> wordProperty() {
        if (word == null) {
            word = new SimpleObjectProperty<>();
        }
        return word;
    }

    /**
     * @param word the word to set
     */
    public void setWord(Word word) {
        this.wordProperty().setValue(word); 
    }

}
