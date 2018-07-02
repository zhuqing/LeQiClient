/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.file;


import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;


/**
 *
 * @author zhangyingchuang
 */
public class LQFileDownloadFieldFormCell extends LQEditableFormCell<Object, Object, Node> {

    private String fileFilter;

    private TextField textField;

    private Button button;

    private HBox hBox;

    private File file;

    private ObjectProperty<byte[]> fileBytesProperty;

    private final EventHandler<ActionEvent> fileChooseEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            byte[] filevalues = fileBytesProperty().getValue();
            if (filevalues == null || filevalues.length <= 0) {
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("文件下载");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(getFileFilter(), "*." + getFileFilter()));
            file = fileChooser.showSaveDialog(button.getScene().getWindow());
            saveFile(file);
        }
    };

    @Override
    protected Node createEditGraghic() {
        if (hBox == null) {
            hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            textField = new TextField();
            textField.setEditable(false);
            button = new Button("下传");
            button.setOnAction(fileChooseEvent);
            button.setPrefWidth(50);
            button.setMinWidth(50);
            hBox.getChildren().addAll(textField, button);
        }
        return hBox;
    }

    @Override
    protected void updateValue(Object t) {
        super.updateValue(t);
        try {
            Object object = this.getItem();
            byte[] bytes = (byte[]) PropertyReflectUtil.getValue(object, this.getPropertyName());
            if (bytes != null) {
                fileBytesProperty().setValue(bytes);
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream(100);
                byteArray.write(bytes, 0, 100);
                String fileName = new String(byteArray.toByteArray());
                getTextField().setText(fileName);
                if (fileName.contains(".")) {
                    setFileFilter(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()));
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(LQFileDownloadFieldFormCell.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    public ObjectProperty<byte[]> fileBytesProperty() {
        if (fileBytesProperty == null) {
            fileBytesProperty = new SimpleObjectProperty<>();
        }
        return fileBytesProperty;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytesProperty().setValue(fileBytes);
    }

    public byte[] getFileBytes() {
        return this.fileBytesProperty.getValue();
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

    private void saveFile(File file) {
        if (file == null) {
            return;
        }
        byte[] filevalues = fileBytesProperty().getValue();
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream(1024);
        byteArray.write(filevalues, 100, filevalues.length - 100);
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(byteArray.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

    @Override
    public LQFileDownloadFieldFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQFileDownloadFieldFormCell downloadFieldFormCell = (LQFileDownloadFieldFormCell) formCell;
            downloadFieldFormCell.setFileFilter(this.getFileFilter());
            return downloadFieldFormCell;
        }
        return null;
    }

}
