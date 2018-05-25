/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form;




import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.util.focus.FocusUtil;
import com.leqienglish.client.util.focus.LQFormViewContainerOrder;
import com.leqienglish.client.util.node.NodeUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * 
 * @author zhuqing
 * @param <S> 
 */
public class LQFormView<S extends Object> extends GridPane implements Cloneable {

    /**
     * 首次加载时焦点所在的FormCell
     */
    private String initFocusId;
    private final ObjectProperty<S> value = new SimpleObjectProperty<>();

    public LQFormView() {
        JavaFxObservable.nullableValuesOf(value)
                .subscribe((opt) -> valueChange(opt.orElse(null)));
        this.getStyleClass().add("hip-form-view");
        FocusUtil.reSetFocusAlgorithm(this, new LQFormViewContainerOrder());
    }

    /**
     * 值改变是调用
     *
     * @param oldValue
     * @param newValue
     */
    protected void valueChange(S newValue) {
        if (this.getChildren() == null) {
            return;
        }
        this.getChildren().stream()
                .filter((Node node) -> node instanceof LQFormCell)
                .map((Node node) -> (LQFormCell) node)
                .forEach((LQFormCell cell) -> {
                    cell.setFormView(LQFormView.this);
                    cell.updateItem(newValue, newValue == null);
                });

    }

    /**
     * 刷新FormCell
     *
     * @param formCellIds
     */
    public void refresh(String... formCellIds) {
        if (formCellIds == null || formCellIds.length == 0) {
            return;
        }
        Set<String> formCellIdSet = Stream.of(formCellIds).collect(Collectors.toSet());
        this.getChildren().stream()
                .filter((Node node) -> formCellIdSet.contains(node.getId()))
                .filter((Node node) -> node instanceof LQFormCell)
                .map((Node node) -> (LQFormCell) node)
                .forEach((LQFormCell cell) -> cell.updateItem(getValue(), getValue() == null));
    }

    public void setFocusTo(String formCellId) {
        LQEditableFormCell hipFormCell = (LQEditableFormCell) this.getChildren().stream()
                .filter((n) -> n instanceof LQEditableFormCell && Objects.equals(n.getId(), formCellId))
                .map((c) -> (LQEditableFormCell) c)
                .findFirst().orElse(null);
        if (hipFormCell == null || hipFormCell.getGraphic() == null) {
            return;
        }
        if (hipFormCell.getScene() == null) {
            FocusUtil.focusAfterGetSceneOnce(hipFormCell);
        } else {
            hipFormCell.requestFocus();
        }

    }

    public ObjectProperty<S> valueProperty() {
        return value;
    }

    public S getValue() {
        return value.getValue();
    }

    public void setValue(S value) {
        this.value.setValue(value);
    }

    public LQFormView<S> clone() {
        try {
            LQFormView hipFormView = this.getClass().newInstance();

            for (Node node : this.getChildren()) {
                int columnIndex = GridPane.getColumnIndex(node) == null ? 0 : GridPane.getColumnIndex(node);
                int rowIndex = GridPane.getRowIndex(node) == null ? 0 : GridPane.getRowIndex(node);
                int cspan = GridPane.getColumnSpan(node) == null ? 1 : GridPane.getColumnSpan(node);
                int rspan = GridPane.getRowSpan(node) == null ? 1 : GridPane.getRowSpan(node);

                Node clonedNode = NodeUtil.clone(node);
                if (clonedNode == null) {
                    continue;
                }
                hipFormView.add(clonedNode, columnIndex, rowIndex, cspan, rspan);
            }
            hipFormView.setStyle(this.getStyle());
            hipFormView.getStyleClass().setAll(this.getStyleClass());
            return hipFormView;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(LQFormView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return the initFocusId
     */
    public String getInitFocusId() {
        return initFocusId;
    }

    /**
     * @param initFocusId the initFocusId to set
     */
    public void setInitFocusId(String initFocusId) {
        this.initFocusId = initFocusId;
    }

}
