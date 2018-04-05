/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lenglish.sample.fxml;


import com.lenglish.sample.HipFXSample;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author yangzheng
 */
public class FxmlSample<T extends Object> extends HipFXSample {

    /**
     * 显示格式
     */
    private final StringProperty FXML = new SimpleStringProperty();
    /**
     * 默认文件夹路径
     */
    private final String directoryPath = "/com/bjgoodwill/hip/client/control/samples/fxml";
    private Node loadedNode;

    private BorderPane panelRoot;

    /**
     * 演示控件名称
     *
     * @return
     */
    @Override
    public String getSampleName() {
        return "FXML_TEST";
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

        panelRoot = new BorderPane();

        return panelRoot;
    }

    /**
     * 控件控制区域
     *
     * @return
     */
    @Override
    public Node getControlPanel() {
        VBox vbox = new VBox();

        //控制面板
        HBox controlRoot = new HBox();
        TextArea FXMLPathText = new TextArea();
        FXMLPathText.setEditable(false);
        FXMLPathText.textProperty().bind(FXML);
        Button fileChangeBtn = new Button();
        fileChangeBtn.setText("加载fxml");
        controlRoot.getChildren().addAll(FXMLPathText, fileChangeBtn);

        //文件选择窗
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("FXML Files", "*.fxml"));

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\com"));
        //点击按钮弹出FXML文件选择框
        fileChangeBtn.setOnAction((ActionEvent event) -> {
            File file = fileChooser.showOpenDialog(fileChangeBtn.getScene().getWindow());
            try {
                loadedNode = (Node) loadFXML(file);
                panelRoot.setCenter(loadedNode);
            } catch (Exception ex) {
                Logger.getLogger(FxmlSample.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Button addData = new Button("addData");
        addData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (loadedNode == null) {
                    return;
                }
               
            }
        });
        vbox.getChildren().addAll(controlRoot, addData);
        return vbox;
    }

    public static Object loadFXML(String path) throws Exception {
        return loadFXML(new File(path));
    }

    /**
     * 加载FXML格式的节点
     *
     * @param element
     * @return
     * @throws Exception
     */
    public static Object loadFXML(File element) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream fxmlStream = new FileInputStream(element);
        return loader.load(fxmlStream);
    }

//    @Override
    public String getControlStylesheetURL() {
        return "";
    }

}
