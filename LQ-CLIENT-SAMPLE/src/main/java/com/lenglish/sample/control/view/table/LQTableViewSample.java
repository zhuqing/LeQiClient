/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lenglish.sample.control.view.table;

import com.lenglish.sample.control.*;
import com.lenglish.sample.HipFXSample;
import com.lenglish.sample.entity.Person;
import com.lenglish.sample.fxml.FxmlSample;
import com.leqienglish.client.control.audio.AudioPlay;
import com.leqienglish.client.control.view.table.LQTableView;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author zhuqing
 */
public class LQTableViewSample extends HipFXSample {

    private LQTableView lqTableView;
//    private TextField textField;

    /**
     * 演示控件名称
     *
     * @return
     */
    @Override
    public String getSampleName() {
        return "HipAudioPlaySample";
    }

    /**
     * api地址
     *
     * @return
     */
    @Override
    public String getJavaDocURL() {
        return "";
    }

    /**
     * 是否显示演示内容
     *
     * @return
     */
    @Override
    public boolean isVisible() {
        return true;
    }

    /**
     * 控件演示区域
     *
     * @param stage
     * @return
     */
    @Override
    public Node getPanel(Stage stage) {
        super.getPanel(stage);
        try {
            lqTableView = (LQTableView) FxmlSample.loadFXML(this.getClass().getResource("/com/sample/view/table/TableView.fxml").getPath());
        } catch (Exception ex) {
            Logger.getLogger(LQTableViewSample.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lqTableView;
    }

    /**
     * 介绍
     *
     * @return
     */
    @Override
    public String getSampleDescription() {
        return "BorderPane";
    }

    /**
     * 控件控制区域
     *
     * @return
     */
    @Override
    public Node getControlPanel() {
        VBox vbox = new VBox();
//        textField = new TextField();
        Button button = new Button("添加数据");
        Label label = new Label("值：");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lqTableView.getItems().setAll(Person.createPersons());
            }
        });
        vbox.getChildren().addAll(button, label);
        return vbox;
    }

//    @Override
    public String getControlStylesheetURL() {
        return "";
    }

}
