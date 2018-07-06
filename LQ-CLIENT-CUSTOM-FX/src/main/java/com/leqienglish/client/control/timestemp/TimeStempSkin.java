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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import xyz.tobebetter.entity.Consistent;

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

    private List<HBox> timeHBoxs;

    private Label timeLabel;

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

        this.timeLabel = new Label("0");

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
                addPoint();
            }
        });

        hbox.getChildren().addAll(audioPlay, addPoint);
        return hbox;
    }

    /**
     * 给句子加上时间点
     */
    private void addPoint() {
        addLastEndPoint();
        if (timeStempIndex >= texts.size()) {
            return;
        }

        String text = texts.get(timeStempIndex);
        if (text.contains(Consistent.SLIP_TIME_AND_TEXT)) {
            text = text.split(Consistent.SLIP_TIME_AND_TEXT)[1];
        }
        texts.set(timeStempIndex, audioPlay.getCurrentPlayTime() + Consistent.SLIP_TIME_AND_TEXT + text);
        labels.get(timeStempIndex).setText(texts.get(timeStempIndex));
        toTargetText(texts);
        timeStempIndex++;
        if (getSkinnable().isSuportChineaseChinease()) {
            timeStempIndex++;
        }

        moveTimeLabel();

    }
  

    /**
     * 给上一个句子加上结束时间
     */
    private void addLastEndPoint() {
        int lastTimeStempIndex = timeStempIndex - 1;
        if (lastTimeStempIndex < 0 || lastTimeStempIndex >= texts.size()) {
            return;
        }

        String text = texts.get(lastTimeStempIndex);
        String timeText = "";
        if (text.contains(Consistent.SLIP_TIME_AND_TEXT)) {
            text = texts.get(lastTimeStempIndex).split(Consistent.SLIP_TIME_AND_TEXT)[1];
            timeText = texts.get(lastTimeStempIndex).split(Consistent.SLIP_TIME_AND_TEXT)[0];
        }

        if (timeText.contains(Consistent.SLIP_START_AND_END)) {
            timeText = timeText.split(Consistent.SLIP_START_AND_END)[0];
        }

        StringBuilder sb = new StringBuilder();
        sb.append(timeText).append(Consistent.SLIP_START_AND_END).append(audioPlay.getCurrentPlayTime()).append(Consistent.SLIP_TIME_AND_TEXT).append(text);

        texts.set(lastTimeStempIndex, sb.toString());
        labels.get(lastTimeStempIndex).setText(texts.get(lastTimeStempIndex));

    }

    private void moveTimeLabel() {
        if (timeStempIndex < texts.size()) {
            timeHBoxs.get(timeStempIndex).getChildren().setAll(timeLabel);
        }
        timeLabel.requestFocus();
    }

    /**
     * 初始化句子的文本筐
     *
     * @param sources
     */
    private void initLabels(List<String> sources) {
        this.labels = new ArrayList<>(sources.size());
        timeHBoxs = new ArrayList<>(sources.size());

        labelBox.getChildren().clear();
        int i = 0;
        for (String text : sources) {

            TextField label = createSetences(text);

            label.setUserData(i);

            i++;
        }

    }

    private TextField createSetences(String text) {
        HBox hbox = new HBox();

        HBox timeBox = new HBox();
        timeBox.setPrefWidth(60);
        timeHBoxs.add(timeBox);

        TextField scentens = new TextField(text);
        scentens.setPrefWidth(this.textField.getWidth() - 70);

        scentens.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextField la = (TextField) event.getSource();
                timeStempIndex = (int) la.getUserData();
                moveTimeLabel();
            }
        });

        hbox.getChildren().addAll(timeBox, scentens);
        
        addWordsButton(hbox,scentens);
        
        labelBox.getChildren().add(hbox);

        labels.add(scentens);
        return scentens;
    }

    /**
     * 单词所在的句子的音频
     * @param hbox
     * @param textField 
     */
    private void addWordsButton(HBox hbox, TextField textField) {
        if (this.getSkinnable().getSentenceConsumer() == null) {
            return;
        }
        Button button = new Button("编辑单词");
        hbox.getChildren().add(button);

        JavaFxObservable.eventsOf(button, MouseEvent.MOUSE_CLICKED)
                .filter(e -> e.getClickCount() == 1)
                .subscribe(e -> {
                    String text = textField.getText();
                    int index = (int) textField.getUserData();
                    getSkinnable().getSentenceConsumer().apply(index,text);
                });
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
                texts.set(0, texts.get(0));
                toTargetText(texts);
                timeLabel.setText("0:");
                moveTimeLabel();
            }
        });

        JavaFxObservable.nullableValuesOf(this.getSkinnable().sourceTextProperty())
                .subscribe((p) -> textField.setText(p.orElse("")));

        JavaFxObservable.nullableValuesOf(this.getSkinnable().audioPathProperty())
                .subscribe(p -> setAudioPath(p.orElse("")));

        JavaFxObservable.changesOf(audioPlay.currentPlayTimeProperty())
                .subscribe((p) -> this.timeLabel.setText(p.getNewVal() + ":"));

        //this.audioPlay.currentPlayTimeProperty().addListener(listener);
        this.getSkinnable().getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() != KeyCode.SHIFT) {
                    return;
                }
                addPoint();
                event.consume();
            }
        });

        if (this.getSkinnable().getSourceText() != null) {
            this.textField.setText("");
        }
        setAudioPath(this.getSkinnable().getAudioPath());
        this.audioPlay.playingProperty().bindBidirectional(this.getSkinnable().playingProperty());
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
                sb.append(texts.get(i)).append(Consistent.SLIP_EN_AND_CH).append(texts.get(i + 1)).append("\n");
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
