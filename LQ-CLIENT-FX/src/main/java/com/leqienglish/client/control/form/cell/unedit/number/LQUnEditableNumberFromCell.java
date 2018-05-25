/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.unedit.number;


import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import com.leqienglish.client.util.sourceitem.SourceItem;
import com.leqienglish.client.util.sourceitem.SourceItemUtil;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Callback;

/**
 *
 * @author zhangyingchuang
 */
public class LQUnEditableNumberFromCell<S, T> extends LQFormCell<S, T> {

    private String unitPropertyName;

    private Callback<S, List<SourceItem<Long>>> customDataSourceCallback;

    private String numberFormat;
    /**
     * 数据源
     */
    private List<SourceItem<Long>> sourceItems;

    @Override
    protected void updateValue(T t) {
        String value = this.toText(t);
        if (getNumberFormat() != null) {
            DecimalFormat decimalFormat = new DecimalFormat(getNumberFormat());
            try {
                Number number = decimalFormat.parse(value);
                value = decimalFormat.format(number);
            } catch (ParseException ex) {
                Logger.getLogger(LQUnEditableNumberFromCell.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            SourceItem selected = getSelectedSourceItem();
            if (selected != null) {
                value = value + selected.getDisplay();
            }
        } catch (Exception ex) {
            Logger.getLogger(LQUnEditableNumberFromCell.class.getName()).log(Level.SEVERE, null, ex);
        }
        setText(value);
    }

    public String getUnitPropertyName() {
        return unitPropertyName;
    }

    public void setUnitPropertyName(String unitPropertyName) {
        this.unitPropertyName = unitPropertyName;
    }

    /**
     * @return the customDataSourceCallback
     */
    public Callback<S, List<SourceItem<Long>>> getCustomDataSourceCallback() {
        return customDataSourceCallback;
    }

    /**
     * @param customDataSourceCallback the customDataSourceCallback to set
     */
    public void setCustomDataSourceCallback(Callback<S, List<SourceItem<Long>>> customDataSourceCallback) {
        this.customDataSourceCallback = customDataSourceCallback;
    }

    protected SourceItem getSelectedSourceItem() throws Exception {
        Object s = this.getItem();
        Object unitValue = PropertyReflectUtil.getValue(s, this.getUnitPropertyName());
        String unnitText = "";
        if (getUnitPropertyName() == null) {
            return null;
        }
        Class clazz = this.getFieldType(getUnitPropertyName());
        if (clazz == null) {
            unnitText = unitValue.toString();
        } else {
            if (clazz.isPrimitive()) {//判断是否是基本数据类型
                unnitText = unitValue + "";
            } else if (clazz.isAssignableFrom(Long.class)) {
                unnitText = unitValue.toString();
            } else {
                unnitText = unitValue.toString();
            }
        }
        return SourceItemUtil.getSourceItem(Long.valueOf(unnitText), getSourceItems());
    }

    public List<SourceItem<Long>> getSourceItems() {
        if (sourceItems == null) {
            sourceItems = getCustomDataSourceCallback().call(this.getItem());
        }
        return sourceItems;
    }

    @Override
    public LQUnEditableNumberFromCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQUnEditableNumberFromCell unEditableCustomeDataSoureNumberFromCell = (LQUnEditableNumberFromCell) formCell;
            unEditableCustomeDataSoureNumberFromCell.setUnitPropertyName(this.getUnitPropertyName());
            unEditableCustomeDataSoureNumberFromCell.setCustomDataSourceCallback(this.getCustomDataSourceCallback());
            unEditableCustomeDataSoureNumberFromCell.setNumberFormat(this.getNumberFormat());
            return unEditableCustomeDataSoureNumberFromCell;
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
