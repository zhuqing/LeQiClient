/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.audio;

import com.leqienglish.client.control.CustomSkin;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

//    private ProgressBar progressBar;
    private BorderPane rootPane;

    private Label timeLabel;

    private Button audioButton;

    private Slider slider;

    private boolean pressed = false;

    private double startX = 0.0;

    private final ChangeListener<Number> valueChangeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (mediaplay == null) {
                return;
            }
            timeLabel.setText(newValue.longValue() + "/" + (long) mediaplay.getTotalDuration().toMillis());
            Duration duran = Duration.millis(newValue.doubleValue());
            mediaplay.seek(duran);
        }
    };

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
        timeLabel.setMinWidth(100);
        timeLabel.setMaxWidth(100);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(timeLabel, createSlider());

        return hbox;

    }

    private Slider createSlider() {
        this.slider = new Slider();
        this.slider.valueProperty().addListener(valueChangeListener);
        slider.setMin(0);
        return slider;
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
                Double totle = mediaplay.getTotalDuration().toMillis();
                getSkinnable().setCurrentPlayTime(current.longValue());

                //progressBar.setProgress(current / totle);
                timeLabel.setText(current.longValue() + "/" + totle.longValue());
                slider.valueProperty().removeListener(valueChangeListener);
                slider.setValue(current);
                slider.valueProperty().addListener(valueChangeListener);
            }
        });
        mediaplay.totalDurationProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                if (newValue == Duration.UNKNOWN) {
                    return;
                }
                slider.setMax(newValue.toMillis());
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
        JavaFxObservable.changesOf(getSkinnable().playingProperty()).map(c -> c.getNewVal())
                .subscribe((c) -> {
                    if (c) {

                        mediaplay.play();

                    } else {
                        mediaplay.pause();
                    }
                });

        JavaFxObservable.changesOf(getSkinnable().resetPlayTimeProperty()).map(c -> c.getNewVal())
                .subscribe((c) -> {
                    if (this.getSkinnable().getPlaying()) {
                        mediaplay.seek(Duration.millis(c.doubleValue()));
                    }
                });
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
                if (getSkinnable().getResetPlayTime() > 0L) {
                    long mill = getSkinnable().getResetPlayTime();
                    getSkinnable().setResetPlayTime(0L);
                    mediaplay.seek(Duration.millis(mill));
                }else{
                      mediaplay.play();
                }
              
                getSkinnable().setPlaying(true);
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
                    newTime = mediaplay.getCurrentTime().toMillis() - 5 * 1000;
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
                getSkinnable().setPlaying(false);
            }
        });

        return button;
    }

}
