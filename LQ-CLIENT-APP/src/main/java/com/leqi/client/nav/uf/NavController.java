/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.nav.uf;

import com.leqi.client.content.UpLoadModel;
import com.leqienglish.client.control.view.listview.cell.LQListCell;
import com.leqienglish.client.control.view.listview.LQListView;
import com.leqienglish.client.fw.nav.NavItem;
import com.leqienglish.client.fw.uf.FXMLController;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("NavController")
public class NavController extends FXMLController<NavModel> {

    @FXML
    private LQListView navListView;

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navListView.setCellMouseClickEventHandler(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LQListCell cell = (LQListCell) event.getSource();

                NavItem item = (NavItem) cell.getItem();

                if (item == null) {
                    return;
                }
                getModel().setCurrentBusinessModel(item.getModelId());
            }
        });

        this.navListView.getItems().setAll(createNavData());

    }

    private List<NavItem> createNavData() {
        List<NavItem> navItems = new ArrayList<>();
        navItems.add(new NavItem("BOOK列表", "BookListModel"));
        navItems.add(new NavItem("上传打点音频", "UpLoadModel"));
        navItems.add(new NavItem("TED音频列表", "TEDAudioContentListModel"));

        return navItems;

    }

}
