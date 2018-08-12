/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.catalog.uf;

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
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.word.Word;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("CatalogModel")
public class CatalogModel extends FXMLModel {
    /**
     * 单词所在的文章
     */
    private ObjectProperty<Content> article;
    /**
     * 已经生成的单词列表
     */
    private ObservableList<Catalog> catalogs;
            

     public CatalogModel() {
        setFxmlPath("/com/leqi/client/catalog/uf/catalog.fxml");

    }
     
    @Override
    @Resource(name = "CatalogController")
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
     * @return the catalogs
     */
    public ObservableList<Catalog> getCatalogs() {
         if(catalogs == null){
            this.catalogs = FXCollections.observableArrayList();
        }
        return catalogs;
    }

    /**
     * @param catalogs the catalogs to set
     */
    public void setCatalogs(List<Catalog> catalogs) {
        this.getCatalogs().setAll(catalogs) ;
    }
    
    
    
}
