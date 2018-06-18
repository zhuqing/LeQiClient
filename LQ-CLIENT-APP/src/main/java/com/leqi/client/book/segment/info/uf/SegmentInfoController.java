/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.info.uf;

import com.leqi.client.book.segment.info.cf.SaveContentCommand;
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

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentInfoController")
public class SegmentInfoController extends FXMLController<SegmentInfoModel> {

    @FXML
    private LQFormView<Content> contentInfoFormView;
    @FXML
    private LQOpenFileFormCell audioPathFormCell;
    @FXML
    private LQTextAreaInputFormCell contentFormCell;
    @FXML
    private TimeStemp timeStemp;
    @FXML
    private CheckBox isSupportChinease;

    @Resource(name = "SaveContentCommand")
    private SaveContentCommand saveContentCommand;

    @Override
    public void refresh() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.nullableValuesOf(this.getModel().contentProperty())
                .subscribe((p) -> contentInfoChange(p.orElse(null)));

        JavaFxObservable.changesOf(this.isSupportChinease.selectedProperty())
                .subscribe((c) -> timeStemp.setSuportChineaseChinease(c.getNewVal()));

        //  audioPathFormCell.setCommitConsumer((audioPath)->timeStemp.setAudioPath(audioPath));
        //  contentFormCell.setCommitConsumer((content)->timeStemp.setSourceText(content+""));
    }

    private void contentInfoChange(Content content) throws Exception {

        contentInfoFormView.setValue(content);
        if (content == null) {
            timeStemp.setAudioPath(null);
            return;
        }
        timeStemp.setAudioPath(FileUtil.toLocalFilePath(content.getAudioPath()));
    }

    @FXML
    public void save(ActionEvent event) {
        this.getModel().getContent().setTimePoint(timeStemp.getTargetText());
        Map<String, Object> map = new HashMap<>();
        map.put(Command.DATA, this.getModel().getContent());
        saveContentCommand.doCommand(map);
    }
}
