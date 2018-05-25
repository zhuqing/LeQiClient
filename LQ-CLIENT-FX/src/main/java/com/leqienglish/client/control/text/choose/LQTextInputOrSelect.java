/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.text.choose;


import com.leqienglish.client.util.sourceitem.SourceItem;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author zhuqing
 */
public class LQTextInputOrSelect<S extends SourceItem> extends Control {

    private ObservableList<S> items;

    private StringProperty value;
    
    private Consumer<S> commit;

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LQTextInputOrSelectSkin((LQTextInputOrSelect<SourceItem>) this);
    }

    /**
     * @return the items
     */
    public ObservableList<S> getItems() {
        if (this.items == null) {
            this.items = FXCollections.observableArrayList();
        }
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<S> items) {
        if(items == null){
            items = Collections.EMPTY_LIST;
        }
        this.getItems().setAll(items);
    }

    /**
     * @return the value
     */
    public String getValue() {
        return valueProperty().getValue();
    }

    /**
     * @return the value
     */
    public StringProperty valueProperty() {
        if (this.value == null) {
            this.value = new SimpleStringProperty();
        }
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.valueProperty().setValue(value);
    }

    /**
     * @return the commit
     */
    public Consumer<S> getCommit() {
        return commit;
    }

    /**
     * @param commit the commit to set
     */
    public void setCommit(Consumer<S> commit) {
        this.commit = commit;
    }

}
