/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.gridview;


import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.util.node.NodeUtil;
import com.leqienglish.client.util.observation.LQObservableUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.util.Callback;

/**
 *
 * @author zhuqing
 */
public class LQGridView<S extends Object> extends ScrollPane {

    /**
     * 选择模式
     */
    private SelectionMode selectMode;

    private GridViewSelectionModel gridViewSelectionModel;

    private ObservableList<S> items;

    private final TilePane tilePane;

    private LQFormView<S> hipFormView;

    private Map<S, LQFormView<S>> formViewMap;

    /**
     *
     */
    private Callback<S, List<String>> stylesCallback;

    private PseudoClass pseudoClass = PseudoClass.getPseudoClass("selected");

    private final ListChangeListener<S> selectItemsChangeListener = new ListChangeListener<S>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends S> c) {
            while (c.next()) {
                for (S s : c.getAddedSubList()) {
                    if (!getFormViewMap().containsKey(s)) {
                        continue;
                    }

                    LQFormView view = getFormViewMap().get(s);
                    view.pseudoClassStateChanged(pseudoClass, true);
                }

                for (S s : c.getRemoved()) {
                    if (!getFormViewMap().containsKey(s)) {
                        continue;
                    }

                    LQFormView view = getFormViewMap().get(s);
                    view.pseudoClassStateChanged(pseudoClass, false);
                }
            }
        }
    };

    private final EventHandler<MouseEvent> selectEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getClickCount() != 1) {
                return;
            }

            if (event.getSource() instanceof LQFormView) {
                LQFormView<S> fromView = (LQFormView) event.getSource();
                getGridViewSelectionModel().select(fromView.getValue());

            }
        }
    };

    private final ListChangeListener<S> itemsChangeListener = new ListChangeListener<S>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends S> c) {
            itemChangeHandler();
        }
    };

    private final ChangeListener<Number> widthChangeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            tilePane.setMaxWidth(newValue.doubleValue());
        }
    };

    public LQGridView() {
        tilePane = new TilePane();
        tilePane.getStyleClass().add("tile-pane");
        this.setContent(tilePane);
        LQObservableUtil.addListener(this.widthProperty(), LQGridView.class, widthChangeListener);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        itemChangeHandler();
        this.getStyleClass().add("hip-grid-view");
        LQObservableUtil.addListener(this.getGridViewSelectionModel().getSelectedItems(), LQGridView.class, selectItemsChangeListener);

    }

    /**
     * @return the items
     */
    public ObservableList<S> getItems() {
        if (items == null) {
            items = FXCollections.observableArrayList();
            LQObservableUtil.addListener(items, LQGridView.class, itemsChangeListener);
        }

        return items;
    }

    protected void itemChangeHandler() {
        this.tilePane.getChildren().clear();
        this.getGridViewSelectionModel().clearSelection();
        if (this.getItems().isEmpty() || this.getLQFormView() == null) {
            return;
        }
        this.formViewMap = new HashMap<>();
        for (S s : this.getItems()) {
            LQFormView<S> formView = this.getLQFormView().clone();
            formView.setValue(s);
            this.tilePane.getChildren().add(formView);
            formView.addEventFilter(MouseEvent.MOUSE_CLICKED, selectEventHandler);
            formViewMap.put(s, formView);
            resetStyles(formView, s);
        }
    }

    /**
     * 重置样式
     *
     * @param formView
     * @param s
     */
    private void resetStyles(LQFormView<S> formView, S s) {

        if (this.getStylesCallback() == null || s == null) {
            NodeUtil.addCustomStyles(Collections.EMPTY_LIST, this);
            return;
        }
        NodeUtil.addCustomStyles(this.getStylesCallback().call(s), formView);
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<S> items) {
        this.getItems().setAll(items);
    }

    /**
     * @return the hipFormView
     */
    public LQFormView<S> getLQFormView() {
        return hipFormView;
    }

    /**
     * @param hipFormView the hipFormView to set
     */
    public void setLQFormView(LQFormView<S> hipFormView) {
        this.hipFormView = hipFormView;
    }

    /**
     * @return the selectMode
     */
    public SelectionMode getSelectMode() {
        if (selectMode == null) {
            selectMode = SelectionMode.SINGLE;
        }
        return selectMode;
    }

    /**
     * @param selectMode the selectMode to set
     */
    public void setSelectMode(SelectionMode selectMode) {
        this.selectMode = selectMode;
    }

    /**
     * @return the gridViewSelectionModel
     */
    public GridViewSelectionModel<S> getGridViewSelectionModel() {
        if (gridViewSelectionModel == null) {
            gridViewSelectionModel = new GridViewSelectionModel<S>(this);

        }
        return gridViewSelectionModel;
    }

    /**
     * @param gridViewSelectionModel the gridViewSelectionModel to set
     */
    public void setGridViewSelectionModel(GridViewSelectionModel<S> gridViewSelectionModel) {
        this.gridViewSelectionModel = gridViewSelectionModel;
    }

    /**
     * @return the formViewMap
     */
    public Map<S, LQFormView<S>> getFormViewMap() {
        if (this.formViewMap == null) {
            formViewMap = new HashMap<>();
        }
        return formViewMap;
    }

    /**
     *
     * @return the stylesCallback
     */
    public Callback<S, List<String>> getStylesCallback() {
        return stylesCallback;
    }

    /**
     *
     * @param stylesCallback the stylesCallback to set
     */
    public void setStylesCallback(Callback<S, List<String>> stylesCallback) {
        this.stylesCallback = stylesCallback;
    }

}
