/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.image.path;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

/**
 *
 * @author zhuqing
 */
public class LQEditableImagePathView extends LQImagePathView {

    private Hyperlink linkButton;

    public LQEditableImagePathView() {
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
                        Logger.getLogger(LQEditableImagePathView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        }
        return linkButton;
    }

    public void reSetImageView(File file) throws IOException {
        this.setImagePath(file.getAbsolutePath());
    }

}
