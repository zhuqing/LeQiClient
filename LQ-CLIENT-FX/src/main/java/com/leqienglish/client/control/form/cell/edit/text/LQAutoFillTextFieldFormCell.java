/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.text;

import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.control.text.AutoFillTextField;
import com.leqienglish.util.text.TextUtil.FILL_DIRACTION;



/**
 *
 * @author zhuqing
 */
public class LQAutoFillTextFieldFormCell<S> extends LQEditableFormCell<S, String, AutoFillTextField> {

    /**
     * 最大长度
     */
    private int maxLength = 10;

    /**
     * 填充文本
     */
    private String fillText = "*";

    /**
     * 填充方向
     */
    private FILL_DIRACTION fillDiraction = FILL_DIRACTION.HEAD;

    private AutoFillTextField textField;

    @Override
    protected AutoFillTextField createEditGraghic() {
        if (textField != null) {
            return textField;
        }
        textField = new AutoFillTextField();
        this.textField.setMaxLength(this.getMaxLength());
        this.textField.setFillText(this.getFillText());
        this.textField.setFillDiraction(this.getFillDiraction());
        textField.setCommit((t) -> {
            this.commitValue(textField.getText());
        });

        return textField;
    }
    public LQFormCell clone(){
        LQAutoFillTextFieldFormCell cell = (LQAutoFillTextFieldFormCell) super.clone();
        cell.setFillDiraction(fillDiraction);
        cell.setFillText(fillText);
        cell.setMaxLength(maxLength);
        return cell;
    }

    @Override
    protected void updateValue(String t) {
        super.updateValue(t);
        this.getTextField().setText(t);

    }

    /**
     * 最大长度
     *
     * @return the maxLength
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * 最大长度
     *
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * 填充文本
     *
     * @return the fillText
     */
    public String getFillText() {
        return fillText;
    }

    /**
     * 填充文本
     *
     * @param fillText the fillText to set
     */
    public void setFillText(String fillText) {
        this.fillText = fillText;
    }

    /**
     * 填充方向
     *
     * @return the fillDiraction
     */
    public FILL_DIRACTION getFillDiraction() {
        return fillDiraction;
    }

    /**
     * 填充方向
     *
     * @param fillDiraction the fillDiraction to set
     */
    public void setFillDiraction(FILL_DIRACTION fillDiraction) {
        this.fillDiraction = fillDiraction;
    }

    /**
     * @return the textField
     */
    public AutoFillTextField getTextField() {
       createEditGraghic();
        return textField;
    }

}
