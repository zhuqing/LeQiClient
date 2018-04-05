/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.listview;

import com.leqienglish.client.util.node.NodeUtil;
import javafx.beans.DefaultProperty;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 *
 * @author zhuqing
 * @param <T>
 */
@DefaultProperty("listCell")
public class LQListView<T> extends ListView<T> {

    private ListCell listCell;

    public LQListView() {
        this.setCellFactory(new Callback<ListView<T>, ListCell<T>>() {
            @Override
            public ListCell<T> call(ListView<T> param) {
                ListCell<T> cell = (ListCell<T>) NodeUtil.clone(getListCell());
                return cell;

            }
        });
    }

    /**
     * @return the listCell
     */
    public ListCell getListCell() {
        return listCell;
    }

    /**
     * @param listCell the listCell to set
     */
    public void setListCell(ListCell listCell) {
        this.listCell = listCell;
    }

}
