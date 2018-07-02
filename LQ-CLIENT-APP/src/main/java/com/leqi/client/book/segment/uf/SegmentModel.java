/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.uf;

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
@Component("SegmentModel")
public class SegmentModel extends FXMLModel {
    
    private ObjectProperty<Catalog> book;
    

    /**
     * contents 对应的article
     */
    private ObjectProperty<Content> article;

    /**
     * 段列表
     */
    private ObservableList<Segment> segments;
    
    private ObjectProperty<Segment> editingSegment;

    public SegmentModel() {
        setFxmlPath("/com/leqi/client/book/segment/uf/Segment.fxml");
    }

    @Override
    @Resource(name = "SegmentController")
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
     * 段列表
     *
     * @return the segments
     */
    public ObservableList<Segment> getSegments() {
        if (segments == null) {
            this.segments = FXCollections.observableArrayList();
        }

        return segments;
    }

    /**
     * 段列表
     *
     * @param segments the segments to set
     */
    public void setSegments(List<Segment> segments) {
        this.getSegments().setAll(segments);
    }

    /**
     * @return the editingSegment
     */
    public Segment getEditingSegment() {
        return editingSegmentProperty().getValue();
    }
    
      /**
     * @return the editingSegment
     */
    public ObjectProperty<Segment> editingSegmentProperty() {
        if(editingSegment == null){
            editingSegment = new SimpleObjectProperty<>();
        }
        return editingSegment;
    }

    /**
     * @param editingSegment the editingSegment to set
     */
    public void setEditingSegment(Segment editingSegment) {
        this.editingSegmentProperty().setValue(editingSegment); 
    }

    /**
     * @return the book
     */
    public Catalog getBook() {
        return bookProperty().getValue();
    }
    
     /**
     * @return the book
     */
    public ObjectProperty<Catalog> bookProperty() {
        if(book == null){
             book = new SimpleObjectProperty<>();
        }
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(Catalog book) {
        this.bookProperty().setValue(book);
    }
    
}
