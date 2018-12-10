/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.tool.uf;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.word.Word;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("WordToolModel")
public class WordToolModel extends FXMLModel {




     public WordToolModel() {
        setFxmlPath("/com/leqi/client/word/tool/uf/WordTool.fxml");

    }
     
    @Override
    @Resource(name = "WordToolController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); 
    }
   

    
}
