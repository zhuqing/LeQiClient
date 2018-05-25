/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.choose.text;


import com.leqienglish.client.control.form.cell.edit.choose.LQChooseFormCell;
import com.leqienglish.client.control.text.choose.LQTextInputOrSelect;
import com.leqienglish.client.util.sourceitem.SourceItem;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author zhuqing
 */
public class LQTextInputOrSelectFormCell<S> extends LQChooseFormCell<S, String, LQTextInputOrSelect> {

    private LQTextInputOrSelect textInputSelect;

    @Override
    protected LQTextInputOrSelect createEditGraghic() {
        if (textInputSelect != null) {
            return textInputSelect;
        }
        textInputSelect = new LQTextInputOrSelect();
        this.textInputSelect.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                commitValue(newValue);
            }
        });
        return textInputSelect;
    }

    @Override
    protected void updateValue(String t) {

        super.updateValue(t);
        if (this.textInputSelect == null) {
            createEditGraghic();
        }
        if (this.getCustomDataSourceCallback() != null) {
            List<SourceItem<String>> items = this.getCustomDataSourceCallback().call(this.getItem());
            textInputSelect.setItems(items);
        }

        if (t == null) {
            this.textInputSelect.setValue("");
        } else {
            this.textInputSelect.setValue(t);
        }

    }

}
