/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.number;


import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.control.number.LQNumber;
import com.leqienglish.client.util.lock.LockUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

/**
 *
 * @author zhangyingchuang
 */
public class LQNumberFormCell<S> extends LQEditableFormCell<S, Number, Node> {

    private LQNumber lqNumber;

    private String numberFormat;

    @Override
    protected Node createEditGraghic() {
        if (lqNumber == null) {
            lqNumber = new LQNumber();
            if (numberFormat != null) {
                lqNumber.setNumberFormat(new DecimalFormat(numberFormat));
            }
            lqNumber.numberProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (LockUtil.isLocked(LQNumberFormCell.this)) {
                        return;
                    }
                    LockUtil.lock(LQNumberFormCell.this);
                    Number number = getNumberFieldValue(lqNumber.getNumber());
                    getLQNumber().setNumber(number);
                    commitValue(number);
                    LockUtil.unLock(LQNumberFormCell.this);
                }
            });
        }
        lqNumber.setPrefHeight(this.getPrefHeight());
        lqNumber.setPromptText(this.getPromptText());
        return lqNumber;
    }

    @Override
    protected void updateValue(Number t) {
        super.updateValue(t);
        LockUtil.lock(LQNumberFormCell.this);
        getLQNumber().setText(this.toText(t));
        getLQNumber().parseAndFormatInput();
        LockUtil.unLock(LQNumberFormCell.this);
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

    public LQNumber getLQNumber() {
        if (lqNumber == null) {
            createEditGraghic();
        }
        return lqNumber;
    }

    private Number getNumberFieldValue(Number number) {

        Class clazz = this.getFieldType(getPropertyName());
        if (number == null) {
            if (isSimpleType(clazz.getSimpleName())) {
                number = new BigDecimal(0);
            } else {
                return null;
            }

        }

        if (clazz == null) {
            return number.doubleValue();
        }

        switch (clazz.getSimpleName()) {
            case "long":
            case "Long":
                return number.longValue();
            case "Byte":
            case "byte":
                return number.byteValue();
            case "double":
            case "Double":
                return number.doubleValue();
            case "float":
            case "Float":
                return number.floatValue();
            case "int":
            case "Integer":
                return number.intValue();
            case "short":
            case "Short":
                return number.shortValue();
            case "BigDecimal":
                return new BigDecimal(number.doubleValue());
            default:
                return number.doubleValue();
        }

    }

    private boolean isSimpleType(String type) {
        switch (type) {
            case "long":

            case "byte":

            case "double":

            case "float":

            case "int":

            case "short":

                return true;

        }
        return false;
    }

    @Override
    public LQNumberFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQNumberFormCell hipNumberFormCell = (LQNumberFormCell) formCell;
            hipNumberFormCell.setNumberFormat(this.getNumberFormat());
            return hipNumberFormCell;
        }
        return null;
    }

}
