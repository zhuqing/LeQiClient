/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.row.edit;


import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.view.table.LQEditTableView;
import com.leqienglish.client.control.view.table.cell.LQTableCell;
import com.leqienglish.client.control.view.table.column.LQTableColumn;
import com.leqienglish.client.control.view.table.row.LQTableRow;
import com.leqienglish.client.control.view.table.row.LQTableRowSkin;
import com.leqienglish.client.util.lock.LockUtil;
import com.leqienglish.client.util.node.NodeUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;

/**
 *
 * @author zhuqing
 */
public class LQEditTableRowSkin<T> extends LQTableRowSkin<T> {

    private LQEditTableView<T> editTableView;
    private LQFormView formView;

    private final ChangeListener<Node> foucesNodeChangeListener = new ChangeListener<Node>() {
        @Override
        public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
            if (newValue == null || editTableView == null || getEditTableView().getHipFormView() == null) {
                return;
            }
            LQFormCell formCell = (LQFormCell) NodeUtil.getSpecialParent(newValue, LQFormCell.class);
            int index = getEditTableView().getHipFormView().getChildrenUnmodifiable().indexOf(formCell);
            if (index < 0) {
                return;
            }

            editTableView.scrollToColumnIndex(index);
        }
    };

    private final ListChangeListener unEditableItemsChange = new ListChangeListener() {

        @Override
        public void onChanged(ListChangeListener.Change change) {
            if (getEditTableView() != null && editTableView.getUnEditableItems() != null) {
                if (editTableView.getUnEditableItems().contains(getSkinnable().getItem())) {
                    removeFormView();
                }
            }
        }
    };

    @Override

    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);
        resetFormCellWidth();
    }

    private final ChangeListener<T> editDataChangeListener = new ChangeListener<T>() {
        @Override
        public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
            changeEdit();
        }
    };

    private final ChangeListener<Boolean> emptyChangeListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            changeEdit();
        }
    };

    public LQEditTableRowSkin(LQTableRow tableRow) {
        super(tableRow);

        if (this.getEditTableView() != null) {
            this.getEditTableView().editDataProperty().addListener(editDataChangeListener);
            this.getEditTableView().getUnEditableItems().addListener(unEditableItemsChange);

            this.getSkinnable().emptyProperty().addListener(emptyChangeListener);
            this.getSkinnable().widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    changeEdit();
                }
            });

            this.getSkinnable().heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    changeEdit();
                }
            });

        }
    }

    @Override
    protected void layoutLabelInArea(double x, double y, double w, double h, Pos alignment) {
        super.layoutLabelInArea(x, y, w, h, alignment); //To change body of generated methods, choose Tools | Templates.
        changeEdit();
    }

    @Override
    protected void itemChange() {
        super.itemChange();
        changeEdit();
    }

    private void changeEdit() {
        if (LockUtil.isLocked(getSkinnable())) {
            return;
        }
        if (getSkinnable().getItem() == null) {
            removeFormView();
            return;
        }
        if (editTableView.getUnEditableItems().contains(getSkinnable().getItem())) {
            removeFormView();
            return;
        }

        if (editTableView.getEditData() == getSkinnable().getItem()) {
            addChildNode();
            return;
        }
        removeFormView();
    }

    /**
     * 添加子节点
     *
     * @param isAdd 是否添加子节点
     */
    private void addChildNode() {
        if (getEditTableView() == null || getEditTableView().getHipFormView() == null) {
            return;
        }

        formView = getEditTableView().getHipFormView();
        formView.setValue(this.getSkinnable().getItem());

        if (this.getSkinnable().getScene() != null) {
            this.getSkinnable().getScene().focusOwnerProperty().removeListener(foucesNodeChangeListener);
            this.getSkinnable().getScene().focusOwnerProperty().addListener(foucesNodeChangeListener);
        }

        if (!this.getChildren().contains(formView)) {
            this.getChildren().add(formView);

        }

        formView.resize(getSkinnable().getWidth(), this.getSkinnable().getHeight());
        formView.setVisible(true);
        resetFormCellWidth();
    }

    /**
     * 重新设置FormCell的宽度，以适应tableCell的宽度
     */
    private void resetFormCellWidth() {

        for (Node node : this.getSkinnable().getChildrenUnmodifiable()) {
            if (node == null || !(node instanceof LQTableCell)) {
                continue;
            }
            LQTableCell cell = (LQTableCell) node;

            LQTableColumn tableColumn = (LQTableColumn) cell.getTableColumn();
            LQFormCell formCell = this.editTableView.getFormCellMap().get(tableColumn.getPropertyName());
            if (formCell == null) {
                continue;
            }

            formCell.setPrefWidth(cell.getWidth());
            formCell.resize(cell.getWidth(), cell.getHeight());

        }
    }

    /**
     * 重新放在tableRow 的TranslateY
     *
     * @param currentRow
     */
    private void removeFormView() {
        LQEditTableRow currentRow = (LQEditTableRow) this.getSkinnable();
        if (this.getSkinnable().getScene() != null) {
            this.getSkinnable().getScene().focusOwnerProperty().removeListener(foucesNodeChangeListener);
        }

        if (currentRow == null) {
            return;
        }
        if (!this.getChildren().contains(formView)) {
            return;
        }

        if (currentRow.getHipTableView() == null) {
            return;
        }

        this.getChildren().remove(formView);

        rowCommit(currentRow);
        refreshRow();

    }

    /**
     * 行提交
     *
     * @param currentRow
     */
    private void rowCommit(LQEditTableRow currentRow) {

        LQEditTableView hipEditTableView = (LQEditTableView) currentRow.getHipTableView();
        if (currentRow.getItem() == null) {
            return;
        }
        if (hipEditTableView.getRowCommit() != null) {
            hipEditTableView.getRowCommit().accept(currentRow.getItem());
        }
    }

    /**
     * @return the editTableView
     */
    private LQEditTableView<T> getEditTableView() {
        if (getSkinnable().getTableView() == null) {
            return null;
        }
        if (editTableView == null && getSkinnable().getTableView() instanceof LQEditTableView) {
            editTableView = (LQEditTableView<T>) getSkinnable().getTableView();
        }
        return editTableView;
    }

}
