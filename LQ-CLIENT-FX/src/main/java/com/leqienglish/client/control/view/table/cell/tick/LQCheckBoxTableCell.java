/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.cell.tick;


import com.leqienglish.client.control.view.table.cell.LQTableCell;
import com.leqienglish.util.bool.BooleanUtil;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.CheckBox;
import javafx.util.Callback;

/**
 *
 * @author zhangyingchuang
 * @param <S>
 * @param <T>
 */
public class LQCheckBoxTableCell<S, T> extends LQTableCell<S, T> {

    private CheckBox checkBox;

    private Callback<Object, Boolean> toBooleanCallback;

    public LQCheckBoxTableCell() {
        this.setGraphic(getCheckBox());
    }

    @Override
    public LQCheckBoxTableCell clone() throws CloneNotSupportedException {
        LQCheckBoxTableCell<S, T> hipTableCell = (LQCheckBoxTableCell<S, T>) super.clone();
        hipTableCell.setToBooleanCallback(this.getToBooleanCallback());
        return hipTableCell;
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            this.setContentDisplay(ContentDisplay.TEXT_ONLY);
            getCheckBox().setSelected(false);
            return;
        }
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        if (item == null) {
            getCheckBox().setSelected(false);
        } else {
            if (getToBooleanCallback() != null) {
                getCheckBox().setSelected(getToBooleanCallback().call(item));
            } else {
                getCheckBox().setSelected(BooleanUtil.getBoolean(item));
            }
        }
    }

    public CheckBox getCheckBox() {
        if (checkBox == null) {
            checkBox = new CheckBox();
            checkBox.setDisable(true);
        }
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public Callback<Object, Boolean> getToBooleanCallback() {
        return toBooleanCallback;
    }

    public void setToBooleanCallback(Callback<Object, Boolean> toBooleanCallback) {
        this.toBooleanCallback = toBooleanCallback;
    }

}
