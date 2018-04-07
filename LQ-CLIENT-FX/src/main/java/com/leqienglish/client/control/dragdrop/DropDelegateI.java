package com.leqienglish.client.control.dragdrop;

import java.util.Collection;
import javafx.scene.Node;

public interface DropDelegateI<V extends Node, T> {

    /**
     * 是否可以释放
     *
     * @param hipTableView
     * @param index
     * @param item
     * @throws java.lang.Exception
     */
    public boolean couldDrop(V view, int insertIndex, Collection<T> items) throws Exception;

    /**
     *
     * @param hipTableView
     * @param index
     * @param item
     * @throws java.lang.Exception
     */
    public void drop(V view, int insertIndex, Collection<T> items) throws Exception;

}
