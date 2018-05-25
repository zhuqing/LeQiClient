/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.uf;

import com.leqi.client.book.info.uf.BookInfoModel;
import com.leqienglish.client.control.view.listview.LQListView;
import com.leqienglish.client.fw.sf.FileService;

import com.leqienglish.client.fw.sf.UpLoadFileService;
import com.leqienglish.client.fw.uf.FXMLController;
import java.io.File;
import java.net.URL;
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
    private LQListView bookListView;

    @FXML
    private LQListView articListView;

    @FXML
    private StackPane bookBusinessPane;

    private File imagePath;

    @Resource(name = "UpLoadFileService")
    private UpLoadFileService upLoadFile;

    @Resource(name = "fileService")
    private FileService fileService;
    @Resource(name = "BookInfoModel")
    private BookInfoModel bookInfoModel;

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void createBook(ActionEvent event) {
        bookInfoModel.setCatalog(new Catalog());
        bookBusinessPane.getChildren().setAll(this.bookInfoModel.getRoot());
    }

    @FXML
    public void deleteBook(ActionEvent event) {

    }

}
