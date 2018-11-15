/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.shortword.uf;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;
import xyz.tobebetter.entity.english.play.AudioPlayPoint;
import xyz.tobebetter.entity.english.segment.WordAndSegment;
import xyz.tobebetter.entity.english.shortword.SegmentAndShortWord;
import xyz.tobebetter.entity.english.shortword.ShortWord;

import javax.annotation.Resource;
import java.util.List;

/**
 * 每篇文章下面的段
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentAndShortWordsModel")
public class SegmentAndShortWordsModel extends FXMLModel {



    /**
     * 文章内容
     */
    private ObjectProperty<Segment> segment;

    /**
     * 文章
     */
    private ObjectProperty<Content> article;
    
    private ObservableList<SegmentAndShortWord> segmentAndShortWords;

    private ObservableList<ShortWord> shortWords;
    
    /**
     * 
     */
    private ObjectProperty<AudioPlayPoint> audioPlayPoint;
    

    public SegmentAndShortWordsModel() {
        setFxmlPath("/com/leqi/client/book/segment/shortword/uf/SegmentAndShortWord.fxml");
    }

    @Override
    @Resource(name = "SegmentAndShortWordsController")
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


    public ObservableList<SegmentAndShortWord> getSegmentAndShortWords() {
        if(segmentAndShortWords == null){
            this.segmentAndShortWords = FXCollections.observableArrayList();
        }
        return segmentAndShortWords;
    }

    public void setSegmentAndShortWords(ObservableList<SegmentAndShortWord> segmentAndShortWords) {
        this.segmentAndShortWords = segmentAndShortWords;
    }

    public ObservableList<ShortWord> getShortWords() {
        if(this.shortWords == null){
            this.shortWords = FXCollections.observableArrayList();
        }
        return shortWords;
    }

    public void setShortWords(ObservableList<ShortWord> shortWords) {
        this.shortWords = shortWords;
    }
}
