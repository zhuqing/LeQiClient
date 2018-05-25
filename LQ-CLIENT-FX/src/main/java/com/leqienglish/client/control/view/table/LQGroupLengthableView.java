/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table;


import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.sources.ListChange;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;

/**
 *
 * @author zhangyingchuang
 */
public class LQGroupLengthableView<T, S extends TreeItem<T>> extends LQLengthableTableView<S> {

    private ObservableList<S> items;
    private Map<S, Map<String, Integer>> itemRowSpanMap = new HashMap<>();

    protected void initLayout() {
        if (startRowIndex == 0) {
            startRowIndex = intStartIndex();
        }
        int startRow = this.startRowIndex;
        itemRowSpanMap.clear();
        List<Node> nodes = new ArrayList<>();
        for (S item : this.getItems()) {
            for (Integer columnIndex : this.hipTableCellMaps.keySet()) {
                if (!this.hipTableCellMaps.containsKey(columnIndex)) {
                    continue;
                }
                LQFormCell cell = this.hipTableCellMaps.get(columnIndex);
                int rowSpan = 1;
                if (GridPane.getRowSpan(cell) != null) {
                    String propertyName = cell.getPropertyName();
                    rowSpan = getRowSpan(item, 1, propertyName);
                    Map<String, Integer> map = itemRowSpanMap.get(item);
                    if (map == null) {
                        map = new HashMap<>();
                        itemRowSpanMap.put(item, map);//存放实体与属性跨度对应关系
                    }
                    map.put(propertyName, rowSpan);//存放当前实体属性对应的行跨度值
                    cell.setPrefHeight(cell.getHeight() * rowSpan);
                    S parent = (S) item.getParent();
                    if (parent != null && compareValue(parent, item, propertyName)) {
                        if (itemRowSpanMap.get(parent).get(propertyName) > parent.getChildren().indexOf(item)) {//判断是否被合并
                            continue;
                        }
                    }
                }
                LQFormCell formCell = clone(cell);
                formCell.updateItem(item.getValue(), false);
                GridPane.setRowIndex(formCell, startRow);
                GridPane.setColumnIndex(formCell, columnIndex);
                GridPane.setRowSpan(formCell, rowSpan);
                nodes.add(formCell);
            }
            startRow++;
        }
        this.getChildren().addAll(nodes);
    }

    /**
     * @return the items
     */
    @Override
    public ObservableList<S> getItems() {
        if (items == null) {
            items = FXCollections.observableArrayList();
            JavaFxObservable.changesOf(items).subscribe((ListChange<S> c) -> {
                super.initTableCellMap();
                clearChildren();
                initLayout();
            });

        }
        return items;
    }

    /**
     * @param items the items to set
     */
    @Override
    public void setItems(List<S> items) {
        this.getItems().setAll(items);
    }

    /**
     * 获得需要合并的单元格格式
     */
    private int getRowSpan(S item, int index, String propertyName) {
        if (item == null) {
            return index;
        } else {
            if (item.getChildren() == null || item.getChildren().isEmpty()) {
                return index;
            } else {
                for (int i = 0; i < item.getChildren().size(); i++) {
                    S treeItem = (S) item.getChildren().get(i);
                    if (compareValue(item, treeItem, propertyName)) {
                        index = index + 1;
                        index = index + getRowSpan(treeItem, 0, propertyName);
                    } else {
                        break;
                    }
                }
            }
        }
        return index;
    }

    /**
     * 判断需要合并的单元格内容是否相同
     */
    private boolean compareValue(S parent, S item, String propertyName) {
        if (parent == null || item == null) {
            return false;
        }
        try {
            Object parentValue = PropertyReflectUtil.getValue(parent.getValue(), propertyName);
            Object itemValue = PropertyReflectUtil.getValue(item.getValue(), propertyName);
            if (parentValue == null && itemValue == null) {
                return true;
            } else if (parentValue == null) {
                return false;
            } else if (itemValue == null) {
                return false;
            } else {
                return parentValue.equals(itemValue);
            }
        } catch (Exception e) {
            Logger.getLogger("属性值获取失败");
        }
        return false;

    }
}
