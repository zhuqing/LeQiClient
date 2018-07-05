/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.dialog.uf;

import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("LQDialogModel")
public class LQDialogModel  extends FXMLModel{
    private StringProperty businessModelId;
    
    /**
     * 确认按钮事件触发
     */
    private Command okCommand;
    /**
     * 取消按钮事件触发
     */
    private Command cancelCommand;
    
    public LQDialogModel() {
        setFxmlPath("/com/leqi/client/dialog/uf/Dialog.fxml");
    }

    @Override
    @Resource(name = "LQDialogController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the businessModelId
     */
    public String getBusinessModelId() {
        return businessModelIdProperty().getValue();
    }
    
      /**
     * @return the businessModelId
     */
    public StringProperty businessModelIdProperty() {
        if(businessModelId==null){
            businessModelId = new SimpleStringProperty();
        }
        return businessModelId;
    }

    /**
     * @param businessModelId the businessModelId to set
     */
    public void setBusinessModelId(String businessModelId) {
        this.businessModelIdProperty().setValue(businessModelId); 
    }

    /**
     * @return the okCommand
     */
    public Command getOkCommand() {
        return okCommand;
    }

    /**
     * @param okCommand the okCommand to set
     */
    public void setOkCommand(Command okCommand) {
        this.okCommand = okCommand;
    }

    /**
     * @return the cancelCommand
     */
    public Command getCancelCommand() {
        return cancelCommand;
    }

    /**
     * @param cancelCommand the cancelCommand to set
     */
    public void setCancelCommand(Command cancelCommand) {
        this.cancelCommand = cancelCommand;
    }
}
