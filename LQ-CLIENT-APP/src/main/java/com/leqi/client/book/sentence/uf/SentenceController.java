/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.sentence.uf;

import com.leqi.client.book.content.uf.ContentModel;
import com.leqi.client.book.sentence.cf.DeleteSentenceCommand;
import com.leqi.client.book.sentence.cf.QuerySentenceCommand;
import com.leqi.client.book.sentence.cf.SaveSentenceCommand;
import com.leqi.client.book.sentence.word.uf.SentenceAndWordModel;
import com.leqi.client.book.word.uf.*;
import com.leqi.client.book.uf.BookModel;
import com.leqi.client.book.word.cf.DeleteWordAndContentCommand;
import com.leqi.client.book.word.cf.QueryWordsCommand;
import com.leqi.client.book.word.cf.SaveWordAndContentCommand;
import com.leqi.client.book.word.cf.SaveWordsCommand;
import com.leqi.client.word.info.cf.UpdateWordCommand;
import com.leqi.client.word.info.uf.WordInfoModel;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.view.table.LQTableView;
import static com.leqienglish.client.fw.cf.Command.DATA;
import static com.leqienglish.client.fw.cf.Command.ID;
import com.leqienglish.client.fw.dialog.LQDialog;
import com.leqienglish.client.fw.sf.RestClient;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.client.util.event.EventUtil;
import com.leqienglish.client.util.sourceitem.SourceItemUtil;
import com.leqienglish.client.wordpane.WordsPane;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.sentence.Sentence;
import xyz.tobebetter.entity.word.Word;
import xyz.tobebetter.entity.word.WordMean;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("SentenceController")
public class SentenceController extends FXMLController<SentenceModel> {

    @FXML
    private TextField filter;

    @FXML
    private LQTableView<Sentence> sentenceTableView;
    
    @FXML
    private LQFormView<Sentence> sentenceInfoFormView;

    @Resource(name = "SaveSentenceCommand")
    protected SaveSentenceCommand saveSentenceCommand;

    @Resource(name = "QuerySentenceCommand")
    protected QuerySentenceCommand querySentenceCommand;

    @Resource(name = "DeleteSentenceCommand")
    protected DeleteSentenceCommand deleteSentenceCommand;
    
    
    @Resource(name = "SentenceRootModel")
    private ContentModel contentModel;

    @Resource(name = "SentenceAndWordModel")
    private SentenceAndWordModel sentenceAndWordModel;

    @Override
    public void refresh() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().currentSentenceProperty())
                .subscribe((c) -> eidtSentence(c.getNewVal()));
        
        JavaFxObservable.changesOf(this.getModel().getSentences())
                .subscribe((c)->sentenceTableView.getItems().setAll(this.getModel().getSentences()));
        
        JavaFxObservable.changesOf(this.sentenceTableView.getSelectionModel().selectedItemProperty())
                .subscribe((c)->this.getModel().setCurrentSentence(c.getNewVal()));
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
    public void saveSentence(ActionEvent event) {

        this.saveSentenceCommand.doCommand(null);
    }
    
    

    @FXML
    public void delete(ActionEvent event) {
        if(!AlertUtil.couldDo("是否删除？")){
            return;
        }
        Sentence sentence =EventUtil.getEntityFromButton(event);
        
      
         Map<String, Object> param = new HashMap<>();
        param.put(DATA, sentence);
        this.deleteSentenceCommand.doCommand(param);
    }

    @FXML
    public void querySentence(ActionEvent event) {
        Map<String,Object> param = new HashMap<>();
        param.put(DATA, this.filter.getText());
        querySentenceCommand.doCommand(param);
    }
    
    @FXML
    public void addRelationWords(ActionEvent event) {
        Sentence sentence =EventUtil.getEntityFromButton(event);
         sentenceAndWordModel.setCurrentSentence(sentence);
        
        contentModel.setAddBreadCrumb(SourceItemUtil.create(sentence.getEnglish().substring(0, 10), "SentenceAndWordModel"));
      //  contentModel.setAddBreadCrumb(addBreadCrumb);
    }

}
