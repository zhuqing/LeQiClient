package com.leqi.client.word.shortwordAndWord.uf;

import com.leqi.client.book.sentence.cf.QuerySentenceCommand;
import com.leqi.client.word.cf.LQQueryWordsCommand;
import com.leqi.client.word.shortword.sentence.cf.DeleteShortWordAndSentenceCommand;
import com.leqi.client.word.shortword.sentence.cf.QueryShortWordSentenceCommand;
import com.leqi.client.word.shortword.sentence.cf.SaveShortWordAndSentenceCommand;
import com.leqi.client.word.shortwordAndWord.cf.DeleteShortWordAndWordsCommand;
import com.leqi.client.word.shortwordAndWord.cf.QueryShortWordAndWordsCommand;
import com.leqi.client.word.shortwordAndWord.cf.SaveShortWordAndWordsCommand;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.client.util.event.EventUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.sentence.Sentence;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.entity.english.shortword.ShortWordAndSentenceVO;
import xyz.tobebetter.entity.english.shortword.ShortWordAndWord;
import xyz.tobebetter.entity.english.shortword.ShortWordAndWordVO;
import xyz.tobebetter.entity.word.Word;

import javax.annotation.Resource;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static com.leqienglish.client.fw.cf.Command.CONSUMER;
import static com.leqienglish.client.fw.cf.Command.DATA;

/**
 * Created by zhuleqi on 2018/11/17.
 */
@Lazy
@Component("ShortWordAndWordController")
public class ShortWordAndWordController  extends FXMLController<ShortWordAndWordModel> {

    @FXML
    private TextField filter;

    @FXML
    private LQTableView<Word> wordsTableView;

    @FXML
    private LQFormView<ShortWord> shortWordLQFormView;

    @FXML
    private LQTableView<ShortWordAndWordVO> shortWordAndWordTableView;



    @Resource(name = "LQQueryWordsCommand")
    protected LQQueryWordsCommand lqQueryWordsCommand;

    @Resource(name = "SaveShortWordAndWordsCommand")
    protected SaveShortWordAndWordsCommand saveShortWordAndWordsCommand;


    @Resource(name = "DeleteShortWordAndWordsCommand")
    protected DeleteShortWordAndWordsCommand deleteShortWordAndWordsCommand;



    @Resource(name = "QueryShortWordAndWordsCommand")
    protected QueryShortWordAndWordsCommand queryShortWordAndWordsCommand;

    @Override
    public void refresh() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().currentDataProperty())
                .subscribe((c)-> this.currentDataChange(getModel().getCurrentData()));
        JavaFxObservable.changesOf(this.getModel().getWords())
                .subscribe((c)->this.wordsTableView.getItems().setAll(this.getModel().getWords()));
        JavaFxObservable.changesOf(this.getModel().getShortWordAndWordVOS())
                .subscribe((c)->this.shortWordAndWordTableView.getItems().setAll(this.getModel().getShortWordAndWordVOS()));

        if(this.getModel().getCurrentData()!=null){
            this.currentDataChange(getModel().getCurrentData());
        }
    }

    private void currentDataChange(ShortWord shortWord){
        this.shortWordLQFormView.setValue(shortWord);
        Map<String,Object> param = new HashMap<>();
        param.put(DATA,shortWord);
        param.put(CONSUMER, new Consumer<List<ShortWordAndWordVO>>() {
            @Override
            public void accept(List<ShortWordAndWordVO> shortWordAndWordVOS) {
                getModel().getShortWordAndWordVOS().setAll(shortWordAndWordVOS);
            }
        });
        this.queryShortWordAndWordsCommand.doCommand(param);
    }

    @FXML
    public void query(ActionEvent event){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(DATA, this.filter.getText());

        map.put(CONSUMER, new Consumer<Word[]>() {
            @Override
            public void accept(Word[] words) {
                getModel().getWords().setAll(words);
            }
        });

        this.lqQueryWordsCommand.doCommand(map);
    }



    @FXML
    public void add2ShortWord(ActionEvent event){
        Word  word = EventUtil.getEntityFromButton(event);
        ShortWordAndWordVO shortWordAndWord = new ShortWordAndWordVO();
        shortWordAndWord.setWordId(word.getId());
        shortWordAndWord.setShortWordId(this.getModel().getCurrentData().getId());
        shortWordAndWord.setWord(word.getWord());
        Map<String,Object> map = new HashMap<>();
        map.put(DATA,shortWordAndWord);

       map.put(CONSUMER, new Consumer<ShortWordAndWord>() {
           @Override
           public void accept(ShortWordAndWord shortWordAndWord) {
                ShortWordAndWordVO shortWordAndWordVO = new ShortWordAndWordVO();
                shortWordAndWordVO.setWordId(shortWordAndWord.getWordId());
                shortWordAndWordVO.setShortWordId(shortWordAndWord.getShortWordId());
                shortWordAndWordVO.setWord(word.getWord());
               getModel().getShortWordAndWordVOS().add(shortWordAndWordVO);
           }
       });

        this.saveShortWordAndWordsCommand.doCommand(map);
    }
    @FXML
    public void deleteShortWordAndWord(ActionEvent event){
        if(!AlertUtil.couldDo("是否删除？")){
            return;
        }
        ShortWordAndWordVO shortWordAndWordVO = EventUtil.getEntityFromButton(event);

        Map<String,Object> map = new HashMap<>();
        map.put(DATA,shortWordAndWordVO);

        map.put(CONSUMER, new Consumer<ShortWordAndWord>() {
            @Override
            public void accept(ShortWordAndWord shortWordAndWord) {
                getModel().getShortWordAndWordVOS().remove(shortWordAndWord);
            }
        });

        this.deleteShortWordAndWordsCommand.doCommand(map);
    }
}
