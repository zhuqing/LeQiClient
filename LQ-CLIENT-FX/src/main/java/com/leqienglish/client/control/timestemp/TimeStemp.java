/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.timestemp;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;

/**
 *
 * @author zhuqing
 */
public class TimeStemp extends Control{
    private StringProperty audioPath;
    private StringProperty sourceText;
    private StringProperty targetText;

    /**
     * @return the audioPath
     */
    public StringProperty getAudioPath() {
        return audioPath;
    }

    /**
     * @param audioPath the audioPath to set
     */
    public void setAudioPath(StringProperty audioPath) {
        this.audioPath = audioPath;
    }

    /**
     * @return the sourceText
     */
    public StringProperty getSourceText() {
        return sourceText;
    }

    /**
     * @param sourceText the sourceText to set
     */
    public void setSourceText(StringProperty sourceText) {
        this.sourceText = sourceText;
    }

    /**
     * @return the targetText
     */
    public StringProperty getTargetText() {
        return targetText;
    }

    /**
     * @param targetText the targetText to set
     */
    public void setTargetText(StringProperty targetText) {
        this.targetText = targetText;
    }
}
