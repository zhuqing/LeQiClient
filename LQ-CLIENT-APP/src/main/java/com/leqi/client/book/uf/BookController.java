/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.uf;

import com.leqi.client.book.info.cf.QueryCatalogCommand;
import com.leqi.client.book.info.uf.BookInfoModel;
import com.leqienglish.client.control.view.listview.LQListView;
import com.leqienglish.client.fw.sf.FileService;

import com.leqienglish.client.fw.sf.UpLoadFileService;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.io.File;
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
import xyz.tobebetter.entity.english.Catalog;

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
    private LQListView<Catalog> articListView;

    @FXML
    private StackPane bookBusinessPane;

    @Resource(name = "BookInfoModel")
    private BookInfoModel bookInfoModel;

    @Resource(name = "QueryCatalogCommand")
    private QueryCatalogCommand queryCatalogCommand;

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

        queryBooks(1, 20);
    }

    private void bookListViewSelectedChange(Catalog catalog) {
        if (catalog == null) {
            return;
        }
        queryArticle(catalog.getId(), 1, 20);
    }

    private void articListViewSelectedChange(Catalog catalog) {
//        if (catalog == null) {
//            return;
//        }
//        queryArticle(catalog.getId(), 1, 20);
    }

    private void queryArticle(String parentId, int page, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", Catalog.CHAPTER_TYPE);
        param.put("parentId", parentId);
        queryCatalogCommand.setPageNum(page);
        queryCatalogCommand.setPageSize(pageSize);
        queryCatalogCommand.doCommand(param);
    }

    private void queryBooks(int page, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", Catalog.BOOK_TYPE);
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
    public void createArticle(ActionEvent event) {
        if (this.bookListView.getSelectionModel().getSelectedItem() == null) {
            AlertUtil.showError("请先选择book");
            return;
        }
        String bookId = this.bookListView.getSelectionModel().getSelectedItem().getId();
        bookInfoModel.setCatalog(createArticle(bookId));
        bookBusinessPane.getChildren().setAll(this.bookInfoModel.getRoot());
    }

    private Catalog createBook() {
        Catalog catalog = new Catalog();
        catalog.setType(Catalog.BOOK_TYPE);
        catalog.setUserId("1");
        return catalog;
    }

    private Catalog createArticle(String bookId) {
        Catalog catalog = new Catalog();
        catalog.setType(Catalog.CHAPTER_TYPE);
        catalog.setParentId(bookId);
        catalog.setUserId("1");
        return catalog;
    }

}
