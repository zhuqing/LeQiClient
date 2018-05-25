package com.leqienglish.client.util.focus;


import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.util.node.NodeUtil;
import com.sun.javafx.scene.traversal.TraversalContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Node;

/**
 * @author zhuqing
 * @version 1.0
 * @param <P>
 */
public class LQFormViewContainerOrder<P extends LQFormView> extends LQContainerOrder<P> {

    /**
     * 焦点切换节点列表
     */
    private List<Node> targetNodes;

    public LQFormViewContainerOrder() {
    }

    @Override
    protected List<Node> getTargetNodes(P root) {
//        if (this.getTargetNodes() != null) {
//            return this.getTargetNodes();
//        }
        List<Node> nodes = root.getChildren().stream()
                .filter((n) -> n instanceof LQEditableFormCell)
                .filter((n) -> n.isFocusTraversable())
                .map((n) -> (LQEditableFormCell) n)
                .map((f) -> f.getGraphic()).collect(Collectors.toList());

        this.setTargetNodes(NodeUtil.getFocusableNodes(nodes));
        return this.getTargetNodes();
    }

    /**
     * 选择下一个
     *
     * @param from
     * @return
     */
    protected Node selectNext(Node from, TraversalContext context) {
        if (from instanceof LQEditableFormCell) {
            LQEditableFormCell cell = (LQEditableFormCell) from;
            List<Node> nodes = NodeUtil.getFocusableNodes(Arrays.asList(cell.getGraphic()));
            if (!nodes.isEmpty()) {
                from = nodes.get(nodes.size() - 1);
            }
        }

        return super.selectNext(from, context);
    }

    /**
     * 选择下一个
     *
     * @param from
     * @return
     */
    protected Node selectPrevious(Node from, TraversalContext context) {
        if (from instanceof LQEditableFormCell) {
            LQEditableFormCell cell = (LQEditableFormCell) from;
            List<Node> nodes = NodeUtil.getFocusableNodes(Arrays.asList(cell.getGraphic()));
            if (!nodes.isEmpty()) {
                from = nodes.get(0);
            }
        }

        return super.selectPrevious(from, context);
    }

    /**
     * 焦点切换节点列表
     *
     * @return the targetNodes
     */
    public List<Node> getTargetNodes() {

        return targetNodes;
    }

    /**
     * 焦点切换节点列表
     *
     * @param targetNodes the targetNodes to set
     */
    public void setTargetNodes(List<Node> targetNodes) {
        this.targetNodes = targetNodes;
    }

}
