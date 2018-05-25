/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.cell.tick;


import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.control.view.table.cell.LQTableCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author zhangyingchuang
 * @param <S>
 * @param <T>
 */
public class LQCheckBoxTickTableCell<S, T> extends LQTableCell<S, T> {

    private CheckBox checkBox;

    private LQTableView lqTableView
;
    private Callback<S, Boolean> editableCallback;

    public LQCheckBoxTickTableCell() {
        this.setGraphic(getCheckBox());
        this.getStyleClass().add("tick-table-cell");
        this.initTableViewListener();
    }

    @Override
    public LQCheckBoxTickTableCell clone() throws CloneNotSupportedException {
        LQCheckBoxTickTableCell<S, T> hipTableCell = (LQCheckBoxTickTableCell<S, T>) super.clone();
        hipTableCell.setEditableCallback(this.getEditableCallback());
        return hipTableCell;
    }

    @Override
    public void updateItem(T item, boolean empty) {
        this.setGraphic(getCheckBox());
        super.updateItem(item, empty);
        if (empty) {
            this.setContentDisplay(ContentDisplay.TEXT_ONLY);
            return;
        }
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        S showValue = (S) this.getTableRow().getItem();
        lqTableView = (LQTableView) this.getTableView();
        getCheckBox().selectedProperty().removeListener(selectChangeListener);
        Boolean editable = true;
        if (this.getEditableCallback() != null && showValue != null) {
            editable = this.getEditableCallback().call(showValue);
            getCheckBox().setDisable(!editable);
        }
        if (editable && getLQTableView().getTickItems().contains(showValue)) {
            getCheckBox().setSelected(true);
        } else if (getCheckBox().isSelected()) {
            getCheckBox().setSelected(false);
            remove(showValue);
        } else if (!editable) {
            getCheckBox().setSelected(false);
            remove(showValue);
        }
        // resetColumn();

        getCheckBox().selectedProperty().addListener(selectChangeListener);

    }

//    private void resetColumn() {
//        if (this.getTableColumn() != null && this.getTableColumn() instanceof HipTickTableColumn) {
//            HipTickTableColumn c = (HipTickTableColumn) this.getTableColumn();
//            c.setCouldTick(editableCallback);
//        }
//    }
    private ListChangeListener tickListChangeListener = new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change c) {
            getCheckBox().selectedProperty().removeListener(selectChangeListener);

            if (getLQTableView() != null) {
                getLQTableView().getTickItems().removeListener(tickListChangeListener);
            }
//            System.err.println("start tickListChangeListener ");
            Boolean editable = true;
            S showValue = (S) getTableRow().getItem();
            if (getEditableCallback() != null && showValue != null) {
                editable = getEditableCallback().call(showValue);
            }
            if (editable && c.getList().contains(getTableRow().getItem())) {
                getCheckBox().setSelected(true);
            } else if (getCheckBox().isSelected()) {
                getCheckBox().setSelected(false);
                remove(showValue);
            }
//            System.err.println("end tickListChangeListener ");
            if (getLQTableView() != null) {
                getLQTableView().getTickItems().addListener(tickListChangeListener);
            }
            getCheckBox().selectedProperty().addListener(selectChangeListener);
        }
    };

    private ChangeListener<Boolean> selectChangeListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            getLQTableView().getTickItems().removeListener(getTickListChangeListener());
//            System.err.println("selectChangeListener oldValue+" + oldValue + ",newValue:" + newValue);
            if (newValue) {

                add((S) getTableRow().getItem());

            } else {
                remove((S) getTableRow().getItem());
            }
            getLQTableView().getTickItems().addListener(getTickListChangeListener());
        }
    };

    protected void remove(S s) {
        getLQTableView().getTickItems().removeListener(getTickListChangeListener());
        if (getLQTableView().getTickItems().contains(s)) {
            getLQTableView().getTickItems().remove(s);
        }
        getLQTableView().getTickItems().addListener(getTickListChangeListener());
    }

    protected void add(S s) {
        if (!lqTableView.getTickItems().contains(s)) {
            getLQTableView().getTickItems().add(s);
        }

    }

    public CheckBox getCheckBox() {
        if (checkBox == null) {
            checkBox = new CheckBox();
            checkBox.selectedProperty().addListener(selectChangeListener);
        }
        return checkBox;
    }

    private void initTableViewListener() {
        if (this.getTableView() == null) {
            this.tableViewProperty().addListener(new ChangeListener<TableView>() {
                @Override
                public void changed(ObservableValue<? extends TableView> observable, TableView oldValue, TableView newValue) {
                    if (newValue != null) {
                        initTableViewListener();
                    }
                }
            });

            return;
        }

        lqTableView = (LQTableView) this.getTableView();
        lqTableView.getTickItems().addListener(getTickListChangeListener());
        lqTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<S>() {
            @Override
            public void changed(ObservableValue<? extends S> observable, S oldValue, S newValue) {
                if (newValue != null && getTableRow().getItem() == newValue) {
                    getCheckBox().requestFocus();
                }
            }
        });
        lqTableView.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == null || !newValue) {
                    return;
                }

                if (lqTableView.getSelectionModel().getSelectedItem() == getTableRow().getItem()) {
                    getCheckBox().requestFocus();
                }
            }
        });

        getCheckBox().selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                System.err.println("CheckBox oldValue+" + oldValue + ",newValue:" + newValue);
            }
        });

    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    /**
     * @return the hipTableView
     */
    public LQTableView getLQTableView() {
        return lqTableView;
    }

    /**
     * @return the editableCallback
     */
    public Callback<S, Boolean> getEditableCallback() {
        return editableCallback;
    }

    /**
     * @param editableCallback the editableCallback to set
     */
    public void setEditableCallback(Callback<S, Boolean> editableCallback) {

        this.editableCallback = editableCallback;
    }

    /**
     * @return the tickListChangeListener
     */
    public ListChangeListener getTickListChangeListener() {
        return tickListChangeListener;
    }

}
