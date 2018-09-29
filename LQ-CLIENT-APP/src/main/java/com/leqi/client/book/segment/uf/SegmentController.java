/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.uf;

import com.leqi.client.book.content.uf.ContentModel;
import com.leqi.client.book.segment.cf.DeleteSegmentCommand;
import com.leqi.client.book.segment.cf.UpdateContentStatusCommand;
import com.leqi.client.book.segment.cf.QuerySegmentsCommand;
import com.leqi.client.book.segment.cf.UpdateSegmentStatusCommand;
import com.leqi.client.book.segment.check.uf.SegmentCheckModel;
import com.leqi.client.book.segment.cf.SaveSegmentCommand;
import com.leqi.client.book.segment.info.uf.SegmentInfoModel;
import com.leqi.client.book.uf.BookModel;
import com.leqienglish.client.comman.cf.DownLoadFileCommand;

import com.leqienglish.client.control.button.LQButton;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.timestemp.TimeStemp;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.control.view.table.row.LQTableRow;
import com.leqienglish.client.fw.cf.Command;
import static com.leqienglish.client.fw.cf.Command.ID;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
@Component("SegmentController")
public class SegmentController extends FXMLController<SegmentModel> {

    @FXML
    private LQTableView<Segment> segmentsTableView;

    @FXML
    private LQFormView<Segment> segmentFormView;
    @FXML
    private CheckBox isSupportChinease;
    
    @Resource(name = "QuerySegmentsCommand")
    private QuerySegmentsCommand querySegmentsCommand;

    @Resource(name = "DownLoadFileCommand")
    private DownLoadFileCommand downLoadFileCommand;

    @Resource(name = "UpdateSegmentStatusCommand")
    private UpdateSegmentStatusCommand updateSegmentStatusCommand;

    @FXML
    private TimeStemp timeStemp;

    @Resource(name = "SaveSegmentCommand")
    private SaveSegmentCommand saveSegmentCommand;

    @Resource(name = "DeleteSegmentCommand")
    private DeleteSegmentCommand deleteSegmentCommand;

    @Resource(name = "ContentModel")
    private ContentModel contentModel;

    @Resource(name = "SegmentCheckModel")
    private SegmentCheckModel segmentCheckModel;

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().getSegments())
                .subscribe((lc) -> segmentsTableView.getItems().setAll(this.getModel().getSegments()));

        JavaFxObservable.changesOf(segmentsTableView.getSelectionModel().selectedItemProperty())
                .subscribe((b) -> this.getModel().setEditingSegment(b.getNewVal()));

        JavaFxObservable.changesOf(this.getModel().editingSegmentProperty())
                .map(c -> c.getNewVal())
                .subscribe(c -> editingSegmentChange(c));

        JavaFxObservable.nullableValuesOf(this.getModel().articleProperty())
                .subscribe(op -> articleChange(op.orElse(null)));
        articleChange(getModel().getArticle());
        
        isSupportChinease.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                timeStemp.setSuportChineaseChinease(t1);
            }
        });

        segmentsTableView.setRowMouseEventHandler((MouseEvent event) -> {
            if (event.getClickCount() != 2) {
                return;
            }

            LQTableRow row = (LQTableRow) event.getSource();

            if (row.getItem() == null) {
                return;
            }
            getModel().setEditingSegment((Segment) row.getItem());
        });

    }

    private void editingSegmentChange(Segment segment) {
        try {
            Content article = this.getModel().getArticle();
            String filePath = FileUtil.getInstence().toLocalFilePath(article.getAudioPath());
            File file = new File(filePath);
            timeStemp.setAudioPath(file.toURI().toString());
            timeStemp.setSourceText(segment.getContent());
            this.segmentFormView.setValue(segment);
        } catch (Exception ex) {
            Logger.getLogger(SegmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void articleChange(Content content) {
        if (content == null) {
            this.getModel().getSegments().clear();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("contentId", content.getId());
        querySegmentsCommand.doCommand(map);
        map.put(DownLoadFileCommand.FILE_PATH, content.getAudioPath());
        downLoadFileCommand.doCommand(map);
    }

    /**
     * 发布按钮点击事件
     *
     * @param event
     */
    @FXML
    public void lunch(ActionEvent event) {
        callUpdateContent(event, Consistent.HAS_LAUNCHED);
    }

    @FXML
    public void createSegment(ActionEvent event) {
        Segment segment = this.createSegmentInfo(this.getModel().getArticle());

        if (segment == null) {
            return;
        }

        this.getModel().setEditingSegment(segment);
    }

    /**
     * 创建文章的片段
     *
     * @param artical
     * @return
     */
    private Segment createSegmentInfo(Content artical) {
        if (artical == null) {
            AlertUtil.showError("文章不能为null");
            return null;
        }
        Segment segment = new Segment();
        segment.setUserId("1");

        segment.setContentId(artical.getId());
        return segment;
    }

    @FXML
    public void refresh(ActionEvent event) {

    }

    @FXML
    public void deleteSegment(ActionEvent event) {
        if (!AlertUtil.couldDo(AlertUtil.IF_DELETE)) {
            return;
        }
        Button button = (Button) event.getSource();
        Segment segment = (Segment) button.getUserData();
        Map<String, Object> map = new HashMap<>();
        map.put(ID, segment.getId());
        deleteSegmentCommand.doCommand(map);
    }

    @FXML
    public void check(ActionEvent event) {
        Button button = (Button) event.getSource();
        Segment segment = (Segment) button.getUserData();
        segmentCheckModel.setSegment(segment);
        this.segmentCheckModel.setArticle(this.getModel().getArticle());
        contentModel.setAddBreadCrumb(SourceItemUtil.create(segment.getTitle(), "SegmentCheckModel"));
    }

    @FXML
    public void save(ActionEvent event) {
        this.getModel().getEditingSegment().setContent(timeStemp.getTargetText());
        Map<String, Object> map = new HashMap<>();
        map.put(Command.DATA, this.getModel().getEditingSegment());
        timeStemp.setPlaying(Boolean.FALSE);
        saveSegmentCommand.doCommand(map);
    }

    /**
     * 取消发布按钮点击事件
     *
     * @param event
     */
    @FXML
    public void cancelLunch(ActionEvent event) {
        callUpdateContent(event, Consistent.UN_LAUNCH);
    }

    private void callUpdateContent(ActionEvent event, int status) {
        LQButton button = (LQButton) event.getSource();
        Segment content = (Segment) button.getUserData();
        Map<String, Object> param = new HashMap<>();
        param.put(Command.ID, content.getId());
        param.put(Command.DATA, status);
        param.put(Command.MODEL, this.getModel());
        updateSegmentStatusCommand.doCommand(param);
    }
}
