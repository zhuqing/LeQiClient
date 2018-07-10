/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.word.uf;

import com.leqi.client.book.segment.info.uf.*;
import com.leqi.client.book.segment.cf.SaveSegmentCommand;
import com.leqi.client.book.segment.uf.*;
import com.leqi.client.book.segment.word.cf.DeleteSegmentWrodsCommand;
import com.leqi.client.book.segment.word.cf.QuerySegmentWrodsCommand;
import com.leqi.client.book.segment.word.cf.SaveWordAndSegmentsCommand;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.form.cell.edit.file.LQOpenFileFormCell;
import com.leqienglish.client.control.form.cell.edit.text.LQTextAreaInputFormCell;
import com.leqienglish.client.control.form.cell.unedit.LQTitleFormCell;
import com.leqienglish.client.control.timestemp.TimeStemp;
import com.leqienglish.client.control.view.gridview.LQGridView;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.fw.cf.Command;
import static com.leqienglish.client.fw.cf.Command.DATA;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.wordpane.WordsPane;
import com.leqienglish.util.file.FileUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;
import xyz.tobebetter.entity.english.play.AudioPlayPoint;
import xyz.tobebetter.entity.english.segment.WordAndSegment;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentWordsController")
public class SegmentWordsController extends FXMLController<SegmentWordsModel> {

    @FXML
    private LQTableView<WordAndSegment> wordAndSegmentsTableView;

    @FXML
    private WordsPane wordsPane;

    @Resource(name = "SaveWordAndSegmentsCommand")
    private SaveWordAndSegmentsCommand saveWordAndSegmentsCommand;

    @Resource(name = "QuerySegmentWrodsCommand")
    private QuerySegmentWrodsCommand querySegmentWrodsCommand;
    
       @Resource(name = "DeleteSegmentWrodsCommand")
    private DeleteSegmentWrodsCommand deleteSegmentWrodsCommand;

    @Override
    public void refresh() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.nullableValuesOf(this.getModel().segmentProperty())
                .subscribe((p) -> segmentInfoChange(p.orElse(null)));

        JavaFxObservable.nullableValuesOf(this.getModel().audioPlayPointProperty())
                .subscribe(c -> audioPlayPointChange(c.orElse(null)));

        JavaFxObservable.changesOf(this.getModel().getWordAndSegments())
                .subscribe(c -> wordAndSegmentsTableView.getItems().setAll(this.getModel().getWordAndSegments()));

        JavaFxObservable.changesOf(this.wordsPane.getWords())
                .subscribe(c -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put(Command.DATA, this.wordsPane.getWords());
                    saveWordAndSegmentsCommand.doCommand(map);
                });

    }

    private void audioPlayPointChange(AudioPlayPoint app) {
        if (app == null) {
            this.wordsPane.setText("");
            return;
        }

        this.wordsPane.setText(app.getEnText());
    }

    private void segmentInfoChange(Segment segment) throws Exception {
        if (segment == null) {
            this.getModel().getWordAndSegments().clear();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put(Command.ID, segment.getId());
        querySegmentWrodsCommand.doCommand(map);

    }

    @FXML
    public void refresh(ActionEvent event) {
        Segment segment = this.getModel().getSegment();
        this.getModel().setSegment(null);
        this.getModel().setSegment(segment);
    }

    @FXML
    public void deleteWord(ActionEvent event) {
        Button button = (Button) event.getSource();
        
        WordAndSegment was = (WordAndSegment) button.getUserData();
        Map<String,Object> param = new HashMap<>();
        param.put(DATA, was);
        deleteSegmentWrodsCommand.doCommand(param);
    }
}
