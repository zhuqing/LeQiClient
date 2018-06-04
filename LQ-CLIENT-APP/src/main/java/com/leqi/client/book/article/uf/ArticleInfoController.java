/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.uf;

import com.leqi.client.book.article.cf.SaveArticleCommand;
import com.leqi.client.book.info.cf.SavaCatalogCommand;
import com.leqienglish.client.control.form.LQFormView;
import static com.leqienglish.client.fw.cf.Command.DATA;
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

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("ArticleInfoController")
public class ArticleInfoController extends FXMLController<ArticleInfoModel> {

    @FXML
    private LQFormView contentInfoFormView;



    @Resource(name = "SaveArticleCommand")
    private SaveArticleCommand saveArticleCommand;

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //对当前编辑的Catalog加监听
        JavaFxObservable.nullableValuesOf(this.getModel().contentProperty())
                .map((p) -> p.orElse(null))
                .subscribe((c) -> this.contentInfoFormView.setValue(c));
      
    }

    @FXML
    public void save(ActionEvent event) {
        Map<String,Object> param = new HashMap<>();
        param.put(DATA, this.getModel().getContent());
        saveArticleCommand.doCommand(param);
    }

}
