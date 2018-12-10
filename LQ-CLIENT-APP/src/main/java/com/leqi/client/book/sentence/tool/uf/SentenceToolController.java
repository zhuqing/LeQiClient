/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.sentence.tool.uf;

import com.leqi.client.book.sentence.tool.cf.SentenceToolCommand;
import com.leqi.client.word.tool.cf.WordToolCommand;
import com.leqi.client.word.tool.uf.WordToolModel;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.leqienglish.client.fw.cf.Command.DATA;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("SentenceToolController")
public class SentenceToolController extends  FXMLController<WordToolModel> {
    
    @FXML
    private TextArea textArea;
    
    @Resource(name = "SentenceToolCommand")
    protected SentenceToolCommand sentenceToolCommand;


    @Override
    public void refresh() {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    

    @FXML
    public void save(ActionEvent event){
      String text = textArea.getText();
      if(text.isEmpty()){
          AlertUtil.showInformation("没有数据");

          return;
      }

      Map<String,Object> param = new HashMap<>();

      param.put(DATA,text);

      this.sentenceToolCommand.doCommand(param);
    }
}
