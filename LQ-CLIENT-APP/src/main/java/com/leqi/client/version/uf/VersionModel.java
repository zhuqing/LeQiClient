/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.version.uf;


import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.version.Version;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("VersionModel")
public class VersionModel extends FXMLModel {

    /**
     * Version列表
     */
    private ObservableList<Version> versionList;


    private ObjectProperty<Version> editing;

    public VersionModel() {
        setFxmlPath("/com/leqi/client/version/uf/Version.fxml");

//        setFxmlPath("/com/bjgoodwill/hip/client/fw/root/uf/titleRoot.fxml");
    }

    @Override
    @Resource(name = "VersionController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the versionList
     */
    public ObservableList<Version> getVersionList() {
        
        if(this.versionList == null){
            this.versionList = FXCollections.observableArrayList();
        }
        return versionList;
    }

    /**
     * @param versionList the versionList to set
     */
    public void setVersionList(List<Version> versionList) {
      this.getVersionList().setAll(versionList);
    }

    /**
     * @return the editing
     */
    public Version getEditing() {
        return editingProperty().getValue();
    }
    
      /**
     * @return the editing
     */
    public ObjectProperty<Version> editingProperty() {
        if(editing == null){
            this.editing = new SimpleObjectProperty<>();
        }
        return editing;
    }

    /**
     * @param editing the editing to set
     */
    public void setEditing(Version editing) {
        this.editingProperty().setValue(editing); 
    }

   

  
   
}
