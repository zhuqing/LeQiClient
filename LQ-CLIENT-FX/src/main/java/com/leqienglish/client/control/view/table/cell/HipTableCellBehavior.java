/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.cell;

import static com.sun.javafx.scene.control.behavior.CellBehaviorBase.getAnchor;
import static com.sun.javafx.scene.control.behavior.CellBehaviorBase.hasNonDefaultAnchor;
import static com.sun.javafx.scene.control.behavior.CellBehaviorBase.removeAnchor;
import static com.sun.javafx.scene.control.behavior.CellBehaviorBase.setAnchor;
import com.sun.javafx.scene.control.behavior.TableCellBehavior;
import javafx.scene.control.Control;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableFocusModel;
import javafx.scene.control.TablePositionBase;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.input.MouseButton;

/**
 *
 * @author zhuqing
 */
public class HipTableCellBehavior<S, T> extends TableCellBehavior<S, T> {

    public HipTableCellBehavior(TableCell<S, T> control) {
        super(control);
    }

    private int getColumn() {
        if (getSelectionModel().isCellSelectionEnabled()) {
            TableColumnBase<S, T> tc = getTableColumn();
            return getVisibleLeafIndex(tc);
        }

        return -1;
    }

    protected void doSelect(final double x, final double y, final MouseButton button,
            final int clickCount, final boolean shiftDown, final boolean shortcutDown) {
        // Note that table.select will reset selection
        // for out of bounds indexes. So, need to check
        final TableCell tableCell = getControl();

        // If the mouse event is not contained within this tableCell, then
        // we don't want to react to it.
        if (!tableCell.contains(x, y)) {
            return;
        }

        final Control tableView = getCellContainer();
        if (tableView == null) {
            return;
        }

        int count = getItemCount();
        if (tableCell.getIndex() >= count) {
            return;
        }

        TableSelectionModel<S> sm = getSelectionModel();
        if (sm == null) {
            return;
        }

        final boolean selected = isSelected();
        final int row = tableCell.getIndex();
        final int column = getColumn();
        final TableColumnBase<S, T> tableColumn = getTableColumn();

        TableFocusModel fm = getFocusModel();
        if (fm == null) {
            return;
        }

        TablePositionBase focusedCell = getFocusedCell();

        // if the user has clicked on the disclosure node, we do nothing other
        // than expand/collapse the tree item (if applicable). We do not do editing!
        if (handleDisclosureNode(x, y)) {
            return;
        }

        // if shift is down, and we don't already have the initial focus index
        // recorded, we record the focus index now so that subsequent shift+clicks
        // result in the correct selection occuring (whilst the focus index moves
        // about).
        if (shiftDown) {
            if (!hasNonDefaultAnchor(tableView)) {
                setAnchor(tableView, focusedCell, false);
            }
        } else {
            removeAnchor(tableView);
        }

        // we must update the table appropriately, and this is determined by
        // what modifiers the user held down as they released the mouse.
        if (button == MouseButton.PRIMARY || (button == MouseButton.SECONDARY && !selected)) {
            if (sm.getSelectionMode() == SelectionMode.SINGLE) {
                simpleSelect(button, clickCount, shortcutDown);
            } else if (shortcutDown) {
                if (selected) {
                    // we remove this row/cell from the current selection
                    sm.clearSelection(row, tableColumn);
                    fm.focus(row, tableColumn);
                } else {
                    // We add this cell/row to the current selection
                    sm.select(row, tableColumn);
                }
            } else if (shiftDown) {
                // we add all cells/rows between the current selection focus and
                // this cell/row (inclusive) to the current selection.
                final TablePositionBase anchor = getAnchor(tableView, focusedCell);

                final int anchorRow = anchor.getRow();

                final boolean asc = anchorRow < row;

                // clear selection, but maintain the anchor
                sm.clearSelection();

                // and then determine all row and columns which must be selected
                int minRow = Math.min(anchorRow, row);
                int maxRow = Math.max(anchorRow, row);
                TableColumnBase<S, T> minColumn = anchor.getColumn() < column ? anchor.getTableColumn() : tableColumn;
                TableColumnBase<S, T> maxColumn = anchor.getColumn() >= column ? anchor.getTableColumn() : tableColumn;

                // and then perform the selection.
                // RT-21444: We need to put the range in the correct
                // order or else the last selected row will not be the
                // last item in the selectedItems list of the selection
                // model,
                if (asc) {
                    sm.selectRange(minRow, minColumn, maxRow, minColumn);
                } else {
                    sm.selectRange(maxRow, minColumn, minRow, minColumn);
                }

                // This line of code below was disabled as a fix for RT-30394.
                // Unit tests were written, so if by disabling this code I
                // have introduced regressions elsewhere, it is allowable to
                // re-enable this code as tests will fail if it is done so
                // without taking care of RT-30394 in an alternative manner.
                // return selection back to the focus owner
                // focus(anchor.getRow(), tableColumn);
            } else {
                simpleSelect(button, clickCount, shortcutDown);
            }
        }
    }
}
