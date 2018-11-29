/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.catalog.uf;

import com.leqi.client.book.article.catalog.cf.DeleteCatalogAndContentCommand;
import com.leqi.client.book.article.catalog.cf.QueryContentAndCatalogCommand;
import com.leqi.client.book.article.catalog.cf.SaveContentAndCatalogCommand;
import com.leqi.client.book.article.cf.*;
import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.content.uf.ContentModel;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqi.client.book.word.uf.WordModel;
import com.leqi.client.catalog.cf.QueryCatalogsCommand;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.content.ContentAndCatalog;

import javax.annotation.Resource;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static com.leqienglish.client.fw.cf.Command.CONSUMER;
import static com.leqienglish.client.fw.cf.Command.DATA;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("CatalogAndArticleController")
public class CatalogAndArticleController extends FXMLController<CatalogAndArticleModel> {

    @FXML
    private TextField filter;


    @FXML
    private LQFormView<Content> contentLQFormView;

    @FXML
    private LQTableView<Catalog> catalogsTableView;
    @FXML
    private LQTableView<ContentAndCatalog> catalogAndAritcleTableView;

    @Resource(name = "BookRootModel")
    private ContentModel contentModel;



    @Resource(name = "QueryCatalogsCommand")
    private QueryCatalogsCommand queryCatalogsCommand;



    @Resource(name = "DeleteCatalogAndContentCommand")
    private   DeleteCatalogAndContentCommand deleteCatalogAndContentCommand;



    @Resource(name = "QueryContentAndCatalogCommand")
    private QueryContentAndCatalogCommand queryContentAndCatalogCommand;

    @Resource(name = "SaveContentAndCatalogCommand")
    private SaveContentAndCatalogCommand saveContentAndCatalogCommand;

    public CatalogAndArticleController() {
    }

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //对当前编辑的Catalog加监听
        JavaFxObservable.changesOf(this.getModel().contentProperty())
                .subscribe((c) -> {
                    articleChange(c.getNewVal());
                });



        JavaFxObservable.changesOf(this.getModel().getCatalogs())
                .subscribe(c -> this.catalogsTableView.getItems().setAll(this.getModel().getCatalogs()));


        JavaFxObservable.changesOf(this.getModel().getContentAndCatalogs())
                .subscribe((c) -> this.catalogAndAritcleTableView.getItems().setAll(this.getModel().getContentAndCatalogs()));



    }






    private void articleChange(Content article) {
        this.contentLQFormView.setValue(article);
        Map<String, Object> map = new HashMap<>();
        map.put(DATA, article);
        this.queryContentAndCatalogCommand.doCommand(map);

    }

    /**
     * 创建文章
     *
     * @param bookId
     * @return
     */
    private Content createArticle(String bookId) {
        Content article = new Content();
        article.setCatalogId(bookId);

        article.setUserId("1");
        return article;
    }


    @FXML
    public void query(ActionEvent event) {
        Map<String, Object> param = new HashMap<>();
        param.put(CONSUMER, new Consumer<Catalog[]>() {
            @Override
            public void accept(Catalog[] catalogs) {

                getModel().getCatalogs().setAll(catalogs);
            }
        });
        this.queryCatalogsCommand.doCommand(param);
    }

    /**query
     * 取消发布按钮点击事件
     *
     * @param event
     */
    @FXML
    public void deleteCatalogAndArticle(ActionEvent event) {
        if (!AlertUtil.couldDo(AlertUtil.IF_DELETE)) {
            return;
        }
        ContentAndCatalog article = EventUtil.getEntityFromButton(event);

        Map<String, Object> param = new HashMap<>();
        param.put(DATA, article);
        this.deleteCatalogAndContentCommand.doCommand(param);
    }




    @FXML
    public void add2Content(ActionEvent event) {
        Catalog catalog = EventUtil.getEntityFromButton(event);
        ContentAndCatalog cc = new ContentAndCatalog();

        Content content = this.contentLQFormView.getValue();
        if (content == null || content.getId() == null) {
            AlertUtil.showError("(content == null||content.getId() == null");
            return;
        }

        cc.setContentId(content.getId());
        cc.setCatalogId(catalog.getId());
        cc.setCatalogName(catalog.getTitle());

        Map<String, Object> param = new HashMap<>();
        param.put(DATA, cc);
        this.saveContentAndCatalogCommand.doCommand(param);
    }



}
