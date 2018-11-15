/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.shortword.uf;

import com.leqi.client.book.content.uf.ContentModel;
import com.leqi.client.word.shortword.sentence.uf.ShortWordAndSentenceModel;
import com.leqi.client.word.uf.*;
import com.leqi.client.book.word.cf.SaveWordsCommand;
import com.leqi.client.word.shortword.cf.DeleteShortWordCommand;
import com.leqi.client.word.shortword.cf.QueryShortWordsCommand;
import com.leqi.client.word.shortword.cf.SaveShortWordCommand;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.view.table.LQTableView;
import static com.leqienglish.client.fw.cf.Command.DATA;
import static com.leqienglish.client.fw.cf.Command.ID;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.client.util.event.EventUtil;
import com.leqienglish.client.util.sourceitem.SourceItem;
import com.leqienglish.client.wordpane.WordsPane;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.entity.word.Word;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("ShortWordController")
public class ShortWordController extends  FXMLController<ShortWordModel> {
    
    @FXML
    private TextField filter;
    
     @FXML
    private LQTableView<ShortWord> wordsTableView;
 
     @FXML
    private LQFormView<ShortWord> wordInfoFormView;
    
    @Resource(name = "SaveShortWordCommand")
    protected SaveShortWordCommand saveShortWordCommand;
    
     
    @Resource(name = "QueryShortWordsCommand")
    protected QueryShortWordsCommand queryShortWordsCommand;

      @Resource(name = "DeleteShortWordCommand")
    protected DeleteShortWordCommand deleteShortWordCommand;

    @Resource(name = "ShortWordRootModel")
    private ContentModel contentModel;

    @Resource(name = "ShortWordAndSentenceModel")
    private ShortWordAndSentenceModel shortWordAndSentenceModel;


    @Override
    public void refresh() {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().editingDataProperty())
                .subscribe((c)->this.wordInfoFormView.setValue(c.getNewVal()));
        JavaFxObservable.changesOf(this.getModel().getWords())
                .subscribe((c)->this.wordsTableView.getItems().setAll(this.getModel().getWords()));
    }
    
       @FXML
    public void query(ActionEvent event){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(DATA, this.filter.getText());
        this.queryShortWordsCommand.doCommand(map);
    }
    
     @FXML
    public void create(ActionEvent event){
        

        
        this.getModel().setEditingData(new ShortWord());
    }
    @FXML
    public void save(ActionEvent event){
        Map<String,Object> param = new HashMap<>();
        param.put(DATA, this.getModel().getEditingData());
      saveShortWordCommand.doCommand(param);
    }
    
     @FXML
    public void edit(ActionEvent event){
         ShortWord shortWord = EventUtil.getEntityFromButton(event);
         this.getModel().setEditingData(shortWord);
    }
      @FXML
    public void delete(ActionEvent event){
        if(!AlertUtil.couldDo(AlertUtil.IF_DELETE)){
            return;
        }
      ShortWord shortWord = EventUtil.getEntityFromButton(event);
        Map<String,Object> param = new HashMap<>();
        param.put(DATA, shortWord);
        deleteShortWordCommand.doCommand(param);
    }

    @FXML
    public void   addSentence(ActionEvent event){
        ShortWord shortWord = EventUtil.getEntityFromButton(event);
        this.shortWordAndSentenceModel.setCurrentData(shortWord);
        this.contentModel.setAddBreadCrumb(new SourceItem(shortWord.getId(),shortWord.getWord(),"ShortWordAndSentenceModel"));

    }

    
}
