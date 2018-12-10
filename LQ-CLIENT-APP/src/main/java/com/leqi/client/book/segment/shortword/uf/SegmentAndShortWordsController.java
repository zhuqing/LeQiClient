/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.shortword.uf;

import com.leqi.client.book.segment.shortword.cf.DeleteSegmentAndShortWordCommand;
import com.leqi.client.book.segment.shortword.cf.QuerySegmentAndShortWordCommand;
import com.leqi.client.book.segment.shortword.cf.SaveSegmentAndShortWordCommand;
import com.leqi.client.book.segment.word.cf.DeleteSegmentWrodsCommand;
import com.leqi.client.book.segment.word.cf.QuerySegmentWrodsCommand;
import com.leqi.client.book.segment.word.cf.SaveWordAndSegmentsCommand;
import com.leqi.client.word.shortword.cf.QueryShortWordsCommand;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.client.util.event.EventUtil;
import com.leqienglish.client.wordpane.WordsPane;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;
import xyz.tobebetter.entity.english.play.AudioPlayPoint;
import xyz.tobebetter.entity.english.segment.WordAndSegment;
import xyz.tobebetter.entity.english.shortword.SegmentAndShortWord;
import xyz.tobebetter.entity.english.shortword.ShortWord;

import javax.annotation.Resource;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static com.leqienglish.client.fw.cf.Command.CONSUMER;
import static com.leqienglish.client.fw.cf.Command.DATA;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentAndShortWordsController")
public class SegmentAndShortWordsController extends FXMLController<SegmentAndShortWordsModel> {

    @FXML
    private LQTableView<ShortWord> shortWordsTableView;

    @FXML
    private TextField filter;

    @FXML
    private LQTableView<SegmentAndShortWord> shortWordSegmentsTableView;

    @FXML
    private LQFormView<AudioPlayPoint> audioPlayerPointFormView;


    @Resource(name = "DeleteSegmentAndShortWordCommand")
    private DeleteSegmentAndShortWordCommand deleteSegmentAndShortWordCommand;

    @Resource(name = "QuerySegmentAndShortWordCommand")
    private QuerySegmentAndShortWordCommand querySegmentAndShortWordCommand;

    @Resource(name = "SaveSegmentAndShortWordCommand")
    private SaveSegmentAndShortWordCommand saveSegmentAndShortWordCommand;
    @Resource(name = "QueryShortWordsCommand")
    private QueryShortWordsCommand queryShortWordsCommand;




    @Override
    public void refresh() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.nullableValuesOf(this.getModel().segmentProperty())
                .subscribe((p) -> segmentInfoChange(p.orElse(null)));

        JavaFxObservable.nullableValuesOf(this.getModel().audioPlayPointProperty())
                .subscribe(c -> audioPlayPointChange(c.orElse(null)));

        JavaFxObservable.changesOf(this.getModel().getSegmentAndShortWords())
                .subscribe(c -> shortWordSegmentsTableView.getItems().setAll(this.getModel().getSegmentAndShortWords()));


        JavaFxObservable.changesOf(this.getModel().getShortWords())
                .subscribe(c -> this.shortWordsTableView.getItems().setAll(this.getModel().getShortWords()));


    }

    private void audioPlayPointChange(AudioPlayPoint app) {
      this.audioPlayerPointFormView.setValue(app);
      this.segmentInfoChange(this.getModel().getSegment());
    }

    private void segmentInfoChange(Segment segment){
        shortWordSegmentsTableView.getItems().clear();
        if (segment == null) {
            this.getModel().getSegmentAndShortWords().clear();
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put(DATA, segment.getId());
       querySegmentAndShortWordCommand.doCommand(map);

    }

    @FXML
    public void add2Segments(ActionEvent event) {
       ShortWord shortWord = EventUtil.getEntityFromButton(event);
       AudioPlayPoint audioPlayPoint = this.getModel().getAudioPlayPoint();
       Segment segment = this.getModel().getSegment();
        Content content = this.getModel().getArticle();

       SegmentAndShortWord segmentAndShortWord = new SegmentAndShortWord();

        segmentAndShortWord.setWordId(shortWord.getId());

       segmentAndShortWord.setScentence(audioPlayPoint.getEnText());
       segmentAndShortWord.setChSentence(audioPlayPoint.getChText());
       segmentAndShortWord.setSegmentId(segment.getId());
       segmentAndShortWord.setContentId(segment.getContentId());
       segmentAndShortWord.setAudioPath(segment.getAudioPath());
       segmentAndShortWord.setContentTitle(content.getTitle());

       segmentAndShortWord.setWord(shortWord.getWord());
       segmentAndShortWord.setScentenceIndex(audioPlayPoint.getIndex());
       segmentAndShortWord.setStartTime(audioPlayPoint.getStartTime());
       segmentAndShortWord.setEndTime(audioPlayPoint.getEndTime());


        Map<String, Object> map = new HashMap<>();
        map.put(DATA, segmentAndShortWord);
        this.saveSegmentAndShortWordCommand.doCommand(map);
    }


    @FXML
    public void query(ActionEvent event) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(DATA, this.filter.getText());
        map.put(CONSUMER, new Consumer<ShortWord[]>() {
            @Override
            public void accept(ShortWord[] shortWords) {
                getModel().getShortWords().setAll(shortWords);
            }
        });
        this.queryShortWordsCommand.doCommand(map);
    }
    @FXML
    public void deleteSegmentAndShortWord(ActionEvent event) {
        if (!AlertUtil.couldDo(AlertUtil.IF_DELETE)) {
            return;
        }
        SegmentAndShortWord segmentAndShortWord = EventUtil.getEntityFromButton(event);
        Map<String, Object> map = new HashMap<>();
        map.put(DATA, segmentAndShortWord);
        this.deleteSegmentAndShortWordCommand.doCommand(map);
    }
}
