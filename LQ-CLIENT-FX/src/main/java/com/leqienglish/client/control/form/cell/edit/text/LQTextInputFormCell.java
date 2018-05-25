/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.text;

import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.StackPane;


public class LQTextInputFormCell<S> extends LQEditableFormCell<S, String, StackPane> {

    private StackPane graghicRoot;

    private TextInputControl textField;

    private ChangeListener<String> textChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (getTextField().getText() == null || getTextField().getText().isEmpty()) {
                commitValue(null);
            } else {
                commitValue(getTextField().getText());
            }
        }
    };

    @Override
    protected StackPane createEditGraghic() {
        if (textField == null) {
            graghicRoot = new StackPane();
            textField = createTextField();
            textField.setPromptText(this.getPromptText());
            textField.textProperty().addListener(getTextChangeListener());
            graghicRoot.getChildren().add(textField);
        }
        return graghicRoot;
    }

    protected TextInputControl createTextField() {
        return new TextField();
    }

    @Override
    protected void updateValue(String t) {
        super.updateValue(t);
        this.updateTextInputValue(t);
    }

    protected void updateTextInputValue(String t) {
        this.getTextField().textProperty().removeListener(getTextChangeListener());
        this.getTextField().setText(toText(t));
        this.getTextField().textProperty().addListener(getTextChangeListener());
    }

    /**
     * @return the textField
     */
    public TextInputControl getTextField() {
        if (textField == null) {
            createEditGraghic();
        }
        return textField;
    }

    public StackPane getGraghicRoot() {
        return graghicRoot;
    }

    public void setGraghicRoot(StackPane graghicRoot) {
        this.graghicRoot = graghicRoot;
    }

    protected ChangeListener<String> getTextChangeListener() {

        return textChangeListener;
    }
}
