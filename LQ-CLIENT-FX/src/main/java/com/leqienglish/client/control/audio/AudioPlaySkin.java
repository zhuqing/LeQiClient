/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.audio;

import com.leqienglish.client.control.CustomSkin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author zhuqing
 * @param <C>
 * @param <BB>
 */
public class AudioPlaySkin extends CustomSkin<AudioPlay, AudioPlayBehavior<AudioPlay>> {

    private MediaPlayer mediaplay;

    private ProgressBar progressBar;

    private BorderPane rootPane;

    private Label timeLabel;

    public AudioPlaySkin(AudioPlay audioPlay) {
        super(audioPlay, new AudioPlayBehavior(audioPlay));
    }

    @Override
    protected void showSkin() {
        super.showSkin(); //To change body of generated methods, choose Tools | Templates.
        this.getChildren().add(this.rootPane);
    }

    @Override
    protected void initSkin() {
        super.initSkin(); //To change body of generated methods, choose Tools | Templates.
        initListener();
        this.mediaplay = this.createMediaplayer(this.getSkinnable().getSource());
        this.rootPane = new BorderPane();

        this.rootPane.setLeft(this.createOpeatorBar());
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(createprogressBar());
        this.rootPane.setCenter(stackPane);

    }

    private HBox createprogressBar() {
        HBox hbox = new HBox();
        timeLabel = new Label();
        this.progressBar = this.createProgressBar();
        hbox.getChildren().addAll(timeLabel, progressBar);

        return hbox;

    }

    private HBox createOpeatorBar() {
        HBox hbox = new HBox();
        hbox.getChildren().addAll(this.playButton(), this.pauseButton(), this.backButton());
        return hbox;
    }

    private MediaPlayer createMediaplayer(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        Media media = new Media(source);

        MediaPlayer mediaplay = new MediaPlayer(media);
        mediaplay.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                Double current = newValue.toMillis();
                double totle = mediaplay.getTotalDuration().toMillis();
                getSkinnable().setCurrentPlayTime(current.longValue());
                progressBar.setProgress(current / totle);
                timeLabel.setText(newValue.toSeconds() + "");

            }
        });

        return mediaplay;
    }

    private void initListener() {
        this.getSkinnable().sourceProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (mediaplay != null) {
                    mediaplay.stop();
                }
                if (newValue == null || newValue.isEmpty()) {
                    return;
                }
                mediaplay = createMediaplayer(newValue);
                getSkinnable().setCurrentPlayTime(0L);
            }
        });
    }

    private ProgressBar createProgressBar() {
        ProgressBar pb = new ProgressBar(0.0);

        return pb;
    }

    private Button playButton() {
        Button button = new Button("play");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mediaplay == null) {
                    return;
                }
                mediaplay.getTotalDuration().toMillis();
                mediaplay.play();
            }
        });

        return button;
    }

    private Button backButton() {
        Button button = new Button("back");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mediaplay == null) {
                    return;
                }

                double newTime = 0.0;
                if (10 * 1000 < mediaplay.getCurrentTime().toMillis()) {
                    newTime = mediaplay.getCurrentTime().toMillis() - 10 * 1000;
                }
                mediaplay.seek(new Duration((newTime)));
            }
        });

        return button;
    }

    private Button pauseButton() {
        Button button = new Button("pause");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mediaplay == null) {
                    return;
                }

                mediaplay.pause();
            }
        });

        return button;
    }

}
