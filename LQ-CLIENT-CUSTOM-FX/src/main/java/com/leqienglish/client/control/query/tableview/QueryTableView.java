package com.leqienglish.client.control.query.tableview;

import com.leqienglish.client.control.view.listview.LQListView;
import com.leqienglish.client.control.view.table.LQTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by zhuleqi on 2018/6/29.
 */
public class QueryTableView<T> extends VBox{
    private LQTableView<T> lqTableView;

    private HBox queryBar;

    public QueryTableView(){
        if(this.queryBar !=null){
            this.getChildren().add(queryBar);
        }

        if(this.getLqTableView() !=null){
            this.getChildren().add(this.getLqTableView());
        }

    }

    public LQTableView<T> getLqTableView() {
        return lqTableView;
    }

    public void setLqTableView(LQTableView<T> lqTableView) {
        this.lqTableView = lqTableView;
    }

    public HBox getQueryBar() {
        return queryBar;
    }

    public void setQueryBar(HBox queryBar) {
        this.queryBar = queryBar;
    }
}
