/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.row.edit;


import com.leqienglish.client.control.view.table.LQEditTableView;
import com.leqienglish.client.control.view.table.row.LQTableRow;
import javafx.scene.control.Skin;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author zhuqing
 */
public class LQEditTableRow<T, H extends LQEditTableView> extends LQTableRow<T, H> {

    public LQEditTableRow(H hipTableView) {
        super(hipTableView);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LQEditTableRowSkin<>(this);
    }

    @Override
    protected void mouseClick(MouseEvent event) {
        super.mouseClick(event);

        if (event.getClickCount() == 1) {
            if (event.getTarget() instanceof TableCell || event.getTarget() instanceof TableRow) {
                this.getHipTableView().setEditData(null);
            }
        }

        if (event.getClickCount() == 2) {
            if (!this.getHipTableView().isEditable()) {
                return;
            }
            if (this.getHipTableView().getEditData() == this.getItem()) {
                return;
            }
            if (this.getHipTableView().getUnEditableItems().contains(this.getItem())) {
                this.getHipTableView().setEditData(null);
            } else {
                this.getHipTableView().setEditData(this.getItem());
            }
        }
    }

}
