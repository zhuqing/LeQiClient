/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table;


import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.view.table.row.edit.LQEditRowFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 *
 * @author zhuqing
 */
public class LQEditTableView<S> extends LQTableView<S> {

    private ObjectProperty<S> editData;

    private ObservableList<S> unEditableItems;

    private Consumer<S> rowCommit;

    /**
     * 编辑的form
     */
    private LQFormView hipFormView;

    private Map<String, LQFormCell> formCellMap;

    public LQEditTableView() {
        this.setEditable(true);
        this.setRowFactory(new LQEditRowFactory<>());
        this.getItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                if (getEditData() != null) {
                    if (!getItems().contains(getEditData())) {
                        setEditData(null);
                    }
                }
            }

        });
    }

    /**
     * @return the hipFormView
     */
    public LQFormView getHipFormView() {
        return hipFormView;
    }

    /**
     * @param hipFormView the hipFormView to set
     */
    public void setHipFormView(LQFormView hipFormView) {
        this.hipFormView = hipFormView;
        formCellMap = null;

    }

    /**
     * @return the formCellMap
     */
    public Map<String, LQFormCell> getFormCellMap() {
//        if (formCellMap == null) {
        formCellMap = new HashMap<>();
        if (this.getHipFormView() == null || this.getHipFormView().getChildren() == null) {
            return formCellMap;
        }
        for (Node node : this.getHipFormView().getChildren()) {
            if (!(node instanceof LQFormCell)) {
                continue;
            }
            LQFormCell cell = (LQFormCell) node;
            formCellMap.put(cell.getPropertyName(), cell);

        }
//        }
        return formCellMap;
    }

    /**
     * @return the editData
     */
    public S getEditData() {
        return editDataProperty().getValue();
    }

    /**
     * @return the editData
     */
    public ObjectProperty<S> editDataProperty() {
        if (editData == null) {
            editData = new SimpleObjectProperty<>();
        }
        return editData;
    }

    /**
     * @param editData the editData to set
     */
    public void setEditData(S editData) {
        editDataProperty().setValue(editData);
    }

    public ObservableList<S> getUnEditableItems() {
        if (unEditableItems == null) {
            unEditableItems = FXCollections.observableArrayList();
        }
        return unEditableItems;
    }

    public void setUnEditableItems(ObservableList<S> unEditableItems) {
        this.unEditableItems = unEditableItems;
    }

    /**
     * @return the rowCommit
     */
    public Consumer<S> getRowCommit() {
        return rowCommit;
    }

    /**
     * @param rowCommit the rowCommit to set
     */
    public void setRowCommit(Consumer<S> rowCommit) {
        this.rowCommit = rowCommit;
    }

}
