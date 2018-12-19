/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.user.manager.uf;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.user.User;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("UserManagerModel")
public class UserManagerModel extends FXMLModel {


    private ObjectProperty<User> user;
    private ObservableList<User> userlist;

    public UserManagerModel() {
        setFxmlPath("/com/leqi/client/user/manager/uf/UserManager.fxml");
    }

    @Override
    @Resource(name = "UserManagerController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }




    public User getUser() {
        return this.userProperty().getValue();
    }


    public ObjectProperty<User> userProperty() {
        if(user == null){
            user = new SimpleObjectProperty<>();
        }
        return user;
    }

    public void setUser(User user) {
        this.userProperty().setValue(user);
    }

    public ObservableList<User> getUserlist() {

        if(this.userlist == null){
            this.userlist = FXCollections.observableArrayList();
        }
        return userlist;
    }



    public void setUserlist(List<User> userlist) {
        this.getUserlist().setAll(userlist) ;
    }
}
