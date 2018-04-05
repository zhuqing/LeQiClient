/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.root.uf.business;

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
@Component("businessRootController")
public class BusinessRootController extends FXMLController<BusinessRootModel> {

    @FXML
    private StackPane rootPane;

    @FXML
    private TabPane rootTabPane;

    @FXML
    private StackPane hipPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getModel().businessModelProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null) {
                    return;
                }

                openModel(newValue, null);
            }

        });

    }

    private void openModel(String businessId, String parameter) {
        final FXMLModel businessModel = getModel(businessId);

    }

    @Override
    public void refresh() {

    }

    public TabPane getRootTabPane() {
        return rootTabPane;
    }

    private void addVisiableListener(TabPane tabPane, Tab subTab, BooleanProperty businessVisitProperty) {
        businessVisitProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (tabPane.getTabs().contains(subTab)) {
                        return;
                    } else {
                        tabPane.getTabs().add(subTab);
                    }
                } else {
                    tabPane.getTabs().remove(subTab);
                }
            }
        });
    }
}
