/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.content.uf;

import com.leqi.client.user.uf.*;
import com.leqi.client.book.article.cf.QueryArticlesCommand;
import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.cf.QueryCatalogCommand;
import com.leqi.client.book.info.uf.BookInfoModel;
import com.leqi.client.book.segment.info.uf.SegmentInfoModel;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqienglish.client.control.navigation.LQBreadCrumbBar;
import com.leqienglish.client.control.view.listview.LQListView;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.client.util.sourceitem.SourceItem;
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
@Component("ContentController")
public class ContentController extends FXMLController<ContentModel> {

    @FXML
    private LQBreadCrumbBar<SourceItem> lqBreadCrumBar;

    @FXML
    private StackPane contentBusinessPane;

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().addBreadCrumbProperty())
                .subscribe(s -> addBreadCrumb(s.getNewVal()));

        JavaFxObservable.changesOf(this.lqBreadCrumBar.selectedProperty())
                .subscribe(s -> breadCrumbarSelect(s.getNewVal()));
        SourceItem item = new SourceItem();
        item.setDisplay("Book");
        item.setValue("BookModel");
         lqBreadCrumBar.add(item);

    }

    private void addBreadCrumb(SourceItem sourceItem) {
        if (sourceItem == null) {
            return;
        }

        lqBreadCrumBar.add(sourceItem);
        breadCrumbarSelect(sourceItem);
    }

    /**
     *
     * @param sourceItem
     */
    private void breadCrumbarSelect(SourceItem sourceItem) {
        if (sourceItem == null) {
            return;
        }

        FXMLModel fxmlModel = this.getModel(sourceItem.getValue().toString());
        contentBusinessPane.getChildren().setAll(fxmlModel.getRoot());
    }

}
