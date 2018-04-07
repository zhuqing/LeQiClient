/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.timestemp;

import com.leqienglish.client.control.CustomSkin;
import com.leqienglish.client.control.audio.AudioPlay;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author zhuqing
 */
public class TimeStempSkin extends CustomSkin<TimeStemp, TimeStempBehavior<TimeStemp>> {

    private TextArea sourceTextArea = new TextArea();

    private TextArea targetTextArea = new TextArea();

    private AudioPlay audioPlay;

    private BorderPane rootPane;

    private Integer timeStempIndex = 0;

    List<String> texts = new ArrayList<>();

    public TimeStempSkin(TimeStemp timeStemp) {
        super(timeStemp, new TimeStempBehavior<TimeStemp>(timeStemp));
    }

    @Override
    protected void showSkin() {
        super.showSkin(); //To change body of generated methods, choose Tools | Templates.
        this.getChildren().addAll(rootPane);
    }

    @Override
    protected void initSkin() {
        super.initSkin(); //To change body of generated methods, choose Tools | Templates.
        rootPane = new BorderPane();

        rootPane.setLeft(sourceTextArea);
        rootPane.setRight(targetTextArea);
        rootPane.setBottom(createplayBar());
        initListener();
    }

    private HBox createplayBar() {
        HBox hbox = new HBox();
        audioPlay = new AudioPlay();
        audioPlay.sourceProperty().bind(this.getSkinnable().audioPathProperty());
        Button addPoint = new Button("打点");
        addPoint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (timeStempIndex >= texts.size()-1) {
                    return;
                } else {
                    timeStempIndex++;
                    texts.set(timeStempIndex, audioPlay.getCurrentPlayTime() + ":" + texts.get(timeStempIndex));
                    toTargetText(texts);
                }
            }
        });

        hbox.getChildren().addAll(audioPlay, addPoint);
        return hbox;
    }

    private void initListener() {
        sourceTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null || newValue.isEmpty()) {
                    return;
                }
                String[] strs = newValue.split("\n");
                texts = new ArrayList<>();
                for (String item : strs) {
                    if (item.isEmpty()) {
                        continue;
                    }
                    texts.add(item);
                }
                timeStempIndex = 0;
                texts.set(0, "0:" + texts.get(0));
                toTargetText(texts);
            }
        });
    }

    private void toTargetText(List<String> texts) {
        StringBuffer sb = new StringBuffer();
        for (String s : texts) {
            sb.append(s).append("\n");
        }

        targetTextArea.setText(sb.toString());
        this.getSkinnable().setTargetText(sb.toString());
    }

}
