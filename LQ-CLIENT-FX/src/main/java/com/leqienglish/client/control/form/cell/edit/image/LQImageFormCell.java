/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.image;


import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.control.image.LQEditableImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author zhangyingchuang
 */
public class LQImageFormCell<S> extends LQEditableFormCell<S, byte[], LQEditableImageView> {

    private LQEditableImageView hipImageView;

    private final ChangeListener<byte[]> imageViewBytesChangeListener = new ChangeListener<byte[]>() {
        @Override
        public void changed(ObservableValue<? extends byte[]> observable, byte[] oldValue, byte[] newValue) {
            commitValue(newValue);
        }
    };

    @Override
    protected LQEditableImageView createEditGraghic() {
        if (hipImageView == null) {
            hipImageView = new LQEditableImageView();
            hipImageView.imageViewBytesProperty().addListener(imageViewBytesChangeListener);
            hipImageView.getImageView().setFitWidth(this.getPrefWidth());
            hipImageView.getImageView().setFitHeight(this.getPrefHeight());
        }
        return hipImageView;
    }

    @Override
    protected void updateValue(byte[] t) {
        super.updateValue(t);
        this.getHipImageView().imageViewBytesProperty().removeListener(imageViewBytesChangeListener);
        this.getHipImageView().setImageViewBytes(t);
        this.getHipImageView().imageViewBytesProperty().addListener(imageViewBytesChangeListener);
    }

    public LQEditableImageView getHipImageView() {
        if (hipImageView == null) {
            createEditGraghic();
        }
        return hipImageView;
    }

}
