/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.shortword.sentence.uf;

import com.leqi.client.book.sentence.cf.QuerySentenceCommand;
import com.leqi.client.word.shortword.cf.DeleteShortWordCommand;
import com.leqi.client.word.shortword.cf.QueryShortWordsCommand;
import com.leqi.client.word.shortword.cf.SaveShortWordCommand;
import com.leqi.client.word.shortword.sentence.cf.DeleteShortWordAndSentenceCommand;
import com.leqi.client.word.shortword.sentence.cf.QueryShortWordSentenceCommand;
import com.leqi.client.word.shortword.sentence.cf.SaveShortWordAndSentenceCommand;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.client.util.event.EventUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.sentence.Sentence;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.entity.english.shortword.ShortWordAndSentence;
import xyz.tobebetter.entity.english.shortword.ShortWordAndSentenceVO;

import javax.annotation.Resource;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static com.leqienglish.client.fw.cf.Command.CONSUMER;
import static com.leqienglish.client.fw.cf.Command.DATA;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("ShortWordAndSentenceController")
public class ShortWordAndSentenceController extends  FXMLController<ShortWordAndSentenceModel> {
    
    @FXML
    private TextField filter;
    
     @FXML
    private LQTableView<Sentence> sentencesTableView;
 
     @FXML
    private LQFormView<ShortWord> wordInfoFormView;

    @FXML
    private LQTableView<ShortWordAndSentenceVO> shortWordSentenceTableView;



    @Resource(name = "QuerySentenceCommand")
    protected QuerySentenceCommand querySentenceCommand;

    @Resource(name = "SaveShortWordAndSentenceCommand")
    protected SaveShortWordAndSentenceCommand saveShortWordAndSentenceCommand;


    @Resource(name = "DeleteShortWordAndSentenceCommand")
    protected DeleteShortWordAndSentenceCommand deleteShortWordAndSentenceCommand;



    @Resource(name = "QueryShortWordSentenceCommand")
    protected QueryShortWordSentenceCommand queryShortWordSentenceCommand;

    @Override
    public void refresh() {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().currentDataProperty())
                .subscribe((c)-> this.currentDataChange(getModel().getCurrentData()));
        JavaFxObservable.changesOf(this.getModel().getSentences())
                .subscribe((c)->this.sentencesTableView.getItems().setAll(this.getModel().getSentences()));
        JavaFxObservable.changesOf(this.getModel().getShortWordAndSentenceVOS())
                .subscribe((c)->this.shortWordSentenceTableView.getItems().setAll(this.getModel().getShortWordAndSentenceVOS()));

        if(this.getModel().getCurrentData()!=null){
            this.currentDataChange(getModel().getCurrentData());
        }
    }

    private void currentDataChange(ShortWord shortWord){
        this.wordInfoFormView.setValue(shortWord);
        Map<String,Object> param = new HashMap<>();
        param.put(DATA,shortWord);
        this.queryShortWordSentenceCommand.doCommand(param);
    }
    
       @FXML
    public void query(ActionEvent event){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(DATA, this.filter.getText());

        map.put(CONSUMER, new Consumer<Sentence[]>() {
            @Override
            public void accept(Sentence[] sentences) {
                getModel().getSentences().setAll(sentences);
            }
        });

        this.querySentenceCommand.doCommand(map);
    }
    
     @FXML
    public void create(ActionEvent event){
        this.getModel().setCurrentData(new ShortWord());
    }
    @FXML
    public void save(ActionEvent event){
        Map<String,Object> param = new HashMap<>();
        param.put(DATA, this.getModel().getCurrentData());

    }
    
     @FXML
    public void add2ShortWord(ActionEvent event){
      Sentence  sentence = EventUtil.getEntityFromButton(event);
      ShortWordAndSentenceVO shortWordAndSentence = new ShortWordAndSentenceVO();
      shortWordAndSentence.setSentenceId(sentence.getId());
      shortWordAndSentence.setShortWordId(this.getModel().getCurrentData().getId());
      shortWordAndSentence.setEnglish(sentence.getEnglish());
      shortWordAndSentence.setChinese(sentence.getChinese());

      Map<String,Object> map = new HashMap<>();
      map.put(DATA,shortWordAndSentence);

      this.saveShortWordAndSentenceCommand.doCommand(map);
    }
      @FXML
    public void deleteShortWordAndSentence(ActionEvent event){
        if(!AlertUtil.couldDo("是否删除？")){
            return;
        }
        ShortWordAndSentenceVO shortWordAndSentenceVO = EventUtil.getEntityFromButton(event);

          Map<String,Object> map = new HashMap<>();
          map.put(DATA,shortWordAndSentenceVO);

          this.deleteShortWordAndSentenceCommand.doCommand(map);
    }
    
}
