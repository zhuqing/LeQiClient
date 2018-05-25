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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private List<Label> labels;

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
        labelBox = new VBox();
        rootPane.setCenter(labelBox);
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
                if (timeStempIndex >= texts.size() - 1) {
                    return;
                } else {
                    timeStempIndex++;
                    String text = texts.get(timeStempIndex);
                    if (text.contains(":")) {
                        text = text.split(":")[1];
                    }
                    texts.set(timeStempIndex, audioPlay.getCurrentPlayTime() + ":" + text);
                    labels.get(timeStempIndex).setText(texts.get(timeStempIndex));
                    toTargetText(texts);
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

            Label label = new Label(text);
            label.setUserData(i);
            labels.add(label);
            label.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Label la = (Label) event.getSource();
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
    }

    private void toTargetText(List<String> texts) {
        StringBuffer sb = new StringBuffer();
        for (String s : texts) {
            sb.append(s).append("\n");
        }
        isAddTimePoint = true;
        initLabels(texts);
        isAddTimePoint = false;
        this.getSkinnable().setTargetText(sb.toString());
    }

}
