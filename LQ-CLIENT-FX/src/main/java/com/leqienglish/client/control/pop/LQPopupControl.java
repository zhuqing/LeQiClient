/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.pop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.PopupControl;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Window;

/**
 *
 * @author zhuqing
 */
public class LQPopupControl extends PopupControl {

    public LQPopupControl() {
        this.anchorYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (getSkin().getNode() instanceof Region) {
                    Region r = (Region) getSkin().getNode();
                    double screenY = newValue.doubleValue();
                   if (screenY + r.getHeight() > getSenceHeight()) {
                        LQPopupControl.this.setAnchorY(screenY - r.getHeight() - 30 - 1);
                    }
                }
            }
        });
    }

    @Override
    public void show(Window ownerWindow, double screenX, double screenY) {
        screenX = newScreenX(screenX);
        screenY = newScreenY(screenY);

        super.show(ownerWindow, screenX, screenY);
    }

    @Override
    public void show(Node ownerNode, double screenX, double screenY) {
        screenX = newScreenX(screenX);
        screenY = newScreenY(screenY);
        ownerNode.getScene().getWidth();
        super.show(ownerNode, screenX, screenY); //To change body of generated methods, choose Tools | Templates.
    }

    private double newScreenX(double screenX) {

        if (screenX < 0) {
            return 1.0;
        }
        if (this.getSkin().getNode() instanceof Region) {
            Region r = (Region) this.getSkin().getNode();
            if (screenX + r.getWidth() > getSenceWidth()) {
                return getSenceWidth() - r.getWidth() - 1;
            }
        }

        return screenX;
    }

    private double newScreenY(double screenY) {
        if (screenY < 0) {
            return 1.0;
        }
        if (this.getSkin().getNode() instanceof Region) {
            Region r = (Region) this.getSkin().getNode();
            if (screenY + r.getHeight() > getSenceHeight()) {
                return screenY - r.getHeight() - 30 - 1;
            }
        }
        return screenY;
    }

    private double getSenceHeight() {

        return Screen.getPrimary().getVisualBounds().getHeight();
    }

    private double getSenceWidth() {

        return Screen.getPrimary().getVisualBounds().getWidth();
    }

}
