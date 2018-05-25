/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.bool;


import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.util.image.ImageRepertory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author zhangyingchuang
 */
public class LQBooleanImageFormCell<S> extends LQBooleanFormCell {

    private ImageView hipImageView;

    private StringProperty imageURL;

    private String truePath;

    private String falsePath;

    private Label label;

    private final ChangeListener<String> changeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            commitBooleanValue(newValue.equals(getTruePath()));
        }
    };

    private final EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.SPACE) {
                if (getTruePath().equals(imageURLProperty().getValue())) {
                    getHipImageView().setImage(ImageRepertory.getImage(getFalsePath()));
                    imageURLProperty().setValue(getFalsePath());
                } else {
                    getHipImageView().setImage(ImageRepertory.getImage(getTruePath()));
                    imageURLProperty().setValue(getTruePath());
                }
            }
        }
    };

    private final EventHandler<MouseEvent> mouseEventHander = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getClickCount() != 1 || event.getButton() != MouseButton.PRIMARY) {
                return;
            }
            if (getTruePath().equals(imageURLProperty().getValue())) {
                getHipImageView().setImage(ImageRepertory.getImage(getFalsePath()));
                imageURLProperty().setValue(getFalsePath());
            } else {
                getHipImageView().setImage(ImageRepertory.getImage(getTruePath()));
                imageURLProperty().setValue(getTruePath());
            }
        }
    };

    @Override
    protected Node createEditGraghic() {
        if (hipImageView == null) {
            label = new Label();
         
            hipImageView = new ImageView();
            imageURLProperty().addListener(changeListener);
            label.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHander);
            label.addEventHandler(KeyEvent.ANY, keyEventHandler);
        }
        label.setGraphic(hipImageView);

        return label;

    }

    @Override
    protected void updateValue(Object t) {
        super.updateValue(t);
        String path = getFalsePath();
        if (getBoolean(t)) {
            path = getTruePath();
        }
        this.getHipImageView().setImage(ImageRepertory.getImage(path));
        imageURLProperty().removeListener(changeListener);
        imageURLProperty().setValue(path);
        imageURLProperty().addListener(changeListener);
    }

    public StringProperty imageURLProperty() {
        if (imageURL == null) {
            imageURL = new SimpleStringProperty();
        }
        return imageURL;
    }

    private void setImageURL(String path) {
        imageURLProperty().setValue(path);
    }

    private String getImageURL() {
        return imageURLProperty().getValue();
    }

    public ImageView getHipImageView() {
        if (hipImageView == null) {
            createEditGraghic();
        }
        return hipImageView;
    }

    public String getTruePath() {
        if (truePath == null || truePath.isEmpty()) {
            truePath = "/img/tree/on0.png";
        }
        return truePath;
    }

    public void setTruePath(String truePath) {
        this.truePath = truePath;
    }

    public String getFalsePath() {
        if (falsePath == null || falsePath.isEmpty()) {
            falsePath = "/img/tree/off0.png";
        }
        return falsePath;
    }

    public void setFalsePath(String falsePath) {
        this.falsePath = falsePath;
    }

    @Override
    public LQBooleanImageFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQBooleanImageFormCell booleanImageFormCell = (LQBooleanImageFormCell) formCell;
            booleanImageFormCell.setTruePath(this.getTruePath());
            booleanImageFormCell.setFalsePath(this.getFalsePath());
            return booleanImageFormCell;
        }
        return null;
    }
}
