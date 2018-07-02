/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.uf;

import com.leqi.client.book.article.cf.QueryArticlesCommand;
import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.content.uf.ContentModel;
import com.leqi.client.book.info.cf.QueryCatalogCommand;
import com.leqi.client.book.info.uf.BookInfoModel;
import com.leqi.client.book.segment.info.uf.SegmentInfoModel;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.view.listview.LQListView;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.client.util.sourceitem.SourceItem;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private LQTableView<Catalog> bookTableView;
    
    @FXML
    private LQFormView<Catalog> bookInfoFormView;

    @FXML
    private StackPane bookBusinessPane;

    @FXML
    private TextField filter;

    @Resource(name = "ContentModel")
    private ContentModel contentModel;

    @Resource(name = "ArticleModel")
    private ArticleModel articleModel;


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
                .subscribe((lc) -> bookTableView.getItems().setAll(this.getModel().getBooks()));

        JavaFxObservable.changesOf(bookTableView.getSelectionModel().selectedItemProperty())
                .subscribe((catalog) -> bookListViewSelectedChange(catalog.getNewVal()));

//        JavaFxObservable.changesOf(this.getModel().getArticles())
//                .subscribe((lc) -> this.articListView.getItems().setAll(this.getModel().getArticles()));
//
//        JavaFxObservable.changesOf(articListView.getSelectionModel().selectedItemProperty())
//                .subscribe((catalog) -> articListViewSelectedChange(catalog.getNewVal()));
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

        //  queryBooks(1, 20);
    }

    private void bookListViewSelectedChange(Catalog catalog) {
        if (catalog == null) {
            return;
        }
        bookInfoFormView.setValue(catalog);
        //queryArticle(catalog.getId(), 1, 20);
    }

    private void articListViewSelectedChange(Content content) {

//        bookBusinessPane.getChildren().setAll(this.segmentModel.getRoot());
//        segmentModel.setArticle(content);
    }

   

    private void queryBooks(int page, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put(Command.DATA, filter.getText());
        param.put("type", Consistent.BOOK_TYPE);
        queryCatalogCommand.setPageNum(page);
        queryCatalogCommand.setPageSize(pageSize);
        queryCatalogCommand.doCommand(param);
    }

    @FXML
    public void createBook(ActionEvent event) {
        createBook();

    }

    @FXML
    public void queryBook(ActionEvent event) {

        queryBooks(1, 1000);
    }

    @FXML
    public void deleteBook(ActionEvent event) {

    }

    @FXML
    public void cancelLunch(ActionEvent event) {

    }

    @FXML
    public void lunch(ActionEvent event) {

    }

    @FXML
    public void showArticles(ActionEvent event) {
        Button button = (Button) event.getSource();
        Catalog book = (Catalog) button.getUserData();
        if(book == null){
            AlertUtil.showError("没有书本");
            return;
        }
        SourceItem sourceItem = new SourceItem();
        sourceItem.setDisplay(book.getTitle());
        this.contentModel.setAddBreadCrumb(sourceItem);
        this.articleModel.setBook(book);
        
    }

    @FXML
    public void createArticle(ActionEvent event) {
        if (this.bookTableView.getSelectionModel().getSelectedItem() == null) {
            AlertUtil.showError("请先选择book");
            return;
        }
        String bookId = this.bookTableView.getSelectionModel().getSelectedItem().getId();
        articleModel.setContent(createArticle(bookId));
        bookBusinessPane.getChildren().setAll(this.articleModel.getRoot());
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

    

}
