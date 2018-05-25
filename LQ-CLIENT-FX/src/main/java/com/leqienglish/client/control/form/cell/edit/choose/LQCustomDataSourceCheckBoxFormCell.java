/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.choose;


import com.leqienglish.client.control.choose.LQCheckBoxPane;
import com.leqienglish.client.util.sourceitem.SourceItem;
import com.leqienglish.client.util.sourceitem.SourceItemUtil;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author zhangyingchuang
 */
public class LQCustomDataSourceCheckBoxFormCell<S, T, N extends LQCheckBoxPane> extends LQChooseFormCell<S, T, N> {

    private LQCheckBoxPane checkBoxPane;

    private String dataType;

    private final ListChangeListener<SourceItem<T>> valuesChangeListener = new ListChangeListener<SourceItem<T>>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends SourceItem<T>> c) {
            ObservableList<SourceItem<T>> items = getCheckBoxPane().getValues();
            if (items == null || items.isEmpty()) {
                commitValue(null);
                return;
            }

            List<T> tlist = new ArrayList<>();
            for (SourceItem<T> item : items) {
                tlist.add(item.getValue());
            }
            switch (dataType) {
                case "list":
                    commitValue((T) tlist);
                    return;
                case "set":
                    Set<T> ts = new HashSet<>();
                    for (SourceItem<T> item : items) {
                        ts.add(item.getValue());
                    }
                    commitValue((T) ts);
                    return;
                case "array":
                    T[] tarray = (T[]) Array.newInstance(tlist.get(0).getClass(), tlist.size());
                    for (int i = 0; i < tarray.length; i++) {
                        tarray[i] = tlist.get(i);
                    }
                    commitValue((T) tarray);
                    return;
            }

        }
    };

    @Override
    protected N createEditGraghic() {
        if (checkBoxPane == null) {
            checkBoxPane = new LQCheckBoxPane();
            checkBoxPane.getValues().addListener(valuesChangeListener);
        }
        return (N) checkBoxPane;
    }

    @Override
    protected void updateValue(T t) {
        super.updateValue(t);
        dataType = getFieldType(getPropertyName()).getSimpleName().toLowerCase();
        this.getCheckBoxPane().getValues().removeListener(valuesChangeListener);
        List<SourceItem<T>> sourceItems = this.getCustomDataSourceCallback().call(this.getItem());
        List<SourceItem<T>> showValues = new ArrayList<>();
        this.getCheckBoxPane().setSourceItems(sourceItems);
        if (t instanceof Set) {
            Set set = (Set) t;
            if (set != null) {
                for (Object object : set) {
                    SourceItem selected = SourceItemUtil.getSourceItem((T) object, sourceItems);
                    showValues.add(selected);
                }
            }
        } else if (t instanceof List) {
            List list = (List) t;
            for (Object object : list) {
                SourceItem selected = SourceItemUtil.getSourceItem((T) object, sourceItems);
                showValues.add(selected);
            }
        } else if (t instanceof Object[]) {
            dataType = "array";
            T[] items = (T[]) t;
            for (T item : items) {
                SourceItem selected = SourceItemUtil.getSourceItem((T) item, sourceItems);
                showValues.add(selected);
            }
        }
        this.getCheckBoxPane().setValues(showValues);
        this.getCheckBoxPane().getValues().addListener(valuesChangeListener);
    }

    public LQCheckBoxPane getCheckBoxPane() {
        if (checkBoxPane == null) {
            createEditGraghic();
        }
        return checkBoxPane;
    }

    public void setCheckBoxPane(LQCheckBoxPane checkBoxPane) {
        this.checkBoxPane = checkBoxPane;
    }
}
