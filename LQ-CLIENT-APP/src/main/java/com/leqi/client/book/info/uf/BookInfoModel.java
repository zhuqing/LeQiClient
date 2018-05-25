/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.info.uf;

import com.leqi.client.book.uf.*;
import com.leqi.client.content.*;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.fw.uf.FXMLModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Catalog;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("BookInfoModel")
public class BookInfoModel extends FXMLModel {

    public BookInfoModel() {
        setFxmlPath("/com/leqi/client/book/uf/BookInfo.fxml");
    }

    @Override
    @Resource(name = "BookInfoController")
    public void setFxmlController(FXMLController fxmlController) {
        super.setFxmlController(fxmlController); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 分类实体
     */
    private ObjectProperty<Catalog> catalog;

    /**
     * 分类实体
     *
     * @return the catalog
     */
    public Catalog getCatalog() {
        return catalogProperty().getValue();
    }

    /**
     * 分类实体
     *
     * @return the catalog
     */
    public ObjectProperty<Catalog> catalogProperty() {
        if (catalog == null) {
            catalog = new SimpleObjectProperty<>();
        }
        return catalog;
    }

    /**
     * 分类实体
     *
     * @param catalog the catalog to set
     */
    public void setCatalog(Catalog catalog) {
        this.catalogProperty().setValue(catalog);
    }
}
