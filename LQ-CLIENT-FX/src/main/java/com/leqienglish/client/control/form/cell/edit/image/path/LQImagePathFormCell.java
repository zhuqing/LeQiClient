/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.image.path;

import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.control.image.path.LQEditableImagePathView;
import io.reactivex.rxjavafx.observables.JavaFxObservable;

/**
 *
 * @author zhangyingchuang
 */
public class LQImagePathFormCell<S> extends LQEditableFormCell<S, String, LQEditableImagePathView> {

    private LQEditableImagePathView hipImageView;

    @Override
    protected LQEditableImagePathView createEditGraghic() {
        if (hipImageView == null) {
            hipImageView = new LQEditableImagePathView();
            JavaFxObservable.nullableValuesOf(hipImageView.imagePathProperty())
                    .subscribe((p) -> commitValue(p.orElse(null)));
            hipImageView.getImageView().setFitWidth(this.getPrefWidth());
            hipImageView.getImageView().setFitHeight(this.getPrefHeight());
        }
        return hipImageView;
    }

    @Override
    protected void updateValue(String path) {
        super.updateValue(path);

        this.getHipImageView().setImagePath(path);

    }

    public LQEditableImagePathView getHipImageView() {
        if (hipImageView == null) {
            createEditGraghic();
        }
        return hipImageView;
    }

}
