/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.content;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("UpLoadModel")
public class UpLoadModel extends FXMLModel {

    public UpLoadModel() {
        setFxmlPath("/com/leqi/client/uf/FileUpLoad.fxml");
//        setFxmlPath("/com/bjgoodwill/hip/client/fw/root/uf/titleRoot.fxml");
    }

    @Override
    @Resource(name = "UpLoadController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }
}
