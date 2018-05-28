/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.cf;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;

/**
 *
 * @author duyi
 */
public abstract class BusinessDataNavCommand extends ChangeListenerCommand {

    protected Tab tab;

    private String businessId;

    private String navId;

    private String businessName;

    private final BooleanProperty businessVisit = new SimpleBooleanProperty(true);

    public String getNavId() {
        return navId;
    }

    public final void setNavId(String navId) {
        this.navId = navId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public final void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public final void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public BooleanProperty businessVisitProperty() {
        return businessVisit;
    }

    public Boolean getBusinessVisit() {
        return businessVisit.getValue();
    }

    public void setBusinessVisit(Boolean businessVisit) {
        if (businessVisit == null) {
            businessVisit = false;
        }
        this.businessVisit.setValue(businessVisit);
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
        tab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    BusinessDataNavCommand.this.doCommand(null);
                }
            }
        });

    }

}
