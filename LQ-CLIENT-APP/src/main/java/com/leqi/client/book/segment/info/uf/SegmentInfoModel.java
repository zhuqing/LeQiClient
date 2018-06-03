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

/**
 * 每篇文章下面的段
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentInfoModel")
public class SegmentInfoModel extends FXMLModel {

    /**
     * Content
     */
    private ObjectProperty<Content> content;

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
    public Content getContent() {
        return contentProperty().getValue();
    }

    /**
     * Content
     *
     * @return the content
     */
    public ObjectProperty<Content> contentProperty() {
        if (content == null) {
            content = new SimpleObjectProperty<Content>();
        }
        return content;
    }

    /**
     * Content
     *
     * @param content the content to set
     */
    public void setContent(Content content) {
        this.contentProperty().setValue(content);
    }

}
