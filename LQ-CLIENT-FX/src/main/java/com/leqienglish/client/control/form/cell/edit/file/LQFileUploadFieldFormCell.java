/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.file;


import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class LQFileUploadFieldFormCell extends LQEditableFormCell<Object, Object, Node> {

    private String fileFilter;

    private TextField textField;

    private Button button;

    private HBox hBox;

    private File file;

    private ObjectProperty<byte[]> fileBytesProperty;

    private String fileName;

    private final EventHandler<ActionEvent> fileChooseEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("文件上传");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(getFileFilter(), "*." + getFileFilter()));
            file = fileChooser.showOpenDialog(button.getScene().getWindow());
            if (file != null) {
                textField.setText(file.getName());
                fileBytesProperty().setValue(getBytesbyFile(file));
                commitValue(fileBytesProperty().getValue());
            }
        }
    };

    private ChangeListener<String> changeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            commitValue(fileName, newValue);
        }
    };

    @Override
    protected Node createEditGraghic() {
        if (hBox == null) {
            hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            textField = new TextField();
            textField.setPromptText("文件过滤：" + getFileFilter());
            textField.textProperty().addListener(changeListener);
            button = new Button("上传");
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
            getTextField().textProperty().removeListener(changeListener);
            byte[] bytes = (byte[]) PropertyReflectUtil.getValue(object, this.getPropertyName());
            if (bytes != null) {
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream(100);
                byteArray.write(bytes, 0, 100);
                getTextField().setText(new String(byteArray.toByteArray()));
            }
            getTextField().textProperty().addListener(changeListener);
        } catch (Exception ex) {
            Logger.getLogger(LQFileUploadFieldFormCell.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private byte[] getBytesbyFile(File file) {
        try {
            if (file == null) {
                return null;
            }
            InputStream inputStream = new FileInputStream(file);
            final int defaultLen = 1024;
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream(defaultLen);
            byte[] fileNamebytes = new byte[100];
            byte[] names = file.getName().getBytes();
            for (int i = 0; i < names.length; i++) {
                fileNamebytes[i] = names[i];
            }

            byteArray.write(fileNamebytes, 0, 100);
            byte[] bytes = new byte[defaultLen];
            int size = 0;
            while ((size = inputStream.read(bytes, 0, defaultLen)) > 0) {
                byteArray.write(bytes, 0, size);
            }
            return byteArray.toByteArray();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public LQFileUploadFieldFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQFileUploadFieldFormCell fileUploadFieldFormCell = (LQFileUploadFieldFormCell) formCell;
            fileUploadFieldFormCell.setFileFilter(this.getFileFilter());
            fileUploadFieldFormCell.setFileName(this.getFileName());
            return fileUploadFieldFormCell;
        }
        return null;
    }
}
