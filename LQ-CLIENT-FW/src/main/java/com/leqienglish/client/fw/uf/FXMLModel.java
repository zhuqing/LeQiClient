/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.uf;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import com.leqienglish.client.fw.LogFacade;
import com.leqienglish.client.fw.cf.BusinessDataNavCommand;
import javafx.scene.control.Tab;

/**
 *
 * @author duyi
 */
public abstract class FXMLModel extends LogFacade {
    
    private Tab tab;

    private Node root;

    private String fxmlPath;

    private FXMLController fxmlController;

    private String showBusinessDataNav;

    private BusinessDataNavCommand businessDataNavCommand;

    public FXMLModel() {
        super();
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public final void setFxmlPath(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public FXMLController getFxmlController() {
        return fxmlController;
    }

    public void setFxmlController(FXMLController fxmlController) {
        this.fxmlController = fxmlController;
    }

    public Node getRoot() {
        if (root == null) {
            try {
                root = initRoot();
            } catch (Exception ex) {
                getLogger().error("初始化页面错误！！！", ex);
            }
        }
        return root;
    }

    protected final Node initRoot() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getFxmlPath()));
        getFxmlController().setModel(this);
        fxmlLoader.setController(getFxmlController());
        fxmlLoader.load();
        Node node = fxmlLoader.getRoot();
        return node;
    }

    public final void refesh() {
        getFxmlController().refresh();
    }
    
    public final void refeshView(){
        getFxmlController().refreshView();
    }

    public final void close() {
        getFxmlController().close();
    }

    public final void hide() {
        getFxmlController().hide();
    }

    public final String getShowBusinessDataNav() {
        return showBusinessDataNav;
    }

    public final void setShowBusinessDataNav(String showBusinessDataNav) {
        this.showBusinessDataNav = showBusinessDataNav;
    }

    public BusinessDataNavCommand getBusinessDataNavCommand() {
        return businessDataNavCommand;
    }

    public void setBusinessDataNavCommand(BusinessDataNavCommand businessDataNavCommand) {
        this.businessDataNavCommand = businessDataNavCommand;
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

}
