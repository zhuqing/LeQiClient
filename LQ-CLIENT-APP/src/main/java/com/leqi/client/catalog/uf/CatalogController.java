/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.catalog.uf;

import com.leqi.client.catalog.uf.CatalogModel;
import com.leqi.client.word.uf.*;
import com.leqi.client.book.word.cf.SaveWordsCommand;
import com.leqi.client.catalog.cf.QueryCatalogsCommand;
import com.leqi.client.catalog.cf.SaveCatalogsCommand;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.view.table.LQTableView;
import static com.leqienglish.client.fw.cf.Command.DATA;
import static com.leqienglish.client.fw.cf.Command.ID;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.client.wordpane.WordsPane;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.word.Word;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("CatalogController")
public class CatalogController extends FXMLController<CatalogModel> {

    @FXML
    private LQTableView<Catalog>  catalogTableView;
    
    @FXML
    private LQFormView<Catalog> catalogFormView;
    
    

    @Resource(name = "QueryCatalogsCommand")
    protected QueryCatalogsCommand queryCatalogsCommand;
    
    @Resource(name = "SaveCatalogsCommand")
    protected SaveCatalogsCommand saveCatalogsCommand;

    @Override
    public void refresh() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().getCatalogs())
                .subscribe((c)->this.catalogTableView.getItems().setAll(this.getModel().getCatalogs()));
        
       queryCatalogsCommand.doCommand(Collections.EMPTY_MAP);
    }

    @FXML
    public void createCatalog(ActionEvent event) {

        Catalog catalog = new Catalog();
        catalog.setType(Consistent.CATALOG_TYPE);
        this.catalogFormView.setValue(catalog);
    }

    @FXML
    public void saveCatalog(ActionEvent event) {

        Catalog catalog = this.catalogFormView.getValue();
        if(catalog == null){
            AlertUtil.showError("还没有创建数据");
            return;
        }
    }

    @FXML
    public void cancelLunch(ActionEvent event) {

    }

    @FXML
    public void lunch(ActionEvent event) {

    }

    @FXML
    public void delete(ActionEvent event) {

    }
}
