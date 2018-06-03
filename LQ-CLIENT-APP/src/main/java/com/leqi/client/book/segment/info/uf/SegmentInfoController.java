/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.info.uf;

import com.leqi.client.book.segment.info.cf.SaveContentCommand;
import com.leqi.client.book.segment.uf.*;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.timestemp.TimeStemp;
import com.leqienglish.client.control.view.gridview.LQGridView;
import com.leqienglish.client.fw.uf.FXMLController;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
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
@Component("SegmentInfoController")
public class SegmentInfoController extends FXMLController<SegmentInfoModel> {

    @FXML
    private LQFormView<Content> contentInfoFormView;

    @FXML
    private TimeStemp timeStemp;

    @Resource(name = "SaveContentCommand")
    private SaveContentCommand saveContentCommand;

    @Override
    public void refresh() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.nullableValuesOf(this.getModel().contentProperty())
                .subscribe((p) -> contentInfoChange(p.orElse(null)));
    }

    private void contentInfoChange(Content content) {
        contentInfoFormView.setValue(content);
    }

    @FXML
    public void save(ActionEvent event) {
        this.getModel().getContent().setTimePoint(timeStemp.getTargetText());
        saveContentCommand.doCommand(null);
    }
}
