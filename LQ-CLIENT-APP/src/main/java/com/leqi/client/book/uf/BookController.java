/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.uf;

import com.leqi.client.book.article.cf.QueryArticlesCommand;
import com.leqi.client.book.article.uf.ArticleInfoModel;
import com.leqi.client.book.info.cf.QueryCatalogCommand;
import com.leqi.client.book.info.uf.BookInfoModel;
import com.leqi.client.book.segment.info.uf.SegmentInfoModel;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqienglish.client.control.view.listview.LQListView;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import com.leqienglish.client.util.alert.AlertUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("BookController")
public class BookController extends FXMLController<BookModel> {

    @FXML
    private LQListView<Catalog> bookListView;

    @FXML
    private LQListView<Content> articListView;

    @FXML
    private StackPane bookBusinessPane;

    @Resource(name = "BookInfoModel")
    private BookInfoModel bookInfoModel;

    @Resource(name = "ArticleInfoModel")
    private ArticleInfoModel articleInfoModel;

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
        JavaFxObservable.changesOf(this.getModel().bookBusinessIdProperty())
                .map((p) -> p.getNewVal())
                .subscribe((businessId) -> {
                    if (businessId == null) {
                        this.bookBusinessPane.getChildren().clear();
                    } else {
                        FXMLModel fxmlModel = this.getModel(businessId);
                        if (fxmlModel == null) {
                            return;
                        }
                        this.bookBusinessPane.getChildren().setAll(fxmlModel.getRoot());
                    }
                });

        queryBooks(1, 20);
    }

    private void bookListViewSelectedChange(Catalog catalog) {
        if (catalog == null) {
            return;
        }
        queryArticle(catalog.getId(), 1, 20);
    }

    private void articListViewSelectedChange(Content content) {

        bookBusinessPane.getChildren().setAll(this.segmentModel.getRoot());
        segmentModel.setArticle(content);
    }

    private void queryArticle(String parentId, int page, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("catalogId", parentId);
        queryArticlesCommand.setPageNum(page);
        queryArticlesCommand.setPageSize(pageSize);
        queryArticlesCommand.doCommand(param);
    }

    private void queryBooks(int page, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", Consistent.BOOK_TYPE);
        queryCatalogCommand.setPageNum(page);
        queryCatalogCommand.setPageSize(pageSize);
        queryCatalogCommand.doCommand(param);
    }

    @FXML
    public void createBook(ActionEvent event) {

        bookInfoModel.setCatalog(createBook());
        bookBusinessPane.getChildren().setAll(this.bookInfoModel.getRoot());
    }

    @FXML
    public void createSegment(ActionEvent event) {

        if (this.articListView.getSelectionModel().getSelectedItem() == null) {
            AlertUtil.showError("没有选中文章");
            return;
        }

        this.bookBusinessPane.getChildren().setAll(this.segmentInfoModel.getRoot());
        segmentInfoModel.setArticle(this.articListView.getSelectionModel().getSelectedItem());
        segmentInfoModel.setSegment(createSegmentInfo(this.articListView.getSelectionModel().getSelectedItem()));
    }

    @FXML
    public void createArticle(ActionEvent event) {
        if (this.bookListView.getSelectionModel().getSelectedItem() == null) {
            AlertUtil.showError("请先选择book");
            return;
        }
        String bookId = this.bookListView.getSelectionModel().getSelectedItem().getId();
        articleInfoModel.setContent(createArticle(bookId));
        bookBusinessPane.getChildren().setAll(this.articleInfoModel.getRoot());
    }

    /**
     * 创建书本
     *
     * @return
     */
    private Catalog createBook() {
        Catalog catalog = new Catalog();
        catalog.setType(Consistent.BOOK_TYPE);
        catalog.setUserId("1");
        return catalog;
    }

    /**
     * 创建文章的片段
     *
     * @param artical
     * @return
     */
    private Segment createSegmentInfo(Content artical) {
        Segment segment = new Segment();
        segment.setUserId("1");

        segment.setContentId(artical.getId());
        return segment;
    }

    /**
     * 创建文章
     *
     * @param bookId
     * @return
     */
    private Content createArticle(String bookId) {
        Content catalog = new Content();
        catalog.setCatalogId(bookId);

        catalog.setUserId("1");
        return catalog;
    }

}
