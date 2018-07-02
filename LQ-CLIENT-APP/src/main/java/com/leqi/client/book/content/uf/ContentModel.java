/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.content.uf;

import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import com.leqienglish.client.util.sourceitem.SourceItem;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
@Component("ContentModel")
public class ContentModel extends FXMLModel {

    private ObjectProperty<SourceItem> addBreadCrumb;

    public ContentModel() {
        setFxmlPath("/com/leqi/client/content/uf/content.fxml");

//        setFxmlPath("/com/bjgoodwill/hip/client/fw/root/uf/titleRoot.fxml");
    }

    @Override
    @Resource(name = "ContentController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the addBreadCrumb
     */
    public SourceItem getAddBreadCrumb() {
        return addBreadCrumbProperty().getValue();
    }

     /**
     * @return the addBreadCrumb
     */
    public ObjectProperty<SourceItem> addBreadCrumbProperty() {
        if(addBreadCrumb == null){
            addBreadCrumb = new SimpleObjectProperty<SourceItem>();
        }
        return addBreadCrumb;
    }
    /**
     * @param addBreadCrumb the addBreadCrumb to set
     */
    public void setAddBreadCrumb(SourceItem addBreadCrumb) {
        this.addBreadCrumbProperty().setValue(addBreadCrumb); 
    }

   
}
