/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.unedit;


import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author duyi
 * @param <S>
 * @param <T>
 */
public class LQRequiredFormTitleCell<S> extends LQEditableFormCell<S, Object, Node> {

    private Boolean required;

    public Boolean getRequired() {
        if (required == null) {
            required = false;
        }
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Override
    protected Node createEditGraghic() {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_RIGHT);
        box.setSpacing(0);
        box.setPadding(Insets.EMPTY);
        if (getRequired()) {
            Label targetlabel = new Label("*");
            targetlabel.getStyleClass().add("required-target-label");
            box.getChildren().add(targetlabel);
        }
        box.getChildren().add(new Label(this.getText()));
        return box;
    }

    @Override
    protected void updateValue(Object t) {
//        super.updateValue(t); //To change body of generated methods, choose Tools | Templates.
    }

}
