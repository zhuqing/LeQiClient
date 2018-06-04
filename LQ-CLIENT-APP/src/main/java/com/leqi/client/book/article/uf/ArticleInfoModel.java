/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.uf;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;

/**
 * 创建文章实体
 *
 * @author zhuqing
 */
@Lazy
@Component("ArticleInfoModel")
public class ArticleInfoModel extends FXMLModel {

    /**
     * 实体
     */
    private ObjectProperty<Content> content;

    public ArticleInfoModel() {
        setFxmlPath("/com/leqi/client/book/article/uf/ArticleInfo.fxml");
    }

    @Override
    @Resource(name = "ArticleInfoController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 分类实体
     *
     * @return the catalog
     */
    public Content getContent() {
        return contentProperty().getValue();
    }

    /**
     * 分类实体
     *
     * @return the catalog
     */
    public ObjectProperty<Content> contentProperty() {
        if (content == null) {
            content = new SimpleObjectProperty<>();
        }
        return content;
    }

    /**
     * 分类实体
     *
     * @param article the catalog to set
     */
    public void setContent(Content article) {
        this.contentProperty().setValue(article);
    }

}
