/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lenglish.sample.control;


import com.lenglish.sample.HipFXSample;
import com.leqienglish.client.control.audio.AudioPlay;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author zhuqing
 */
public class HipAudioPlaySample extends HipFXSample {

    private AudioPlay audioPlay;
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
        String map3Path = "C:\\Users\\zhuqing.BJGOODWILL\\Downloads\\zted180404_4137744FWD.mp3";
        File filr = new File(map3Path);
        this.audioPlay = new AudioPlay();
       this.audioPlay.setSource(filr.toURI().toString());
        return audioPlay;
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
        Button button = new Button("获取值");
        Label label = new Label("值：");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              //  label.setText("值：\r" + hipAddress.getValue().toString());
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
