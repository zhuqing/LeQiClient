/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.choose;

import com.leqienglish.client.control.combobox.LQFilterableCombobox;
import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.util.focus.FocusUtil;
import com.leqienglish.client.util.sourceitem.SourceItem;
import com.leqienglish.client.util.sourceitem.SourceItemUtil;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author zhuqing
 */
public class LQFilterableComboBoxFormCell<S, T, N extends LQFilterableCombobox> extends LQChooseFormCell<S, T, N> {

    /**
     * 下拉框组件
     */
    private LQFilterableCombobox<SourceItem<T>> hipComboBox;

    private Boolean autoShow;

    private DoubleProperty prefPopupHeight;

    private DoubleProperty prefPopupWidth;

    @Override
    protected N createEditGraghic() {
        if (hipComboBox == null) {
            hipComboBox = createN();
            hipComboBox.prefHeightProperty().bind(this.prefHeightProperty());
            hipComboBox.prefWidthProperty().bind(this.prefWidthProperty());
            hipComboBox.setAutoShow(this.isAutoShow());
            hipComboBox.prefPopupHeightProperty().bind(this.prefPopupHeightProperty());
            hipComboBox.prefPopupWidthProperty().bind(this.prefPopupWidthProperty());

            this.hipComboBox.setCommit((newValue) -> {
                if (newValue == null) {
                    commitValue(null);

                } else {
                    commitValue(newValue.getValue());
                    FocusUtil.focusNext(LQFilterableComboBoxFormCell.this, hipComboBox, Boolean.FALSE);
                }

            });
        }
        //hipComboBox.setPromptText(this.getPromptText());
        return (N) hipComboBox;
    }

    @Override
    public LQFormCell clone() {
        LQFilterableComboBoxFormCell cell = (LQFilterableComboBoxFormCell) super.clone();
        cell.setAutoShow(autoShow);
        return cell;
    }

    protected N createN() {
        return (N) new LQFilterableCombobox();
    }

    @Override
    protected void updateValue(T t) {
        super.updateValue(t);

        List<SourceItem<T>> sourceItems = this.fetchSourceItems();
        this.getHipComboBox().getItems().setAll(sourceItems);
        SourceItem selected = SourceItemUtil.getSourceItem(t, sourceItems);

        this.getHipComboBox().setValue(selected);

    }

    /**
     * @return the hipComboBox
     */
    public LQFilterableCombobox getHipComboBox() {
        if (this.hipComboBox == null) {
            this.createEditGraghic();
        }
        return hipComboBox;
    }

    /**
     * @return the autoShow
     */
    public Boolean isAutoShow() {
        if (autoShow == null) {
            return false;
        }
        return autoShow;
    }

    /**
     * @param autoShow the autoShow to set
     */
    public void setAutoShow(Boolean autoShow) {
        this.autoShow = autoShow;
    }

    public DoubleProperty prefPopupHeightProperty() {
        if (prefPopupHeight == null) {
            prefPopupHeight = new SimpleDoubleProperty(100);
        }
        return prefPopupHeight;
    }

    public Double getPrefPopupHeight() {
        return prefPopupHeightProperty().getValue();
    }

    public void setPrefPopupHeight(Double prefPopupHeight) {
        prefPopupHeightProperty().setValue(prefPopupHeight);
    }

    public DoubleProperty prefPopupWidthProperty() {
        if (prefPopupWidth == null) {
            prefPopupWidth = new SimpleDoubleProperty();
        }
        return prefPopupWidth;
    }

    public Double getPrefPopupWidth() {
        return prefPopupWidthProperty().getValue();
    }

    public void setPrefPopupWidth(Double prefPopupWidth) {
        prefPopupWidthProperty().setValue(prefPopupWidth);
    }
}
