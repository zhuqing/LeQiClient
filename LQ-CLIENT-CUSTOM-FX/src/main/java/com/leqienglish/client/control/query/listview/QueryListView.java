package com.leqienglish.client.control.query.listview;

import com.leqienglish.client.control.view.listview.LQListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import javafx.geometry.Pos;

/**
 * Created by zhuleqi on 2018/6/29.
 */
public class QueryListView<T> extends VBox {
    private LQListView<T> lqListView;

    private HBox queryBar;

    public LQListView<T> getLqListView() {
        return lqListView;
    }

    public void setLqListView(LQListView<T> lqListView) {
        this.lqListView = lqListView;
    }

    public HBox getQueryBar() {
        return queryBar;
    }

    public void setQueryBar(HBox queryBar) {
        this.queryBar = queryBar;
    }


    
    
    public QueryListView(){
        if(this.queryBar !=null){
            this.getChildren().add(queryBar);
        }

        if(this.getLqListView() !=null){
            this.getChildren().add(this.getLqListView());
        }

    }
}
