/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.info.uf;

import com.leqi.client.book.cf.SavaCatalogCommand;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.fw.sf.FileService;

import com.leqienglish.client.fw.uf.FXMLController;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("BookInfoController")
public class BookInfoController extends FXMLController<BookInfoModel> {

    @FXML
    private LQFormView bookInfoFormView;

    @FXML
    private Label catalogTitle;

    private File imagePath;



    @Resource(name = "fileService")
    private FileService fileService;

    @Resource(name = "SavaCatalogCommand")
    private SavaCatalogCommand savaCatalogCommand;

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //对当前编辑的Catalog加监听
        JavaFxObservable.nullableValuesOf(this.getModel().catalogProperty())
                .map((p) -> p.orElse(null))
                .subscribe((c) -> this.bookInfoFormView.setValue(c));

        //对title加监听
        JavaFxObservable.nullableValuesOf(this.getModel().titleProperty())
                .map((p) -> p.orElse(""))
                .subscribe((title) -> catalogTitle.setText(title));
        
        if (this.getModel().getCatalog() != null) {
            bookInfoFormView.setValue(getModel().getCatalog());
        }

        if (this.getModel().getTitle() != null) {
            this.catalogTitle.setText(this.getModel().getTitle());
        }
    }

    @FXML
    public void saveBook(ActionEvent event) {
        savaCatalogCommand.doCommand(null);
    }

}
