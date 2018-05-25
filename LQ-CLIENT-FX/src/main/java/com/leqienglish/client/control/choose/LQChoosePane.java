/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.choose;


import com.leqienglish.client.util.sourceitem.SourceItem;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.*;

/**
 *
 * @author zhuqing
 */
public abstract class LQChoosePane<T,N extends Node> extends FlowPane {

    /**
     * 数据源
     */
    private ObservableList<SourceItem<T>> sourceItems;

    private final ListChangeListener<SourceItem<T>> sourceItemsChangeListener = new ListChangeListener<SourceItem<T>>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends SourceItem<T>> c) {
           sourceItemChangeHandler();
        }
    };

    
    
    public LQChoosePane() {
        this.getStyleClass().add("hip-choose-pane");
        this.getSourceItems().addListener(sourceItemsChangeListener);
    }

    public ObservableList<SourceItem<T>> getSourceItems() {
        if(sourceItems == null){
            this.sourceItems = FXCollections.observableArrayList();
        }
        return sourceItems;
    }

    public void setSourceItems(List<SourceItem<T>> sourceItems) {
        this.getSourceItems().setAll(sourceItems);
    }
    
    protected void sourceItemChangeHandler(){
         getChildren().setAll(creatChildren(getSourceItems()));
    }

    protected abstract List<N> creatChildren(List<SourceItem<T>> sourceItems);

}
