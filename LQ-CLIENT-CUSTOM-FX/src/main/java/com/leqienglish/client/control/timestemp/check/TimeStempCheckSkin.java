/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.timestemp.check;

import com.leqienglish.client.control.timestemp.*;
import com.leqienglish.client.control.CustomSkin;
import com.leqienglish.client.control.audio.AudioPlay;
import com.leqienglish.client.util.alert.AlertUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.play.AudioPlayPoint;

/**
 *
 * @author zhuqing
 */
public class TimeStempCheckSkin extends CustomSkin<TimeStempCheck, TimeStempCheckBehavior<TimeStempCheck>> {

    private AudioPlay audioPlay;

    private List<AudioPlayPoint> audioPlayPoints;

    private Map<AudioPlayPoint, Label> labelMap = new HashMap<>();

    private boolean isAddTimePoint = false;

    public int currentPlayIndex = -1;

    private BorderPane rootPane;

    private Integer timeStempIndex = 0;

    private List<String> texts = new ArrayList<>();

    private List<HBox> timeHBoxs = new ArrayList<>();

    private Label currentPlay;

    private VBox labelBox;

    public TimeStempCheckSkin(TimeStempCheck timeStemp) {
        super(timeStemp, new TimeStempCheckBehavior<TimeStempCheck>(timeStemp));
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
        audioPlay = new AudioPlay();

        labelBox = new VBox();
        labelBox.setPrefHeight(300);
        labelBox.setMinWidth(900);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(labelBox);

        rootPane.setCenter(scrollPane);
        rootPane.setBottom(audioPlay);
        currentPlay = new Label("===>");
        currentPlay.setMinWidth(100);
        initListener();
        buildAudioPlayPoints(this.getSkinnable().getCheckText());
    }

    private void buildAudioPlayPoints(String text) {
         this.labelBox.getChildren().clear();
        if (text == null || text.isEmpty()) {
            return;
        }
        try {
            this.audioPlayPoints = AudioPlayPoint.toAudioPlays(text);
            rebuildSubtitles();
        } catch (Exception ex) {
            // AlertUtil.showError(ex.getMessage());
            Logger.getLogger(TimeStempCheckSkin.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    private void rebuildSubtitles() {

        this.labelBox.getChildren().clear();
        if (this.getSkinnable().getWidth() > 10) {
            labelBox.setPrefWidth(this.getSkinnable().getWidth());
        }
        labelMap.clear();
        if (this.audioPlayPoints == null || this.audioPlayPoints.isEmpty()) {
            return;
        }

        this.audioPlay.setResetPlayTime(this.audioPlayPoints.get(0).getStartTime());

        for (AudioPlayPoint app : this.audioPlayPoints) {
            Label label = createSetences(app);
            //labelBox.getChildren().add(label);
            labelMap.put(app, label);
        }
    }

    private Label createSetences(AudioPlayPoint playPoint) {
        HBox hbox = new HBox();

        HBox timeBox = new HBox();
        timeBox.setMinWidth(120);
        timeHBoxs.add(timeBox);
        Label scentens = null;
        if (playPoint.getChText() != null) {
            scentens = new Label(playPoint.getEnText() + "<>" + playPoint.getChText());
        } else {
            scentens = new Label(playPoint.getEnText());
        }

        if (getSkinnable().getWidth() > 10) {
            scentens.setPrefWidth(this.getSkinnable().getWidth() - 70);
        }else{
            scentens.setPrefWidth(800);
        }

        hbox.getChildren().addAll(timeBox, scentens);

        addWordsButton(hbox, playPoint);

        labelBox.getChildren().add(hbox);

        return scentens;
    }

    /**
     * 单词所在的句子的音频
     *
     * @param hbox
     * @param textField
     */
    private void addWordsButton(HBox hbox, AudioPlayPoint textField) {
        if (this.getSkinnable().getSentenceConsumer() == null) {
            return;
        }
        Button button = new Button("编辑单词");
        hbox.getChildren().add(button);

        JavaFxObservable.eventsOf(button, MouseEvent.MOUSE_CLICKED)
                .filter(e -> e.getClickCount() == 1)
                .subscribe(e -> getSkinnable().getSentenceConsumer().accept(textField));
    }

    private void initListener() {

        JavaFxObservable.nullableValuesOf(this.getSkinnable().audioPathProperty())
                .subscribe(p -> setAudioPath(p.orElse("")));

        JavaFxObservable.changesOf(this.getSkinnable().checkTextProperty())
                .subscribe(c -> this.buildAudioPlayPoints(c.getNewVal()));

        JavaFxObservable.changesOf(audioPlay.currentPlayTimeProperty())
                .subscribe((p) -> currentPlayTimeChange(p.getNewVal().longValue()));

        setAudioPath(this.getSkinnable().getAudioPath());
        this.audioPlay.playingProperty().bindBidirectional(this.getSkinnable().playingProperty());
    }

    private void currentPlayTimeChange(long currentTime) {

        AudioPlayPoint currentPlayPoint = null;
        for (AudioPlayPoint app : this.audioPlayPoints) {
            if (app.getStartTime() < currentTime && app.getEndTime() > currentTime) {
                currentPlayPoint = app;
                break;
            }

        }

        if (currentPlayPoint == null) {
            return;
        }

        int index = currentPlayPoint.getIndex();

        if (index == this.currentPlayIndex) {
            return;
        }
        currentPlayIndex = index;
        HBox currentHbox = this.timeHBoxs.get(index);
        currentHbox.getChildren().clear();
        currentHbox.getChildren().add(this.currentPlay);

    }

    private void setAudioPath(String path) {
        if (path == null || path.isEmpty()) {
            this.audioPlay.setSource(null);
            return;
        }
        //File filr = new File(path);
        this.audioPlay.setSource(path);
    }

}
