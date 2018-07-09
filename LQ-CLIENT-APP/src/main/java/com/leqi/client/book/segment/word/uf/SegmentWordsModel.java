/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.word.uf;

import com.leqi.client.book.segment.info.uf.*;
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
import xyz.tobebetter.entity.english.Segment;
import xyz.tobebetter.entity.english.play.AudioPlayPoint;
import xyz.tobebetter.entity.english.segment.WordAndSegment;

/**
 * 每篇文章下面的段
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentWordsModel")
public class SegmentWordsModel extends FXMLModel {

    /**
     * 文章内容
     */
    private ObjectProperty<Segment> segment;

    /**
     * 文章
     */
    private ObjectProperty<Content> article;
    
    private ObservableList<WordAndSegment> wordAndSegments;
    
    /**
     * 
     */
    private ObjectProperty<AudioPlayPoint> audioPlayPoint;
    

    public SegmentWordsModel() {
        setFxmlPath("/com/leqi/client/book/segment/word/uf/SegmentWords.fxml");
    }

    @Override
    @Resource(name = "SegmentWordsController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Content
     *
     * @return the content
     */
    public Segment getSegment() {
        return segmentProperty().getValue();
    }

    /**
     * Content
     *
     * @return the content
     */
    public ObjectProperty<Segment> segmentProperty() {
        if (segment == null) {
            segment = new SimpleObjectProperty<Segment>();
        }
        return segment;
    }

    /**
     * Content
     *
     * @param content the content to set
     */
    public void setSegment(Segment content) {
        this.segmentProperty().setValue(content);
    }

    /**
     * 文章
     *
     * @return the article
     */
    public Content getArticle() {
        return articleProperty().getValue();
    }

    /**
     * 文章
     *
     * @return the article
     */
    public ObjectProperty<Content> articleProperty() {
        if (article == null) {
            article = new SimpleObjectProperty<Content>();
        }
        return article;
    }

    /**
     * 文章
     *
     * @param article the article to set
     */
    public void setArticle(Content article) {
        this.articleProperty().setValue(article);
    }

    /**
     * @return the audioPlayPoint
     */
    public AudioPlayPoint getAudioPlayPoint() {
        return audioPlayPointProperty().getValue();
    }
    
      /**
     * @return the audioPlayPoint
     */
    public ObjectProperty<AudioPlayPoint> audioPlayPointProperty() {
        if(audioPlayPoint == null){
            audioPlayPoint = new SimpleObjectProperty<AudioPlayPoint>();
        }
        return audioPlayPoint;
    }

    /**
     * @param audioPlayPoint the audioPlayPoint to set
     */
    public void setAudioPlayPoint(AudioPlayPoint audioPlayPoint) {
        this.audioPlayPointProperty().setValue(audioPlayPoint); 
    }

    /**
     * @return the wordAndSegments
     */
    public ObservableList<WordAndSegment> getWordAndSegments() {
        if(this.wordAndSegments == null){
            this.wordAndSegments = FXCollections.observableArrayList();
        }
        return wordAndSegments;
    }

    /**
     * @param wordAndSegments the wordAndSegments to set
     */
    public void setWordAndSegments(List<WordAndSegment> wordAndSegments) {
        this.getWordAndSegments().setAll(wordAndSegments) ;
    }

   
}
