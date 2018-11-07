/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.sentence.word.uf;

import com.leqi.client.book.content.uf.ContentModel;
import com.leqi.client.book.sentence.cf.DeleteSentenceCommand;
import com.leqi.client.book.sentence.cf.QuerySentenceCommand;
import com.leqi.client.book.sentence.cf.SaveSentenceCommand;
import com.leqi.client.book.sentence.word.cf.DeleteSentenceAndWordCommand;
import com.leqi.client.book.sentence.word.cf.QuerySentenceAndWordsCommand;
import com.leqi.client.book.sentence.word.cf.SaveSentenceAndWordCommand;
import com.leqi.client.book.word.cf.QueryWordsCommand;
import com.leqi.client.word.cf.LQQueryWordsCommand;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.view.table.LQTableView;
import static com.leqienglish.client.fw.cf.Command.CONSUMER;
import static com.leqienglish.client.fw.cf.Command.DATA;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.client.util.event.EventUtil;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.sentence.Sentence;
import xyz.tobebetter.entity.english.sentence.SentenceAndWord;
import xyz.tobebetter.entity.word.Word;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("SentenceAndWordController")
public class SentenceAndWordController extends FXMLController<SentenceAndWordModel> {

    @FXML
    private TextField filter;

    @FXML
    private LQTableView<Word> wordsTableView;
    
    @FXML
    private LQTableView<SentenceAndWord> wordAndSentenceTableView;
    
    @FXML
    private LQFormView<Sentence> sentenceInfoFormView;

    @Resource(name = "SaveSentenceCommand")
    protected SaveSentenceCommand saveSentenceCommand;

    @Resource(name = "DeleteSentenceAndWordCommand")
    protected DeleteSentenceAndWordCommand deleteSentenceAndWordCommand;

    @Resource(name = "QuerySentenceAndWordsCommand")
    protected QuerySentenceAndWordsCommand querySentenceAndWordsCommand;
    
    @Resource(name = "LQQueryWordsCommand")
    protected LQQueryWordsCommand queryWordsCommand;
    
    
    @Resource(name = "SentenceRootModel")
    private ContentModel contentModel;
    
 @Resource(name = "SaveSentenceAndWordCommand")
    private SaveSentenceAndWordCommand saveSentenceAndWordCommand;

    @Override
    public void refresh() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().currentSentenceProperty())
                .subscribe((c) -> eidtSentence(c.getNewVal()));
        
      JavaFxObservable.changesOf(this.getModel().getSentenceAndWords())
              .subscribe((c)->this.wordAndSentenceTableView.getItems().setAll(this.getModel().getSentenceAndWords()));
        
        
        if(this.getModel().getCurrentSentence()!=null){
            this.sentenceInfoFormView.setValue(this.getModel().getCurrentSentence());
            
            Map<String,Object> param = new HashMap<>();
            param.put(DATA, this.getModel().getCurrentSentence().getId());
            this.querySentenceAndWordsCommand.doCommand(param);
        }
        
        
    }

    private void eidtSentence(Sentence sentence) {

        this.sentenceInfoFormView.setValue(sentence);
    }

    @FXML
    public void createSentence(ActionEvent event) {
        if (this.sentenceInfoFormView.getValue() != null) {
            Boolean isContinue = AlertUtil.couldDo("有正在编辑的数据，是否继续创建");
            if (!isContinue) {
                return;
            }
        }
        Sentence sentence = new Sentence();
        this.getModel().setCurrentSentence(sentence);
    }

    @FXML
    public void save(ActionEvent event) {

        //this.saveSentenceCommand.doCommand(null);
    }
    
    

    @FXML
    public void deleteWordAndSentence(ActionEvent event) {
       SentenceAndWord sentenceAndWord = EventUtil.getEntityFromButton(event);
       
       Map<String,Object> param = new HashMap<>();
       param.put(DATA, sentenceAndWord);
       deleteSentenceAndWordCommand.doCommand(param);
    }

    @FXML
    public void query(ActionEvent event) {
        Map<String,Object> param = new HashMap<>();
        param.put(CONSUMER, new Consumer<Word[]>(){
            @Override
            public void accept(Word[] t) {
                
                if(t != null){
                     wordsTableView.getItems().setAll(t);
                }else{
                     wordsTableView.getItems().clear();
                }
             
            }
        });
        
        param.put(DATA, this.filter.getText());
        
       queryWordsCommand.doCommand(param);
    }
    
    @FXML
    public void add2Sentence(ActionEvent event) {
       Word word = EventUtil.getEntityFromButton(event);
       SentenceAndWord sentenceAndWord = new SentenceAndWord();
       sentenceAndWord.setSentenceId(this.getModel().getCurrentSentence().getId());
       sentenceAndWord.setWordId(word.getId());
       sentenceAndWord.setWord(word.getWord());
       
       Map<String,Object> param = new HashMap<String,Object>();
       param.put(DATA, sentenceAndWord);
       
       this.saveSentenceAndWordCommand.doCommand(param);
       
    }

}
