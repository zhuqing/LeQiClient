/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.uf;

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
@Component("WordRootModel")
public class WordRootModel extends FXMLModel {
    /**
     * 单词所在的文章
     */
    private ObjectProperty<Content> article;
    /**
     * 已经生成的单词列表
     */
    private ObservableList<Word> words;
            

     public WordRootModel() {
        setFxmlPath("/com/leqi/client/book/word/uf/WordRoot.fxml");

    }
     
    @Override
    @Resource(name = "WordRootController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); 
    }
    /**
     * @return the article
     */
    public Content getArticle() {
        return articleProperty().getValue();
    }
    
     /**
     * @return the article
     */
    public ObjectProperty<Content> articleProperty() {
        if(article == null){
            article = new SimpleObjectProperty<>();
        }
        return article;
    }

    /**
     * @param article the article to set
     */
    public void setArticle(Content article) {
        this.articleProperty().setValue(article);
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
    
}
