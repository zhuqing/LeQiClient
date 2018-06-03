/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.unedit;

import com.leqienglish.client.control.form.cell.LQFormCell;

/**
 *
 * @author zhuqing
 * @param <S>
 * @param <T>
 */
public class LQTitleFormCell<S, T> extends LQFormCell<S, T> {

    public LQTitleFormCell() {
        this.setWrapText(true);
        this.setPrefHeight(24);
    }

    @Override
    protected void updateValue(T t) {
        if (this.getToTextCallback() == null) {
            return;
        }
        this.setText(this.getToTextCallback().call(t));
        if (this.getToTextCallback() == null) {
            return;
        }

        this.setText(this.getToTextCallback().call(t));

    }

    public LQFormCell clone() {
        LQFormCell cell = super.clone();
        cell.setWrapText(this.isWrapText());
        cell.setText(this.getText());
        return cell;
    }
}
