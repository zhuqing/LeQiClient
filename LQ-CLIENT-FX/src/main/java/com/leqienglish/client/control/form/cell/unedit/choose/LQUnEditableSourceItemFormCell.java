/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.unedit.choose;


import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.util.sourceitem.SourceItem;
import com.leqienglish.client.util.sourceitem.SourceItemUtil;
import java.util.List;
import javafx.util.Callback;

/**
 *
 * @author zhuqing
 */
public class LQUnEditableSourceItemFormCell<S, T> extends LQFormCell<S, T> {

    private Callback<S, List<SourceItem<T>>> customDataSourceCallback;

    @Override
    protected void updateValue(T t) {
        List<SourceItem<T>> sourceItems = this.getCustomDataSourceCallback().call(this.getItem());
        SourceItem selected = SourceItemUtil.getSourceItem(t, sourceItems);
        if (selected != null) {
            setText(selected.getDisplay());
        } else {
            setText("");
        }
    }

    @Override
    public LQFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQUnEditableSourceItemFormCell unEditableCustomDataSourceFormCell = (LQUnEditableSourceItemFormCell) formCell;
            unEditableCustomDataSourceFormCell.setCustomDataSourceCallback(customDataSourceCallback);
            return unEditableCustomDataSourceFormCell;
        }
        return null;

    }

    /**
     * @return the customDataSourceCallback
     */
    public Callback<S, List<SourceItem<T>>> getCustomDataSourceCallback() {
        return customDataSourceCallback;
    }

    /**
     * @param customDataSourceCallback the customDataSourceCallback to set
     */
    public void setCustomDataSourceCallback(Callback<S, List<SourceItem<T>>> customDataSourceCallback) {
        this.customDataSourceCallback = customDataSourceCallback;
    }

}
