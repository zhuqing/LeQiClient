/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.sentence.tool.uf;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("SentenceToolModel")
public class SentenceToolModel extends FXMLModel {




     public SentenceToolModel() {
        setFxmlPath("/com/leqi/client/book/sentence/tool/uf/SentenceTool.fxml");

    }
     
    @Override
    @Resource(name = "SentenceToolController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); 
    }
   

    
}
