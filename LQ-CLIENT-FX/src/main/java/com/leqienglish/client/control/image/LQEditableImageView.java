/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.apache.http.util.ByteArrayBuffer;

/**
 *
 * @author zhuqing
 */
public class LQEditableImageView extends LQImageView {

    private Hyperlink linkButton;

    public LQEditableImageView() {
        this.getChildren().add(this.getLinkButton());
    }

    /**
     * @return the liknButton
     */
    public Hyperlink getLinkButton() {
        if (this.linkButton == null) {
            linkButton = new Hyperlink("选择图片");
            StackPane.setAlignment(linkButton, Pos.CENTER);
            linkButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open Resource File");
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Images", "*.*"),
                            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                            new FileChooser.ExtensionFilter("PNG", "*.png")
                    );
                    try {
                        reSetImageView(fileChooser.showOpenDialog(linkButton.getScene().getWindow()));
                    } catch (IOException ex) {
                        Logger.getLogger(LQEditableImageView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        }
        return linkButton;
    }

    public void reSetImageView(File file) throws IOException {
        try {
            if (file == null) {
                return;
            }
            InputStream inputStream = new FileInputStream(file);

            final int defaultLen = 1024;
            ByteArrayBuffer byteArray = new ByteArrayBuffer(defaultLen);
            byte[] bytes = new byte[defaultLen];
            int size = 0;
            while ((size = inputStream.read(bytes, 0, defaultLen)) > 0) {
                byteArray.append(bytes, 0, size);
            }
            this.setImageViewBytes(byteArray.toByteArray());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LQEditableImageView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
