/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.info.uf;

import com.leqi.client.book.segment.info.cf.SaveSegmentCommand;
import com.leqi.client.book.segment.uf.*;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.form.cell.edit.file.LQOpenFileFormCell;
import com.leqienglish.client.control.form.cell.edit.text.LQTextAreaInputFormCell;
import com.leqienglish.client.control.form.cell.unedit.LQTitleFormCell;
import com.leqienglish.client.control.timestemp.TimeStemp;
import com.leqienglish.client.control.view.gridview.LQGridView;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.util.file.FileUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentInfoController")
public class SegmentInfoController extends FXMLController<SegmentInfoModel> {

    @FXML
    private LQFormView<Segment> contentInfoFormView;
    @FXML
    private LQOpenFileFormCell audioPathFormCell;
    @FXML
    private LQTextAreaInputFormCell contentFormCell;
    @FXML
    private TimeStemp timeStemp;
    @FXML
    private CheckBox isSupportChinease;

    @Resource(name = "SaveContentCommand")
    private SaveSegmentCommand saveContentCommand;

    @Override
    public void refresh() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.nullableValuesOf(this.getModel().segmentProperty())
                .subscribe((p) -> segmentInfoChange(p.orElse(null)));

        JavaFxObservable.nullableValuesOf(this.getModel().articleProperty())
                .subscribe((p) -> aritcleChange(p.orElse(null)));

        JavaFxObservable.changesOf(this.isSupportChinease.selectedProperty())
                .subscribe((c) -> timeStemp.setSuportChineaseChinease(c.getNewVal()));

        //  audioPathFormCell.setCommitConsumer((audioPath)->timeStemp.setAudioPath(audioPath));
        //  contentFormCell.setCommitConsumer((content)->timeStemp.setSourceText(content+""));
    }

    private void aritcleChange(Content content) throws Exception {
        if (content == null) {
            timeStemp.setAudioPath(null);
            return;
        }
        timeStemp.setAudioPath(FileUtil.toLocalFilePath(content.getAudioPath()));
    }

    private void segmentInfoChange(Segment content) throws Exception {
        contentInfoFormView.setValue(content);
    }

    @FXML
    public void save(ActionEvent event) {
        this.getModel().getSegment().setContent(timeStemp.getTargetText());
        Map<String, Object> map = new HashMap<>();
        map.put(Command.DATA, this.getModel().getSegment());
        timeStemp.setPlaying(Boolean.FALSE);
        saveContentCommand.doCommand(map);
    }
}
