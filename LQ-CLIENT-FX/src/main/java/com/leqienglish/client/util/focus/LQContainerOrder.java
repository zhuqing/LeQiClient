package com.leqienglish.client.util.focus;

import com.leqienglish.client.util.node.NodeUtil;
import com.sun.javafx.scene.traversal.Algorithm;
import com.sun.javafx.scene.traversal.Direction;
import static com.sun.javafx.scene.traversal.Direction.DOWN;
import static com.sun.javafx.scene.traversal.Direction.LEFT;
import static com.sun.javafx.scene.traversal.Direction.NEXT;
import static com.sun.javafx.scene.traversal.Direction.UP;
import com.sun.javafx.scene.traversal.TraversalContext;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * @author zhuqing
 * @version 1.0
 * @param <P>
 */
public abstract class LQContainerOrder<P extends Parent> implements Algorithm {

    public LQContainerOrder() {
    }

    @Override
    public Node select(Node node, Direction dir, TraversalContext context) {
        switch (dir) {
            case NEXT:
            case NEXT_IN_LINE:
                return selectNext(node, context);
            case PREVIOUS:
                return selectPrevious(node, context);
            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
                List<Node> nodes = context.getAllTargetNodes();

                int target = trav2D(context.getSceneLayoutBounds(node), dir, nodes, context);
                if (target != -1) {
                    return nodes.get(target);
                }
        }
        return null;
    }

    protected abstract List<Node> getTargetNodes(P root);

    /**
     * 选择下一个
     *
     * @param from
     * @return
     */
    protected Node selectNext(Node from, TraversalContext context) {
        Node newNode = null;
        int index = NodeUtil.indexInParents(from, this.getTargetNodes((P) context.getRoot()));
        if (index == -1) {
            index = -1;
        }
        if (index == this.getTargetNodes((P) context.getRoot()).size() - 1) {
            return null;
        }
        newNode = this.findNextFocusableNode(index + 1, this.getTargetNodes((P) context.getRoot()));

        return newNode;

    }

    /**
     * 找下一个该获取焦点的node
     *
     * @param from
     * @param dir
     * @param contextd
     * @return
     */
    protected Node selectPrevious(Node from, TraversalContext context) {

        int index = NodeUtil.indexInParents(from, this.getTargetNodes((P) context.getRoot()));
        if (index == -1) {
            index = this.getTargetNodes((P) context.getRoot()).size();
        }

        if (index == 0) {
            return null;
        }

        return this.findPreFocusableNode(index - 1, this.getTargetNodes((P) context.getRoot()));
    }

    /**
     * 获取下一个节点
     *
     * @param index
     * @param node
     * @return
     */
    protected Node findNextFocusableNode(int index, List<Node> nodes) {
        if (index < 0 || nodes == null || nodes.isEmpty()) {
            return null;
        }

        for (int i = index; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (!FocusUtil.isCouldGetFocus(node)) {
                continue;
            }

            return node;
        }

        return null;
    }

    /**
     * 获取上一个节点
     *
     * @param index
     * @param node
     * @return
     */
    protected Node findPreFocusableNode(int index, List<Node> nodes) {
        if (index < 0 || nodes == null || nodes.isEmpty()) {
            return null;
        }

        for (int i = index; i >= 0; i--) {
            Node node = nodes.get(i);
            if (!FocusUtil.isCouldGetFocus(node)) {
                continue;
            }

            return node;

        }

        return null;
    }

    @Override
    public Node selectFirst(TraversalContext context) {
        return findFirstFocusableNode(context);
    }

    @Override
    public Node selectLast(TraversalContext context) {
        return findLastFocusableNode(context);
    }

    /**
     * 获取第一个节点
     *
     * @return
     */
    protected Node findFirstFocusableNode(TraversalContext context) {
        if (context == null || context.getAllTargetNodes() == null || context.getAllTargetNodes().isEmpty()) {
            return null;
        }

        for (Node node : context.getAllTargetNodes()) {
            if (!FocusUtil.isCouldGetFocus(node)) {
                continue;
            }
            FocusUtil.focus(node);

        }

        return null;
    }

    /**
     * 获取最后一个节点
     *
     * @return
     */
    protected Node findLastFocusableNode(TraversalContext context) {
        if (context == null || context.getAllTargetNodes() == null || context.getAllTargetNodes().isEmpty()) {
            return null;
        }

        for (int i = context.getAllTargetNodes().size() - 1; i >= 0; i--) {
            Node node = context.getAllTargetNodes().get(i);
            if (!FocusUtil.isCouldGetFocus(node)) {
                continue;
            }

            FocusUtil.focus(node);
        }

        return null;
    }

    private int trav2D(Bounds origin, Direction dir, List<Node> peers, TraversalContext context) {

        Bounds bestBounds = null;
        double bestMetric = 0.0;
        int bestIndex = -1;

        for (int i = 0; i < peers.size(); i++) {
            final Bounds targetBounds = context.getSceneLayoutBounds(peers.get(i));
            final double outd = outDistance(dir, origin, targetBounds);
            final double metric;

            if (isOnAxis(dir, origin, targetBounds)) {
                metric = outd + centerSideDistance(dir, origin, targetBounds) / 100;
            } else {
                final double cosd = cornerSideDistance(dir, origin, targetBounds);
                metric = 100000 + outd * outd + 9 * cosd * cosd;
            }

            if (outd < 0.0) {
                continue;
            }

            if (bestBounds == null || metric < bestMetric) {
                bestBounds = targetBounds;
                bestMetric = metric;
                bestIndex = i;
            }
        }

        return bestIndex;
    }

    private boolean isOnAxis(Direction dir, Bounds cur, Bounds tgt) {

        final double cmin, cmax, tmin, tmax;

        if (dir == UP || dir == DOWN) {
            cmin = cur.getMinX();
            cmax = cur.getMaxX();
            tmin = tgt.getMinX();
            tmax = tgt.getMaxX();
        } else { // dir == LEFT || dir == RIGHT
            cmin = cur.getMinY();
            cmax = cur.getMaxY();
            tmin = tgt.getMinY();
            tmax = tgt.getMaxY();
        }

        return tmin <= cmax && tmax >= cmin;
    }

    /**
     * Compute the out-distance to the near edge of the target in the traversal
     * direction. Negative means the near edge is "behind".
     */
    private double outDistance(Direction dir, Bounds cur, Bounds tgt) {

        final double distance;

        if (dir == UP) {
            distance = cur.getMinY() - tgt.getMaxY();
        } else if (dir == DOWN) {
            distance = tgt.getMinY() - cur.getMaxY();
        } else if (dir == LEFT) {
            distance = cur.getMinX() - tgt.getMaxX();
        } else { // dir == RIGHT
            distance = tgt.getMinX() - cur.getMaxX();
        }

        return distance;
    }

    /**
     * Computes the side distance from current center to target center. Always
     * positive. This is only used for on-axis nodes.
     */
    private double centerSideDistance(Direction dir, Bounds cur, Bounds tgt) {

        final double cc; // current center
        final double tc; // target center

        if (dir == UP || dir == DOWN) {
            cc = cur.getMinX() + cur.getWidth() / 2.0f;
            tc = tgt.getMinX() + tgt.getWidth() / 2.0f;
        } else { // dir == LEFT || dir == RIGHT
            cc = cur.getMinY() + cur.getHeight() / 2.0f;
            tc = tgt.getMinY() + tgt.getHeight() / 2.0f;
        }

        return Math.abs(tc - cc);
        //return (tc > cc) ? tc - cc : cc - tc;
    }

    /**
     * Computes the side distance between the closest corners of the current and
     * target. Always positive. This is only used for off-axis nodes.
     */
    private double cornerSideDistance(Direction dir, Bounds cur, Bounds tgt) {

        final double distance;

        if (dir == UP || dir == DOWN) {

            if (tgt.getMinX() > cur.getMaxX()) {
                // on the right
                distance = tgt.getMinX() - cur.getMaxX();
            } else {
                // on the left
                distance = cur.getMinX() - tgt.getMaxX();
            }
        } else // dir == LEFT or dir == RIGHT
         if (tgt.getMinY() > cur.getMaxY()) {
                // below
                distance = tgt.getMinY() - cur.getMaxY();
            } else {
                // above
                distance = cur.getMinY() - tgt.getMaxY();
            }
        return distance;
    }

}
