/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.combobox;


import com.leqienglish.client.util.sourceitem.SourceItem;
import java.util.List;
import java.util.function.Consumer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author zhuqing
 */
public class LQFilterableCombobox<S extends SourceItem> extends Control {

    private ObservableList<S> items;

    private ObjectProperty<S> value;

    private Consumer<S> commit;

    private String promptText;

    private IntegerProperty selectIndex;

    private Boolean autoShow;

    private DoubleProperty prefPopupHeight;

    private DoubleProperty prefPopupWidth;

    @Override
    protected Skin<?> createDefaultSkin() {
        this.getStyleClass().add("filter-combox");
        return new LQFilterableComboboxSkin(this);
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
        this.getItems().setAll(items);
    }

    /**
     * @return the value
     */
    public S getValue() {
        return valueProperty().getValue();
    }

    /**
     * @return the value
     */
    public ObjectProperty<S> valueProperty() {
        if (this.value == null) {
            this.value = new SimpleObjectProperty();
        }
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(S value) {
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

    /**
     * @return the promptText
     */
    public String getPromptText() {
        return promptText;
    }

    /**
     * @param promptText the promptText to set
     */
    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

    /**
     * @return the selectIndex
     */
    public Integer getSelectIndex() {
        return selectIndexProperty().getValue();
    }

    /**
     * @return the selectIndex
     */
    public IntegerProperty selectIndexProperty() {
        if (selectIndex == null) {
            selectIndex = new SimpleIntegerProperty();
        }
        return selectIndex;
    }

    /**
     * @param selectIndex the selectIndex to set
     */
    public void setSelectIndex(Integer selectIndex) {
        this.selectIndexProperty().setValue(selectIndex);
    }

    /**
     * @return the autoShow
     */
    public Boolean isAutoShow() {
        if (this.autoShow == null) {
            return true;
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
            prefPopupHeight = new SimpleDoubleProperty();
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
