/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.row.edit;


import com.leqienglish.client.control.view.table.LQEditTableView;
import com.leqienglish.client.control.view.table.row.LQRowFactory;
import com.leqienglish.client.control.view.table.row.LQTableRow;
import javafx.scene.control.TableView;

/**
 *
 * @author duyi
 * @param <S>
 */
public class LQEditRowFactory<S extends Object> extends LQRowFactory<S> {
    @Override
    protected LQTableRow createTableRow(TableView<S> param) {
        return new LQEditTableRow((LQEditTableView) param);
    }

 

}
