/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.image;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author zhuqing
 */
public class LQImageView extends StackPane {

    private ImageView imageView;

    /**
     * 图片的二进制数据
     */
    private ObjectProperty<byte[]> imageViewBytes;
    private final ChangeListener<byte[]> imageViewBytesChangeListener = new ChangeListener<byte[]>() {
        @Override
        public void changed(ObservableValue<? extends byte[]> observable, byte[] oldValue, byte[] newValue) {
            if (newValue == null) {
                getImageView().setImage(null);
            } else {
                getImageView().setImage(new Image(new ByteArrayInputStream(newValue)));
            }
        }
    };

    public LQImageView() {
        this.getChildren().add(this.getImageView());
        this.imageViewBytesProperty().addListener(imageViewBytesChangeListener);

    }

    /**
     * @return the imageViewBytes
     */
    public byte[] getImageViewBytes() {
        return imageViewBytesProperty().getValue();
    }

    /**
     * @return the imageViewBytes
     */
    public ObjectProperty<byte[]> imageViewBytesProperty() {
        if (imageViewBytes == null) {
            imageViewBytes = new SimpleObjectProperty<byte[]>();
        }
        return imageViewBytes;
    }

    /**
     * @param imageViewBytes the imageViewBytes to set
     */
    public void setImageViewBytes(byte[] imageViewBytes) {
        this.imageViewBytesProperty().setValue(imageViewBytes);
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

}
