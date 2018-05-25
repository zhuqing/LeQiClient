/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.cell.text;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author zhuqing
 * @param <S>
 * @param <T>
 */
public class LQHyperlinkTableCell<S, T> extends LQTableTextCell<S, T> {

    private Hyperlink hyperlink;

    public LQHyperlinkTableCell() {
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        if (this.getGraphic() == null) {
            this.setGraphic(this.getHyperlink());
        }
        this.getHyperlink().setText(toText(item));
    }

    /**
     * @return the hyperlink
     */
    public Hyperlink getHyperlink() {
        if (hyperlink == null) {
            hyperlink = new Hyperlink();
            JavaFxObservable.eventsOf(hyperlink, MouseEvent.MOUSE_CLICKED)
                    .filter((me) -> me.getClickCount() == 1)
                    .filter((me) -> this.getMouseClickEventHander() != null)
                    .subscribe((me) -> {
                        this.getMouseClickEventHander().accept(this.getTableRow().getItem());
                        me.consume();
                    });
        }
        return hyperlink;
    }

    /**
     * @param hyperlink the hyperlink to set
     */
    public void setHyperlink(Hyperlink hyperlink) {
        this.hyperlink = hyperlink;
    }

}
