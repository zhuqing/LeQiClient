/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.nav.uf;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 导航模块
 *
 * @author zhuqing
 */
@Lazy
@Component("NavModel")
public class NavModel extends FXMLModel {

    private StringProperty currentBusinessModel;

    public NavModel() {
        setFxmlPath("/com/leqi/client/nav/uf/Nav.fxml");
//        setFxmlPath("/com/bjgoodwill/hip/client/fw/root/uf/titleRoot.fxml");
    }

    @Override
    @Resource(name = "NavController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the currentBusinessModel
     */
    public String getCurrentBusinessModel() {
        return currentBusinessModelProperty().getValue();
    }

    /**
     * @return the currentBusinessModel
     */
    public StringProperty currentBusinessModelProperty() {
        if (currentBusinessModel == null) {
            currentBusinessModel = new SimpleStringProperty();
        }
        return currentBusinessModel;
    }

    /**
     * @param currentBusinessModel the currentBusinessModel to set
     */
    public void setCurrentBusinessModel(String currentBusinessModel) {
        this.currentBusinessModelProperty().setValue(currentBusinessModel);
    }
}
