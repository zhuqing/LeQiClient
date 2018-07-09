/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.word.uf;

import com.leqi.client.book.uf.BookModel;
import com.leqi.client.book.word.cf.DeleteWordAndContentCommand;
import com.leqi.client.book.word.cf.QueryWordsCommand;
import com.leqi.client.book.word.cf.SaveWordAndContentCommand;
import com.leqi.client.book.word.cf.SaveWordsCommand;
import com.leqi.client.word.info.cf.UpdateWordCommand;
import com.leqi.client.word.info.uf.WordInfoModel;
import com.leqienglish.client.control.view.table.LQTableView;
import static com.leqienglish.client.fw.cf.Command.DATA;
import static com.leqienglish.client.fw.cf.Command.ID;
import com.leqienglish.client.fw.dialog.LQDialog;
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Word;
import xyz.tobebetter.entity.english.word.WordMean;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("WordController")
public class WordController extends FXMLController<WordModel> {

    @FXML
    private WordsPane wordsPane;

    @FXML
    private LQTableView<Word> wordTableView;

    @Resource(name = "WordInfoModel")
    protected WordInfoModel wordInfoModel;

    @Resource(name = "SaveWordAndContentCommand")
    protected SaveWordAndContentCommand saveWordAndContentCommand;

    @Resource(name = "QueryWordsCommand")
    protected QueryWordsCommand queryWordsCommand;

    @Resource(name = "UpdateWordCommand")
    protected UpdateWordCommand updateWordCommand;
    
    @Resource(name = "DeleteWordAndContentCommand")
    protected DeleteWordAndContentCommand deleteWordAndContentCommand;
    

    @Override
    public void refresh() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().articleProperty())
                .subscribe(c -> queryWords(c.getNewVal()));

        JavaFxObservable.changesOf(this.getModel().getWords())
                .subscribe(c -> this.wordTableView.getItems().setAll(this.getModel().getWords()));

        queryWords(this.getModel().getArticle());
    }

    private void queryWords(Content article) {
        if (article == null) {
            this.wordTableView.getItems().clear();
            return;
        }
        Map<String, Object> param = new HashMap<>();
        param.put(ID, article.getId());
        queryWordsCommand.doCommand(param);
    }

    @FXML
    public void wordEdit(ActionEvent event) {
        Button button = (Button) event.getSource();
        Word word = (Word) button.getUserData();
        this.wordInfoModel.setWord(word);
        System.err.print(WordMean.toWordMeans(word.getMeans(), word));
        ButtonType buttonType = LQDialog.openDialog(word.getWord(), wordInfoModel, button, ButtonType.OK, ButtonType.CANCEL);
        if (ButtonType.OK == buttonType) {
            Map<String, Object> param = new HashMap<>();
            param.put(DATA, word);
            updateWordCommand.doCommand(param);
        }
    }

    @FXML
    public void showWord(ActionEvent event) {
        Button button = (Button) event.getSource();
        Word word = (Word) button.getUserData();
        this.wordInfoModel.setWord(word);
        System.err.print(WordMean.toWordMeans(word.getMeans(), word));
        LQDialog.openDialog(word.getWord(), wordInfoModel, button, ButtonType.CLOSE);
    }

    @FXML
    public void addWords(ActionEvent event) {

    }
    
     @FXML
    public void delete(ActionEvent event) {
        Button button = (Button) event.getSource();
        Word word = (Word) button.getUserData();
         Map<String, Object> param = new HashMap<>();
        param.put(DATA, word);
        deleteWordAndContentCommand.doCommand(param);
    }

    @FXML
    public void refresh(ActionEvent event) {
        this.queryWords(this.getModel().getArticle());
    }

    @FXML
    public void save(ActionEvent event) {
        List<Word> words = this.wordsPane.getWords();
        Content article = this.getModel().getArticle();
        Map<String, Object> param = new HashMap<>();
        param.put(DATA, words);
        param.put(ID, article.getId());
        saveWordAndContentCommand.doCommand(param);
    }
}
