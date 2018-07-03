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

import javafx.collections.ObservableList;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;

import java.util.List;
import javafx.collections.FXCollections;

/**
 * 创建文章实体
 *
 * @author zhuqing
 */
@Lazy
@Component("ArticleModel")
public class ArticleModel extends FXMLModel {

    /**
     * 文章对应的Book
     */
    private ObjectProperty<Catalog> book;



    /**
     * 实体
     */
    private ObjectProperty<Content> content;


    /**
     * 书下的文章列表
     */
    private ObservableList<Content> articles;

    public ArticleModel() {
        setFxmlPath("/com/leqi/client/book/article/uf/Article.fxml");
    }

    @Override
    @Resource(name = "ArticleController")
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

    public Catalog getBook() {
        return book.getValue();
    }

    public ObjectProperty<Catalog> bookProperty() {
        if (book == null) {
            book = new SimpleObjectProperty<>();
        }
        return book;
    }



    public void setBook(Catalog book) {
        this.bookProperty().setValue(book);
    }

    public ObservableList<Content> getArticles() {
        if(this.articles == null){
            this.articles = FXCollections.observableArrayList();
        }
        return articles;
    }

    public void setArticles(List<Content> articles) {
        this.getArticles().setAll(articles);
    }

}
