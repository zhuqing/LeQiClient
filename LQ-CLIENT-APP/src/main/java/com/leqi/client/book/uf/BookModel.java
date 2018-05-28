/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.uf;

import com.leqi.client.content.*;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Catalog;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("BookModel")
public class BookModel extends FXMLModel {

    /**
     * 书列表
     */
    private ObservableList<Catalog> books;
    /**
     * 书下的文章列表
     */
    private ObservableList<Catalog> articles;

    public BookModel() {
        setFxmlPath("/com/leqi/client/book/uf/Book.fxml");

//        setFxmlPath("/com/bjgoodwill/hip/client/fw/root/uf/titleRoot.fxml");
    }

    @Override
    @Resource(name = "BookController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 书列表
     *
     * @return the books
     */
    public ObservableList<Catalog> getBooks() {
        if (books == null) {
            this.books = FXCollections.observableArrayList();
        }
        return books;
    }

    /**
     * 书列表
     *
     * @param books the books to set
     */
    public void setBooks(List<Catalog> books) {
        this.getBooks().setAll(books);
    }

    /**
     * 书下的文章列表
     *
     * @return the articles
     */
    public ObservableList<Catalog> getArticles() {
        if (articles == null) {
            this.articles = FXCollections.observableArrayList();
        }
        return articles;
    }

    /**
     * 书下的文章列表
     *
     * @param articles the articles to set
     */
    public void setArticles(List<Catalog> articles) {
        this.getArticles().setAll(articles);
    }
}
