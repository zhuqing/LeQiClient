/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.text;

import javafx.scene.control.Skin;
import javafx.scene.control.TextField;

/**
 *
 * @author zhuqing
 */
public class LQTextField extends TextField {

    /**
     * 是否可以替换文本
     */
    private Boolean unableReplace;

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LQTextFieldSkin(this); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 是否可以替换文本
     *
     * @return the couldReplace
     */
    public Boolean isUnableReplace() {
        if (unableReplace == null) {
            unableReplace = false;
        }
        return unableReplace;
    }

    /**
     * 是否可以替换文本
     *
     * @param unableReplace the couldReplace to set
     */
    public void setUnableReplace(Boolean unableReplace) {
        this.unableReplace = unableReplace;
    }

}
