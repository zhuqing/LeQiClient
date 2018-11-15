/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.timestemp.check;

import com.leqienglish.client.control.timestemp.*;
import com.leqienglish.client.control.timestemp.TimeStemp.SentenceAndIndex;
import io.reactivex.functions.Consumer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import xyz.tobebetter.entity.english.play.AudioPlayPoint;



/**
 *
 * @author zhuqing
 */
public class TimeStempCheck extends Control {

    private StringProperty audioPath;
    private StringProperty checkText;

    /**
     * 对单独的句子处理
     */
    private Consumer<Button> sentenceConsumer;
    /**
     * 是否支持中文
     */
    private BooleanProperty suportChinease;

    private BooleanProperty playing;

    @Override
    protected Skin<?> createDefaultSkin() {
        return new TimeStempCheckSkin(this);
    }

    /**
     * @return the audioPath
     */
    public String getAudioPath() {
        return audioPathProperty().getValue();
    }

    /**
     * @return the audioPath
     */
    public StringProperty audioPathProperty() {
        if (audioPath == null) {
            audioPath = new SimpleStringProperty();
        }
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPathProperty().setValue(audioPath);
    }

    /**
     * 是否支持中文
     *
     * @return the chinease
     */
    public Boolean isSuportChineaseChinease() {
        return suportChineaseProperty().getValue();
    }

    /**
     * 是否支持中文
     *
     * @return the chinease
     */
    public BooleanProperty suportChineaseProperty() {
        if (suportChinease == null) {
            suportChinease = new SimpleBooleanProperty(Boolean.FALSE);
        }
        return suportChinease;
    }

    /**
     * 是否支持中文
     *
     * @param chinease the chinease to set
     */
    public void setSuportChineaseChinease(Boolean chinease) {
        this.suportChineaseProperty().setValue(chinease);
    }

    /**
     * @return the playing
     */
    public Boolean getPlaying() {
        return playingProperty().getValue();
    }

    /**
     * @return the playing
     */
    public BooleanProperty playingProperty() {
        if (playing == null) {
            playing = new SimpleBooleanProperty(Boolean.FALSE);
        }
        return playing;
    }

    /**
     * @param playing the playing to set
     */
    public void setPlaying(Boolean playing) {
        this.playingProperty().setValue(playing);
    }

    /**
     * @return the checkText
     */
    public String getCheckText() {
        return checkTextProperty().getValue();
    }

    /**
     * @return the checkText
     */
    public StringProperty checkTextProperty() {
        if (checkText == null) {
            checkText = new SimpleStringProperty();
        }
        return checkText;
    }

    /**
     * @param checkText the checkText to set
     */
    public void setCheckText(String checkText) {
        this.checkTextProperty().setValue(checkText);
    }

    /**
     * @return the sentenceConsumer
     */
    public Consumer<Button> getSentenceConsumer() {
        return sentenceConsumer;
    }

    /**
     * @param sentenceConsumer the sentenceConsumer to set
     */
    public void setSentenceConsumer(Consumer<Button> sentenceConsumer) {
        this.sentenceConsumer = sentenceConsumer;
    }

}
