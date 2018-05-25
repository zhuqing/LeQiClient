/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.text;


import com.leqienglish.client.control.form.cell.LQFormCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;

/**
 * 
 * @author zhuqing
 * @param <S> 
 */
public class LQTextAreaInputFormCell<S> extends LQTextInputFormCell<S> {

    /**
     * 显示行数
     */
    private Integer rowCount;

    @Override
    protected TextInputControl createTextField() {
        TextArea ta = new TextArea();
        ta.setPrefRowCount(getRowCount());
        ta.setWrapText(true);
        return ta;
    }

    public LQFormCell clone() {
        LQTextAreaInputFormCell cell = (LQTextAreaInputFormCell) super.clone();
        cell.setRowCount(rowCount);
        return cell;
    }

    /**
     * @return the rowCount
     */
    public Integer getRowCount() {
        if (this.rowCount == null) {
            rowCount = 2;
        }
        return rowCount;
    }

    /**
     * @param rowCount the rowCount to set
     */
    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

}
