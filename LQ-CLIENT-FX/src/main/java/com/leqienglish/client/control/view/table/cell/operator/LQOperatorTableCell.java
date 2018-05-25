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
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
public class LQOperatorTableCell<S, T> extends LQTableCell<S, T> {

    /**
     * 需要刷新的数据
     */
    private ObservableList<LQButton> buttons;

    private Callback<List<LQButton>, List<LQButton>> filterButtonsCallback;

    public LQOperatorTableCell() {
        super();
        this.getButtons().addListener(new ListChangeListener<LQButton>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends LQButton> c) {
                reSetGraphic(getButtons());
            }
        });

    }

    @Override
    public LQOperatorTableCell clone() throws CloneNotSupportedException {
        LQOperatorTableCell<S, T> hipTableCell = (LQOperatorTableCell<S, T>) super.clone();
        List<LQButton> oldButtonList = getButtons();
        List<LQButton> cloneButtonList = new ArrayList<>();
        for (LQButton button : oldButtonList) {
            LQButton cloneBtn = button.clone();
            cloneBtn.getStyleClass().clear();
            cloneBtn.getStyleClass().addAll(button.getStyleClass());
            cloneBtn.setGraphic(new ImageView());
            cloneButtonList.add(cloneBtn);
        }
        hipTableCell.setButtons(cloneButtonList);
        hipTableCell.setFilterButtonsCallback(this.getFilterButtonsCallback());
        return hipTableCell;
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        for (LQButton hipButton : getButtons()) {
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
            reSetGraphic(this.getFilterButtonsCallback().call(this.getButtons()));
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

    /**
     * 需要刷新的数据
     *
     * @return the buttons
     */
    public ObservableList<LQButton> getButtons() {
        if (buttons == null) {
            buttons = FXCollections.observableArrayList();
        }
        return buttons;
    }

    /**
     * 需要刷新的数据
     *
     * @param buttons the buttons to set
     */
    public void setButtons(List<LQButton> buttons) {
        this.getButtons().setAll(buttons);
    }

}
