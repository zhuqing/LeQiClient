/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.image.path;

import com.sun.org.apache.xerces.internal.util.URI;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author zhuqing
 */
public  class LQImagePathView extends StackPane {

    private ImageView imageView;

    /**
     * 图片的二进制数据
     */
    private StringProperty imagePath;

    public LQImagePathView() {
        this.getChildren().add(this.getImageView());
        JavaFxObservable.nullableValuesOf(this.imagePathProperty())
                .filter(p -> p.isPresent())
                .subscribe(p -> imagePathChange(p.get()));
    }

    private void imagePathChange(String path) throws FileNotFoundException {

        Image image = null;
        if (URI.isWellFormedIPv4Address(path)) {
            image = new Image(path, true);
        } else {
            image = new Image(new FileInputStream(new File(path)));
        }

        this.getImageView().setImage(image);
    }

    /**
     * @return the imageView
     */
    public ImageView getImageView() {
        if (this.imageView == null) {
            this.imageView = new ImageView();
        }
        return imageView;
    }

    /**
     * 图片的二进制数据
     *
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePathProperty().getValue();
    }

    /**
     * 图片的二进制数据
     *
     * @return the imagePath
     */
    public StringProperty imagePathProperty() {
        if (this.imagePath == null) {
            this.imagePath = new SimpleStringProperty();
        }
        return imagePath;
    }

    /**
     * 图片的二进制数据
     *
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePathProperty().setValue(imagePath);
    }

}
