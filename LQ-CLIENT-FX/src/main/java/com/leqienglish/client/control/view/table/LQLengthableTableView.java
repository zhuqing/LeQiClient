/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table;


import com.leqienglish.client.control.form.cell.LQFormCell;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.sources.ListChange;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author zhuqing
 */
public class LQLengthableTableView<S> extends GridPane {

    private ObservableList<S> items;

    private ObservableList<LQFormCell> selectedHipFormCells;

    private final static String CUSTOM = "CUSTOM";
    private final static String DISPOSABLE = "Disposable";
    private final static String TITLE = "title";
    private final static String HOVER_STYLE = "index-cell-hover";
    private final static String SELECT_STYLE = "index-cell-selected";

    private double startX = 0.0;
    private double startY = 0.0;

    private final EventHandler<MouseEvent> mousePressedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            startX = event.getX();
            startY = event.getY();
        }
    };

    private final EventHandler<MouseEvent> mouseDraggedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            setSelected(startX, startY, event.getX(), event.getY());
        }
    };

    /**
     * 行起始的索引
     */
    public int startRowIndex;

    /**
     * 用户配置的Tablecell
     */
    public Map<Integer, LQFormCell> hipTableCellMaps;

    public LQLengthableTableView() {
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedHandler);
        this.getStyleClass().add("hip-lengthable-table-view");
    }

    private void setSelected(double startX, double startY, double endX, double endY) {

        if (startX > endX) {
            double temp = startX;
            startX = endX;
            endX = temp;
        }

        if (startY > endY) {
            double temp = startY;
            startY = endY;
            endY = temp;
        }

        for (Node node : this.getChildren()) {

            if (!node.getProperties().containsKey(CUSTOM)) {
                continue;
            }

            if (!(node instanceof LQFormCell)) {
                continue;
            }

            double layoutX = node.getLayoutX();
            double layoutY = node.getLayoutY();
            LQFormCell cell = (LQFormCell) node;
            cell.getStyleClass().remove(SELECT_STYLE);
            if (layoutX > startX && layoutX <= endX && layoutY > startY && layoutY < endY) {
                cell.getStyleClass().add(SELECT_STYLE);
            }
        }
    }

    /**
     * 只显示选中的数据 不可恢复
     */
    public void onlyShowSelect() {
        if (this.getSelectedHipFormCells().isEmpty()) {
            return;
        }
        this.getChildren().stream()
                .map((Node node) -> (LQFormCell) node)
                .filter((LQFormCell formCell) -> {
                    formCell.getStyleClass().remove(HOVER_STYLE);
                    formCell.getStyleClass().remove(TITLE);
                    Disposable disposable = (Disposable) formCell.getProperties().get(DISPOSABLE);
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    if (getSelectedHipFormCells().contains(formCell)) {
                        formCell.getStyleClass().remove(SELECT_STYLE);

                        return false;
                    } else {
                        return true;
                    }
                })
                .forEach((LQFormCell cell) -> cell.setText(""));

    }

    @Override
    public LQLengthableTableView<S> clone() throws CloneNotSupportedException {
        LQLengthableTableView view = new LQLengthableTableView();
        List<LQFormCell> newSelecteds = new ArrayList<>();
        List<LQFormCell> formcells = this.getChildren().stream().filter((Node node) -> node instanceof LQFormCell).map((Node node) -> {
            LQFormCell formCell = (LQFormCell) node;
            LQFormCell newFormCell = formCell.clone();
            if (getSelectedHipFormCells().contains(formCell)) {
                newSelecteds.add(newFormCell);
            }
            GridPane.setColumnIndex(newFormCell, GridPane.getColumnIndex(node));
            GridPane.setRowIndex(newFormCell, GridPane.getRowIndex(node));
            GridPane.setRowSpan(newFormCell, GridPane.getRowSpan(node));
            GridPane.setColumnSpan(newFormCell, GridPane.getColumnSpan(node));
            newFormCell.getStyleClass().add(TITLE);
            return newFormCell;
        }).collect(Collectors.toList());

        view.getChildren().setAll(formcells);
        view.getSelectedHipFormCells().setAll(newSelecteds);
        view.setItems(items);
        return view;
    }

    public void initTableCellMap() {
        if (hipTableCellMaps != null && !hipTableCellMaps.isEmpty()) {
            return;
        }
        if (hipTableCellMaps == null) {
            hipTableCellMaps = new HashMap<>();
        }
        for (Node node : this.getChildren()) {
            if (!(node instanceof LQFormCell)) {
                continue;
            }

            int columnSpan = GridPane.getColumnSpan(node) == null ? 1 : GridPane.getColumnSpan(node);
            if (columnSpan > 1) {
                continue;
            }
            LQFormCell formCell = (LQFormCell) node;
            int columnIndex = GridPane.getColumnIndex(formCell);
            hipTableCellMaps.put(columnIndex, formCell);
        }

        startRowIndex = intStartIndex();
    }

    private void formCellSelected(LQFormCell formCell) {
        if (formCell.getStyleClass().contains(SELECT_STYLE)) {
            formCell.getStyleClass().remove(SELECT_STYLE);
            getSelectedHipFormCells().remove(formCell);
        } else {
            formCell.getStyleClass().add(SELECT_STYLE);
            getSelectedHipFormCells().add(formCell);
        }
    }

    private void initLayout() {
        if (startRowIndex == 0) {
            startRowIndex = intStartIndex();
        }
        int startRow = this.startRowIndex;
        List<Node> nodes = new ArrayList<>();
        for (S item : this.getItems()) {
            for (Integer columnIndex : this.hipTableCellMaps.keySet()) {
                if (!this.hipTableCellMaps.containsKey(columnIndex)) {
                    continue;
                }
                LQFormCell cell = this.hipTableCellMaps.get(columnIndex);
                LQFormCell formCell = clone(cell);
                formCell.updateItem(item, false);
                GridPane.setRowIndex(formCell, startRow);
                GridPane.setColumnIndex(formCell, columnIndex);
                nodes.add(formCell);
            }
            startRow++;
        }
        this.getChildren().addAll(nodes);
    }

    public LQFormCell clone(LQFormCell hipFormCell) {
        LQFormCell formCell = hipFormCell.clone();
        Disposable disposable = JavaFxObservable.eventsOf(formCell, MouseEvent.MOUSE_CLICKED)
                .filter((MouseEvent event) -> event.getClickCount() == 1 && (event.getSource() instanceof LQFormCell))
                .map((MouseEvent event) -> (LQFormCell) event.getSource())
                .subscribe((LQFormCell cell) -> formCellSelected(cell));
        formCell.getStyleClass().remove(TITLE);//移除title样式名
        formCell.getStyleClass().add(HOVER_STYLE);
        formCell.getProperties().put(CUSTOM, CUSTOM);
        formCell.getProperties().put(DISPOSABLE, disposable);
        return formCell;
    }

    public int intStartIndex() {
        int startIndex = 0;
        for (Node node : this.getChildren()) {
            int rowIndex = GridPane.getRowIndex(node);
            int rowSpan = GridPane.getRowSpan(node) == null ? 1 : GridPane.getRowSpan(node);
            if (startIndex < rowIndex + rowSpan - 1) {
                startIndex = rowIndex + rowSpan - 1;
            }
        }

        return startIndex + 1;
    }

    public void clearChildren() {
        List<Node> customNodes = this.getChildren().stream().filter((Node node) -> node.getProperties().containsKey(CUSTOM)).collect(Collectors.toList());
        this.getChildren().removeAll(customNodes);
    }

    /**
     * @return the items
     */
    public ObservableList<S> getItems() {
        if (items == null) {
            items = FXCollections.observableArrayList();
            JavaFxObservable.changesOf(items).subscribe((ListChange<S> c) -> {
                initTableCellMap();
                clearChildren();
                initLayout();
            });

        }
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<S> items) {
        this.getItems().setAll(items);
    }

    /**
     * @return the selectedHipFormCells
     */
    public ObservableList<LQFormCell> getSelectedHipFormCells() {
        if (selectedHipFormCells == null) {
            selectedHipFormCells = FXCollections.observableArrayList();
        }

        return selectedHipFormCells;
    }

    /**
     * @param selectedHipFormCells the selectedHipFormCells to set
     */
    public void setSelectedHipFormCells(ObservableList<LQFormCell> selectedHipFormCells) {
        this.selectedHipFormCells = selectedHipFormCells;
    }
}
