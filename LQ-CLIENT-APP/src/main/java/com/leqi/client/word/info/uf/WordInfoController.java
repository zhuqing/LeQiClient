/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.info.uf;

import com.leqi.client.word.uf.*;
import com.leqi.client.book.word.uf.*;
import com.leqi.client.book.uf.BookModel;
import com.leqi.client.book.word.cf.SaveWordsCommand;
import com.leqienglish.client.control.form.LQFormView;
import static com.leqienglish.client.fw.cf.Command.DATA;
import static com.leqienglish.client.fw.cf.Command.ID;
import com.leqienglish.client.fw.sf.RestClient;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.wordpane.WordsPane;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.word.Word;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("WordInfoController")
public class WordInfoController extends  FXMLController<WordInfoModel> {
    
    @FXML
    private LQFormView<Word> wordInfoFormView;
    
    @Resource(name = "SaveWordsCommand")
    protected SaveWordsCommand saveWordsCommand;

    @Override
    public void refresh() {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(this.getModel().getWord()!=null){
            this.wordInfoFormView.setValue(this.getModel().getWord());
        }
        
        JavaFxObservable.changesOf(this.getModel().wordProperty())
                .subscribe((c)->this.wordInfoFormView.setValue(c.getNewVal()));
    }
    
   
}
