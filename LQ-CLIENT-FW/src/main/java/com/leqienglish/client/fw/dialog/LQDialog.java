/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.dialog;

import com.leqienglish.client.fw.app.AbstractApplication;
import com.leqienglish.client.fw.uf.FXMLModel;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author zhuleqi
 */
public class LQDialog {

    public static void openDialog(String businessId, Node ownnerNode) {

    }
    
      public static ButtonType openDialog(String title,  FXMLModel fxmlModel , Node ownnerNode, ButtonType... buttonTypes) {

        Dialog dialog = new Dialog();
        DialogPane pane = new DialogPane();
        pane.setContent(fxmlModel.getRoot());
        if (buttonTypes != null) {
            pane.getButtonTypes().addAll(buttonTypes);
        }
        dialog.setDialogPane(pane);
        Optional<ButtonType> opt = dialog.showAndWait();
       
        return opt.orElse(null);

    }

  
}
