/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.row;

import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.util.lock.LockUtil;
import com.sun.javafx.scene.control.skin.TableRowSkin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;

/**
 *
 * @author
 */
public class LQTableRowSkin<T> extends TableRowSkin<T> {

    /**
     * 日志
     */
    private boolean isLayoutChildren = false;

    private ChangeListener<T> itemcChangeListener = new ChangeListener<T>() {
        @Override
        public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
            itemChange();
        }
    };

    @Override

    protected void layoutChildren(double x, double y, double w, double h) {
        this.isLayoutChildren = true;
        super.layoutChildren(0, y, w, h);
        this.isLayoutChildren = false;
    }

    protected void itemChange() {
        LQTableView tableView = (LQTableView) getSkinnable().getTableView();
        if (tableView.getRefreshItems() == null || getSkinnable().getItem() == null) {
            return;
        }
        if (tableView.getRefreshItems().contains(getSkinnable().getItem())) {
            refreshRow();
        }
    }

    @Override
    protected double snappedRightInset() {
        if (isLayoutChildren) {
            return 0.0;
        }

        return super.snappedRightInset();

    }

    @Override
    protected double snappedLeftInset() {
        if (isLayoutChildren) {
            return 0.0;
        }
        return super.snappedLeftInset();
    }

    public LQTableRowSkin(LQTableRow tableRow) {
        super(tableRow);
        LQTableView tableView = (LQTableView) this.getSkinnable().getTableView();

        this.getSkinnable().itemProperty().addListener(itemcChangeListener);
        tableView.getRefreshItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                if (tableView.getRefreshItems() == null || getSkinnable().getItem() == null) {
                    return;
                }
                if (tableView.getRefreshItems().contains(getSkinnable().getItem())) {
                    refreshRow();
                }
            }
        });
    }

    protected void refreshRow() {
        LockUtil.lock(this.getSkinnable());
        this.getSkinnable().itemProperty().removeListener(itemcChangeListener);
        T v = this.getSkinnable().getItem();
        this.getSkinnable().setItem(null);
        this.getSkinnable().setItem(v);
        this.getSkinnable().itemProperty().addListener(itemcChangeListener);
        LockUtil.unLock(this.getSkinnable());
    }

}
