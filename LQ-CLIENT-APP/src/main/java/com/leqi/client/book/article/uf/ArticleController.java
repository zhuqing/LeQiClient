/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.uf;

import com.leqi.client.book.article.cf.QueryArticlesCommand;
import com.leqi.client.book.article.cf.SaveArticleCommand;
import com.leqi.client.book.content.uf.ContentModel;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqi.client.book.word.uf.WordModel;
import com.leqi.client.root.RootModel;
import com.leqienglish.client.control.form.LQFormView;
import static com.leqienglish.client.fw.cf.Command.DATA;

import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.client.util.sourceitem.SourceItem;

import com.sun.javafx.collections.MappingChange;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("ArticleController")
public class ArticleController extends FXMLController<ArticleModel> {

    @FXML
    private LQFormView<Content> contentInfoFormView;

    @FXML
    private LQTableView<Content> articleTableView;

    @Resource(name = "ContentModel")
    private ContentModel contentModel;

    @Resource(name = "SegmentModel")
    private SegmentModel segmentModel;

    @Resource(name = "WordModel")
    private WordModel wordModel;

    @Resource(name = "QueryArticlesCommand")
    private QueryArticlesCommand queryArticlesCommand;

    @Resource(name = "SaveArticleCommand")
    private SaveArticleCommand saveArticleCommand;

    public ArticleController() {
    }

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //对当前编辑的Catalog加监听
        JavaFxObservable.changesOf(this.getModel().contentProperty())
                .subscribe((c) -> this.contentInfoFormView.setValue(c.getNewVal()));

        JavaFxObservable.nullableValuesOf(this.getModel().bookProperty())
                .map(p -> p.orElse(null))
                .subscribe(book -> this.bookChange(book));

        JavaFxObservable.changesOf(this.getModel().getArticles())
                .subscribe(c -> this.articleTableView.getItems().setAll(this.getModel().getArticles()));

        JavaFxObservable.nullableValuesOf(this.articleTableView.getSelectionModel().selectedItemProperty())
                .subscribe((c) -> this.getModel().setContent(c.orElse(null)));

    }

    @FXML
    public void save(ActionEvent event) {
        Map<String, Object> param = new HashMap<>();
        param.put(DATA, this.getModel().getContent());
        saveArticleCommand.doCommand(param);
    }

    private void bookChange(Catalog book) {
        if (book == null) {
            this.articleTableView.getItems().clear();
            return;
        }
        Map<String, Object> value = new HashMap<String, Object>();
        value.put(Command.ID, book.getId());
        this.queryArticlesCommand.doCommand(value);

    }

    /**
     * 取消发布按钮点击事件
     *
     * @param event
     */
    @FXML
    public void cancelLunch(ActionEvent event) {
        // callUpdateContent(event, Consistent.UN_LAUNCH);
    }

    @FXML
    public void openWord(ActionEvent event) {
        Button button = (Button) event.getSource();
        Content article = (Content) button.getUserData();

        if (article == null) {
            AlertUtil.showError("没有文章");
            return;
        }

        wordModel.setArticle(article);
        SourceItem sourceItem = new SourceItem();
        sourceItem.setDisplay(article.getTitle());
        sourceItem.setValue("WordModel");
        contentModel.setAddBreadCrumb(sourceItem);
    }

    @FXML
    public void createArticle(ActionEvent event) {
       Content article = createArticle(this.getModel().getBook().getId());
       this.contentInfoFormView.setValue(article);
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

    /**
     * 取消发布按钮点击事件
     *
     * @param event
     */
    @FXML
    public void lunch(ActionEvent event) {
        // callUpdateContent(event, Consistent.UN_LAUNCH);
    }

    @FXML
    public void showSegemnt(ActionEvent event) {
        // callUpdateContent(event, Consistent.UN_LAUNCH);
        Button button = (Button) event.getSource();
        Content article = (Content) button.getUserData();
        if (article == null) {
            AlertUtil.showError("没有文章");
            return;
        }

        SourceItem sourceItem = new SourceItem();
        sourceItem.setDisplay(article.getTitle());
        sourceItem.setValue("SegmentModel");
        this.contentModel.setAddBreadCrumb(sourceItem);
        this.segmentModel.setArticle(article);
        this.segmentModel.setBook(this.getModel().getBook());
    }

}
