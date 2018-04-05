/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.root.uf.business;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.annotation.Resource;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author duyi
 */
@Lazy
@Component("businessRootModel")
public class BusinessRootModel extends FXMLModel {

    private final StringProperty businessModel = new SimpleStringProperty();


    public BusinessRootModel() {
        setFxmlPath("/com/bjgoodwill/hip/client/fw/root/uf/business/businessRoot.fxml");
    }

    @Override
    @Resource(name = "businessRootController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }

   

    public StringProperty businessModelProperty() {
        return businessModel;
    }

    public void setBusinessModel(String businessModel) {
        this.businessModel.setValue(businessModel);
    }

    public String getBusinessModel() {
        return this.businessModel.getValue();
    }

   

    public void clearAllBusiness() {
        BusinessRootController businessRootController = (BusinessRootController) getFxmlController();
       // businessRootController.getRootTabPane().getTabs().clear();
    }

}
