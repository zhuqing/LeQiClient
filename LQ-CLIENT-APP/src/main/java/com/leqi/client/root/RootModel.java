/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.root;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javax.annotation.Resource;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;

import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author duyi
 */
@Lazy
@Component("rootModel")
public class RootModel extends FXMLModel {
    
    private final DoubleProperty width = new SimpleDoubleProperty();
    
    private final DoubleProperty height = new SimpleDoubleProperty();
    
    private final BooleanProperty winBtnBarVisiable = new SimpleBooleanProperty(false);
    
    private final BooleanProperty navVisiable = new SimpleBooleanProperty(false);
    
    private final BooleanProperty waitShow = new SimpleBooleanProperty();
    
   
    
    private Set<String> navItems;
    
    public RootModel() {
        setFxmlPath("/com/bjgoodwill/hip/client/fw/root/uf/root.fxml");
//        setFxmlPath("/com/bjgoodwill/hip/client/fw/root/uf/titleRoot.fxml");
    }
    
    @Override
    @Resource(name = "rootController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }
    
    public DoubleProperty widthProperty() {
        return width;
    }
    
    public void setWidth(Double width) {
        this.width.setValue(width);
    }
    
    public Double getWidth() {
        return this.width.getValue();
    }
    
    public DoubleProperty heightProperty() {
        return height;
    }
    
    public void setHeight(Double height) {
        this.height.setValue(height);
    }
    
    public Double getHeight() {
        return this.height.getValue();
    }
    
    public BooleanProperty navVisiableProperty() {
        return navVisiable;
    }
    
    public void setNavVisiable(Boolean navVisiable) {
        this.navVisiable.setValue(navVisiable);
    }
    
    public Boolean getNavVisiable() {
        return this.navVisiable.getValue();
    }
    
    public BooleanProperty waitShowProperty() {
        return waitShow;
    }
    
    public void setWaitShow(Boolean waitShow) {
        this.waitShow.setValue(waitShow);
    }
    
    public Boolean getWaitShow() {
        return this.waitShow.getValue();
    }
    
    public Set<String> getNavItems() {
        return navItems;
    }
    
    public void setNavItems(Set<String> navItems) {
        this.navItems = navItems;
    }
    
    public BooleanProperty winBtnBarVisiableProperty() {
        return winBtnBarVisiable;
    }
    
    public Boolean getWinBtnBarVisiable() {
        return winBtnBarVisiable.getValue();
    }
    
    public void setWinBtnBarVisiable(Boolean winBtnBarVisiable) {
        this.winBtnBarVisiable.setValue(winBtnBarVisiable);
    }
    
   
}
