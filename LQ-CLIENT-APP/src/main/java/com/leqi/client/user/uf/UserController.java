/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.user.uf;

import com.leqi.client.book.article.cf.QueryArticlesCommand;
import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.cf.QueryCatalogCommand;
import com.leqi.client.book.info.uf.BookInfoModel;
import com.leqi.client.book.segment.info.uf.SegmentInfoModel;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqienglish.client.control.view.listview.LQListView;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("UserController")
public class UserController extends FXMLController<UserModel> {

    @FXML
    private LQListView<Catalog> bookListView;

    @FXML
    private LQListView<Content> articListView;

    @FXML
    private StackPane bookBusinessPane;

    @Resource(name = "BookInfoModel")
    private BookInfoModel bookInfoModel;

    @Resource(name = "ArticleModel")
    private ArticleModel articleModel;

    @Resource(name = "SegmentInfoModel")
    private SegmentInfoModel segmentInfoModel;

    @Resource(name = "SegmentModel")
    private SegmentModel segmentModel;

    @Resource(name = "QueryCatalogCommand")
    private QueryCatalogCommand queryCatalogCommand;

    @Resource(name = "QueryArticlesCommand")
    private QueryArticlesCommand queryArticlesCommand;

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().getBooks())
                .subscribe((lc) -> bookListView.getItems().setAll(this.getModel().getBooks()));

        JavaFxObservable.changesOf(bookListView.getSelectionModel().selectedItemProperty())
                .subscribe((catalog) -> bookListViewSelectedChange(catalog.getNewVal()));

        JavaFxObservable.changesOf(this.getModel().getArticles())
                .subscribe((lc) -> this.articListView.getItems().setAll(this.getModel().getArticles()));

        JavaFxObservable.changesOf(articListView.getSelectionModel().selectedItemProperty())
                .subscribe((catalog) -> articListViewSelectedChange(catalog.getNewVal()));

    }

    private void bookListViewSelectedChange(Catalog catalog) {
        if (catalog == null) {
            return;
        }

    }

    private void articListViewSelectedChange(Content content) {

        bookBusinessPane.getChildren().setAll(this.segmentModel.getRoot());
        segmentModel.setArticle(content);
    }

    @FXML
    public void createBook(ActionEvent event) {

    }

    @FXML
    public void createSegment(ActionEvent event) {

    }

    @FXML
    public void createArticle(ActionEvent event) {
        if (this.bookListView.getSelectionModel().getSelectedItem() == null) {
            AlertUtil.showError("请先选择book");
            return;
        }
        String bookId = this.bookListView.getSelectionModel().getSelectedItem().getId();
        articleModel.setContent(createArticle(bookId));
        bookBusinessPane.getChildren().setAll(this.articleModel.getRoot());
    }

    private Content createContent(Content catalog) {
        Content content = new Content();
        content.setParentId(catalog.getId());
        content.setUserId("1");
        return content;
    }

    private Content createArticle(String bookId) {
        Content catalog = new Content();
        catalog.setCatalogId(bookId);

        catalog.setUserId("1");
        return catalog;
    }

}
