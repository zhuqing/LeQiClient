/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.info.uf;

import com.leqi.client.book.info.cf.SavaCatalogCommand;
import com.leqi.client.book.uf.*;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.view.listview.LQListView;
import com.leqienglish.client.fw.sf.FileService;

import com.leqienglish.client.fw.sf.UpLoadFileService;
import com.leqienglish.client.fw.uf.FXMLController;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    private File imagePath;

    @Resource(name = "UpLoadFileService")
    private UpLoadFileService upLoadFile;

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
        JavaFxObservable.nullableValuesOf(this.getModel().catalogProperty())
                .map((p) -> p.orElse(null))
                .subscribe((c) -> this.bookInfoFormView.setValue(c));
        if (this.getModel().getCatalog() != null) {
            bookInfoFormView.setValue(getModel().getCatalog());
        }
    }

    @FXML
    public void saveBook(ActionEvent event) {
        savaCatalogCommand.doCommand();
    }

}
