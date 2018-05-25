/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.cascade;


import com.leqienglish.client.control.cascade.LQCascade;
import com.leqienglish.client.control.cascade.LQCustomCascade;
import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import com.leqienglish.client.util.sourceitem.SourceItem;
import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.util.Callback;

/**
 *
 * @author zhuqing
 */
public class LQCascadeFieldFormCell<S> extends LQEditableFormCell<S, Object, LQCascade> {

    private String textPropertyName;
    private String valuePropertyName;
    private LQCustomCascade lqCascade;

    private Callback<Object, List<TreeItem>> customDataSourceCallback;

    private String defTabName;
    private ChangeListener<TreeItem<SourceItem>> valueChangeListener = new ChangeListener<TreeItem<SourceItem>>() {
        @Override
        public void changed(ObservableValue<? extends TreeItem<SourceItem>> observable, TreeItem<SourceItem> oldValue, TreeItem<SourceItem> newValue) {
            if (newValue == null || newValue.getValue() == null) {
                commitValue(null);
            } else {
                try {
                    Object value = PropertyReflectUtil.getValue(newValue.getValue(), getValuePropertyName());
                    commitValue(value);
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    Logger.getLogger(LQCascadeFieldFormCell.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    };

    @Override
    protected LQCascade createEditGraghic() {
        if (lqCascade == null) {
            lqCascade = new LQCustomCascade();

            this.lqCascade.setDefTabName(this.getDefTabName());
            lqCascade.setTextPropertyName(this.getTextPropertyName());
            this.lqCascade.setValuePropertyName(this.getValuePropertyName());
            //JavaFxObservable.changesOf(hipcascade.valueProperty())
            lqCascade.valueProperty().addListener(valueChangeListener);

            if (getCustomDataSourceCallback() != null) {
                List<TreeItem> tree = getCustomDataSourceCallback().call(this.getItem());
                if (tree != null) {
                    lqCascade.setItems(tree);
                }
            }
        }
        this.lqCascade.setPromptText(this.getPromptText());
        return lqCascade;
    }

    @Override
    public LQCascadeFieldFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQCascadeFieldFormCell cascadeFieldCell = (LQCascadeFieldFormCell) formCell;
            cascadeFieldCell.setDefTabName(this.getDefTabName());
            cascadeFieldCell.setCustomDataSourceCallback(this.getCustomDataSourceCallback());
            return cascadeFieldCell;
        }
        return null;
    }

    @Override
    protected void updateValue(Object t) {
        super.updateValue(t);

        List<TreeItem> tree = getCustomDataSourceCallback().call(this.getItem());
        if (getLQCascade() != null && tree != null) {
            lqCascade.setItems(tree);
        }

        try {
            lqCascade.valueProperty().removeListener(valueChangeListener);
            this.getLQCascade().setValuebyKey(t);
            lqCascade.valueProperty().addListener(valueChangeListener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public LQCascade getLQCascade() {
        if (this.lqCascade == null) {
            this.createEditGraghic();
        }
        return lqCascade;
    }

    public Callback<Object, List<TreeItem>> getCustomDataSourceCallback() {
        return customDataSourceCallback;
    }

    public void setCustomDataSourceCallback(Callback<Object, List<TreeItem>> customDataSourceCallback) {
        this.customDataSourceCallback = customDataSourceCallback;
    }

    public String getDefTabName() {
        return defTabName;
    }

    public void setDefTabName(String defTabName) {
        this.defTabName = defTabName;
    }

    /**
     * @return the textPropertyName
     */
    public String getTextPropertyName() {
        return textPropertyName;
    }

    /**
     * @param textPropertyName the textPropertyName to set
     */
    public void setTextPropertyName(String textPropertyName) {
        this.textPropertyName = textPropertyName;
    }

    /**
     * @return the valuePropertyName
     */
    public String getValuePropertyName() {
        return valuePropertyName;
    }

    /**
     * @param valuePropertyName the valuePropertyName to set
     */
    public void setValuePropertyName(String valuePropertyName) {
        this.valuePropertyName = valuePropertyName;
    }

}
