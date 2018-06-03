/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.gridview;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;
import static javafx.scene.control.SelectionMode.SINGLE;

/**
 *
 * @author zhuqing
 */
public class GridViewSelectionModel<S extends Object> extends MultipleSelectionModel<S> {

    private LQGridView<S> hipGridView;

    private ObservableList<S> selectedItems;

    private ObservableList<Integer> selectedIndices;

    public GridViewSelectionModel(LQGridView<S> hipGridView) {
        this.hipGridView = hipGridView;
    }

    @Override
    public ObservableList<Integer> getSelectedIndices() {
        if (selectedIndices == null) {
            selectedIndices = FXCollections.observableArrayList();
        }
        return selectedIndices;
    }

    @Override
    public ObservableList<S> getSelectedItems() {
        if (selectedItems == null) {
            selectedItems = FXCollections.observableArrayList();
        }
        return selectedItems;
    }

    @Override
    public void selectIndices(int index, int... indices) {
        if (indices == null || indices.length == 0) {
            return;
        }
        List<Integer> ints = new ArrayList<>();

        for (int i : indices) {
            ints.add(i);

        }

        if (ints.isEmpty()) {
            return;
        }
        getSelectedIndices().removeAll(ints);
        getSelectedIndices().addAll(index, ints);

        List<S> selectItems = ints.stream().map((Integer i) -> {
            return getHipGridView().getItems().get(i);
        }).collect(Collectors.toList());

        this.getSelectedItems().removeAll(selectItems);
        this.getSelectedItems().addAll(selectItems);

        this.setSelectedIndex(ints.get(ints.size() - 1));
        this.setSelectedItem(selectItems.get(selectItems.size() - 1));
    }

    @Override
    public void selectAll() {
        if (this.isSingle()) {
            selectFirst();
        } else {
            this.getSelectedItems().setAll(this.getHipGridView().getItems());
            List<Integer> indexs = IntStream.range(0, this.getHipGridView().getItems().size()).boxed().collect(Collectors.toList());
            this.getSelectedIndices().setAll(indexs);
        }
    }

    @Override
    public void selectFirst() {
        this.clearAndSelect(0);
    }

    @Override
    public void selectLast() {
        if (this.getHipGridView() == null || this.getHipGridView().getItems().isEmpty()) {
            return;
        }
        this.select(this.getHipGridView().getItems().size() - 1);
    }

    @Override
    public void clearAndSelect(int index) {
        this.clearSelection();
        this.getSelectedIndices().add(index);
        this.setSelectedIndex(index);

        S item = this.getHipGridView().getItems().get(index);
        this.getSelectedItems().add(item);
        this.setSelectedItem(item);
    }

    @Override
    public void select(int index) {
        S item = this.getHipGridView().getItems().get(index);
        if (this.isSingle()) {
            this.clearAndSelect(index);
        } else {
            if (this.getSelectedIndices().contains(index)) {
                this.getSelectedIndices().remove(Integer.valueOf(index));
                this.getSelectedItems().remove(item);
                if (this.getSelectedIndex() == index) {
                    this.setSelectedIndex(-1);
                    this.setSelectedItem(null);
                }

                return;
            }
            this.setSelectedIndex(index);
            this.setSelectedItem(item);
            if (!this.getSelectedIndices().contains(index)) {
                this.getSelectedIndices().add(index);
            }
            if (!this.getSelectedItems().contains(item)) {
                this.getSelectedItems().add(item);
            }

        }

    }

    @Override
    public void select(S obj) {

        int index = this.getHipGridView().getItems().indexOf(obj);
        if (this.isSingle()) {
            this.clearAndSelect(index);
        } else {
            this.select(index);
        }
    }

    @Override
    public void clearSelection(int index) {
        this.getSelectedIndices().remove(index);
        S item = this.getHipGridView().getItems().get(index);
        this.getSelectedItems().remove(item);
    }

    @Override
    public void clearSelection() {
        this.getSelectedIndices().clear();
        this.getSelectedItems().clear();
        this.setSelectedItem(null);
        this.setSelectedIndex(-1);
    }

    @Override
    public boolean isSelected(int index) {
        return this.getSelectedIndices().contains(index);
    }

    @Override
    public boolean isEmpty() {
        return this.getHipGridView().getItems().isEmpty();
    }

    @Override
    public void selectPrevious() {
        int nexIndex = this.getPrevIndex(getLastSelectedIndex());
        if (isSingle()) {

            this.clearAndSelect(nexIndex);
        } else {
            this.select(nexIndex);
        }
    }

    @Override
    public void selectNext() {
        int nexIndex = this.getNextIndex(getLastSelectedIndex());
        if (isSingle()) {

            this.clearAndSelect(nexIndex);
        } else {
            this.select(nexIndex);
        }
    }

    /**
     * 得到下一个索引
     *
     * @param currentIndex
     * @return
     */
    private int getNextIndex(int currentIndex) {
        if (this.getHipGridView().getItems().size() - 1 == currentIndex) {
            return 0;
        }
        return currentIndex + 1;
    }

    /**
     * 获取上一个索引
     *
     * @param currentIndex
     * @return
     */
    private int getPrevIndex(int currentIndex) {
        if (currentIndex == 0) {
            return this.getHipGridView().getItems().size() - 1;
        }
        return currentIndex - 1;
    }

    /**
     * 获取最后一个选中的位置
     *
     * @return
     */
    private int getLastSelectedIndex() {
        if (this.getSelectedIndices().isEmpty()) {
            return -1;
        }

        return this.getSelectedIndices().get(this.getSelectedIndices().size() - 1);
    }

    private boolean isSingle() {
        if (this.getHipGridView() == null) {
            return true;
        }

        if (this.getHipGridView().getSelectMode() == SINGLE) {
            return true;
        }

        return false;
    }

    /**
     * @return the hipGridView
     */
    public LQGridView<S> getHipGridView() {
        return hipGridView;
    }

    /**
     * @param hipGridView the hipGridView to set
     */
    public void setHipGridView(LQGridView<S> hipGridView) {
        this.hipGridView = hipGridView;
    }

}
