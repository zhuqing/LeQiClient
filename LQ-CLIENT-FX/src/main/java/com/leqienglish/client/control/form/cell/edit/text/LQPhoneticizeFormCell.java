/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.text;


import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.control.phoneticize.LQPhoneticize;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author zhangyingchuang
 */
public class LQPhoneticizeFormCell<S> extends LQEditableFormCell<S, String, LQPhoneticize> {

    private LQPhoneticize hipPhoneticize;
    private String phoneticizeName;

    private Double phoneticizeWidth;

    /**
     * 拼音码的最大长度，默认为10
     */
    private Integer phoneticizeMaxLength = 10;

    @Override
    protected LQPhoneticize createEditGraghic() {
        if (hipPhoneticize == null) {
            hipPhoneticize = new LQPhoneticize();
            hipPhoneticize.setPhoneticizeMaxLength(this.getPhoneticizeMaxLength());
            hipPhoneticize.setCommit((t) -> {
                commitValue(getLQPhoneticize().getText());
                commitValue(getPhoneticizeName(), hipPhoneticize.getPhoneticize());
            });
            hipPhoneticize.getPhoneticizeTextField().setPrefWidth(getPhoneticizeWidth());
            //hipPhoneticize.phoneticizeProperty().addListener(phoneticizeChangeListener);
        }
        hipPhoneticize.getTextField().setPromptText(this.getPromptText());
        return hipPhoneticize;
    }

    @Override
    protected void updateValue(String t) {
        super.updateValue(t);
        S s = this.getItem();
        if (s == null) {
            return;
        }
        getLQPhoneticize().setText(t);
        try {
            String phoneticizeValue = (String) PropertyReflectUtil.getValue(s, this.getPhoneticizeName());
            this.getLQPhoneticize().setPhoneticize(phoneticizeValue);
        } catch (Exception ex) {
            Logger.getLogger(LQPhoneticizeFormCell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LQPhoneticize getLQPhoneticize() {
        if (hipPhoneticize == null) {
            createEditGraghic();
        }
        return hipPhoneticize;
    }

    public void setLQPhoneticize(LQPhoneticize hipPhoneticize) {
        this.hipPhoneticize = hipPhoneticize;
    }

    public String getPhoneticizeName() {
        return phoneticizeName;
    }

    public void setPhoneticizeName(String phoneticizeName) {
        this.phoneticizeName = phoneticizeName;
    }

    @Override
    public LQPhoneticizeFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQPhoneticizeFormCell hipPhoneticizeFormCell = (LQPhoneticizeFormCell) formCell;
            hipPhoneticizeFormCell.setPhoneticizeName(this.getPhoneticizeName());
            hipPhoneticizeFormCell.setPhoneticizeMaxLength(this.getPhoneticizeMaxLength());
            return hipPhoneticizeFormCell;
        }
        return null;
    }

    public Double getPhoneticizeWidth() {
        if (phoneticizeWidth == null) {
            phoneticizeWidth = 60.00;
        }
        return phoneticizeWidth;
    }

    public void setPhoneticizeWidth(Double phoneticizeWidth) {
        this.phoneticizeWidth = phoneticizeWidth;
    }

    /**
     * 拼音码的最大长度，默认为10
     *
     * @return the phoneticizeMaxLength
     */
    public Integer getPhoneticizeMaxLength() {
        return phoneticizeMaxLength;
    }

    /**
     * 拼音码的最大长度，默认为10
     *
     * @param phoneticizeMaxLength the phoneticizeMaxLength to set
     */
    public void setPhoneticizeMaxLength(Integer phoneticizeMaxLength) {
        this.phoneticizeMaxLength = phoneticizeMaxLength;
    }

}
