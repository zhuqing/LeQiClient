/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form;

import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

/**
 * 使用瓦片布局的formCell
 *
 * @author zhuqing
 */
public class LQFlowPaneView<S> extends FlowPane {

    private ObjectProperty<S> value;

    public LQFlowPaneView() {
        JavaFxObservable.nullableValuesOf(this.valueProperty()).subscribe((c) -> update());
    }

    public void refresh(String... formCellIds) {
        if (formCellIds == null || formCellIds.length == 0) {
            return;
        }
        Set<String> formCellIdSet = Stream.of(formCellIds).collect(Collectors.toSet());
        this.getChildren().stream()
                .filter((n) -> n instanceof HBox)
                .map((n) -> (HBox) n)
                .filter((h) -> h.getChildren().size() == 2)
                .map((h) -> h.getChildren().get(1))
                .filter((n) -> n instanceof LQEditableFormCell)
                .map((n) -> (LQEditableFormCell) n)
                .filter((LQEditableFormCell node) -> formCellIdSet.contains(node.getPropertyName()))
                .forEach((LQFormCell cell) -> cell.updateItem(getValue(), getValue() == null));

    }

    public void update() {
        this.getChildren().stream()
                .forEach((Node cell) -> updateFormCell(cell, this.getValue()));
    }

    private void updateFormCell(Node node, S s) {
        if (node instanceof LQFormCell) {
            LQFormCell cell = (LQFormCell) node;
            cell.updateItem(s, s == null);
        } else if (node instanceof HBox) {
            HBox box = (HBox) node;
            for (Node item : box.getChildren()) {
                updateFormCell(item, s);
            }
        }
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
        if (value == null) {
            this.value = new SimpleObjectProperty<S>();
        }
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(S value) {
        this.valueProperty().setValue(value);
    }
}
