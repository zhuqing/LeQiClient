/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.uf;

import com.leqi.client.content.*;
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

/**
 * 每篇文章下面的段
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentModel")
public class SegmentModel extends FXMLModel {
    /**
     * contents  对应的article
     */
    private ObjectProperty<Content> article;

    /**
     * 书列表
     */
    private ObservableList<Content> contents;
  

    public SegmentModel() {
        setFxmlPath("/com/leqi/client/book/segment/uf/Segment.fxml");
    }

    @Override
    @Resource(name = "SegmentController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 书列表
     *
     * @return the contents
     */
    public ObservableList<Content> getContents() {
        if (contents == null) {
            this.contents = FXCollections.observableArrayList();
        }
        return contents;
    }

    /**
     * 书列表
     *
     * @param contents the contents to set
     */
    public void setContents(ObservableList<Content> contents) {
        this.contents = contents;
    }

    /**
     * contents  对应的book
     * @return the book
     */
    public Content getArticle() {
        return articleProperty().getValue();
    }
/**
     * contents  对应的book
     * @return the book
     */
    public ObjectProperty<Content> articleProperty() {
        if(article == null){
            article = new SimpleObjectProperty<>();
        }
        return article;
    }
    /**
     * contents  对应的book
     * @param article

     */
    public void setArticle(Content article) {
        this.articleProperty().setValue(article);
    }
}
