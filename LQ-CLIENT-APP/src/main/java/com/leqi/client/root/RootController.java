/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.root;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import com.leqienglish.client.util.sourceitem.SourceItem;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.observers.JavaFxObserver;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author duyi
 */
@Lazy
@Component("rootController")
public class RootController extends FXMLController<RootModel> {

    @FXML
    private BorderPane rootPane;
    @FXML
    private StackPane businessPane;
    @FXML
    private StackPane menuPane;

    private String defaultBusinessId = "ContentModel";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxObservable.nullableValuesOf(this.getModel().currentBusinessModelProperty())
                .map((o) -> o.orElse(defaultBusinessId))
                .subscribe((businessModeId) -> openModel(businessModeId));
        
        this.menuPane.getChildren().setAll(createMenuBar());
       

    }
    
    private void businessModeChange(String businessId){
       FXMLModel mode =  this.getModel(businessId);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        try {
            menuBar.getMenus().setAll(createMenus());
        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
        }
        menuBar.setUseSystemMenuBar(true);
        return menuBar;
    }

    private List<Menu> createMenus() throws IOException {
        List<SourceItem> sourceItems = this.createMenuDatas();
        return createMenus(sourceItems);
    }

    private List<Menu> createMenus(List<SourceItem> subSourceItems) {
        List<Menu> menus = new ArrayList<>();
        for (SourceItem sourceItem : subSourceItems) {
           Menu menu = new Menu();
           menus.add(menu);
            menu.setText(sourceItem.getDisplay());
            if (sourceItem.getValue() != null) {
                menu.setId(sourceItem.getValue().toString());
            }
            menu.setUserData(sourceItem);
           
            menu.addEventHandler(MouseEvent.MOUSE_CLICKED, (event)
                    -> getModel().setCurrentBusinessModel(sourceItem.getValue() + "")
            );

            if (sourceItem.getChildren() != null) {
                menu.getItems().setAll(this.createMenuItems(sourceItem.getChildren()));
            }
        }

        return menus;
    }
    
    private List<MenuItem> createMenuItems(List<SourceItem> subSourceItems) {
         List<MenuItem> menus = new ArrayList<>();
         for (SourceItem sourceItem : subSourceItems) {
           MenuItem menu = new MenuItem();
menus.add(menu);
            menu.setText(sourceItem.getDisplay());
            if (sourceItem.getValue() != null) {
                menu.setId(sourceItem.getValue().toString());
            }
            menu.setUserData(sourceItem);
           
            menu.setOnAction(new EventHandler<ActionEvent>(){
               @Override
               public void handle(ActionEvent event) {
                  getModel().setCurrentBusinessModel(sourceItem.getValue() + "");
               }
           });
        
       

           
        }

        return menus;
    }

    /**
     * 创建菜单数据
     *
     * @return
     */
    private List<SourceItem> createMenuDatas() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/leqi/client/root/uf/menuDatas.fxml"));
        return fxmlLoader.load();
    }

//    private void initNav() {
//        final NavModel navModel = (NavModel) getModel("NavModel");
//        navPane.getChildren().setAll(navModel.getRoot());
//        openModel("UpLoadModel");
//        navModel.currentBusinessModelProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                openModel(newValue);
//
//            }
//        });
//
//    }
    private void openModel(String businessId) {
        if (businessId == null) {
            businessPane.getChildren().clear();
            return;
        }
        final FXMLModel businessModel = getModel(businessId);
        businessPane.getChildren().setAll(businessModel.getRoot());

    }

    @Override
    public void refresh() {

    }

}
