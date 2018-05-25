/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.unedit.image;

import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.control.image.LQImageView;



/**
 *
 * @author zhangyingchuang
 */
public class LQUnEditableImageFormCell<S> extends LQEditableFormCell<S, byte[], LQImageView> {

    private LQImageView hipImageView;

    @Override
    protected LQImageView createEditGraghic() {
        if (hipImageView == null) {
            hipImageView = new LQImageView();
        }
        return hipImageView;
    }

    @Override
    protected void updateValue(byte[] t) {
        super.updateValue(t);
        getLQImageView().setImageViewBytes(t);
    }

    public LQImageView getLQImageView() {
        if (hipImageView == null) {
            createEditGraghic();
        }
        return hipImageView;
    }

}
