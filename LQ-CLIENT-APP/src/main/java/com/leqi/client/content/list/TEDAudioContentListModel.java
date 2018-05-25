package com.leqi.client.content.list;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("TEDAudioContentListModel")
public class TEDAudioContentListModel extends FXMLModel {

    public TEDAudioContentListModel() {
        setFxmlPath("/com/leqi/content/list/TEDAudioContentList.fxml");
    }

    @Override
    @Resource(name = "TEDAudioContentListController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }
    
}
