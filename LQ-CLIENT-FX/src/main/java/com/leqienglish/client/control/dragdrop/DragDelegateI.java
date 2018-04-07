package com.leqienglish.client.control.dragdrop;

import java.util.Collection;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public interface DragDelegateI<V extends Node, T> {

    /**
     * 拖拽的数据
     * @param event
     * @param view
     * @param currentIndex
     * @return 
     */
    public Collection<T> dragData(MouseEvent event,V view, int currentIndex);

    /**
     * 拖拽类型，如果不能拖拽返回null
     * @param event
     * @param view
     * @param currentIndex
     * @return 
     */
    public TransferMode dragMode(MouseEvent event ,V view, int currentIndex);

}
