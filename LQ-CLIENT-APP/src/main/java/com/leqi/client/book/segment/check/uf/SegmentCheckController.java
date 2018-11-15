/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.check.uf;

import com.leqi.client.book.content.uf.ContentModel;
import com.leqi.client.book.segment.shortword.uf.SegmentAndShortWordsModel;
import com.leqi.client.book.segment.word.uf.SegmentWordsModel;
import com.leqienglish.client.comman.cf.DownLoadFileCommand;

import com.leqienglish.client.control.audio.AudioPlay;
import com.leqienglish.client.control.timestemp.check.TimeStempCheck;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.dialog.LQDialog;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.sourceitem.SourceItemUtil;
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
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;
import xyz.tobebetter.entity.english.play.AudioPlayPoint;

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


    @Resource(name = "SegmentAndShortWordsModel")
    private SegmentAndShortWordsModel segmentAndShortWordsModel;

    @Resource(name = "BookRootModel")
    private ContentModel contentModel;

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

        this.timeStempCheck.setSentenceConsumer((button) -> {
            AudioPlayPoint a = (AudioPlayPoint) button.getUserData();
            Integer value = (Integer) button.getProperties().get("DATA");
            if(value == null){
                return;
            }

            if(value == 1){
                addWords(a);
            }else{
                this.addShortWords(a);
            }

        });
        segmentChange(this.getModel().getSegment());

    }

    private void addShortWords(AudioPlayPoint audioPlayPoint){

        segmentAndShortWordsModel.setArticle(this.getModel().getArticle());
        segmentAndShortWordsModel.setSegment(this.getModel().getSegment());
        segmentAndShortWordsModel.setAudioPlayPoint(audioPlayPoint);

        contentModel.setAddBreadCrumb(SourceItemUtil.create("Add Short Word","SegmentAndShortWordsModel"));
    }

    private void addWords(AudioPlayPoint a){
        segmentWordsModel.setSegment(this.getModel().getSegment());
        segmentWordsModel.setAudioPlayPoint(a);
        segmentWordsModel.setArticle(this.getModel().getArticle());
        LQDialog.openDialog("", segmentWordsModel, timeStempCheck, ButtonType.CLOSE);
    }

    private void segmentChange(Segment segment) {
        if (segment == null) {
            return;
        }
        try {
           if(segment.getAudioPath() == null){
               return;
           }
            String filePath = FileUtil.getInstence().toLocalFilePath(segment.getAudioPath());
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
        map.put(DownLoadFileCommand.FILE_PATH, this.getModel().getSegment().getAudioPath());
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
