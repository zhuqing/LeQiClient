/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.uf;

import com.leqi.client.book.segment.cf.UpdateContentStatusCommand;
import com.leqi.client.book.segment.info.cf.QueryContentsCommand;
import com.leqi.client.common.cf.DownLoadFileCommand;
import com.leqienglish.client.control.button.LQButton;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.uf.FXMLController;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentController")
public class SegmentController extends FXMLController<SegmentModel> {

    @FXML
    private LQTableView<Content> segmentsTableView;

    @Resource(name = "QueryContentsCommand")
    private QueryContentsCommand queryContentsCommand;

    @Resource(name = "UpdateContentStatusCommand")
    private UpdateContentStatusCommand updateContentStatusCommand;

    @Resource(name = "DownLoadFileCommand")
    private DownLoadFileCommand downLoadFileCommand;

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().getContents())
                .subscribe((lc) -> segmentsTableView.getItems().setAll(this.getModel().getContents()));

        JavaFxObservable.changesOf(segmentsTableView.getSelectionModel().selectedItemProperty())
                .subscribe((b) -> segmentsTableViewSelectedChange(b.getNewVal()));

        JavaFxObservable.nullableValuesOf(this.getModel().articleProperty())
                .subscribe(op -> articleChange(op.orElse(null)));
        articleChange(getModel().getArticle());
    }

    private void articleChange(Content content) {
        if (content == null) {
            this.getModel().getContents().clear();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", content.getId());
        queryContentsCommand.doCommand(map);
        map.put(DownLoadFileCommand.FILE_PATH, content.getAudioPath());
        downLoadFileCommand.doCommand(map);
    }

    private void segmentsTableViewSelectedChange(Content content) {
        if (content == null) {
            return;
        }
        // queryArticle(content.getId(), 1, 20);
    }

    /**
     * 发布按钮点击事件
     *
     * @param event
     */
    @FXML
    public void lunch(ActionEvent event) {
        callUpdateContent(event, Content.LUNCH);
    }

    /**
     * 取消发布按钮点击事件
     *
     * @param event
     */
    @FXML
    public void cancelLunch(ActionEvent event) {
        callUpdateContent(event, Content.CANCEL_LUNCH);
    }

    private void callUpdateContent(ActionEvent event, int status) {
        LQButton button = (LQButton) event.getSource();
        Content content = (Content) button.getUserData();
        Map<String, Object> param = new HashMap<>();
        param.put(Command.ID, content.getId());
        param.put(Command.DATA, status + "");
        updateContentStatusCommand.doCommand(param);
    }
}
