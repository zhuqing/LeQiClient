/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.version.uf;

import com.leqi.client.version.cf.DeleteVersionCommand;
import com.leqi.client.version.cf.QueryVersionsCommand;
import com.leqi.client.version.cf.SaveVersionCommand;
import com.leqi.client.version.cf.UpdateVersionStatusCommand;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.fw.cf.Command;
import static com.leqienglish.client.fw.cf.Command.DATA;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.event.EventUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.version.Version;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("VersionController")
public class VersionController extends FXMLController<VersionModel> {

    @FXML
    private LQTableView<Version> versionTableView;

    @FXML
    private LQFormView<Version> versionFormView;

    @FXML
    private TextField filter;

    @Resource(name = "SaveVersionCommand")
    private SaveVersionCommand saveVersionCommand;

    @Resource(name = "QueryVersionsCommand")
    private QueryVersionsCommand queryVersionsCommand;

    @Resource(name = "DeleteVersionCommand")
    private DeleteVersionCommand deleteVersionCommand;

    @Resource(name = "UpdateVersionStatusCommand")
    private UpdateVersionStatusCommand updateVersionStatusCommand;

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().getVersionList())
                .subscribe((c) -> this.versionTableView.getItems().setAll(this.getModel().getVersionList()));

        JavaFxObservable.changesOf(this.getModel().editingProperty())
                .subscribe((c) -> this.versionFormView.setValue(c.getNewVal()));
        queryVersionsCommand.doCommand(null);
        JavaFxObservable.changesOf(this.versionTableView.getSelectionModel().selectedItemProperty())
                .subscribe((c) -> this.versionFormView.setValue(c.getNewVal()));

    }

    @FXML
    public void create(ActionEvent event) {
        Version version = new Version();
        this.getModel().setEditing(version);
    }

    @FXML
    public void query(ActionEvent event) {
        queryVersionsCommand.doCommand(null);

    }

    @FXML
    public void save(ActionEvent event) {
        Map<String, Object> param = new HashMap<>();
        param.put(DATA, this.versionFormView.getValue());
        saveVersionCommand.doCommand(param);
    }

    @FXML
    public void delete(ActionEvent event) {
        Version version = EventUtil.getEntityFromButton(event);
        Map<String, Object> param = new HashMap<>();
        param.put(DATA, version);
        deleteVersionCommand.doCommand(param);
    }

    @FXML
    public void cancelLunch(ActionEvent event) {
        Version content = EventUtil.getEntityFromButton(event);
        Map<String, Object> param = new HashMap<String, Object>();

        param.put(Command.DATA, Consistent.UN_LAUNCH);
        param.put(Command.ID, content.getId());

        updateVersionStatusCommand.doCommand(param);
    }

    @FXML
    public void lunch(ActionEvent event) {
        Version content = EventUtil.getEntityFromButton(event);
        Map<String, Object> param = new HashMap<String, Object>();

        param.put(Command.DATA, Consistent.HAS_LAUNCHED);
        param.put(Command.ID, content.getId());

        updateVersionStatusCommand.doCommand(param);
    }

}
