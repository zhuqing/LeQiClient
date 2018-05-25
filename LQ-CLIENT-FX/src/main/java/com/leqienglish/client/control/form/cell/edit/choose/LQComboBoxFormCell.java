/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.choose;


import com.leqienglish.client.control.combobox.LQComboBox;
import com.leqienglish.client.util.sourceitem.SourceItem;
import com.leqienglish.client.util.sourceitem.SourceItemUtil;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author zhuqing
 */
public class LQComboBoxFormCell<S, T, N extends LQComboBox> extends LQChooseFormCell<S, T, N> {

    /**
     * 下拉框组件
     */
    private LQComboBox<SourceItem<T>> lqComboBox;

    private final ChangeListener<SourceItem<T>> selectChangeListener = new ChangeListener<SourceItem<T>>() {
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
        if (lqComboBox == null) {
            lqComboBox = createN();
            lqComboBox.prefHeightProperty().bind(this.prefHeightProperty());
            lqComboBox.prefWidthProperty().bind(this.prefWidthProperty());

            lqComboBox.getSelectionModel().selectedItemProperty().addListener(selectChangeListener);
        }
        lqComboBox.setPromptText(this.getPromptText());
        return (N) lqComboBox;
    }

    protected N createN() {
        return (N) new LQComboBox();
    }

    @Override
    protected void updateValue(T t) {
        super.updateValue(t);
        this.getLQComboBox().getSelectionModel().selectedItemProperty().removeListener(selectChangeListener);
        List<SourceItem<T>> sourceItems = this.getCustomDataSourceCallback().call(this.getItem());
        this.getLQComboBox().setSourceItems(sourceItems);
        SourceItem selected = SourceItemUtil.getSourceItem(t, sourceItems);

        if (selected != null) {
            this.getLQComboBox().getSelectionModel().select(selected);
        } else {
            this.getLQComboBox().getSelectionModel().clearSelection();
            this.getLQComboBox().setValue(selected);
        }
        this.getLQComboBox().getSelectionModel().selectedItemProperty().addListener(selectChangeListener);
    }

    /**
     * @return the hipComboBox
     */
    public LQComboBox getLQComboBox() {
        if (this.lqComboBox == null) {
            this.createEditGraghic();
        }
        return lqComboBox;
    }

    /**
     * @param lqComboBox the hipComboBox to set
     */
    public void setLQComboBox(LQComboBox lqComboBox) {
        this.lqComboBox = lqComboBox;
    }

}
