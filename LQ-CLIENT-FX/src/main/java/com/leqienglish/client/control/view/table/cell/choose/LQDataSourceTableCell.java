/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.cell.choose;

import com.leqienglish.client.control.view.table.cell.LQTableCell;
import com.leqienglish.client.util.sourceitem.SourceItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhuqing
 */
public class LQDataSourceTableCell<S, T> extends LQTableCell<S, T> {

    private final static Logger logger = LoggerFactory.getLogger(LQDataSourceTableCell.class);
    private Callback<S, List<SourceItem>> customDataSourceCallback;
    private Map<Object, SourceItem> sourceItemMap;

    @Override
    public LQDataSourceTableCell clone() throws CloneNotSupportedException {
        LQDataSourceTableCell cell = (LQDataSourceTableCell) super.clone();
        if (cell == null) {
            return null;
        }
        cell.setCustomDataSourceCallback(this.getCustomDataSourceCallback());
        cell.setSourceItemMap(this.getSourceItemMap());
        return cell;
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
            this.setText("");
            return;
        }

        if (empty) {
            this.setText("");
        } else {
            toText(item);
        }

    }

    protected String toText(T item) {
        if (!this.getSourceItemMap().containsKey(item)) {
            reloadCustomDataSource();
        }

        if (this.getSourceItemMap().containsKey(item)) {
            SourceItem sourceItem = this.getSourceItemMap().get(item);
            if (sourceItem == null) {
                this.setText("");

            } else {
                this.setText(sourceItem.getDisplay());
            }
        } else {
            this.setText("");
        }
        
        return "";

    }

    /**
     * @return the customDataSourceCallback
     */
    public Callback<S, List<SourceItem>> getCustomDataSourceCallback() {
        return customDataSourceCallback;
    }

    /**
     * @param customDataSourceCallback the customDataSourceCallback to set
     */
    public void setCustomDataSourceCallback(Callback<S, List<SourceItem>> customDataSourceCallback) {
        this.customDataSourceCallback = customDataSourceCallback;
    }

    /**
     * @return the sourceItemMap
     */
    public Map<Object, SourceItem> getSourceItemMap() {

        if (this.sourceItemMap == null || this.sourceItemMap.isEmpty()) {
            if (this.getCustomDataSourceCallback() == null || this.getTableRow() == null || this.getTableRow().getItem() == null) {
                this.sourceItemMap = new HashMap<>();
            } else {
                try {
                    this.sourceItemMap = this.getCustomDataSourceCallback().call((S) getTableRow().getItem())
                            .stream().collect(Collectors.toMap((SourceItem s) -> s.getValue(), (SourceItem s) -> s));
                    //  logger.error("=====");
                } catch (Exception ex) {
                    sourceItemMap = new HashMap<>();
                    logger.error(ex.getMessage());
                    System.err.println(ex.getMessage());
                }
            }
        }

        return sourceItemMap;
    }

    private void reloadCustomDataSource() {
        this.sourceItemMap = null;
        this.getSourceItemMap();
    }

    protected void setSourceItemMap(Map<Object, SourceItem> sourceItemMap) {
        this.sourceItemMap = sourceItemMap;
    }

    /**
     * 刷新数据源
     */
    public void refreshCustomDataSourceCallback() {
        this.setSourceItemMap(null);
        this.getSourceItemMap();
    }
}
