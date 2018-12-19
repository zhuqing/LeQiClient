/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.user.manager.uf;

import com.leqi.client.book.article.cf.QueryArticlesCommand;
import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.cf.QueryCatalogCommand;
import com.leqi.client.book.info.uf.BookInfoModel;
import com.leqi.client.book.segment.info.uf.SegmentInfoModel;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqi.client.user.manager.cf.QueryUsersCommand;
import com.leqi.client.user.uf.UserModel;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.view.listview.LQListView;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.user.User;

import javax.annotation.Resource;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.leqienglish.client.fw.cf.Command.DATA;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("UserManagerController")
public class UserManagerController extends FXMLController<UserManagerModel> {

    @FXML
    private TextField filter;

    @FXML
    private LQTableView<User> usersTableView;

    @FXML
    private LQFormView<User> userInfoFormView;






    @Resource(name = "QueryUsersCommand")
    private QueryUsersCommand queryUsersCommand;


    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        JavaFxObservable.changesOf(this.getModel().getUserlist())
                .subscribe((c)->usersTableView.getItems().setAll(this.getModel().getUserlist()));
        JavaFxObservable.changesOf(this.getModel().userProperty())
                .subscribe((c)->this.userInfoFormView.setValue(this.getModel().getUser()));
    }





    @FXML
    public void query(ActionEvent event) {
        String fileter = this.filter.getText();
        Map<String, Object> param = new HashMap<>();
        param.put(DATA, fileter);
        this.queryUsersCommand.doCommand(param);
    }

    @FXML
    public void deleteUser(ActionEvent event) {
        if(!AlertUtil.couldDo("是否删除！！！")){
            return;
        }
    }



    private Content createContent(Content catalog) {
        Content content = new Content();
        content.setParentId(catalog.getId());
        content.setUserId("1");
        return content;
    }

    private Content createArticle(String bookId) {
        Content catalog = new Content();
        catalog.setCatalogId(bookId);

        catalog.setUserId("1");
        return catalog;
    }

}
