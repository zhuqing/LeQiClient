/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.combobox;


import com.leqienglish.client.control.button.LQButton;
import com.leqienglish.client.control.date.LQPopupFieldSkin;
import com.leqienglish.client.control.text.LQTextField;
import com.leqienglish.client.util.lock.LockUtil;
import com.leqienglish.client.util.sourceitem.SourceItem;
import com.leqienglish.client.util.thread.DelayRunner;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author zhuqing
 */
public class LQFilterableComboboxSkin<S extends SourceItem> extends LQPopupFieldSkin<LQFilterableCombobox<S>, LQFilterableComboboxBehavior<S>> {

    private StackPane comboxPane;

    private LQTextField textField;

    private String FILTER = "FILTER";

    private ListView<S> listView;

    private LQButton arrawButton;

    private String COULD_COMMIT = "COULD_COMMIT";
    private String CH_INPUT = "CH_INPUT";

    private VBox popupContent;

    private final PseudoClass SHOWING = PseudoClass.getPseudoClass("showing");

    private final ChangeListener<String> textFieldChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

            if (LockUtil.isLocked(textField)) {
                return;
            }
            if (!textField.isFocused()) {
                return;
            }
            LockUtil.lock(FILTER);
            filter(newValue);
            LockUtil.unLock(FILTER);
        }
    };

    private final ChangeListener<Number> selectIndexChangeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (newValue == null) {
                newValue = 0;
            }
            if (newValue.intValue() >= getSkinnable().getItems().size()) {
                return;
            }
            S item = getSkinnable().getItems().get(newValue.intValue());
            getSkinnable().setValue(item);
        }
    };

    @Override
    protected void showV() {
        if (this.getLQPopup().isShowing()) {
            return;
        }
        super.showV();

    }

    private final ChangeListener<S> valueChangeListener = new ChangeListener<S>() {
        @Override
        public void changed(ObservableValue<? extends S> observable, S oldValue, S newValue) {
            valueChange(newValue);
        }
    };

    private void valueChange(S newValue) {
        if (newValue == null) {
            textField.setText("");
            return;
        }
        getSkinnable().selectIndexProperty().removeListener(selectIndexChangeListener);
        LockUtil.lock(textField);

        setText(newValue);

        int selectedIndex = getSkinnable().getItems().indexOf(newValue);
        getSkinnable().setSelectIndex(selectedIndex);
        listView.getSelectionModel().select(selectedIndex);
        LockUtil.unLock(textField);
        getSkinnable().selectIndexProperty().addListener(selectIndexChangeListener);
    }

    public LQFilterableComboboxSkin(LQFilterableCombobox<S> control) {
        super(control, new LQFilterableComboboxBehavior(control));
    }

    @Override
    protected void initSkin() {
        super.initSkin();

        this.comboxPane = (StackPane) this.getOwner();
        this.comboxPane.setAlignment(Pos.CENTER_RIGHT);
        this.textField = new LQTextField();
        this.textField.setPromptText(this.getSkinnable().getPromptText());
        if (getSkinnable().getPrefPopupWidth() > 0) {
            popupContent.setPrefWidth(getSkinnable().getPrefPopupWidth());
        } else {
            this.getPopContent().prefWidthProperty().bind(textField.widthProperty());
        }

        this.listView.getItems().setAll(this.getSkinnable().getItems());
        this.setText(getSkinnable().getValue());
        this.listView.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() != KeyCode.ENTER) {
                    return;
                }
                LockUtil.lock(COULD_COMMIT);
                hidden();
                event.consume();
            }
        });

        this.textField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.DOWN) {
                    showV();
                    if (listView.getItems().contains(getSkinnable().getValue())) {
                        listView.scrollTo(getSkinnable().getValue());
                        listView.getSelectionModel().select(getSkinnable().getValue());
                    }
                }
            }
        });

        this.textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    DelayRunner.runLaterInApplicationThread(() -> textField.selectAll(), 300L);
                } else {
                    hidden();
                    listView.getItems().setAll(getSkinnable().getItems());
                }
            }
        });

        this.textField.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getLQPopup().isShowing()) {
                    return;
                }

                showV();

                if (listView.getItems().contains(getSkinnable().getValue())) {
                    listView.scrollTo(getSkinnable().getValue());
                    listView.getSelectionModel().select(getSkinnable().getValue());
                }
            }
        });

        this.getSkinnable().getItems().addListener(new ListChangeListener<SourceItem>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends SourceItem> c) {
                if (listView == null || popupContent == null) {
                    return;
                }
                listView.getItems().setAll(getSkinnable().getItems());
                valueChange(getSkinnable().getValue());

                resetPopupContentHeight();
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

        this.getSkinnable().valueProperty().addListener(valueChangeListener);

        this.getSkinnable().selectIndexProperty().addListener(selectIndexChangeListener);

        this.textField.textProperty().addListener(textFieldChangeListener);

        this.arrawButton = new LQButton();
        arrawButton.setGraphic(new ImageView());
        arrawButton.getStyleClass().add("arraw-button");
        arrawButton.setMouseTransparent(false);
        arrawButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (getLQPopup().isShowing()) {
                    hidden();
                } else {
                    showV();
                }

            }
        });
        this.comboxPane.getChildren().addAll(this.textField, this.arrawButton);

        this.getLQPopup().showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                arrawButton.pseudoClassStateChanged(SHOWING, newValue);
            }
        });
    }

    private void resetPopupContentHeight() {
//        double popupHeight = getSkinnable().getPrefPopupHeight();
//        if (getSkinnable().getItems().size() > 4 && getSkinnable().getItems().size() < 10) {
//            popupHeight = getSkinnable().getItems().size() * 26;
//        } else if (getSkinnable().getItems().size() >= 10) {
//            popupHeight = 260;
//        }
//        popupContent.setPrefHeight(popupHeight);
    }

    @Override
    protected void showSkin() {
        super.showSkin();

    }

    @Override
    protected Region createPopupContent() {
        popupContent = new VBox();
        listView = new ListView<S>();
        listView.setCellFactory(new Callback<ListView<S>, ListCell<S>>() {
            @Override
            public ListCell<S> call(ListView<S> param) {
                ListCell<S> cell = new ListCell<S>() {
                    @Override
                    protected void updateItem(S item, boolean empty) {
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
                            ListCell cell = (ListCell) event.getSource();
                            if (listView.getSelectionModel().getSelectedItem() != cell.getItem()) {
                                LockUtil.lock(CH_INPUT);
                                listView.getSelectionModel().select((S) cell.getItem());
                                textField.setUnableReplace(true);
                            }
                            LockUtil.lock(COULD_COMMIT);
                            hidden();
                        }
                    }
                });

                return cell;
            }
        });

        popupContent.getChildren().add(listView);

        popupContent.setPrefHeight(getSkinnable().getPrefPopupHeight());
        popupContent.getStyleClass().add("printDialog");

        return popupContent;
    }

    private void filter(String filterText) {
        listView.getSelectionModel().clearSelection();
        if (filterText == null || filterText.isEmpty()) {
            this.getSkinnable().setValue(null);
            commitValue(null);
            listView.getItems().setAll(this.getSkinnable().getItems());
            showV();

        } else {
            List<S> filtered = this.getSkinnable().getItems().stream()
                    .filter((S sourceItem) -> sourceItem.getDisplay().contains(filterText) || sourceItem.getShortPY().contains(filterText.toUpperCase()) || sourceItem.getMnemonicCode().contains(filterText.toUpperCase()))
                    .collect(Collectors.toList());
            listView.getItems().setAll(filtered);

            showV();
            listView.getSelectionModel().select(0);

        }

        listView.scrollTo(0);

    }

    @Override
    protected Region createPopupOwner() {
        return new StackPane();
    }

    @Override
    protected void showEventHandler() {

    }

    @Override
    protected void hiddenEventHandler() {
        if (!LockUtil.isLocked(COULD_COMMIT)) {
            return;
        }
        LockUtil.unLock(COULD_COMMIT);

        S item = this.listView.getSelectionModel().getSelectedItem();
        getSkinnable().setValue((S) item);
        setText(item);
        commitValue(item);
        if (LockUtil.isLocked(CH_INPUT)) {
            LockUtil.unLock(CH_INPUT);
            // TimerUtil.runLaterInApplicationThread(() -> textField.setText(item.getDisplay()), 300L);
        }

    }

    private void setText(S s) {
        if (s == null) {
            textField.setText("");
        } else {
            textField.setText(s.getDisplay());
        }
    }

    private void commitValue(S s) {
        System.err.println("commitValue==" + s);
        if (this.getSkinnable().getCommit() != null) {
            this.getSkinnable().getCommit().accept(s);
        }
    }

}
