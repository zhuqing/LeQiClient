/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.audio;

import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author zhuqing
 */
public class AudioPlay extends Control {

    private StringProperty source;

    private ObservableList<String> texts;

    private ObservableList<String> results;

    private LongProperty currentPlayTime;

    private LongProperty resetPlayTime;

    private BooleanProperty playing;

    @Override
    protected Skin<?> createDefaultSkin() {
        return new AudioPlaySkin(this);
    }

    /**
     * @return the source
     */
    public StringProperty sourceProperty() {
        if (source == null) {
            source = new SimpleStringProperty();
        }
        return source;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return sourceProperty().getValue();
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.sourceProperty().setValue(source);
    }

    /**
     * @return the texts
     */
    public ObservableList<String> getTexts() {
        if (texts == null) {
            texts = FXCollections.emptyObservableList();
        }
        return texts;
    }

    /**
     * @param texts the texts to set
     */
    public void setTexts(List<String> texts) {
        this.getTexts().setAll(texts);
    }

    /**
     * @return the results
     */
    public ObservableList<String> getResults() {
        if (results == null) {
            results = FXCollections.emptyObservableList();
        }

        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(List<String> results) {
        this.getResults().setAll(results);
    }

    /**
     * @return the currentPlayTime
     */
    public Long getCurrentPlayTime() {
        return currentPlayTimeProperty().getValue();
    }

    /**
     * @return the currentPlayTime
     */
    public LongProperty currentPlayTimeProperty() {
        if (currentPlayTime == null) {
            currentPlayTime = new SimpleLongProperty(0L);
        }
        return currentPlayTime;
    }

    /**
     * @param currentPlayTime the currentPlayTime to set
     */
    public void setCurrentPlayTime(Long currentPlayTime) {
        currentPlayTimeProperty().setValue(currentPlayTime);
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
     * @return the resetPlayTime
     */
    public Long getResetPlayTime() {
        return resetPlayTimeProperty().getValue();
    }

    /**
     * @return the resetPlayTime
     */
    public LongProperty resetPlayTimeProperty() {
        if (resetPlayTime == null) {
            resetPlayTime = new SimpleLongProperty(0L);
        }
        return resetPlayTime;
    }

    /**
     * @param resetPlayTime the resetPlayTime to set
     */
    public void setResetPlayTime(Long resetPlayTime) {
        this.resetPlayTimeProperty().setValue(resetPlayTime); 
    }
}
