/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.cell.operator;

import com.leqienglish.client.control.button.LQButton;
import com.leqienglish.client.control.view.table.cell.LQTableCell;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.HBox;


import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 *
 * @author duyi
 * @param <S>
 * @param <T>
 */
public class LQTableButtonCell<S, T> extends LQTableCell<S, T> {

    private final ObjectProperty<List<LQButton>> buttonList = new SimpleObjectProperty<>();

    private Callback<List<LQButton>, List<LQButton>> filterButtonsCallback;

    public LQTableButtonCell() {
        super();
        buttonList.addListener(new ChangeListener<List<LQButton>>() {
            @Override
            public void changed(ObservableValue<? extends List<LQButton>> observable, List<LQButton> oldValue, List<LQButton> newValue) {
                reSetGraphic(newValue);
            }
        });
    }

    public ObjectProperty<List<LQButton>> buttonListProperty() {
        return buttonList;
    }

    public List<LQButton> getButtonList() {
        return buttonList.getValue();
    }

    public void setButtonList(List<LQButton> buttonList) {
        this.buttonList.setValue(buttonList);
    }

    @Override
    public LQTableButtonCell clone() throws CloneNotSupportedException {
        LQTableButtonCell<S, T> hipTableCell = (LQTableButtonCell<S, T>) super.clone();
        List<LQButton> oldButtonList = getButtonList();
        List<LQButton> cloneButtonList = new ArrayList<>();
        for (LQButton button : oldButtonList) {
            LQButton cloneBtn = button.clone();
            cloneBtn.getStyleClass().clear();
            cloneBtn.getStyleClass().addAll(button.getStyleClass());
            cloneBtn.setGraphic(new ImageView());
            cloneBtn.setId(button.getId());
            cloneButtonList.add(cloneBtn);
        }
        hipTableCell.setButtonList(cloneButtonList);
        hipTableCell.setFilterButtonsCallback(this.getFilterButtonsCallback());
        return hipTableCell;
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        for (LQButton hipButton : getButtonList()) {
            hipButton.setUserData(item);
        }
        if (empty) {
            setText("");
            this.setContentDisplay(ContentDisplay.TEXT_ONLY);
            return;
        }
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        if (item == null) {
            setText("");
        } else {
            setText(item.toString());
        }

        if (this.getFilterButtonsCallback() != null) {
            reSetGraphic(this.getFilterButtonsCallback().call(this.getButtonList()));
        }
    }

    private void reSetGraphic(List<LQButton> buttons) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("button-cell");
        hbox.getChildren().addAll(buttons);
        this.setGraphic(hbox);
    }

    /**
     * @return the filterButtonsCallback
     */
    public Callback<List<LQButton>, List<LQButton>> getFilterButtonsCallback() {
        return filterButtonsCallback;
    }

    /**
     * @param filterButtonsCallback the filterButtonsCallback to set
     */
    public void setFilterButtonsCallback(Callback<List<LQButton>, List<LQButton>> filterButtonsCallback) {
        this.filterButtonsCallback = filterButtonsCallback;
    }

}
