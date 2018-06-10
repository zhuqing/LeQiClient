/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.button;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

/**
 * 
 * @author zhuqing
 */
public class LQButton extends Button {

    private Tooltip tooltip;

    private String tipText;

    private final PseudoClass HOVER = PseudoClass.getPseudoClass("hover");
//    @Override
//    protected Skin<?> createDefaultSkin() {
//        return new HipButtonSkin(this);
//    }

    public LQButton() {
        this("");
    }

    public LQButton(String text) {
        this(text, null);

    }

    public LQButton(String text, Node graphic) {
        super(text, graphic);
        this.getStyleClass().add("hip-button");
        disableProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    pseudoClassStateChanged(HOVER, false);
                }
            }
        });
    }

    @Override
    public LQButton clone() throws CloneNotSupportedException {
        LQButton lqButton = new LQButton();
        lqButton.setText(getText());
        lqButton.setVisible(isVisible());
        lqButton.setDisable(isDisable());
        lqButton.setOnAction(getOnAction());
        lqButton.setTipText(getTipText());
        lqButton.setId(this.getId());
        return lqButton;
    }

    public String getTipText() {
        return tipText;
    }

    public void setTipText(String tipText) {
        this.tipText = tipText;
        if (tipText != null && !tipText.isEmpty()) {
            tooltip = new Tooltip(tipText);
            this.setTooltip(tooltip);
        }
    }

}
