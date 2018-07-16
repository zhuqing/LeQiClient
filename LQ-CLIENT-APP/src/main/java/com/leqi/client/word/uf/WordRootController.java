/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.uf;

import com.leqi.client.book.word.cf.SaveWordsCommand;
import static com.leqienglish.client.fw.cf.Command.DATA;
import static com.leqienglish.client.fw.cf.Command.ID;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.wordpane.WordsPane;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.word.Word;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("WordRootController")
public class WordRootController extends  FXMLController<WordRootModel> {
    
    @FXML
    private WordsPane wordsPane;
    
    @Resource(name = "SaveWordsCommand")
    protected SaveWordsCommand saveWordsCommand;

    @Override
    public void refresh() {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
     @FXML
    public void wordEdit(ActionEvent event){
        
    }
    @FXML
    public void save(ActionEvent event){
        List<Word> words = this.wordsPane.getWords();
        Content article = this.getModel().getArticle();
        Map<String,Object> param = new HashMap<>();
        param.put(DATA, words);
        param.put(ID, article.getId());
        saveWordsCommand.doCommand(param);
    }
}
