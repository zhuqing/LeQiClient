/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.timestemp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author zhuqing
 */
public class TimeStemp extends Control {

    private StringProperty audioPath;
    private StringProperty sourceText;
    private StringProperty targetText;
    /**
     * 是否支持中文
     */
    private BooleanProperty suportChinease;

    @Override
    protected Skin<?> createDefaultSkin() {
        return new TimeStempSkin(this);
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

    /**
     * @param audioPath the audioPath to set
     */
    public void setAudioPath(String audioPath) {
        this.audioPathProperty().setValue(audioPath);
    }

    /**
     * @return the audioPath
     */
    public StringProperty sourceTextProperty() {
        if (sourceText == null) {
            sourceText = new SimpleStringProperty();
        }
        return sourceText;
    }

    /**
     * @return the sourceText
     */
    public String getSourceText() {
        return sourceTextProperty().getValue();
    }

    /**
     * @param sourceText the sourceText to set
     */
    public void setSourceText(String sourceText) {
        this.sourceTextProperty().setValue(sourceText);
    }

    /**
     * @return the targetText
     */
    public String getTargetText() {
        return targetTextProperty().getValue();
    }

    /**
     * @return the targetText
     */
    public StringProperty targetTextProperty() {
        if (targetText == null) {
            targetText = new SimpleStringProperty();
        }
        return targetText;
    }

    /**
     * @param targetText the targetText to set
     */
    public void setTargetText(String targetText) {
        this.targetTextProperty().setValue(targetText);
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
}
