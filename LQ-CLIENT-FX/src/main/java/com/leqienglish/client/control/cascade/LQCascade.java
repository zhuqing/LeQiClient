/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.cascade;


import com.leqienglish.client.control.pop.LQPopup;
import com.leqienglish.client.util.focus.FocusUtil;
import com.leqienglish.client.util.thread.DelayRunner;
import com.leqienglish.client.util.treeitem.TreeItemUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

/**
 *
 * @author zhuqing
 */
public abstract class LQCascade<T> extends TextField {

    private ObservableList<TreeItem<T>> items;

    private Map<Object, TreeItem<T>> dictElementMap;

    private String defTabName;

    /**
     * 选择的值
     */
    private ObjectProperty<TreeItem<T>> value;

    private LQCascadePane<T> hipCascadePane;

    private LQCascadePane<T> searchPane;

    private LQPopup lqPopup;

    private Map<String, List<TreeItem<T>>> countyMap;

    /**
     * 是否正在提交数据
     */
    private boolean committingData = false;

    private final ListChangeListener<TreeItem<T>> itemsChangeListener = new ListChangeListener<TreeItem<T>>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends TreeItem<T>> c) {
            dictElementMap = toMap(getItems());
            countyMap = LQCascade.this.createFilterMap(getItems());
            getHipCascadePane().setItems(items);

        }
    };

    private final ListChangeListener<TreeItem<T>> hipAddressPaneValuesChangeListener = new ListChangeListener<TreeItem<T>>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends TreeItem<T>> c) {
            commitData((List<TreeItem<T>>) c.getList());
        }

    };

    private final ChangeListener<TreeItem<T>> valueChangeListener = new ChangeListener<TreeItem<T>>() {
        @Override
        public void changed(ObservableValue<? extends TreeItem<T>> observable, TreeItem<T> oldValue, TreeItem<T> newValue) {
            getHipCascadePane().getValues().removeListener(hipAddressPaneValuesChangeListener);
            getHipCascadePane().setValues(toList(newValue));
            toText(getHipCascadePane().getValues());
            getHipCascadePane().getValues().addListener(hipAddressPaneValuesChangeListener);
        }
    };

    private final ChangeListener<Boolean> focusChangeListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                popupShow(getHipCascadePane());
            } else {
                getLQPopup().hide();
            }

        }
    };

    /**
     * TableCell点击事件处理
     */
    private final EventHandler<MouseEvent> mouseCilckEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (!getLQPopup().isShowing() && LQCascade.this.isFocused()) {
                popupShow(getHipCascadePane());
            }
        }
    };

    public LQCascade() {
        this.focusedProperty().addListener(focusChangeListener);
        this.setOnMouseClicked(mouseCilckEventHandler);
        JavaFxObservable.changesOf(this.textProperty())
                .filter((c) -> this.isFocused())
                .filter((c) -> !committingData)
                .subscribe((c) -> startQuery());

        JavaFxObservable.eventsOf(this, KeyEvent.KEY_PRESSED)
                .filter((ke) -> ke.getCode() == KeyCode.ENTER)
                .subscribe((ke) -> {
                    popupShow();
                    ke.consume();
                });

    }

    private void startQuery() {
        DelayRunner.delayRunner(this, () -> query(this.getText()));
    }

    private void query(String text) {
        System.err.println("1");
        if (text == null || text.isEmpty()) {
            popupShow(this.getHipCascadePane());
            return;
        }
        List<TreeItem<T>> filtereds = this.filter(text);
        if (filtereds == null) {
            return;
        }
        getSearchPane().setItems(filtereds);
        System.err.println("2");
        popupShow(getSearchPane());

    }

    /**
     * 根据字符串过滤出 匹配的listTreeitem
     *
     * @param filterText
     * @return
     */
    protected List<TreeItem<T>> filter(String filterText) {
        if (this.countyMap == null || this.countyMap.isEmpty() || filterText == null) {
            return Collections.EMPTY_LIST;
        }
        final String filterUpper = filterText.toUpperCase();

        return countyMap.keySet().stream()
                .filter((s) -> s.contains(filterUpper))
                .flatMap((s) -> countyMap.get(s).stream())
                .collect(Collectors.toList());

    }

    /**
     * 创建过滤时使用的Map
     *
     * @return
     */
    protected abstract Map<String, List<TreeItem<T>>> createFilterMap(List<TreeItem<T>> items);

    private void popupShow() {
        System.err.println("3");
        if (!this.isFocused()) {
            getLQPopup().hide();
            return;
        }
        if (!getLQPopup().isShowing()) {
            getLQPopup().showV(LQCascade.this);
        }
    }

    /**
     * 显示弹出框
     */
    private void popupShow(Node content) {
        getLQPopup().getContent().setAll(content);
        popupShow();
    }

    /**
     * @return the items
     */
    public ObservableList<TreeItem<T>> getItems() {
        if (this.items == null) {
            this.items = FXCollections.observableArrayList();
            this.items.addListener(itemsChangeListener);
        }
        return items;
    }

    private void commitData(List<TreeItem<T>> values) {
        committingData = true;
        if (values == null || values.isEmpty()) {
            this.setText("");
            committingData = false;
            return;
        }
        valueProperty().removeListener(valueChangeListener);
        this.setValue(values.get(values.size() - 1));
        if (this.getValue().getChildren().isEmpty()) {
            this.getLQPopup().hide();
        }
        valueProperty().addListener(valueChangeListener);
        toText(values);
        committingData = false;
    }

    /**
     *
     * @param values
     */
    protected abstract void toText(List<TreeItem<T>> values);

    protected abstract String getShowPropertyName();

    /**
     * @param items the items to set
     */
    public void setItems(List<TreeItem<T>> items) {
        this.getItems().setAll(items);
    }

    public LQCascadePane getHipCascadePane() {
        if (this.hipCascadePane == null) {
            hipCascadePane = new LQCascadePane();
            hipCascadePane.setDiaplayPropertyName(getShowPropertyName());
            hipCascadePane.setDefTabName(getDefTabName());
            this.hipCascadePane.getValues().addListener(hipAddressPaneValuesChangeListener);
        }
        return hipCascadePane;
    }

    public void setHipAddressPane(LQCascadePane hipAddressPane) {
        this.hipCascadePane = hipAddressPane;
    }

    public LQCascadePane getSearchPane() {
        if (searchPane == null) {
            searchPane = new LQCascadePane<T>();
            searchPane.setDiaplayPropertyName(getShowPropertyName());
            searchPane.setDefTabName("查询结果");
            JavaFxObservable.changesOf(searchPane.getValues())
                    .filter((c) -> searchPane.getValues().size() == 1)
                    .subscribe((c) -> {
                        TreeItem<T> item = searchPane.getValues().get(0);
                        List<TreeItem<T>> values = TreeItemUtil.getTreeItemsFromParent2Child(item);
                        commitData(values);
                    }
                    );
        }
        return searchPane;
    }

    public void setSearchPane(LQCascadePane searchPane) {
        this.searchPane = searchPane;
    }

    public LQPopup getLQPopup() {
        if (this.lqPopup == null) {
            this.lqPopup = new LQPopup();
            this.lqPopup.setAutoHide(true);
            this.lqPopup.setAutoFix(true);
            this.lqPopup.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (LQCascade.this.getValue() != null) {
                        FocusUtil.focusToNext(LQCascade.this);
                    }

                }
            });
        }
        return lqPopup;
    }

    public void setLQPopup(LQPopup hipPopup) {
        this.lqPopup = hipPopup;
    }

    /**
     * @return the value
     */
    public TreeItem<T> getValue() {
        return valueProperty().getValue();
    }

    /**
     * @return the value
     */
    public ObjectProperty<TreeItem<T>> valueProperty() {
        if (this.value == null) {
            this.value = new SimpleObjectProperty<>();
            //  JavaFxObservable.valuesOf(value).subscribe(onNext)

            this.value.addListener(valueChangeListener);
        }
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(TreeItem<T> value) {
        this.valueProperty().setValue(value);
    }

    public void setValuebyKey(Object dictElementId) {
        if (this.getDictElementMap().containsKey(dictElementId)) {
            setValue(this.getDictElementMap().get(dictElementId));
        } else {
            setValue(null);
        }
    }

    /**
     * @return the dictElementMap
     */
    public Map<Object, TreeItem<T>> getDictElementMap() {
        if (this.dictElementMap == null) {
            if (this.getItems().isEmpty()) {
                return null;
            }
            this.dictElementMap = this.toMap(this.getItems());
        }
        return dictElementMap;
    }

    protected Map<Object, TreeItem<T>> toMap(List<TreeItem<T>> list) {
        Map<Object, TreeItem<T>> map = new HashMap<>();
        for (TreeItem<T> item : list) {
            map.put(getValue(item.getValue()), item);
            if (!item.getChildren().isEmpty()) {
                map.putAll(this.toMap(item.getChildren()));
            }
        }

        return map;
    }

    protected abstract Object getValue(T t);

    public List<TreeItem<T>> toList(TreeItem<T> treeItems) {
        if (treeItems == null) {
            return Collections.EMPTY_LIST;
        }
        List<TreeItem<T>> list = new ArrayList<>();

        do {
            list.add(0, treeItems);
            treeItems = treeItems.getParent();
        } while (treeItems != null);
        return list;
    }

    public String getDefTabName() {
        return defTabName;
    }

    public void setDefTabName(String defTabName) {
        this.defTabName = defTabName;
    }

}
