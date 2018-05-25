package com.leqienglish.client.util.focus;


import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.util.node.NodeUtil;
import com.sun.javafx.scene.traversal.TraversalContext;
import java.util.List;
import javafx.scene.Node;

/**
 * form中循环执行
 * @author zhuqing
 * @version 1.0
 * @param <P>
 */
public class LQFormViewContainerOrderInCircle<P extends LQFormView> extends LQFormViewContainerOrder<P> {

    /**
     * 焦点切换节点列表
     */
    private List<Node> targetNodes;

    public LQFormViewContainerOrderInCircle() {
    }

  
    
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
            index = -1;
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
            index =this.getTargetNodes((P) context.getRoot()).size();
        }

        return this.findPreFocusableNode(index - 1, this.getTargetNodes((P) context.getRoot()));
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
