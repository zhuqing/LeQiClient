/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.column;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;
import com.leqienglish.client.control.view.table.cell.LQTableCell;
import com.leqienglish.client.util.concurrent.HipExecutors;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @param <S>
 * @param <T>
 */
public class LQTableColumn<S, T> extends TableColumn<S, T> {

    private Double percentWidth;

    private String propertyName;

    private TableColumnSortPolicy sortPolicy;

    private ChangeListener<Number> viewWidthListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (percentWidth == null || percentWidth == 0D) {
                return;
            }
            LQTableColumn.this.setPrefWidth(newValue.doubleValue() * percentWidth / 103);
        }
    };

    private ListChangeListener itemsChangeListener = new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change c) {
            itemsChangeHandler(getTableView().getItems());
        }
    };

    private LQTableCell<S, T> tableCell;

    public Double getPercentWidth() {
        return percentWidth;
    }

    public void itemsChangeHandler(List<S> items) {

    }

    @Override
    public LQTableColumn<S, T> clone() throws CloneNotSupportedException {
        LQTableColumn column = new LQTableColumn();
        column.setPercentWidth(percentWidth);
        column.setPropertyName(propertyName);
        column.setSortPolicy(sortPolicy);
        column.setTableCell(tableCell.clone());
        column.setText(this.getText());
        return column;
    }

    public void setPercentWidth(Double percentWidth) {
        this.percentWidth = percentWidth;
    }

    public LQTableCell<S, T> getTableCell() {
        return tableCell;
    }

    public void setTableCell(LQTableCell<S, T> tableCell) {
        this.tableCell = tableCell;
        this.setCellFactory(new Callback<TableColumn<S, T>, TableCell<S, T>>() {
            @Override
            public TableCell<S, T> call(TableColumn<S, T> param) {

                LQTableCell cloneLQTableCell;
                try {
                    cloneLQTableCell = LQTableColumn.this.tableCell.clone();
                    return cloneLQTableCell;
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(LQTableColumn.class.getName()).log(Level.SEVERE, null, ex);
                }

                return null;

            }
        });
        resetCellValueFactory();
    }

    protected void resetCellValueFactory() {
        this.setCellValueFactory(new Callback<CellDataFeatures<S, T>, ObservableValue<T>>() {
            ObjectProperty<T> valueProperty = new SimpleObjectProperty<>();

            @Override
            public ObservableValue<T> call(CellDataFeatures<S, T> param) {
                try {
                    valueProperty.set((T) PropertyReflectUtil.getValue(param.getValue(), propertyName));
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    Logger.getLogger(LQTableColumn.class.getName()).log(Level.SEVERE, null, ex);
                }
                return valueProperty;
            }
        });
    }

    public LQTableColumn() {
        //this.setSortable(false);
        this.tableViewProperty().addListener(new ChangeListener<TableView<S>>() {
            @Override
            public void changed(ObservableValue<? extends TableView<S>> observable, TableView<S> oldValue, TableView<S> newValue) {
                if (oldValue != null) {
                    oldValue.widthProperty().removeListener(viewWidthListener);
                    oldValue.getItems().removeListener(itemsChangeListener);
                }
                if (newValue != null && percentWidth != null) {
                    newValue.widthProperty().addListener(viewWidthListener);

                }

                if (newValue != null) {
                    newValue.getItems().addListener(itemsChangeListener);
                }
            }
        });

        if (this.getTableView() != null) {
            this.getTableView().getItems().addListener(itemsChangeListener);
        }
        /**
         * 设置sort监听
         */
        this.sortTypeProperty().addListener(new ChangeListener<SortType>() {
            @Override
            public void changed(ObservableValue<? extends SortType> observable, SortType oldValue, SortType newValue) {
                if (null != newValue && null != oldValue) {
                    if (oldValue.compareTo(newValue) != 0) {
                        if (sortPolicy != null) {
                            getData(newValue);
                        }
                    }
                }
            }
        });
    }

    /**
     * @return the propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @param propertyName the propertyName to set
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * 設置排序方法
     *
     * @param sortPolicy
     */
    public void setSortPolicy(TableColumnSortPolicy sortPolicy) {
        this.sortPolicy = sortPolicy;
    }

    /**
     * 获取数据并将数据设置到tableView中
     *
     * @param sortType
     */
    private void getData(SortType sortType) {
        HipExecutors.getSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ObservableList<S> list = sortPolicy.getData(tableViewProperty().get(), LQTableColumn.this, getId(), sortType);
                if (null != list) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            tableViewProperty().get().setItems(list);
                        }
                    });
                }
            }
        });
    }

    /**
     *
     */
    public interface TableColumnSortPolicy<S> {

        public abstract ObservableList<S> getData(TableView table, TableColumn column, String columnId, TableColumn.SortType sortType);
    }
}
