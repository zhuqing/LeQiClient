/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.row;

import com.leqienglish.client.control.view.table.LQTableView;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;


/**
 *
 * @param <S>
 */
public class LQRowFactory<S extends Object> implements Callback<TableView<S>, TableRow<S>> {

    private ContextMenu contextMenu;

    private EventHandler<MouseEvent> mouseEventHandler;

    @Override
    public TableRow<S> call(TableView<S> param) {
        if (!(param instanceof LQTableView)) {
            return null;
        }
        LQTableRow tableRow = createTableRow((LQTableView) param);
        tableRow.setOnMouseClicked(mouseEventHandler);
        if (getContextMenu() != null) {
            ContextMenu cloneContextMenu = cloneContextMenu(getContextMenu(), tableRow);
            tableRow.setContextMenu(cloneContextMenu);
        }

        return tableRow;
    }

    protected LQTableRow createTableRow(TableView<S> param) {
        return new LQTableRow((LQTableView) param);
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public void setContextMenu(ContextMenu contextMenu) {
        this.contextMenu = contextMenu;
    }

    public EventHandler<MouseEvent> getMouseEventHandler() {
        return mouseEventHandler;
    }

    public void setMouseEventHandler(EventHandler<MouseEvent> mouseEventHandler) {
        this.mouseEventHandler = mouseEventHandler;
    }

    private ContextMenu cloneContextMenu(ContextMenu contextMenu, LQTableRow tableRow) {
        ContextMenu cloneContextMenu = new ContextMenu();
        cloneContextMenu.setUserData(tableRow);
        List<MenuItem> menuItemList = contextMenu.getItems();
        for (MenuItem menuItem : menuItemList) {
            MenuItem cloneMenuItem = new MenuItem();
            cloneMenuItem.setText(menuItem.getText());
            cloneMenuItem.setOnAction(menuItem.getOnAction());
            cloneContextMenu.getItems().add(cloneMenuItem);
        }
        return cloneContextMenu;
    }

}
