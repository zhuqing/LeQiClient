/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.file;

import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

/**
 * 打开本地文件
 *
 * @author zhuqing
 */
public class LQOpenFileFormCell extends LQEditableFormCell<Object, String, Node> {

    private String fileFilter;

    private TextField textField;

    private Button button;

    private HBox hBox;

    private File file;

    private final EventHandler<ActionEvent> fileChooseEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("文件下载");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(getFileFilter(), "*." + getFileFilter()));
            File file = fileChooser.showOpenDialog(button.getScene().getWindow());
            commitValue(file.getAbsolutePath());
            textField.setText(file.getAbsolutePath());
        }
    };

    @Override
    protected Node createEditGraghic() {

        if (hBox == null) {
            hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPrefWidth(this.getPrefWidth());
            textField = new TextField();
            textField.setEditable(false);
            button = new Button("open file");
            button.setOnAction(fileChooseEvent);
            button.setPrefWidth(50);
            button.setMinWidth(50);
            hBox.getChildren().addAll(textField, button);
        }
        return hBox;
    }

    @Override
    protected void updateValue(String t) {
        super.updateValue(t);
        getTextField().setText(t);
    }

    public String getFileFilter() {
        if (fileFilter == null) {
            fileFilter = "*";
        }
        return fileFilter;
    }

    public void setFileFilter(String fileFilter) {
        this.fileFilter = fileFilter;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public TextField getTextField() {
        if (textField == null) {
            createEditGraghic();
        }
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    @Override
    public LQOpenFileFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQOpenFileFormCell downloadFieldFormCell = (LQOpenFileFormCell) formCell;
            downloadFieldFormCell.setFileFilter(this.getFileFilter());
            return downloadFieldFormCell;
        }
        return null;
    }

}
