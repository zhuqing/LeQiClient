/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.catalog.uf;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.content.ContentAndCatalog;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建文章实体
 *
 * @author zhuqing
 */
@Lazy
@Component("CatalogAndArticleModel")
public class CatalogAndArticleModel extends FXMLModel {



    /**
     * 实体
     */
    private ObjectProperty<Content> content;

    private ObservableList<Catalog> catalogs;

    /**
     * 书下的文章列表
     */
    private ObservableList<ContentAndCatalog> contentAndCatalogs;

    public CatalogAndArticleModel() {
        setFxmlPath("/com/leqi/client/book/article/uf/catalog/CatalogAndArticle.fxml");
    }

    @Override
    @Resource(name = "CatalogAndArticleController")
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





    /**
     * @return the contentAndCatalogs
     */
    public ObservableList<ContentAndCatalog> getContentAndCatalogs() {
        if (this.contentAndCatalogs == null) {
            this.contentAndCatalogs = FXCollections.observableArrayList();
        }
        return contentAndCatalogs;
    }

    /**
     * @param contentAndCatalogs the contentAndCatalogs to set
     */
    public void setContentAndCatalogs(ObservableList<ContentAndCatalog> contentAndCatalogs) {
        this.contentAndCatalogs = contentAndCatalogs;
    }

    /**
     * 书下的文章列表
     */
    public ObservableList<Catalog> getCatalogs() {
        if (this.catalogs == null) {
            this.catalogs = FXCollections.observableArrayList();
        }
        return catalogs;
    }

    public void setCatalogs(ObservableList<Catalog> catalogs) {
        this.catalogs = catalogs;
    }
}
