/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.cell.check;


import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.control.view.table.cell.LQTableCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.RadioButton;

/**
 *
 * @author zhangyingchuang
 * @param <S>
 * @param <T>
 */
public class HipRadioButtonTickTableCell<S, T> extends LQTableCell<S, T> {

    private RadioButton radioButton;

    private LQTableView lqTableView;

    private boolean hasAddListener = false;

    public HipRadioButtonTickTableCell() {
        this.setGraphic(getRadioButton());
    }

    @Override
    public HipRadioButtonTickTableCell clone() throws CloneNotSupportedException {
        HipRadioButtonTickTableCell<S, T> hipTableCell = (HipRadioButtonTickTableCell<S, T>) super.clone();
        return hipTableCell;
    }

    @Override
    public void updateItem(T item, boolean empty) {
        if (this.getGraphic() == null) {
            getRadioButton();
        }
        super.updateItem(item, empty);
        if (empty) {
            this.setContentDisplay(ContentDisplay.TEXT_ONLY);
            return;
        }
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Object showValue = this.getTableRow().getItem();
        lqTableView = (LQTableView) this.getTableView();
        if (lqTableView.getTickItems().contains(showValue)) {
            radioButton.setSelected(true);
        } else {
            radioButton.setSelected(false);
        }
        if (!hasAddListener) {
            lqTableView.getTickItems().addListener(listChangeListener);
            hasAddListener = true;
        }
    }

    private ListChangeListener listChangeListener = new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change c) {
            getRadioButton().selectedProperty().removeListener(selectChangeListener);
            if (c.getList().contains(getTableRow().getItem())) {
                getRadioButton().setSelected(true);
            } else {
                getRadioButton().setSelected(false);
            }
            getRadioButton().selectedProperty().addListener(selectChangeListener);
        }
    };

    private ChangeListener<Boolean> selectChangeListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            lqTableView.getTickItems().removeListener(listChangeListener);
            if (newValue) {
                lqTableView.getTickItems().setAll(getTableRow().getItem());
            }
            lqTableView.getTickItems().addListener(listChangeListener);
        }
    };

    public RadioButton getRadioButton() {
        if (radioButton == null) {
            radioButton = new RadioButton();
            radioButton.selectedProperty().addListener(selectChangeListener);
        }
        return radioButton;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }
}
