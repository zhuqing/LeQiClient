/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.bool;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;

/**
 *
 * @author zhangyingchuang
 */
public class LQBooleanRadioFormCell<S> extends LQBooleanFormCell {

    private RadioButton button;

    private final Tooltip tooltip = new Tooltip("空格选中,回车提交");

    private final ChangeListener<Boolean> changeListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            commitBooleanValue(newValue);
        }
    };

    @Override
    protected RadioButton createEditGraghic() {
        if (button == null) {
            button = new RadioButton();
            button.selectedProperty().addListener(changeListener);
            //FocusUtil.registEnterToNextFocus(this,button);
            button.setTooltip(tooltip);

        }
        return button;
    }

    @Override
    protected void updateValue(Object t) {
        super.updateValue(t);
        this.getRadioButton().selectedProperty().removeListener(changeListener);
        this.getRadioButton().setSelected(getBoolean(t));
        this.getRadioButton().selectedProperty().addListener(changeListener);
    }

    public RadioButton getRadioButton() {
        if (button == null) {
            createEditGraghic();
        }
        button.setText(this.getText());
        return button;
    }
}
