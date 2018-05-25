/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.node;



import com.leqienglish.client.control.date.DateTimeField;
import com.leqienglish.client.control.pop.LQPopup;
import com.leqienglish.client.util.focus.FocusUtil;
import com.leqienglish.client.util.focus.IFocusNodes;
import com.leqienglish.client.util.reflect.MethodReflectUtil;
import com.leqienglish.client.util.shortcutkey.LQShortCutKeyEvent;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Utility class that provides methods to simplify node handling. Possible use
 * cases are searching for nodes at specific locations, adding/removing nodes
 * to/from parents (Parent interface does not give write access to children).
 *
 * @author Michael Hoffer
 */
public class NodeUtil {

    private static int zIndex = 1000;

    // no instanciation allowed
    private NodeUtil() {
        throw new AssertionError(); // not in this class either!
    }

    /**
     *
     * @param node
     * @return The X screen coordinate of the node.
     */
    static public double screenX(Node node) {
        return node.localToScene(node.getBoundsInLocal()).getMinX() + node.getScene().getX() + node.getScene().getWindow().getX();

    }

    /**
     *
     * @param node
     * @return The Y screen coordinate of the node.
     */
    static public double screenY(Node node) {
        return node.localToScene(node.getBoundsInLocal()).getMinY() + node.getScene().getY() + node.getScene().getWindow().getY();
    }

    /**
     * Removes the specified node from its parent.
     *
     * @param n the node to remove
     *
     * @throws IllegalArgumentException if an unsupported parent class has been
     * specified or the parent is <code>null</code>
     */
    public static void removeFromParent(Node n) {
        if (n.getParent() instanceof Group) {
            ((Group) n.getParent()).getChildren().remove(n);
        } else if (n.getParent() instanceof Pane) {
            ((Pane) n.getParent()).getChildren().remove(n);
        } else {
            throw new IllegalArgumentException("Unsupported parent: " + n.getParent());
        }
    }

    /**
     * Adds the given node to the specified parent.
     *
     * @param p parent
     * @param n node
     *
     * @throws IllegalArgumentException if an unsupported parent class has been
     * specified or the parent is <code>null</code>
     */
    public static void addToParent(Parent p, Node n) {
        if (p instanceof Group) {
            ((Group) p).getChildren().add(n);
        } else if (p instanceof Pane) {
            ((Pane) p).getChildren().add(n);
        } else {
            throw new IllegalArgumentException("Unsupported parent: " + p);
        }
    }

    /**
     * Returns the first node at the given location that is an instance of the
     * specified class object. The search is performed recursively until either
     * a node has been found or a leaf node is reached.
     *
     * @param p parent node
     * @param sceneX x coordinate
     * @param sceneY y coordinate
     * @param nodeClass node class to search for
     * @return a node that contains the specified screen coordinates and is an
     * instance of the specified class or <code>null</code> if no such node
     * exist
     */
    public static Node getNode(Parent p, double sceneX, double sceneY, Class<?> nodeClass) {

        // dammit! javafx uses "wrong" children order.
        List<Node> rightOrder = new ArrayList<>();
        rightOrder.addAll(p.getChildrenUnmodifiable());
        Collections.reverse(rightOrder);

        for (Node n : rightOrder) {
            boolean contains = n.contains(n.sceneToLocal(sceneX, sceneY));

            if (contains) {

                if (nodeClass.isAssignableFrom(n.getClass())) {
                    return n;
                }

                if (n instanceof Parent) {
                    return getNode((Parent) n, sceneX, sceneY, nodeClass);
                }
            }
        }

        return null;
    }

    public static List<Node> nodesWithParent(Parent p, List<Node> nodes) {
        List<Node> result = new ArrayList<>();

        for (Node n : nodes) {
            if (p.equals(n.getParent())) {
                result.add(n);
            }
        }

        return result;
    }

    public static List<Node> nodesThatImplement(List<Node> nodes, Class<?> cls) {
        List<Node> result = new ArrayList<>();

        for (Node n : nodes) {
            if (cls.isAssignableFrom(n.getClass())) {
                result.add(n);
            }
        }

        return result;
    }

    public static boolean isVisable(Node parent) {
        if (parent == null) {
            return true;
        }

        if (!parent.isVisible()) {
            return false;
        }
        return isVisable(parent.getParent());
    }

    /**
     * node 是否是parent的子
     *
     * @param node
     * @param parent
     * @return
     */
    public static boolean isChild(Node node, Parent parent) {
        if (parent.getChildrenUnmodifiable().contains(node)) {
            return true;
        }
        return isChild(node, parent.getChildrenUnmodifiable());

    }

    /**
     * node 是否是parents里某个Node的子
     *
     * @param node
     * @param parents
     * @return
     */
    public static boolean isChild(Node node, List<Node> parents) {
        if (parents == null || parents.isEmpty() || node == null) {
            return false;
        }

        for (Node item : parents) {
            if (!(item instanceof Parent)) {
                continue;
            }
            if (isChild(node, (Parent) item)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取node 在 parents中的位置
     *
     * @param node
     * @param parents
     * @return
     */
    public static int indexInParents(Node node, List<Node> parents) {
        int index = parents.indexOf(node);
        if (index != -1) {
            return index;
        }

        index = -1;
        for (Node item : parents) {
            index++;
            if (!(item instanceof Parent)) {
                continue;
            }

            if (isChild(node, (Parent) item)) {
                return index;
            }
        }

        return -1;

    }

    public static List<Node> getFocusableNodes(List<Node> nodes) {
        List<Node> focusables = new ArrayList<>();
        for (Node node : nodes) {
            focusables.addAll(getFocusableNodes(node));
        }

        return focusables;
    }

    public static List<Node> getFocusableNodes(Node node) {

        if (node == null) {
            return Collections.EMPTY_LIST;
        }

        if (node instanceof ImageView) {
            return Collections.EMPTY_LIST;
        }

        if (!node.isFocusTraversable() && node.isDisabled()) {
            return Collections.EMPTY_LIST;
        }

        List<Node> focusables = new ArrayList<>();

        if (node instanceof IFocusNodes) {
            IFocusNodes ifocu = (IFocusNodes) node;

            focusables.addAll(ifocu.getFocusNode());
            return focusables;
        }
        if (node.getProperties().containsKey(FocusUtil.FOCUS_NODE)) {
            if (node.getProperties().get(FocusUtil.FOCUS_NODE) instanceof Collection) {
                Collection c = (Collection) node.getProperties().get(FocusUtil.FOCUS_NODE);
                focusables.addAll(c);
            } else {
                focusables.add((Node) node.getProperties().get(FocusUtil.FOCUS_NODE));
            }

            return focusables;
        }

        if (node instanceof Pane) {
            focusables.addAll(getFocusableNodes(((Pane) node).getChildren()));
            return focusables;
        }

        if (node instanceof DateTimeField) {
            focusables.addAll(getFocusableNodes(((DateTimeField) node).getChildrenUnmodifiable()));
            return focusables;
        }

        focusables.add(node);

        return focusables;
    }

    public static List<LQShortCutKeyEvent> getKeyEventList(Node node) {
        List<LQShortCutKeyEvent> keyEventList = new ArrayList<>();
        if (node == null) {
            return keyEventList;
        }
        if (node instanceof Pane) {
            List<Node> subNodeList = ((Pane) node).getChildren();
            for (Node subNode : subNodeList) {
                keyEventList.addAll(getKeyEventList(subNode));
            }
            return keyEventList;
        }
        if (node instanceof TabPane) {
            TabPane tabPane = (TabPane) node;
            for (Tab subNode : tabPane.getTabs()) {
                if (subNode.getContent() == null) {
                    continue;
                }
                keyEventList.addAll(getKeyEventList(subNode.getContent()));
            }
            return keyEventList;
        }
        if (node instanceof SplitPane) {
            List<Node> subNodeList = ((SplitPane) node).getItems();
            for (Node subNode : subNodeList) {
                keyEventList.addAll(getKeyEventList(subNode));
            }
            return keyEventList;
        }

        if (node instanceof Label) {
            Label label = (Label) node;
            if (label.getGraphic() != null) {
                keyEventList.addAll(getKeyEventList(label.getGraphic()));
            }
        }
        LQShortCutKeyEvent hipShortCutKeyEvent = (LQShortCutKeyEvent) node.getProperties().get(LQShortCutKeyEvent.SHORTCUT_KEY);
        if (hipShortCutKeyEvent == null) {
            return keyEventList;
        }
        keyEventList.add(hipShortCutKeyEvent);
        return keyEventList;
    }

    public static void registShortCutKeyEvent(Node root) {

        if (root == null) {
            return;
        }

        if (root.getProperties().containsKey("keyEventList")) {
            return;
        }

        final List<LQShortCutKeyEvent> keyEventList = getKeyEventList(root);
        root.getProperties().put("keyEventList", keyEventList);
        root.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //  System.err.print(event.getCode());
                //  System.err.print(event.isAltDown() + ":" + event.isControlDown() + ":" + event.isShiftDown());

                if (keyEventList == null) {
                    return;
                }

                for (LQShortCutKeyEvent hipShortCutKeyEvent : keyEventList) {
                    if (hipShortCutKeyEvent.equalsKeyEvent(event)) {

                        if (LQPopup.isOpening()) {
                            LQPopup.closeAll();
                        }
                        hipShortCutKeyEvent.getEventHandler().handle(event);
                        event.consume();
                    }
                }
            }
        });
    }

    /**
     * 给节点添加样式
     *
     * @param styles
     * @param node
     */
    public static void addCustomStyles(List<String> styles, Node node) {
        node.getStyleClass().removeIf((css) -> css.startsWith("CUSTOM"));
        node.getStyleClass().addAll(styles);
    }

    /**
     * 给节点添加样式
     *
     * @param styles
     * @param node
     */
    public static void addCustomStyles(String style, Node node) {
        node.getStyleClass().removeIf((css) -> css.startsWith("CUSTOM") && Objects.equals(css, style));
        node.getStyleClass().add(style);
    }

    public static Node getSpecialParent(Node child, Class parentClass) {
        if (child == null || parentClass == null) {
            return child;
        }
        if (parentClass.isAssignableFrom(child.getClass())) {
            return child;
        }

        return getSpecialParent(child.getParent(), parentClass);
    }

    public static void resetZOrder() {

        User32 user32 = User32.INSTANCE;
        int i = zIndex++;
        WinDef.HWND hh = null;

        Map<Integer, WinDef.HWND> map = findHWHD(i);
        if (map.isEmpty()) {
            return;
        }
        Integer newIndex = map.keySet().iterator().next();
        hh = map.get(newIndex);

        WinDef.INT_PTR HWND_NOTOPMOST = new WinDef.INT_PTR(-2);
        WinDef.HWND notTop = new WinDef.HWND(HWND_NOTOPMOST.toPointer());

        user32.SetWindowPos(hh, notTop, -1, -1, -1, -1, User32.SWP_NOSIZE | User32.SWP_NOMOVE);
    }

    public static void notMostTop() {

        User32 user32 = User32.INSTANCE;
        int i = zIndex++;
        WinDef.HWND hh = null;
        WinDef.HWND after = null;

        Map<Integer, WinDef.HWND> map = findHWHD(i);
        if (map.isEmpty()) {
            return;
        }
        Integer newIndex = map.keySet().iterator().next();
        hh = map.get(newIndex);
        map = findHWHD(newIndex - 1);

        after = map.values().iterator().next();

        WinDef.INT_PTR HWND_NOTOPMOST = new WinDef.INT_PTR(-2);
        WinDef.HWND notTop = new WinDef.HWND(HWND_NOTOPMOST.toPointer());

        WinDef.INT_PTR HWND_TOPMOST = new WinDef.INT_PTR(-1);
        WinDef.HWND topMost = new WinDef.HWND(HWND_TOPMOST.toPointer());
        user32.SetWindowPos(after, topMost, -1, -1, -1, -1, User32.SWP_NOSIZE | User32.SWP_NOMOVE);
        user32.SetWindowPos(hh, notTop, -1, -1, -1, -1, User32.SWP_NOSIZE | User32.SWP_NOMOVE);
    }

    public static Map<Integer, WinDef.HWND> findHWHD(int index) {
        User32 user32 = User32.INSTANCE;
        int i = index;
        Map<Integer, WinDef.HWND> map = new HashMap<>();
        WinDef.HWND hh = null;
        while (true) {
            String className = "GlassWndClass-GlassWindowClass-" + i;
            hh = user32.FindWindow(className, null);

            if (hh != null) {
                map.put(i, hh);
                break;
            }
            i--;
            if (i == 0) {
                break;
            }
        }
        return map;
    }

    public static Node clone(Node node) {
        if (node == null) {
            return null;
        }
        Method method = MethodReflectUtil.getMethod(node.getClass(), "clone");
        method.setAccessible(true);
        if (method == null || !method.isAccessible()) {
            return null;
        }
        Node clonedNode = (Node) MethodReflectUtil.executeMethod(method, node);

        return clonedNode;
    }

}
