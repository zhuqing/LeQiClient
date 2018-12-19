/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.uf;

import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.cf.DeleteCatalogCommand;
import com.leqi.client.book.cf.QueryCatalogCommand;
import com.leqi.client.book.cf.SavaCatalogCommand;
import com.leqi.client.book.cf.UpdateCatalogStatusCommand;
import com.leqi.client.book.content.uf.ContentModel;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.client.util.event.EventUtil;
import com.leqienglish.client.util.sourceitem.SourceItem;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Catalog;

import javax.annotation.Resource;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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

    @Resource(name = "BookRootModel")
    private ContentModel contentModel;

    @Resource(name = "ArticleModel")
    private ArticleModel articleModel;

    @Resource(name = "QueryCatalogCommand")
    private QueryCatalogCommand queryCatalogCommand;

    @Resource(name = "DeleteCatalogCommand")
    private DeleteCatalogCommand deleteCatalogCommand;

    @Resource(name = "SavaCatalogCommand")
    private SavaCatalogCommand savaCatalogCommand;

    @Resource(name = "UpdateCatalogStatusCommand")
    private UpdateCatalogStatusCommand updateCatalogStatusCommand;

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
        //  queryBooks(1, 20);
    }

    private void bookListViewSelectedChange(Catalog catalog) {
        if (catalog == null) {
            return;
        }
        bookInfoFormView.setValue(catalog);
        //queryArticle(catalog.getId(), 1, 20);
    }

    private void queryBooks(int page, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put(Command.DATA, filter.getText());
        param.put("type", Consistent.BOOK_TYPE);
        queryCatalogCommand.setPage(page);
        queryCatalogCommand.setPageSize(pageSize);
        queryCatalogCommand.doCommand(param);
    }

    @FXML
    public void createBook(ActionEvent event) {
        Catalog book = createBook();
        this.bookInfoFormView.setValue(book);

    }

    @FXML
    public void queryBook(ActionEvent event) {

        queryBooks(1, 1000);
    }

    @FXML
    public void saveBook(ActionEvent event) {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put(Command.DATA, bookInfoFormView.getValue());
        savaCatalogCommand.doCommand(param);
    }

    @FXML
    public void delete(ActionEvent event) {
        if (!AlertUtil.couldDo(AlertUtil.IF_DELETE)) {
            return;
        }
        Catalog book = EventUtil.getEntityFromButton(event);
        Map<String, Object> param = new HashMap<String, Object>();

        param.put(Command.DATA, book);
        deleteCatalogCommand.doCommand(param);
    }

    @FXML
    public void cancelLunch(ActionEvent event) {
        Catalog catalog = EventUtil.getEntityFromButton(event);
        Map<String, Object> param = new HashMap<String, Object>();

        param.put(Command.DATA, Consistent.UN_LAUNCH);
        param.put(Command.ID, catalog.getId());

        updateCatalogStatusCommand.doCommand(param);
    }

    @FXML
    public void lunch(ActionEvent event) {

        Catalog catalog = EventUtil.getEntityFromButton(event);
        Map<String, Object> param = new HashMap<String, Object>();

        param.put(Command.DATA, Consistent.HAS_LAUNCHED);
        param.put(Command.ID, catalog.getId());

        updateCatalogStatusCommand.doCommand(param);
    }

    @FXML
    public void showArticles(ActionEvent event) {
        Button button = (Button) event.getSource();
        Catalog book = (Catalog) button.getUserData();
        if (book == null) {
            AlertUtil.showError("没有书本");
            return;
        }
        SourceItem sourceItem = new SourceItem();
        sourceItem.setValue("ArticleModel");
        sourceItem.setDisplay(book.getTitle());
        this.contentModel.setAddBreadCrumb(sourceItem);
        this.articleModel.setBook(book);

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
