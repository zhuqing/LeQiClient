/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.bool;


import com.leqienglish.client.control.form.cell.LQFormCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author zhangyingchuang
 */
public class LQBooleanLabelFormCell<S> extends LQBooleanFormCell {

    private Label label;

    private String trueText;

    private String falseText;

    private final ChangeListener<String> changeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            commitBooleanValue(newValue.equals(getTrueText()));
        }

    };

    private final EventHandler<MouseEvent> mouseEventHander = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (getLabel().getText().equals(getFalseText())) {
                getLabel().setText(getTrueText());
            } else {
                getLabel().setText(getFalseText());
            }
        }
    };

    private final EventHandler<KeyEvent> keyEventHander = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.SPACE) {
                if (getLabel().getText().equals(getFalseText())) {
                    getLabel().setText(getTrueText());
                } else {
                    getLabel().setText(getFalseText());
                }
            }
        }
    };

    @Override
    protected Node createEditGraghic() {
        if (label == null) {
            label = new Label(getText());
            label.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHander);
            label.textProperty().addListener(changeListener);
            label.addEventHandler(KeyEvent.ANY, keyEventHander);
            label.getStyleClass().add("boolean-label");
          //  FocusUtil.registEnterToNextFocus(this,label);
        }
        return label;

    }

    @Override
    protected void updateValue(Object t) {
        super.updateValue(t);
        this.getLabel().textProperty().removeListener(changeListener);
        if (getBoolean(t)) {
            this.getLabel().setText(getTrueText());
        } else {
            this.getLabel().setText(getFalseText());
        }
        this.getLabel().textProperty().addListener(changeListener);
    }

    public Label getLabel() {
        if (label == null) {
            createEditGraghic();
        }
        return label;
    }

    public String getTrueText() {
        if (trueText == null || trueText.isEmpty()) {
            trueText = "true";
        }
        return trueText;
    }

    public void setTrueText(String trueText) {
        this.trueText = trueText;
    }

    public String getFalseText() {
        if (falseText == null || falseText.isEmpty()) {
            falseText = "false";
        }
        return falseText;
    }

    public void setFalseText(String falseText) {
        this.falseText = falseText;
    }

    @Override
    public LQBooleanLabelFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQBooleanLabelFormCell booleanLabelFormCell = (LQBooleanLabelFormCell) formCell;
            booleanLabelFormCell.setTrueText(this.getTrueText());
            booleanLabelFormCell.setFalseText(this.getFalseText());
            return booleanLabelFormCell;
        }
        return null;
    }
}
