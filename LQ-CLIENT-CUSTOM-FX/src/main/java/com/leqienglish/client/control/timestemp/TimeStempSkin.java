/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.timestemp;

import com.leqienglish.client.control.CustomSkin;
import com.leqienglish.client.control.audio.AudioPlay;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author zhuqing
 */
public class TimeStempSkin extends CustomSkin<TimeStemp, TimeStempBehavior<TimeStemp>> {

    private TextArea textField = new TextArea();

    private AudioPlay audioPlay;

    private boolean isAddTimePoint = false;

    private BorderPane rootPane;

    private Integer timeStempIndex = 0;

    private List<TextField> labels;

    private List<String> texts = new ArrayList<>();

    private VBox labelBox;

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
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(textField);
        rootPane.setTop(stackPane);
        ScrollPane scrollPane = new ScrollPane();

        labelBox = new VBox();
        labelBox.setPrefHeight(600);
        scrollPane.setContent(labelBox);

        rootPane.setCenter(scrollPane);
        rootPane.setBottom(createplayBar());
        initListener();
    }

    private HBox createplayBar() {
        HBox hbox = new HBox();
        audioPlay = new AudioPlay();
        // audioPlay.sourceProperty().bind(this.getSkinnable().audioPathProperty());
        Button addPoint = new Button("打点");
        addPoint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (timeStempIndex >= texts.size()) {
                    return;
                } else {

                    String text = texts.get(timeStempIndex);
                    if (text.contains(":")) {
                        text = text.split(":")[1];
                    }
                    texts.set(timeStempIndex, audioPlay.getCurrentPlayTime() + ":" + text);
                    labels.get(timeStempIndex).setText(texts.get(timeStempIndex));
                    toTargetText(texts);
                    timeStempIndex++;
                    if (getSkinnable().isSuportChineaseChinease()) {
                        timeStempIndex++;
                    }
                }
            }
        });

        hbox.getChildren().addAll(audioPlay, addPoint);
        return hbox;
    }

    private void initLabels(List<String> sources) {
        this.labels = new ArrayList<>(sources.size());
        labelBox.getChildren().clear();
        int i = 0;
        for (String text : sources) {

            TextField label = new TextField(text);
            label.setUserData(i);
            labels.add(label);
            label.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    TextField la = (TextField) event.getSource();
                    timeStempIndex = (int) la.getUserData();
                }
            });
            i++;
        }

        labelBox.getChildren().addAll(labels);
    }

    private void initListener() {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null || newValue.isEmpty()) {
                    return;
                }
                if (isAddTimePoint) {
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

        JavaFxObservable.nullableValuesOf(this.getSkinnable().sourceTextProperty())
                .subscribe((p) -> textField.setText(p.orElse("")));

        JavaFxObservable.nullableValuesOf(this.getSkinnable().audioPathProperty())
                .subscribe(p -> setAudioPath(p.orElse("")));

        if (this.getSkinnable().getSourceText() != null) {
            this.textField.setText("");
        }
        setAudioPath(this.getSkinnable().getAudioPath());
    }

    private void setAudioPath(String path) {
        if (path == null || path.isEmpty()) {
            this.audioPlay.setSource(null);
            return;
        }
        File filr = new File(path);
        this.audioPlay.setSource(filr.toURI().toString());
    }

    private void toTargetText(List<String> texts) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < texts.size(); i++) {
            if (this.getSkinnable().isSuportChineaseChinease()) {
                sb.append(texts.get(i)).append("|||").append(texts.get(i + 1)).append("\n");
                i++;
            } else {
                sb.append(texts.get(i)).append("\n");
            }

        }

        isAddTimePoint = true;
        initLabels(texts);
        isAddTimePoint = false;
        this.getSkinnable().setTargetText(sb.toString());
    }

}
