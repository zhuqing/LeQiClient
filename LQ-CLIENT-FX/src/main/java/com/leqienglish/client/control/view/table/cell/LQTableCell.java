/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.cell;


import com.leqienglish.client.util.node.NodeUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author duyi
 * @param <S>
 * @param <T>
 */
public class LQTableCell<S, T> extends TableCell<S, T> {

    private Consumer mouseClickEventHander;
    private Consumer enterEventHandler;

    /**
     *
     */
    private Callback<S, List<String>> stylesCallback;

    public LQTableCell() {
        this.setEditable(false);
        JavaFxObservable.eventsOf(this, MouseEvent.MOUSE_CLICKED)
                .filter((me) -> me.getClickCount() == 1 && getMouseClickEventHander() != null && getTableRow().getItem() != null)
                .subscribe((me) -> getMouseClickEventHander().accept(getTableRow().getItem()));

        this.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER && getEnterEventHandler() != null) {
                    getEnterEventHandler().accept(getTableRow().getItem());
                }
            }
        });
    }

    @Override
    public LQTableCell clone() throws CloneNotSupportedException {
        try {
            LQTableCell<S, T> hipTableCell = this.getClass().newInstance();
            hipTableCell.setContentDisplay(this.getContentDisplay());
            hipTableCell.setEditable(this.isEditable());
            hipTableCell.setPrefHeight(this.getPrefHeight());
            hipTableCell.setMinHeight(this.getMinHeight());
            hipTableCell.setOnMouseClicked(this.getOnMouseClicked());
            hipTableCell.setMouseClickEventHander(this.getMouseClickEventHander());
            hipTableCell.setEnterEventHandler(this.getEnterEventHandler());
            hipTableCell.setStylesCallback(this.getStylesCallback());
            hipTableCell.setId(this.getId());
            return hipTableCell;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(LQTableCell.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        resetStyles();
        if (empty) {
            setText("");
            return;
        }
        if (item == null) {
            setText("");
        } else {
            setText(item.toString());
        }

    }

    private void resetStyles() {

        if (this.getStylesCallback() == null || this.getTableRow() == null || this.getTableRow().getItem() == null) {
            NodeUtil.addCustomStyles(Collections.EMPTY_LIST, this);
            return;
        }
        NodeUtil.addCustomStyles(this.getStylesCallback().call((S) this.getTableRow().getItem()), this);
    }

    /**
     * @return the mouseClickEventHander
     */
    public Consumer getMouseClickEventHander() {
        return mouseClickEventHander;
    }

    /**
     * @param mouseClickEventHander the mouseClickEventHander to set
     */
    public void setMouseClickEventHander(Consumer mouseClickEventHander) {
        this.mouseClickEventHander = mouseClickEventHander;
    }

    /**
     * @return the enterEventHandler
     */
    public Consumer getEnterEventHandler() {
        return enterEventHandler;
    }

    /**
     * @param enterEventHandler the enterEventHandler to set
     */
    public void setEnterEventHandler(Consumer enterEventHandler) {
        this.enterEventHandler = enterEventHandler;
    }

    /**
     *
     * @return the stylesCallback
     */
    public Callback<S, List<String>> getStylesCallback() {
        return stylesCallback;
    }

    /**
     *
     * @param stylesCallback the stylesCallback to set
     */
    public void setStylesCallback(Callback<S, List<String>> stylesCallback) {
        this.stylesCallback = stylesCallback;
    }

}
