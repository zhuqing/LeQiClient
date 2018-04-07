/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.root.uf;

import com.leqienglish.client.fw.root.uf.business.*;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author duyi
 */
@Lazy
@Component("rootController")
public class RootController extends FXMLController<RootModel> {

    @FXML
    private StackPane rootPane;

    @FXML
    private StackPane navPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        getModel()().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (newValue == null) {
//                    return;
//                }
//
//                openModel(newValue, null);
//            }
//
//        });
        openModel("UpLoadModel");
    }

    private void openModel(String businessId) {
        final FXMLModel businessModel = getModel(businessId);
        rootPane.getChildren().setAll(businessModel.getRoot());

    }

    @Override
    public void refresh() {

    }

}
