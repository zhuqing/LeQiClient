/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.cell;

import com.sun.javafx.scene.control.behavior.TableCellBehavior;
import com.sun.javafx.scene.control.skin.TableCellSkin;
import com.sun.javafx.scene.control.skin.TableCellSkinBase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

/**
 *
 * @author zhuqing
 */
public class LQTableCellSkin<S, T> extends TableCellSkinBase<TableCell<S, T>, TableCellBehavior<S, T>> {

    private final TableColumn<S, T> tableColumn;

    public LQTableCellSkin(TableCell<S, T> tableCell) {
        super(tableCell, new HipTableCellBehavior<S, T>(tableCell));

        this.tableColumn = tableCell.getTableColumn();

        super.init(tableCell);
    }

    @Override
    protected BooleanProperty columnVisibleProperty() {
        return tableColumn.visibleProperty();
    }

    @Override
    protected ReadOnlyDoubleProperty columnWidthProperty() {
        return tableColumn.widthProperty();
    }

}
