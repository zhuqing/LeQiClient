/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.number.customdatasource;


import com.leqienglish.client.control.combobox.LQComboBox;
import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.number.LQNumberFormCell;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import com.leqienglish.client.util.sourceitem.SourceItem;
import com.leqienglish.client.util.sourceitem.SourceItemUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 *
 * @author zhangyingchuang
 */
public class LQNumberUnitFromCell<S> extends LQNumberFormCell<S> {

    private Double unitWidth;

    private String unitPropertyName;

    private Boolean unitEditing;

    private Callback<S, List<SourceItem<Long>>> customDataSourceCallback;

    private String unitClass;

    private HBox box;

    private Label unitLabel;

    private LQComboBox unitComboBox;

    /**
     * 数据源
     */
    private List<SourceItem<Long>> sourceItems;

    private final ChangeListener<SourceItem<Long>> selectChangeListener = new ChangeListener<SourceItem<Long>>() {
        @Override
        public void changed(ObservableValue<? extends SourceItem<Long>> observable, SourceItem<Long> oldValue, SourceItem<Long> newValue) {
            commitUnitValue(newValue.getValue());
        }
    };

    @Override
    protected HBox createEditGraghic() {
        super.createEditGraghic();
        if (box == null) {
            box = new HBox();
            if (getUnitEditing()) {
                unitComboBox = new LQComboBox();
                unitComboBox.setPrefWidth(getUnitWidth());
                unitComboBox.setMinWidth(getUnitWidth());
                unitComboBox.setSourceItems(this.getSourceItems());
                unitComboBox.getSelectionModel().selectedItemProperty().addListener(selectChangeListener);
                box.getChildren().addAll(getLQNumber(), unitComboBox);
            } else {
                unitLabel = new Label();
                unitLabel.setPrefWidth(getUnitWidth());
                unitLabel.setMinWidth(getUnitWidth());
                box.getChildren().addAll(getLQNumber(), unitLabel);
            }

            box.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue != null && newValue) {
                        getLQNumber().requestFocus();
                    }
                }
            });
        }
        return box;
    }

    @Override
    protected void updateValue(Number t) {
        super.updateValue(t);
        try {
            SourceItem selected = getSelectedSourceItem();
            if (getUnitEditing()) {
                this.getUnitComboBox().getSelectionModel().selectedItemProperty().removeListener(selectChangeListener);
                if (selected != null) {
                    this.getUnitComboBox().getSelectionModel().select(selected);
                } else {
                    this.getUnitComboBox().getSelectionModel().clearSelection();
                    this.getUnitComboBox().setValue(selected);
                }
                this.getUnitComboBox().getSelectionModel().selectedItemProperty().addListener(selectChangeListener);

            } else if (selected != null) {
                getUnitLabel().setText(selected.getDisplay());
            } else {
                getUnitLabel().setText("");
            }
        } catch (Exception ex) {
            Logger.getLogger(LQNumberUnitFromCell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void commitUnitValue(Number unnit) {
        try {
            Object s = this.getItem();
            switch (getUnitClass()) {
                case "long":
                    commitValue(getUnitPropertyName(), Long.getLong(unnit.toString()));
                    return;
                default:
                    commitValue(getUnitPropertyName(), unnit);
            }
        } catch (Exception ex) {
            Logger.getLogger(LQNumberUnitFromCell.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Label getUnitLabel() {
        if (unitLabel == null) {
            createEditGraghic();
        }
        return unitLabel;
    }

    public Double getUnitWidth() {
        if (unitWidth == null) {
            unitWidth = 50.00;
        }
        return unitWidth;
    }

    public void setUnitWidth(Double unitWidth) {
        this.unitWidth = unitWidth;
    }

    public String getUnitPropertyName() {
        return unitPropertyName;
    }

    public void setUnitPropertyName(String unitPropertyName) {
        this.unitPropertyName = unitPropertyName;
    }

    public String getUnitClass() {
        return unitClass;
    }

    public void setUnitClass(String unitClass) {
        this.unitClass = unitClass;
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

        Class clazz = this.getFieldType(getUnitPropertyName());

        if (clazz == null) {
            unnitText = unitValue.toString();
        } else if (clazz.isPrimitive()) {//判断是否是基本数据类型
            unitClass = clazz.getName();
            unnitText = unitValue + "";
        } else if (clazz.isAssignableFrom(Long.class)) {
            unitClass = "long";
            unnitText = unitValue.toString();
        } else {
            unnitText = unitValue.toString();
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
    public LQNumberUnitFromCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQNumberUnitFromCell customeDataSoureNumberUnitFromCell = (LQNumberUnitFromCell) formCell;
            customeDataSoureNumberUnitFromCell.setUnitWidth(this.getUnitWidth());
            customeDataSoureNumberUnitFromCell.setUnitPropertyName(this.getUnitPropertyName());
            customeDataSoureNumberUnitFromCell.setCustomDataSourceCallback(this.getCustomDataSourceCallback());
            customeDataSoureNumberUnitFromCell.setUnitEditing(this.getUnitEditing());
            customeDataSoureNumberUnitFromCell.setUnitClass(unitClass);
            return customeDataSoureNumberUnitFromCell;
        }
        return null;
    }

    public LQComboBox getUnitComboBox() {
        if (unitComboBox == null) {
            createEditGraghic();
        }
        return unitComboBox;
    }

    public Boolean getUnitEditing() {
        return unitEditing;
    }

    public void setUnitEditing(Boolean unitEditing) {
        this.unitEditing = unitEditing;
    }

}
