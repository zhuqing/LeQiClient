/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.check.uf;

import com.leqi.client.book.segment.uf.*;
import com.leqi.client.book.segment.cf.UpdateContentStatusCommand;
import com.leqi.client.book.segment.cf.QuerySegmentsCommand;
import com.leqi.client.book.segment.cf.UpdateSegmentStatusCommand;
import com.leqi.client.book.segment.info.cf.SaveSegmentCommand;
import com.leqi.client.book.segment.info.uf.SegmentInfoModel;
import com.leqi.client.book.segment.word.uf.SegmentWordsModel;
import com.leqi.client.book.uf.BookModel;
import com.leqi.client.common.cf.DownLoadFileCommand;
import com.leqienglish.client.control.button.LQButton;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.timestemp.TimeStemp;
import com.leqienglish.client.control.timestemp.check.TimeStempCheck;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.control.view.table.row.LQTableRow;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.dialog.LQDialog;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.file.FileUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentCheckController")
public class SegmentCheckController extends FXMLController<SegmentCheckModel> {

    @FXML
    private TimeStempCheck timeStempCheck;

    @Resource(name = "DownLoadFileCommand")
    private DownLoadFileCommand downLoadFileCommand;

    @Resource(name = "SegmentWordsModel")
    private SegmentWordsModel segmentWordsModel;

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        JavaFxObservable.changesOf(this.getModel().segmentProperty())
                .map(c -> c.getNewVal())
                .subscribe(c -> segmentChange(c));

        JavaFxObservable.nullableValuesOf(this.getModel().articleProperty())
                .subscribe(op -> articleChange(op.orElse(null)));
        articleChange(getModel().getArticle());

        this.timeStempCheck.setSentenceConsumer((a) -> {
            segmentWordsModel.setSegment(this.getModel().getSegment());
            LQDialog.openDialog("", segmentWordsModel, timeStempCheck, ButtonType.CLOSE);
        });
        segmentChange(this.getModel().getSegment());

    }

    private void segmentChange(Segment segment) {
        if (segment == null) {
            return;
        }
        try {
            Content article = this.getModel().getArticle();
            String filePath = FileUtil.toLocalFilePath(article.getAudioPath());
            File file = new File(filePath);
            this.timeStempCheck.setAudioPath(file.toURI().toString());
            this.timeStempCheck.setCheckText(segment.getContent());

        } catch (Exception ex) {
            Logger.getLogger(SegmentCheckController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void articleChange(Content content) {
        if (content == null) {

            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put(DownLoadFileCommand.FILE_PATH, content.getAudioPath());
        downLoadFileCommand.doCommand(map);
    }

    @FXML
    public void save(ActionEvent event) {

        Map<String, Object> map = new HashMap<>();
        map.put(Command.DATA, this.getModel().getSegment());
        this.timeStempCheck.setPlaying(Boolean.FALSE);
        //saveContentCommand.doCommand(map);
    }

}