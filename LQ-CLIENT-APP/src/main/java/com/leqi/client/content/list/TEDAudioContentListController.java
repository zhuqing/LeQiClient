package com.leqi.client.content.list;

import com.leqi.client.content.UpLoadModel;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.fw.uf.FXMLController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author zhuqing
 */
@Lazy
@Component("TEDAudioContentListController")
public class TEDAudioContentListController extends FXMLController< TEDAudioContentListModel> {

    @FXML
    private LQTableView contentListTableView;

    @Override
    public void refresh() {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }

    @FXML
    public void createContent(ActionEvent event) {

    }

    @FXML
    public void queryContent(ActionEvent event) {

    }

    @FXML
    public void lunchContent(ActionEvent event) {

    }

    @FXML
    public void disLunchContent(ActionEvent event) {

    }

}
