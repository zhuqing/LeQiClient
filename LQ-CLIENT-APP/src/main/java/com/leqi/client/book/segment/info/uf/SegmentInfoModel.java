/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.info.uf;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;

/**
 * 每篇文章下面的段
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentInfoModel")
public class SegmentInfoModel extends FXMLModel {

    /**
     * 文章内容
     */
    private ObjectProperty<Segment> segment;

    /**
     * 文章
     */
    private ObjectProperty<Content> article;

    public SegmentInfoModel() {
        setFxmlPath("/com/leqi/client/book/segment/info/uf/SegmentInfo.fxml");
    }

    @Override
    @Resource(name = "SegmentInfoController")
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

}
