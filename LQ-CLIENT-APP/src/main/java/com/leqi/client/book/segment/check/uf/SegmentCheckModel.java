/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.check.uf;

import com.leqi.client.book.segment.uf.*;
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
import xyz.tobebetter.entity.english.Segment;

/**
 * 每篇文章下面的段
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentCheckModel")
public class SegmentCheckModel extends FXMLModel {
    


   
    private ObjectProperty<Segment> segment;
    
       /**
     * contents 对应的article
     */
    private ObjectProperty<Content> article;

    public SegmentCheckModel() {
        setFxmlPath("/com/leqi/client/book/segment/check/uf/SegmentCheck.fxml");
    }

    @Override
    @Resource(name = "SegmentCheckController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }

  

    /**
     * contents 对应的book
     *
     * @return the book
     */
    public Content getArticle() {
        return articleProperty().getValue();
    }

    /**
     * contents 对应的book
     *
     * @return the book
     */
    public ObjectProperty<Content> articleProperty() {
        if (article == null) {
            article = new SimpleObjectProperty<>();
        }
        return article;
    }

    /**
     * contents 对应的book
     *
     * @param article
     *
     */
    public void setArticle(Content article) {
        this.articleProperty().setValue(article);
    }

    /**
     * @return the segment
     */
    public Segment getSegment() {
        return segmentProperty().getValue();
    }
    
        /**
     * @return the segment
     */
    public ObjectProperty<Segment> segmentProperty() {
         if(segment == null){
             segment = new SimpleObjectProperty<>();
        }
        return segment;
    }

    /**
     * @param segment the segment to set
     */
    public void setSegment(Segment segment) {
        this.segmentProperty().setValue(segment);
    }
    
}
