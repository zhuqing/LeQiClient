/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.cell.check;

import com.leqienglish.client.control.view.table.cell.tick.LQCheckBoxTickTableCell;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;

/**
 *
 * @author zhuqing
 */
public class LQTreeItemCheckBoxTickTableCell<S, T> extends LQCheckBoxTickTableCell<TreeItem<S>, T> {

    @Override
    protected void add(TreeItem<S> s) {

        if (this.getLQTableView()== null) {
            return;
        }
        List list = getAllSelected(s);
        List addList = new ArrayList<>();
        for (Object item : list) {
            if (!getLQTableView().getTickItems().contains(item)) {
                addList.add(item);
               
            }
        }

        getLQTableView().getTickItems().addAll(addList);
    }

    private List<TreeItem<S>> getAllSelected(TreeItem<S> s) {
        List<TreeItem<S>> items = new ArrayList<>();
        if (s != null && s.getParent() != null) {
            items.addAll(s.getParent().getChildren());
            items.add(s.getParent());
        } else if (s != null) {
            items.addAll(s.getChildren());
            items.add(s);
        }
      
        return items;
    }

    @Override
    protected void remove(TreeItem<S> s) {
        if (this.getLQTableView() == null) {
            return;
        }
       
        getLQTableView().getTickItems().removeListener(this.getTickListChangeListener());
        getLQTableView().getTickItems().removeAll(getAllSelected(s));
        getLQTableView().getTickItems().addListener(this.getTickListChangeListener());
    }
}
