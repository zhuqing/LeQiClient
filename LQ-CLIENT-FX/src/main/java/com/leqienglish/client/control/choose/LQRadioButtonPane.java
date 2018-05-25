/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.choose;


import com.leqienglish.client.util.sourceitem.SourceItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * 单选面板
 *
 * @author zhuqing
 * @param <T>
 */
public class LQRadioButtonPane<T> extends LQChoosePane<T, RadioButton> {

    /**
     * 选中数据的值
     */
    private ObjectProperty<SourceItem<T>> value;
    private ToggleGroup toggleGroup;

    private final Tooltip tooltip = new Tooltip("空格选中,回车提交");

    private boolean firstVisit = true;

    private String oldValue;

    private final ChangeListener<Toggle> toggleChangeListener = new ChangeListener<Toggle>() {
        @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
            value.removeListener(valueChangeListener);
            if (newValue == null) {
                setValue(null);
            } else {
                setValue((SourceItem) newValue.getUserData());
            }
            value.addListener(valueChangeListener);
        }
    };

    private final ChangeListener<SourceItem<T>> valueChangeListener = new ChangeListener<SourceItem<T>>() {
        @Override
        public void changed(ObservableValue<? extends SourceItem<T>> observable, SourceItem<T> oldValue, SourceItem<T> newValue) {
            valueChange();
        }

    };

    private final EventHandler keyEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            RadioButton radioButton = (RadioButton) event.getSource();
            if (KeyCode.ENTER.equals(event.getCode())) {
                if (value != null && value.getValue() != null) {
                    String name = value.getValue().getDisplay();
                    if (oldValue != null && oldValue.equals(name) && radioButton.isSelected()) {
                        radioButton.setSelected(false);
                        oldValue = null;
                    } else {
                        radioButton.setSelected(true);
                        oldValue = name;
                    }
                } else {
                    radioButton.setSelected(true);
                    oldValue = radioButton.getText();
                }
            }
        }
    };

    private final EventHandler mouseEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            RadioButton radioButton = (RadioButton) event.getSource();
            if (value != null) {
                String name = value.getValue().getDisplay();
                if (oldValue != null && oldValue.equals(name) && radioButton.isSelected()) {
                    radioButton.setSelected(false);
                    oldValue = null;
                } else {
                    radioButton.setSelected(true);
                    oldValue = name;
                }

            }
        }
    };

    private RadioButton builderRadioButton(SourceItem sourceItem) {
        RadioButton radioButton = new RadioButton();
        radioButton.setUserData(sourceItem);
        radioButton.setText(sourceItem.getDisplay());
        radioButton.setTooltip(tooltip);
        radioButton.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
        radioButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandler);
        return radioButton;
    }

    @Override
    protected List<RadioButton> creatChildren(List<SourceItem<T>> sourceItems) {

        if (sourceItems == null || sourceItems.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        if (this.toggleGroup == null) {
            toggleGroup = new ToggleGroup();
            this.toggleGroup.selectedToggleProperty().addListener(toggleChangeListener);
        } else {
            toggleGroup.getToggles().clear();
        }

        List<RadioButton> radioButtons = new ArrayList<>(sourceItems.size());

        for (SourceItem sourceItem : sourceItems) {
            RadioButton radioButton = builderRadioButton(sourceItem);
            radioButtons.add(radioButton);
            radioButton.setToggleGroup(toggleGroup);
        }
        return radioButtons;
    }

    @Override
    protected void sourceItemChangeHandler() {
        super.sourceItemChangeHandler();
        this.setValue(null);
        oldValue = null;
    }

    /**
     * 值改变
     */
    private void valueChange() {
        if (this.getChildren().isEmpty()) {
            return;
        }

        List<RadioButton> radioButtons = this.getChildren().stream().filter((Node node) -> node instanceof RadioButton).map((Node node) -> (RadioButton) node).collect(Collectors.toList());
        for (RadioButton radioButton : radioButtons) {
            SourceItem curr = (SourceItem) radioButton.getUserData();
            if (curr == null) {
                continue;
            }
            if (Objects.equals(curr, this.getValue())) {
                radioButton.setSelected(true);
                oldValue = radioButton.getText();
            }
        }
    }

    public SourceItem getValue() {
        return valueProperty().getValue();
    }

    public ObjectProperty<SourceItem<T>> valueProperty() {
        if (this.value == null) {
            this.value = new SimpleObjectProperty<SourceItem<T>>();
            this.value.addListener(valueChangeListener);
        }
        return value;
    }

    public void setValue(SourceItem<T> value) {
        valueProperty().setValue(value);
    }

}
