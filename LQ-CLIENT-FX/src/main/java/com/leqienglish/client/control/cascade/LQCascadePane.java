/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.cascade;


import com.leqienglish.client.control.view.table.column.LQTableColumn;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.control.view.table.cell.text.LQTableTextCell;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

/**
 *
 * @author zhuqing
 */
public class LQCascadePane<T> extends TabPane {

    /**
     * 数据源列表
     */
    private ObservableList<TreeItem<T>> items;

    /**
     * T中显示的属性
     */
    private String diaplayPropertyName;
    /**
     * 选中的值列表
     */
    private ObservableList<TreeItem<T>> values;

    private String defTabName;

    /**
     * 当前正在显示的TableView
     */
    private LQTableView<TreeItem<T>> currentTableView;

    private final ListChangeListener<TreeItem<T>> itemsChangeListener = new ListChangeListener<TreeItem<T>>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends TreeItem<T>> c) {
            itemsChangeHandler(getItems());
        }
    };
    private final ListChangeListener<TreeItem<T>> valuesChangeListener = new ListChangeListener<TreeItem<T>>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends TreeItem<T>> c) {
            valueChangeHandler(getValues());
        }
    };

    /**
     *
     */
    private final ChangeListener<Number> tabSelectedChangeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            tabSelectedChangeHandler(newValue.intValue());
        }
    };

    public LQCascadePane() {
        this.getSelectionModel().selectedIndexProperty().addListener(tabSelectedChangeListener);
        this.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP) {
                    System.err.println("tableView");
                    if (currentTableView != null) {
                        System.err.println("tableView focus");
                        currentTableView.requestFocus();
                    }

                    return;
                }

                if (event.getCode() == KeyCode.LEFT) {
                    if (getSelectionModel().getSelectedIndex() > 0) {
                        getSelectionModel().select(getSelectionModel().getSelectedIndex() - 1);
                    }
                    return;
                }
            }
        });
        this.getStyleClass().add("hip-cascade-pane");
    }

    private void itemsChangeHandler(List<TreeItem<T>> items) {
        reset();
    }

    private void valueChangeHandler(List<TreeItem<T>> values) {
        this.getTabs().clear();
        this.getTabs().add(this.creataTab(null));
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).getChildren().isEmpty()) {
                continue;
            }
            this.getTabs().add(this.creataTab(values.get(i)));
        }

        this.getSelectionModel().select(this.getTabs().size() - 1);
    }

    private void tableViewSelectedHandler(TreeItem<T> selected) {
        this.getValues().removeListener(valuesChangeListener);
        int index = this.getSelectionModel().getSelectedIndex();
        this.getTabs().remove(index + 1, this.getTabs().size());
        this.getValues().remove(index, this.getValues().size());
        this.getValues().add(selected);
        this.getValues().addListener(valuesChangeListener);
        if (selected.getChildren().isEmpty()) {
            return;
        }
        this.getTabs().add(creataTab(selected));
        this.getSelectionModel().select(this.getTabs().size() - 1);
        if (currentTableView != null) {
            currentTableView.requestFocus();
        }
    }

    private void tabSelectedChangeHandler(int tabIndex) {
        if (tabIndex >= this.getTabs().size() || tabIndex < 0) {
            return;
        }
        currentTableView = (LQTableView<TreeItem<T>>) this.getTabs().get(tabIndex).getContent();

        if (tabIndex + 1 == this.getTabs().size()) {
            return;
        }
        this.getTabs().remove(tabIndex + 1, this.getTabs().size());

    }

    /**
     * 重置地址面板
     */
    public void reset() {
        this.getTabs().clear();
        this.getTabs().add(creataTab(null));
        this.getSelectionModel().select(0);
        if (currentTableView != null) {
            currentTableView.requestFocus();
        }
    }

    /**
     * 创建tab
     *
     * @param treeItem
     * @return
     */
    private Tab creataTab(TreeItem<T> treeItem) {
        List<TreeItem<T>> subTreeItems = null;
        if (treeItem == null) {
            subTreeItems = this.getItems();
        } else {
            subTreeItems = treeItem.getChildren();
        }

        Tab tab = new Tab();
        tab.setClosable(false);

        if (subTreeItems == null || subTreeItems.isEmpty()) {
            return tab;
        }
        LQTableView<TreeItem<T>> tableView = createTableView();

        JavaFxObservable.eventsOf(tableView, KeyEvent.KEY_PRESSED)
                .filter((ev) -> ev.getCode() == KeyCode.ENTER)
                .filter((ev) -> tableView.getSelectionModel().getSelectedItem() != null)
                .subscribe((ev) -> {
                    tableViewSelectedHandler(tableView.getSelectionModel().getSelectedItem());
                });

        currentTableView = tableView;
        tableView.getItems().setAll(subTreeItems);
        tableView.getSelectionModel().select(0);

        tab.setContent(tableView);
        if (treeItem == null) {
            tab.setText(getDefTabName());
        } else {
            tab.setText(getDisplayName(treeItem));
        }

        return tab;

    }

    /**
     * 获取T中显示的名称
     *
     * @param t
     * @return
     */
    protected String getDisplayName(TreeItem<T> t) {
        T value = t.getValue();
        if (value == null) {
            return "";
        }
        try {
            return (String) PropertyReflectUtil.getValue(value, getDiaplayPropertyName());

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(LQCascadePane.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    /**
     * 创建TableView
     *
     * @return
     */
    private LQTableView<TreeItem<T>> createTableView() {
        LQTableView<TreeItem<T>> tableView = new LQTableView<TreeItem<T>>();
        LQTableColumn tableColumn = new LQTableColumn();
        tableColumn.setPercentWidth(100.0);
        tableColumn.setPropertyName(getDiaplayPropertyName());
        LQTableTextCell tableCell = new LQTableTextCell();

        tableCell.setMouseClickEventHander((item) -> tableViewSelectedHandler((TreeItem<T>) item));
        tableCell.setEnterEventHandler((item) -> tableViewSelectedHandler((TreeItem<T>) item));
        tableColumn.setTableCell(tableCell);
        tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TreeItem, String>, ObservableValue<String>>() {
            ObjectProperty<String> valueProperty = new SimpleObjectProperty<String>();

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TreeItem, String> param) {
                if (param == null || param.getValue() == null) {
                    valueProperty.set("");
                } else {
                    valueProperty.set(getDisplayName(param.getValue()));
                }

                return valueProperty;
            }
        });
        tableView.getColumns().setAll(tableColumn);
        tableView.getStyleClass().add("addressPane_table");
        return tableView;
    }

    public ObservableList<TreeItem<T>> getItems() {
        if (this.items == null) {
            this.items = FXCollections.observableArrayList();
            this.items.addListener(itemsChangeListener);
        }
        return items;
    }

    public void setItems(List<TreeItem<T>> items) {
        this.getItems().setAll(items);
    }

    public ObservableList<TreeItem<T>> getValues() {
        if (this.values == null) {
            this.values = FXCollections.observableArrayList();
            this.values.addListener(valuesChangeListener);
        }
        return values;
    }

    public void setValues(List<TreeItem<T>> values) {
        this.getValues().setAll(values);
    }

    /**
     * @return the diaplayPropertyName
     */
    public String getDiaplayPropertyName() {
        return diaplayPropertyName;
    }

    /**
     * @param diaplayPropertyName the diaplayPropertyName to set
     */
    public void setDiaplayPropertyName(String diaplayPropertyName) {
        this.diaplayPropertyName = diaplayPropertyName;
    }

    public String getDefTabName() {
        if (defTabName == null) {
            defTabName = "省/直辖市";
        }
        return defTabName;
    }

    public void setDefTabName(String defTabName) {
        this.defTabName = defTabName;
    }

}
