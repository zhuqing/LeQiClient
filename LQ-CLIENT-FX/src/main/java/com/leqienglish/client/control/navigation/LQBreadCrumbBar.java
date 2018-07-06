/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.navigation;

import com.leqienglish.client.control.button.LQButton;
import com.leqienglish.client.util.sourceitem.SourceItem;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.controlsfx.control.BreadCrumbBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhuleqi
 */
public class LQBreadCrumbBar<T extends SourceItem> extends HBox {

    BreadCrumbBar<SourceItem> breadCrumbBar;

    private ObjectProperty<SourceItem> selected;

    public LQBreadCrumbBar() {

    }

    private void initBreadCrumbBar(TreeItem<SourceItem> treeItem) {
        if (this.breadCrumbBar != null) {
            return;
        }
        this.breadCrumbBar = new BreadCrumbBar(treeItem);
        breadCrumbBar.setAutoNavigationEnabled(true);

        this.breadCrumbBar.setCrumbFactory(new Callback<TreeItem<SourceItem>, Button>() {
            @Override
            public Button call(TreeItem<SourceItem> param) {
                Button button = new Button(param.getValue().getDisplay());
                return button;
            }
        });

        JavaFxObservable.changesOf(this.breadCrumbBar.selectedCrumbProperty())
                .subscribe((c) -> this.setSelected(c.getNewVal().getValue()));
        this.getChildren().add(this.breadCrumbBar);
    }

    public void add(SourceItem item) {

        TreeItem<SourceItem> treeItem = new TreeItem<>();
        treeItem.setValue(item);
        if (breadCrumbBar == null) {
            initBreadCrumbBar(treeItem);
            this.setSelected(item);
        } else if (this.breadCrumbBar.getSelectedCrumb() != null) {
            this.breadCrumbBar.getSelectedCrumb().getChildren().setAll(treeItem);
           this.breadCrumbBar.setSelectedCrumb(treeItem);
        }

  

        // this.setSelected(item);
    }

    public void clear() {

    }

    /**
     * @return the selected
     */
    public SourceItem getSelected() {
        return selectedProperty().getValue();
    }

    /**
     * @return the selected
     */
    public ObjectProperty<SourceItem> selectedProperty() {
        if (selected == null) {
            selected = new SimpleObjectProperty<SourceItem>();

        }
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(SourceItem selected) {
        this.selectedProperty().setValue(selected);
    }
}
