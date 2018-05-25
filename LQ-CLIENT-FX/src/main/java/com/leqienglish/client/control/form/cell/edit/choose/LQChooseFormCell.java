/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.choose;

import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.util.sourceitem.SourceItem;
import java.util.List;
import javafx.scene.Node;
import javafx.util.Callback;

/**
 *
 * @author zhuqing
 */
public abstract class LQChooseFormCell<S, T, N extends Node> extends LQEditableFormCell<S, T, N> {

    /**
     * 数据源是否可变
     */
    private Boolean sourceItemChangeable;

    /**
     * 数据源回调
     */
    private Callback<S, List<SourceItem<T>>> customDataSourceCallback;
    /**
     * 数据源
     */
    private List<SourceItem<T>> sourceItems;

    /**
     * @return the customDataSourceCallback
     */
    public Callback<S, List<SourceItem<T>>> getCustomDataSourceCallback() {
        //   KeyEvent key = new KeyEvent(this, this, eventType, character, text, KeyCode.HOME, true, true, true, true)
        return customDataSourceCallback;

    }

    /**
     * @param customDataSourceCallback the customDataSourceCallback to set
     */
    public void setCustomDataSourceCallback(Callback<S, List<SourceItem<T>>> customDataSourceCallback) {
        this.customDataSourceCallback = customDataSourceCallback;
    }

    @Override
    public LQFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQChooseFormCell customDataSourceChooseFormCell = (LQChooseFormCell) formCell;
            customDataSourceChooseFormCell.setCustomDataSourceCallback(this.getCustomDataSourceCallback());
            return customDataSourceChooseFormCell;
        }
        return null;
    }

    /**
     * @return the sourceItems
     */
    public List<SourceItem<T>> getSourceItems() {

        return sourceItems;
    }

    /**
     * @param sourceItems the sourceItems to set
     */
    public void setSourceItems(List<SourceItem<T>> sourceItems) {
        this.sourceItems = sourceItems;
    }

    /**
     * 数据源是否可变
     *
     * @return the sourceItemChangeable
     */
    public Boolean isSourceItemChangeable() {
        if (sourceItemChangeable == null) {
            return false;
        }
        return sourceItemChangeable;
    }

    /**
     * 数据源是否可变
     *
     * @param sourceItemChangeable the sourceItemChangeable to set
     */
    public void setSourceItemChangeable(Boolean sourceItemChangeable) {
        this.sourceItemChangeable = sourceItemChangeable;
    }

    protected List<SourceItem<T>> fetchSourceItems() {
        if (this.getSourceItems() == null) {
            List<SourceItem<T>> sourceItems = this.getCustomDataSourceCallback().call(this.getItem());
            this.setSourceItems(sourceItems);
        } else if (this.isSourceItemChangeable()) {
            List<SourceItem<T>> sourceItems = this.getCustomDataSourceCallback().call(this.getItem());
            this.setSourceItems(sourceItems);
        }

        return this.getSourceItems();
    }

}
