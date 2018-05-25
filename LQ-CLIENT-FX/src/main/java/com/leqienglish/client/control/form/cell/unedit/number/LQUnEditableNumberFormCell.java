/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.unedit.number;

import com.github.pagehelper.StringUtil;
import com.leqienglish.client.control.form.cell.LQFormCell;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zhangyingchuang
 */
public class LQUnEditableNumberFormCell extends LQFormCell<Object, Object> {

    private String numberFormat;

    public LQUnEditableNumberFormCell() {
        this.getStyleClass().add("number-form-cell");
    }

    @Override
    protected void updateValue(Object t) {
        String value = this.toText(t);
        if (getNumberFormat() != null) {
            DecimalFormat decimalFormat = new DecimalFormat(getNumberFormat());
            try {
                if (StringUtil.isEmpty(value)) {
                    value = "0";
                }
                Number number = decimalFormat.parse(value);
                value = decimalFormat.format(number);
            } catch (ParseException ex) {
                Logger.getLogger(LQUnEditableNumberFormCell.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setText(value);
    }

    @Override
    public LQUnEditableNumberFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQUnEditableNumberFormCell unEditableNumberFormCell = (LQUnEditableNumberFormCell) formCell;
            unEditableNumberFormCell.setNumberFormat(this.getNumberFormat());
            return unEditableNumberFormCell;
        }
        return null;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

}
