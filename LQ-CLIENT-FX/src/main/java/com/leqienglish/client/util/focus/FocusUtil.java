/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.focus;


import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.util.node.NodeUtil;
import com.sun.javafx.scene.traversal.Direction;
import com.sun.javafx.scene.traversal.ParentTraversalEngine;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.IndexedCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 *
 * @author zhuqing
 */
public class FocusUtil {

    private final static String FOCUS_EVENT_KEY = "FOCUS_EVENT_KEY";
    private final static String LAST_NODE = "FOCUS_LAST_NODE";

    public static final String FOCUS_NODE = "FOCUS_NODE";
    public static final String ENTER_EVENT = "ENTER_EVENT";

    public static void registEnterToNextFocus(LQEditableFormCell formCell) {
        if (formCell == null) {
            return;
        }

        List<Node> nodes = NodeUtil.getFocusableNodes(formCell.getGraphic());
        if (nodes == null || nodes.isEmpty()) {
            return;
        }

        for (Node node : nodes) {
            if (node.getProperties().containsKey(FOCUS_EVENT_KEY)) {
                continue;
            } else {
                node.getProperties().put(FOCUS_EVENT_KEY, FOCUS_EVENT_KEY);
            }
            node.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() != KeyCode.ENTER) {
                        return;
                    }

                    if (event.isConsumed()) {
                        return;
                    }

                   focusNext(formCell,node,true);
                }
            });
            node.getProperties().remove(LAST_NODE);
        }

        nodes.get(nodes.size() - 1).getProperties().put(LAST_NODE, LAST_NODE);

    }

    public static void focusNextAfterKey(LQEditableFormCell formCell) {

        formCell.getProperties().put(ENTER_EVENT, ENTER_EVENT);
        formCell.getFocusNext().accept(formCell);
        formCell.getProperties().remove(ENTER_EVENT);

    }

    public static void focusNext(LQEditableFormCell formCell, Node node, Boolean isEnter) {
        String enterType = null;
        if (isEnter) {
            enterType = ENTER_EVENT;
        }
        if (node.getProperties().containsKey(LAST_NODE)) {
            if (formCell.getFocusNext() == null) {
                node.impl_traverse(Direction.NEXT);
            } else {
                formCell.getProperties().put(ENTER_EVENT, enterType);
                formCell.getFocusNext().accept(formCell);
                formCell.getProperties().remove(ENTER_EVENT);
            }
        } else {
            node.impl_traverse(Direction.NEXT);
        }
    }

    public static void requestFocus(List<Node> nodes, Class claz) {
        for (Node node : nodes) {
            if (claz.isAssignableFrom(node.getClass())) {
                node.requestFocus();
                break;
            }

            if (node instanceof Region) {
                Region re = (Region) node;
                requestFocus(re.getChildrenUnmodifiable(), claz);
            }
        }
    }

    public static void setFocusInner(LQFormView p) {
        p.setImpl_traversalEngine(new ParentTraversalEngine(p, new LQFormViewContainerOrderInCircle()));
    }

    public static void reSetFocusAlgorithm(Parent p, LQContainerOrder order) {
        p.setImpl_traversalEngine(new ParentTraversalEngine(p, order));
    }

    public static void focusToNext(Node node) {
        if (node instanceof LQEditableFormCell) {
            LQEditableFormCell formCell = (LQEditableFormCell) node;
            if (formCell.getFocusNext() != null) {
                formCell.getFocusNext().accept(node);
                return;
            }
        }
        node.impl_traverse(Direction.NEXT);
    }

    public static void focus(Node node) {

        if (IndexedCell.class.isAssignableFrom(node.getClass())) {
            IndexedCell cell = (IndexedCell) node;
            if (cell.getGraphic() != null) {
                cell.getGraphic().requestFocus();
                return;
            }
        }

        node.requestFocus();
    }

    public static void focusAfterGetSceneOnce(Node node) {
        if (node == null) {
            return;
        }

        if (node instanceof StackPane) {
            StackPane sp = (StackPane) node;
            if (!sp.getChildren().isEmpty()) {
                focusAfterGetSceneOnce(sp.getChildren().get(0));
                return;
            }
        }

        if (node.getScene() != null) {
            node.requestFocus();
            return;
        }
        ChangeListener<Scene> change = new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                if (newValue == null) {
                    return;
                }
                node.requestFocus();
            }
        };

        node.sceneProperty().addListener(change);

        node.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                if (newValue == null) {
                    return;
                }
                node.sceneProperty().removeListener(change);
            }
        });

    }

    /**
     * 是否可获取焦点
     *
     * @param parent
     * @return
     */
    public static boolean isCouldGetFocus(Node parent) {
        if (parent == null) {
            return true;
        }
        if (!parent.isFocusTraversable()) {
            return false;
        }

        if (parent.isDisable()) {
            return false;
        }

        if (!NodeUtil.isVisable(parent)) {
            return false;
        }

        return true;
    }
}
