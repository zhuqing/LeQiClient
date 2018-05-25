/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.choose;


import com.leqienglish.client.util.sourceitem.SourceItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

/**
 * 多选面板
 *
 * @author zhuqing
 */
public class LQCheckBoxPane<T> extends LQChoosePane<T,CheckBox> {

    private ObservableList<SourceItem<T>> values;

    private final ListChangeListener<SourceItem<T>> valuesChangeListener = new ListChangeListener<SourceItem<T>>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends SourceItem<T>> c) {
            valuesChangeHandler();
        }
    };

    @Override
    protected List<CheckBox> creatChildren(List<SourceItem<T>> sourceItems) {
        if (sourceItems == null || sourceItems.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<CheckBox> checkBoxs = new ArrayList<>(sourceItems.size());

        for (SourceItem sourceItem : sourceItems) {
            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        if (getValues().contains(sourceItem)) {
                            return;
                        }
                        getValues().add(sourceItem);
                    } else {
                        getValues().remove(sourceItem);
                    }
                }
            });
            checkBox.setUserData(sourceItem);
            checkBox.setText(sourceItem.getDisplay());
            checkBoxs.add(checkBox);
        }
        return checkBoxs;
    }

    @Override
    protected void sourceItemChangeHandler() {
        super.sourceItemChangeHandler(); 
        this.getValues().clear();
    }

    private void valuesChangeHandler() {
        if (this.getChildren().isEmpty()) {
            return;
        }

        this.getChildren().stream().filter((Node node) -> node instanceof CheckBox).map((Node node) -> (CheckBox) node).forEach((CheckBox checkBox) -> {
            SourceItem curr = (SourceItem) checkBox.getUserData();
            checkBox.setSelected(getValues().contains(curr));
        });
    }

    /**
     * @return the values
     */
    public ObservableList<SourceItem<T>> getValues() {
        if (values == null) {
            values = FXCollections.observableArrayList();
            this.values.addListener(valuesChangeListener);
        }
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(List<SourceItem<T>> values) {
        this.getValues().setAll(values);
    }

}
