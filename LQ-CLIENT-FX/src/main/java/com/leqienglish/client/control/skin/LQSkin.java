/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.skin;



import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyi
 */
public class LQSkin<C extends Control, BB extends BehaviorBase<C>> extends BehaviorSkinBase<C, BB> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LQSkin.class);

    /**
     * 是否是第一次执行
     */
    private boolean initable = true;

    /**
     * 构造，HipSkin不会销毁消息，消息会传给父handle
     *
     * @param control
     * @param behavior
     */
    protected LQSkin(final C control, BB behavior) {
        super(control, behavior);
        consumeMouseEvents(false);
    }

    /**
     * getCssMetaData
     * 将原先在构造中执行的initSkin及showSkin放到了getCssMetaData函数中，要求该函数构造后执行，且只执行一次，
     * 目前来看，原先加载组件偶尔报错的问题解决了
     *
     * @return
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        if (initable) {
            initable = false;
            try {
                initSkin();
                showSkin();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return super.getCssMetaData();
    }

    /**
     * 在worker线程中运行，不涉及组件的创建等耗时操作放入本方法
     */
    protected void initSkin() {

    }

    /**
     * 在application线程中运行，与组件显示、操作相关的操作放入本方法
     */
    protected void showSkin() {

    }


    /**
     * 监听节点node的visible属性改变，并从父中移除或添加node
     *
     * @param node 监听visible属性控制是否显示的节点
     * @param parent
     * node节点的parent，visible为false时会从父中移除node，为true时将node加入父的children中
     * @param index node在父children中的索引位置（如果固定位置的话）
     * @param setMethodName
     * node在父中设定位置的方法名称，如BorderPane中的setCenter、setLeft、SetTop等
     * @param canVisible 除了node自身的visible之外，本属性的值进一步控制node是否显示，如不需要则设为null
     */
    public void initNodeVisiblePropertyListener(Node node, Pane parent,
            int index, String setMethodName, BooleanProperty canVisible) {
        if (node == null || parent == null) {
            return;
        }
        changeNodeVisible(node, parent, index, setMethodName, canVisible);
        node.visibleProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                changeNodeVisible(node, parent, index, setMethodName, canVisible);
            }
        });
    }

    /**
     * 设置node的可见性，不可见时从父中移除node
     *
     * @param node 监听visible属性控制是否显示的节点
     * @param parent
     * node节点的parent，visible为false时会从父中移除node，为true时将node加入父的children中
     * @param index node在父children中的索引位置（如果固定位置的话）
     * @param setMethodName
     * node在父中设定位置的方法名称，如BorderPane中的setCenter、setLeft、SetTop等
     * @param canVisible 除了node自身的visible之外，本属性的值进一步控制node是否显示，如不需要则设为null
     */
    private void changeNodeVisible(Node node, Pane parent,
            int index, String setMethodName, BooleanProperty canVisible) {

        if (node == null || parent == null) {
            return;
        }
        if (setMethodName == null || setMethodName.trim().isEmpty()) {
            if (!node.isVisible()) {
                parent.getChildren().remove(node);

            } else {
                if (canVisible != null && !canVisible.get()) {
                    return;
                }
                if (parent.getChildren().contains(node)) {
                    return;
                }
                if (parent.getChildren().size() > index) {
                    parent.getChildren().add(index, node);
                } else {
                    parent.getChildren().add(node);
                }
            }
        } else {
            try {
                Method method = parent.getClass().getDeclaredMethod(setMethodName, Node.class);
                if (method != null) {
                    method.setAccessible(true);
                    if (!node.isVisible()) {
                        method.invoke(parent, (Node) null); //这里直接null的话会报错，会被认为没有传入参数
                    } else {
                        if (canVisible != null && !canVisible.get()) {
                            return;
                        }
                        method.invoke(parent, node);
                    }
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                LOGGER.error(null, ex);
            }
        }
    }
}
