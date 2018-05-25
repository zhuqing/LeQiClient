///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.leqienglish.client.control.form.cell.edit.text;
//
//import com.bjgoodwill.hip.client.fx.control.choose.item.SourceItem;
//import com.bjgoodwill.hip.client.fx.control.form.cell.HipFormCell;
//import com.bjgoodwill.hip.client.fx.focus.FocusUtil;
//import java.util.List;
//import java.util.Optional;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Point2D;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.ContentDisplay;
//import javafx.scene.control.Dialog;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableRow;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextArea;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Popup;
//import javafx.stage.Window;
//import javafx.util.Callback;
//
///**
// *
// * @author duyi
// * @param <S>
// */
//public class HipTextAreaInputDialogFormCell<S> extends HipTextAreaInputFormCell<S> {
//
//    private final Button editButton = new Button();
//
//    private final static Image affair = new Image("/img/form/affair.png");
//
//    private String dialogTitle;
//
//    private Callback<String, Object> saveAction;
//
//    private Callback<String, Object> removeAction;
//
//    private Callback<S, List<SourceItem<String>>> customDataSourceCallback;
//
//    private final Button save = new Button();
//
//    private final Button customData = new Button();
//
//    private Popup popup;
//
//    private final TableView<SourceItem<String>> tableView = new TableView<>();
//
//    private Dialog<ButtonType> textAreaDialog;
//
//    private TextArea textArea;
//
//    @Override
//    protected void initGraghic() {
//        if (this.getGraphic() != null) {
//            FocusUtil.registEnterToNextFocus(this);
//            return;
//        }
//        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//        this.setGraphic(this.createEditGraghic());
//        StackPane graphicRoot = this.createEditGraghic();
//        editButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//        editButton.setGraphic(new ImageView(affair));
//        graphicRoot.getChildren().add(editButton);
//        StackPane.setAlignment(editButton, Pos.CENTER_RIGHT);
//        initDialog();
//        FocusUtil.registEnterToNextFocus(this);
//    }
//
//    public HipFormCell clone() {
//        HipTextAreaInputDialogFormCell cell = (HipTextAreaInputDialogFormCell) super.clone();
//
//        cell.setCustomDataSourceCallback(customDataSourceCallback);
//        cell.setDialogTitle(dialogTitle);
//        cell.setRemoveAction(removeAction);
//        cell.setSaveAction(saveAction);
//
//        return cell;
//    }
//
//    private void initDialog() {
//        editButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Dialog<ButtonType> editTextAreaDialog = getTextAreaDialog();
//                String text = HipTextAreaInputDialogFormCell.this.getTextField().getText();
//                textArea.setText(text);
//                Optional<ButtonType> buttonTypeOptional = editTextAreaDialog.showAndWait();
//                if (!buttonTypeOptional.isPresent()) {
//                    event.consume();
//                    return;
//                }
//                ButtonType buttonType = buttonTypeOptional.get();
//                if (ButtonType.OK.equals(buttonType)) {
//                    HipTextAreaInputDialogFormCell.this.getTextField().setText(textArea.getText());
//                }
//                event.consume();
//            }
//        });
//    }
//
//    public Popup getPopup() {
//        if (popup == null) {
//            popup = new Popup();
//            popup.setAutoHide(true);
//            popup.getContent().add(tableView);
//            popup.setWidth(350D);
//            tableView.setPrefWidth(300D);
//            tableView.setMinWidth(300D);
//            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//            TableColumn<SourceItem<String>, String> tableColumn = new TableColumn<>();
//            tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SourceItem<String>, String>, ObservableValue<String>>() {
//                private final StringProperty valueProperty = new SimpleStringProperty();
//
//                @Override
//                public ObservableValue<String> call(TableColumn.CellDataFeatures<SourceItem<String>, String> param) {
//                    SourceItem<String> sourceItem = param.getValue();
//                    if (sourceItem == null) {
//                        valueProperty.setValue("");
//                    } else {
//                        valueProperty.setValue(sourceItem.getValue());
//                    }
//                    return valueProperty;
//                }
//            });
//            TableColumn<SourceItem<String>, String> operationColumn = new TableColumn<>();
//            operationColumn.setMinWidth(50D);
//            operationColumn.setPrefWidth(50D);
//            operationColumn.setMaxWidth(50D);
//            operationColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SourceItem<String>, String>, ObservableValue<String>>() {
//                private final StringProperty valueProperty = new SimpleStringProperty();
//
//                @Override
//                public ObservableValue<String> call(TableColumn.CellDataFeatures<SourceItem<String>, String> param) {
//                    SourceItem<String> sourceItem = param.getValue();
//                    if (sourceItem == null) {
//                        valueProperty.setValue("");
//                    } else {
//                        valueProperty.setValue(sourceItem.getId());
//                    }
//                    return valueProperty;
//                }
//            });
//            operationColumn.setCellFactory(new Callback<TableColumn<SourceItem<String>, String>, TableCell<SourceItem<String>, String>>() {
//                @Override
//                public TableCell<SourceItem<String>, String> call(TableColumn<SourceItem<String>, String> param) {
//                    Button delete = new Button();
//                    TableCell<SourceItem<String>, String> tableCell = new TableCell<SourceItem<String>, String>() {
//                        @Override
//                        protected void updateItem(String item, boolean empty) {
//                            super.updateItem(item, empty);
//                        }
//                    };
//                    delete.setGraphic(new ImageView("/img/controlbar/delete.png"));
//                    delete.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//                    tableCell.setGraphic(delete);
//                    tableCell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//                    delete.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event) {
//                            TableRow<SourceItem<String>> tableRow = tableCell.getTableRow();
//                            SourceItem<String> sourceItem = tableRow.getItem();
//                            if (sourceItem == null) {
//                                return;
//                            }
//                            removeAction.call(sourceItem.getId());
//                            if (popup.isShowing()) {
//                                popup.hide();
//                            }
//                        }
//                    });
//                    return tableCell;
//                }
//            });
//            tableView.getColumns().addAll(tableColumn, operationColumn);
//            tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SourceItem<String>>() {
//                @Override
//                public void changed(ObservableValue<? extends SourceItem<String>> observable, SourceItem<String> oldValue, SourceItem<String> newValue) {
//                    if (newValue == null) {
//                        return;
//                    }
//                    String selectItem = newValue.getValue();
//                    if (selectItem == null || selectItem.isEmpty()) {
//                        selectItem = "";
//                    }
//                    String text = textArea.getText();
//                    if (text == null) {
//                        text = "";
//                    }
//                    text = text + selectItem;
//                    textArea.setText(text);
//                    tableView.getSelectionModel().select(null);
//                    if (popup.isShowing()) {
//                        popup.hide();
//                    }
//                }
//            });
//        }
//        return popup;
//    }
//
//    public Dialog<ButtonType> getTextAreaDialog() {
//        if (textAreaDialog != null) {
//            return textAreaDialog;
//        }
//        textAreaDialog = new Dialog<>();
//        textAreaDialog.setTitle("其他措施及护理");
//        textAreaDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
//        BorderPane root = new BorderPane();
//        textArea = new TextArea();
//        textArea.setWrapText(true);
//        textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (KeyCode.ENTER.equals(event.getCode())) {
//                    textAreaDialog.setResult(ButtonType.OK);
//                    event.consume();
//                }
//            }
//        });
//        textArea.setPrefSize(500D, 300D);
//        textArea.setMinSize(500D, 300D);
//        textAreaDialog.setResizable(true);
//        root.setCenter(textArea);
//        save.setGraphic(new ImageView("/img/controlbar/add.png"));
//        save.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//        save.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                saveAction.call(textArea.getText());
//            }
//        });
//        customData.setGraphic(new ImageView("/img/controlbar/next.png"));
//        customData.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//        customData.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Popup templetPopup = getPopup();
//                if (customDataSourceCallback == null) {
//                    return;
//                }
//                List<SourceItem<String>> sourceItemList = customDataSourceCallback.call(null);
//                tableView.getItems().clear();
//                tableView.getItems().addAll(sourceItemList);
//                Scene scene = customData.getScene();
//                Window window = scene.getWindow();
//                Point2D point2D = customData.localToScene(window.getX() + scene.getX(),
//                        window.getY() + scene.getY() + customData.getHeight());
//                templetPopup.show(customData, point2D.getX(), point2D.getY());
//            }
//        });
//        VBox operationRoot = new VBox();
//        operationRoot.getChildren().addAll(save, customData);
//        root.setRight(operationRoot);
//        textAreaDialog.getDialogPane().setContent(root);
//        return textAreaDialog;
//    }
//
//    public String getDialogTitle() {
//        return dialogTitle;
//    }
//
//    public void setDialogTitle(String dialogTitle) {
//        this.dialogTitle = dialogTitle;
//    }
//
//    public Callback<String, Object> getSaveAction() {
//        return saveAction;
//    }
//
//    public void setSaveAction(Callback<String, Object> saveAction) {
//        this.saveAction = saveAction;
//    }
//
//    public Callback<S, List<SourceItem<String>>> getCustomDataSourceCallback() {
//        return customDataSourceCallback;
//    }
//
//    public void setCustomDataSourceCallback(Callback<S, List<SourceItem<String>>> customDataSourceCallback) {
//        this.customDataSourceCallback = customDataSourceCallback;
//    }
//
//    public Callback<String, Object> getRemoveAction() {
//        return removeAction;
//    }
//
//    public void setRemoveAction(Callback<String, Object> removeAction) {
//        this.removeAction = removeAction;
//    }
//
//}
