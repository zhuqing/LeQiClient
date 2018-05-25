/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.text.choose;


import com.leqienglish.client.control.date.LQPopupFieldSkin;
import com.leqienglish.client.util.sourceitem.SourceItem;
import com.leqienglish.client.util.thread.DelayRunner;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author zhuqing
 */
public class LQTextInputOrSelectSkin extends LQPopupFieldSkin<LQTextInputOrSelect<SourceItem>, LQTextInputOrSelectBehavior> {

    private TextField textField;

    private ListView<SourceItem> listView;

    private boolean filtering = false;
    private boolean couldFilter = true;

    private final ChangeListener<String> textFieldChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            getSkinnable().setValue(newValue);
            filter(newValue);
        }
    };

    public LQTextInputOrSelectSkin(LQTextInputOrSelect<SourceItem> control) {
        super(control, new LQTextInputOrSelectBehavior(control));
    }

    @Override
    protected void initSkin() {
        super.initSkin();
        this.getPopContent();
        //this.listView = (ListView<SourceItem>) this.getPopContent();
        this.textField = (TextField) this.getOwner();

        this.textField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() != KeyCode.DOWN) {
                    return;
                }
                showV();
            }
        });

        this.listView.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() != KeyCode.ENTER) {
                    return;
                }

                setText(listView.getSelectionModel().getSelectedItem());
                hidden();

            }
        });

        this.listView.getItems().setAll(this.getSkinnable().getItems());

        this.textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    //  showV();
                    DelayRunner.runLaterInApplicationThread(() -> textField.selectAll(), 300L);
                } else {
                    hidden();
                }
            }
        });

        this.textField.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showV();
            }
        });

        this.getSkinnable().getItems().addListener(new ListChangeListener<SourceItem>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends SourceItem> c) {
                if (listView == null) {
                    return;
                }
                listView.getItems().setAll(getSkinnable().getItems());
            }
        });

        this.getSkinnable().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue != null && newValue) {
                    textField.requestFocus();
                }
            }
        });

        this.getSkinnable().valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null || textField == null) {
                    return;
                }
                textField.textProperty().removeListener(textFieldChangeListener);
                textField.setText(newValue);
                textField.textProperty().addListener(textFieldChangeListener);
            }
        });

        this.textField.textProperty().addListener(textFieldChangeListener);
    }

    private void filter(String filter) {
        if (filtering || !this.couldFilter) {
            return;
        }
        filtering = true;
        List<SourceItem> newSourceItems = this.getSkinnable().getItems();
        if (filter == null || filter.isEmpty()) {
            newSourceItems = this.getSkinnable().getItems();
        } else {
            final String filterUp = filter.toUpperCase();
            newSourceItems = this.getSkinnable().getItems().stream()
                    .filter((sourceItem) -> sourceItem.getDisplay().contains(filterUp) || sourceItem.getShortPY().contains(filterUp) || sourceItem.getMnemonicCode().contains(filterUp))
                    .collect(Collectors.toList());
        }

        this.listView.getItems().setAll(newSourceItems);
        if (!newSourceItems.isEmpty()) {
            this.showV();
        } else {
            this.hidden();
        }

        filtering = false;
    }

    private void setText(SourceItem sourceItem) {
        couldFilter = false;
        if (sourceItem == null) {
            textField.setText("");
        } else {
            textField.setText(sourceItem.getDisplay());
        }
        this.getSkinnable().setValue((String) sourceItem.getValue());
        couldFilter = true;
    }

    @Override
    protected void showV(Region owner) {
        if (this.getSkinnable().getItems().isEmpty()) {
            return;
        }
        super.showV(owner); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void showV() {
        if (this.getSkinnable().getItems().isEmpty()) {
            return;
        }
        super.showV(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Region createPopupContent() {

        VBox vbox = new VBox();
        listView = new ListView<SourceItem>();
        listView.setCellFactory(new Callback<ListView<SourceItem>, ListCell<SourceItem>>() {
            @Override
            public ListCell<SourceItem> call(ListView<SourceItem> param) {
                ListCell<SourceItem> cell = new ListCell<SourceItem>() {
                    @Override
                    protected void updateItem(SourceItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText("");
                        } else {
                            this.setText(item.getDisplay());
                        }
                    }

                };

                cell.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getSource() instanceof ListCell) {
                            hidden();
                            ListCell cell = (ListCell) event.getSource();
                            setText((SourceItem) cell.getItem());
                        }

                    }
                });

                return cell;
            }
        });
        // listView.setPrefHeight(250);
        vbox.getChildren().add(listView);
        //vbox.setMinHeight(300);
        vbox.setPrefHeight(300);
        //vbox.setTranslateZ(0);
        vbox.getStyleClass().add("printDialog");

        return vbox;
    }

    @Override
    protected Region createPopupOwner() {
        return new TextField();
    }

    @Override
    protected void showEventHandler() {

    }

    @Override
    protected void hiddenEventHandler() {

    }

}
