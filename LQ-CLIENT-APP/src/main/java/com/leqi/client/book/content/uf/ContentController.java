/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.content.uf;

import com.leqienglish.client.control.navigation.LQBreadCrumbBar;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import com.leqienglish.client.util.sourceitem.SourceItem;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

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
        if (this.getModel().getFirstModel() == null) {
            SourceItem item = new SourceItem();
            item.setDisplay("Book");
            item.setValue("BookModel");
            lqBreadCrumBar.add(item);
        } else {
            SourceItem item = new SourceItem();
            item.setDisplay("Root");
            item.setValue(this.getModel().getFirstModel());
            lqBreadCrumBar.add(item);
        }

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
