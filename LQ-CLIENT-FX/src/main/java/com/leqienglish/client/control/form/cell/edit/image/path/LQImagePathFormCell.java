/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.image.path;

import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.control.image.path.LQEditableImagePathView;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.util.Callback;

/**
 *
 * @author zhangyingchuang
 */
public class LQImagePathFormCell<S> extends LQEditableFormCell<S, String, LQEditableImagePathView> {

    private LQEditableImagePathView hipImageView;
    
    private Callback<String,String> checkFileCallback;

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

        if(this.getCheckFileCallback()!=null){
            path = this.getCheckFileCallback().call(path);
        }
        this.getHipImageView().setImagePath(path);

    }

    public LQEditableImagePathView getHipImageView() {
        if (hipImageView == null) {
            createEditGraghic();
        }
        return hipImageView;
    }

    /**
     * @return the checkFileCallback
     */
    public Callback<String,String> getCheckFileCallback() {
        return checkFileCallback;
    }

    /**
     * @param checkFileCallback the checkFileCallback to set
     */
    public void setCheckFileCallback(Callback<String,String> checkFileCallback) {
        this.checkFileCallback = checkFileCallback;
    }

}
