/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.bool;


import com.leqienglish.client.util.focus.FocusUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 *
 * @author zhangyingchuang
 */
public class LQBooleanCheckBoxFormCell<S> extends LQBooleanFormCell {

    private CheckBox checkbox;

    private final ChangeListener<Boolean> changeListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            commitBooleanValue(newValue);
        }
    };

    @Override
    protected CheckBox createEditGraghic() {
        if (checkbox == null) {
            checkbox = new CheckBox();
            checkbox.selectedProperty().addListener(changeListener);
            FocusUtil.registEnterToNextFocus(this);
        }
        return checkbox;
    }

    @Override
    protected void updateValue(Object t) {
        super.updateValue(t);
        this.getCheckBox().selectedProperty().removeListener(changeListener);
        this.getCheckBox().setSelected(this.getBoolean(t));
        this.getCheckBox().selectedProperty().addListener(changeListener);
    }

    public CheckBox getCheckBox() {
        if (checkbox == null) {
            createEditGraghic();
        }
        checkbox.setText(this.getText());
        return checkbox;
    }
}
