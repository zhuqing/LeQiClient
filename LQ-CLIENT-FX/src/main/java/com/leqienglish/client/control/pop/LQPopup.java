package com.leqienglish.client.control.pop;


import com.leqienglish.client.util.node.NodeUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.PopupWindow;

//import org.xvolks.jnative.misc.basicStructures.HWND;
//import org.xvolks.jnative.util.User32;
/**
 * <p>
 * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;医疗信息平台弹出窗口分为4种，
 * 弹出组件、右键菜单、提示信息和信息面板. </b></p>
 * <p>
 * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;弹出组件依赖于ownerNode。所以组件
 * 窗口的位置与该组件的ownerNode有直接关系。提供2种show方法：showH和showV。</b></p>
 * <p>
 * <b>showH与ownerNode之间的关系是横向的，弹出组件会紧贴ownerNode
 * 的右侧或左侧。showV与ownerNode之间的关系是纵向的，</b></p>
 * <p>
 * <b>弹出组件会紧贴ownerNode的上侧或下侧。2种方法都会根据ownerNode
 * 位置、ownerNode大小和组件大小动态调节显示位置。</b></p>
 * <p>
 * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;右键菜单依赖于坐标。右键菜单显示位置
 * 为坐标的右下、左下、右上、左上。右键菜单会根据坐标位置和菜单大小动态调</b></p>
 * <p>
 * <b>节显示位置。</b></p>
 * <p>
 * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;提示信息。与弹出Node只有事件关联没有直接位置关联关系。
 * 它的show方法使用的是PopupWindow的show方法。但该类只能显示文本提示信息。</b></p>
 * <p>
 * <b>1、show(Window owner)</b></p>
 * <p>
 * <b>2、show(Node ownerNode, double screenX, double screenY)</b></p>
 * <p>
 * <b>3、show(Window ownerWindow, double screenX, double screenY)</b></p>
 * <p>
 * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由此可见，它的显示位置是根据父窗体或Screen坐标来设置的。</b></p>
 * <p>
 * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;信息面板同提示信息类似。但子节点主要以Pane为主。</b></p>
 * <p>
 * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;医疗信息平台组件弹出框，继承自PopupWindow。
 * 代替Popup使用。通过showV和showH来代替Popup类的show方法。</b></p>
 *
 * @author duyi
 * @version 2013-6-24
 */
public class LQPopup extends PopupWindow {

    /**
     * 正在打开的弹出框
     */
    private static final List<LQPopup> opening = new ArrayList<>();

    private static Integer startSearchIndex = 1000;

    private Boolean reSetZOrder = true;
    /**
     * 主框架的Scene
     */
    private Scene rootScene;
    /**
     * 弹出框X值
     */
    private double popupX;
    /**
     * 弹出框Y值
     */
    private double popupY;
    /**
     * 弹出框根节点宽。未运行show方法前，为0.
     */
    private double rootNodeWidth = 0d;
    /**
     * 弹出框根节点高。未运行show方法前，为0.
     */
    private double rootNodeHeight = 0d;

    /**
     * 无参构造方法
     */
    public LQPopup() {
        this.addKeyListener();
        this.setConsumeAutoHidingEvents(false);
    }

    private void addKeyListener() {
        this.getScene().addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    event.consume();
                    hide();
                }
            }
        });

    }

    public static boolean isOpening() {
        return !opening.isEmpty();
    }

    public static void closeAll() {
        if (isOpening()) {
            while (!opening.isEmpty()) {
                LQPopup pop = opening.get(0);
                pop.hide();
            }

        }
    }

    /**
     * 获取子节点列表，用来添加子节点。
     *
     * @return 子节点列表
     */
    @Override
    public final ObservableList<Node> getContent() {
        return super.getContent();
    }

    /**
     * 获取所需要的信息，用来配置属性
     *
     * @param ownerNode 依附节点
     */
    private void getPopupValue(Node ownerNode) {
        //获取依附的scene
        if (this.rootScene == null) {
            this.rootScene = ownerNode.getScene();
            //递归找到主框架Scene

        }
        //获取弹出框内根节点大小。需要显示才可获得。
        if (this.rootNodeWidth == 0d || this.rootNodeHeight == 0d) {
            super.show(ownerNode, popupX, popupY);
            this.rootNodeWidth = this.getContent().get(0).getLayoutBounds().getWidth();
            this.rootNodeHeight = this.getContent().get(0).getLayoutBounds().getHeight();
            this.hide();
        }
    }

    @Override
    public void hide() {
        super.hide(); //To change body of generated methods, choose Tools | Templates.
        opening.remove(this);
        resetZOrder();
    }

    private void resetZOrder() {
        if (!this.getReSetZOrder()) {
            return;
        }
      //  NodeUtil.resetZOrder();
    }

    @Override
    public void show(Node ownerNode, double anchorX, double anchorY) {
        super.show(ownerNode, anchorX, anchorY); //To change body of generated methods, choose Tools | Templates.
       // changeTop();

    }

    private void changeTop() {
        if (!this.getReSetZOrder()) {
            return;
        }
        NodeUtil.notMostTop();
    }

    /**
     * 纵向依附节点弹出效果。优先向右下弹出。
     *
     * @param ownerNode 弹出框所依附的节点
     */
    public void showV(Node ownerNode) {

        //获取信息，配置属性
        getPopupValue(ownerNode);

        double out = NodeUtil.screenX(ownerNode) + this.getRootNodeWidth() - (rootScene.getWindow().getX() + rootScene.getX() + rootScene.getWidth());
        //配置X坐标
        if (out > 0) {
            popupX = NodeUtil.screenX(ownerNode) - out;//- (this.getRootNodeWidth() - ownerNode.getLayoutBounds().getWidth());
        } else {
            popupX = NodeUtil.screenX(ownerNode);
        }

        //配置Y坐标
        if (NodeUtil.screenY(ownerNode) + this.getRootNodeHeight() + ownerNode.getLayoutBounds().getHeight() > rootScene.getWindow().getY() + rootScene.getY() + rootScene.getHeight()) {
            popupY = NodeUtil.screenY(ownerNode) - this.getRootNodeHeight();
        } else {
            popupY = NodeUtil.screenY(ownerNode) + ownerNode.getLayoutBounds().getHeight();
        }

        //显示Popup
        show(ownerNode, popupX, popupY);
//        Utils.executeOnceWhenPropertyIsNonNull(ownerNode.boundsInLocalProperty(), (x) -> {
//            if (isShowing()) {
//                showV(ownerNode);
//            }
//        });
        opening.add(this);

    }

    /**
     * 横向依附节点弹出效果。优先向右下弹出。
     *
     * @param ownerNode 弹出框所依附的节点
     */
    public void showH(Node ownerNode) {
        //获取信息，配置属性
        getPopupValue(ownerNode);

        //配置X坐标
        if (NodeUtil.screenX(ownerNode) + this.getRootNodeWidth() + ownerNode.getLayoutBounds().getWidth()
                > rootScene.getWindow().getX() + rootScene.getX() + rootScene.getWidth()) {
            popupX = NodeUtil.screenX(ownerNode) - this.getRootNodeWidth();
        } else {
            popupX = NodeUtil.screenX(ownerNode) + ownerNode.getLayoutBounds().getWidth();
        }

        double out = NodeUtil.screenY(ownerNode) + this.getRootNodeHeight() - (rootScene.getWindow().getY() + rootScene.getY() + rootScene.getHeight());
        //配置Y坐标
        if (out > 0) {
            popupY = NodeUtil.screenY(ownerNode) - out;
        } else {
            popupY = NodeUtil.screenY(ownerNode);
        }
        //显示Popup
        show(ownerNode.getScene().getWindow(), popupX, popupY);
        opening.add(this);
    }

    /**
     * 纵向依附节点弹出效果。优先向右下弹出。
     *
     * @param ownerNode 弹出框所依附的节点
     */
    public void showV(Node ownerNode, Node showNode) {
        //获取信息，配置属性
        getPopupValue(ownerNode);

        double out = NodeUtil.screenX(showNode) + this.getRootNodeWidth() - (rootScene.getWindow().getX() + rootScene.getX() + rootScene.getWidth());
        //配置X坐标
        if (out > 0) {
            popupX = NodeUtil.screenX(showNode) - out;//- (this.getRootNodeWidth() - ownerNode.getLayoutBounds().getWidth());
        } else {
            popupX = NodeUtil.screenX(showNode);
        }

        //配置Y坐标
        if (NodeUtil.screenY(showNode) + this.getRootNodeHeight() + showNode.getLayoutBounds().getHeight() > rootScene.getWindow().getY() + rootScene.getY() + rootScene.getHeight()) {
            popupY = NodeUtil.screenY(showNode) - this.getRootNodeHeight() - showNode.getLayoutBounds().getHeight();
        } else {
            popupY = NodeUtil.screenY(showNode) + showNode.getLayoutBounds().getHeight();
        }

        //显示Popup
        super.show(ownerNode.getScene().getWindow(), popupX, popupY);
        opening.add(this);
    }

    /**
     * 横向依附节点弹出效果。优先向右下弹出。
     *
     * @param ownerNode 弹出框所依附的节点
     */
    public void showH(Node ownerNode, Node showNode) {
        //获取信息，配置属性
        getPopupValue(ownerNode);

        //配置X坐标
        if (NodeUtil.screenX(showNode) + this.getRootNodeWidth() + showNode.getLayoutBounds().getWidth()
                > rootScene.getWindow().getX() + rootScene.getX() + rootScene.getWidth()) {
            popupX = NodeUtil.screenX(showNode) - this.getRootNodeWidth();
        } else {
            popupX = NodeUtil.screenX(showNode) + showNode.getLayoutBounds().getWidth();
        }

        double out = NodeUtil.screenY(showNode) + this.getRootNodeHeight() - (rootScene.getWindow().getY() + rootScene.getY() + rootScene.getHeight());
        //配置Y坐标
        if (out > 0) {
            popupY = NodeUtil.screenY(showNode) - out;
        } else {
            popupY = NodeUtil.screenY(showNode);
        }
        //显示Popup
        super.show(ownerNode, popupX, popupY);
        opening.add(this);
    }

    public double getRootNodeWidth() {
        return rootNodeWidth;
    }

    public double getRootNodeHeight() {
        return rootNodeHeight;
    }

    /**
     * @return the reSetZOrder
     */
    public Boolean getReSetZOrder() {
        return reSetZOrder;
    }

    /**
     * @param reSetZOrder the reSetZOrder to set
     */
    public void setReSetZOrder(Boolean reSetZOrder) {
        this.reSetZOrder = reSetZOrder;
    }

    private boolean isOwnerNodeEvent(final Event event) {
        final Node ownerNode = this.getOwnerNode();
        if (ownerNode == null) {
            return false;
        }

        final EventTarget eventTarget = event.getTarget();
        if (!(eventTarget instanceof Node)) {
            return false;
        }

        Node node = (Node) eventTarget;
        do {
            if (node == ownerNode) {
                return true;
            }
            node = node.getParent();
        } while (node != null);

        return false;
    }

}
