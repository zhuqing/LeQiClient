/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.listview.cell;


import com.leqienglish.client.control.form.LQFormView;
import javafx.beans.DefaultProperty;
import javafx.scene.control.ListCell;

/**
 *
 * @author zhuqing
 */
@DefaultProperty("hipFormView")
public class LQFormViewListCell<T> extends ListCell<T> {

    private LQFormView lqFormView;

    @Override
    public LQFormViewListCell clone() throws CloneNotSupportedException {

        LQFormViewListCell listCell = new LQFormViewListCell();
        if (getLqFormView() == null) {
            return listCell;
        }
        listCell.setLqFormView(getLqFormView().clone());
        return listCell;

    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        if (this.getLqFormView() == null) {
            return;
        }
        if (empty) {
            this.setGraphic(null);
        } else if (this.getGraphic() == null) {
            this.setGraphic(this.getLqFormView());
            //this.getHipFormView().setValue(null);
            this.getLqFormView().setValue(item);
        } else {
            //this.getHipFormView().setValue(null);
            this.getLqFormView().setValue(item);
        }

    }

    /**
     * @return the lqFormView
     */
    public LQFormView getLqFormView() {
        return lqFormView;
    }

    /**
     * @param lqFormView the lqFormView to set
     */
    public void setLqFormView(LQFormView lqFormView) {
        this.lqFormView = lqFormView;
    }

   
}
