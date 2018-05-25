/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.choose;


import com.leqienglish.client.control.choose.LQRadioButtonPane;
import com.leqienglish.client.util.sourceitem.SourceItem;
import com.leqienglish.client.util.sourceitem.SourceItemUtil;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author zhangyingchuang
 */
public class LQRadioBoxFormCell<S, T, N extends LQRadioButtonPane> extends LQChooseFormCell<S, T, N> {

    private LQRadioButtonPane radioButtonPane;

    private final ChangeListener<SourceItem<T>> valueChangeListener = new ChangeListener<SourceItem<T>>() {
        @Override
        public void changed(ObservableValue<? extends SourceItem<T>> observable, SourceItem<T> oldValue, SourceItem<T> newValue) {
            if (newValue == null) {
                commitValue(null);
            } else {
                commitValue(newValue.getValue());
            }
        }

    };

    @Override
    protected N createEditGraghic() {
        if (radioButtonPane == null) {
            radioButtonPane = new LQRadioButtonPane();
            radioButtonPane.valueProperty().addListener(valueChangeListener);
        }
        return (N) radioButtonPane;
    }

    @Override
    protected void updateValue(T t) {
        super.updateValue(t);
        this.getRadioButtonPane().valueProperty().removeListener(valueChangeListener);
        List<SourceItem<T>> sourceItems = this.getCustomDataSourceCallback().call(this.getItem());
        this.getRadioButtonPane().setSourceItems(sourceItems);
        SourceItem selected = SourceItemUtil.getSourceItem((T) t, sourceItems);
        this.getRadioButtonPane().setValue(selected);
        this.getRadioButtonPane().valueProperty().addListener(valueChangeListener);
    }

    public LQRadioButtonPane getRadioButtonPane() {
        if (radioButtonPane == null) {
            createEditGraghic();
        }
        return radioButtonPane;
    }

    public void setRadioButtonPane(LQRadioButtonPane radioButtonPane) {
        this.radioButtonPane = radioButtonPane;
    }

}
