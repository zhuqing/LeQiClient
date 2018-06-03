/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.uf;

import com.leqi.client.book.segment.info.cf.QueryContentsCommand;
import com.leqienglish.client.control.view.gridview.LQGridView;
import com.leqienglish.client.fw.uf.FXMLController;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentController")
public class SegmentController extends FXMLController<SegmentModel> {

    @FXML
    private LQGridView<Content> contentsGridView;

    @Resource(name = "QueryContentsCommand")
    private QueryContentsCommand queryContentsCommand;

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.changesOf(this.getModel().getContents())
                .subscribe((lc) -> contentsGridView.getItems().setAll(this.getModel().getContents()));

        JavaFxObservable.changesOf(contentsGridView.getGridViewSelectionModel().selectedItemProperty())
                .subscribe((b) -> bookListViewSelectedChange(b.getNewVal()));

        JavaFxObservable.nullableValuesOf(this.getModel().articleProperty())
                .subscribe(op -> articleChange(op.orElse(null)));
        articleChange(getModel().getArticle());
    }

    private void articleChange(Catalog catalog) {
        if (catalog == null) {
            this.getModel().getContents().clear();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("catalogId", catalog.getId());
        queryContentsCommand.doCommand(map);
    }

    private void bookListViewSelectedChange(Content content) {
        if (content == null) {
            return;
        }
        // queryArticle(content.getId(), 1, 20);
    }

}
