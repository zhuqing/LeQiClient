/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell;


import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.util.node.NodeUtil;
import java.util.function.Consumer;
import javafx.scene.Node;

/**
 *
 * @author zhuqing
 */
public class LQCustomFormCell<S, T, N extends Node> extends LQEditableFormCell<S, T, N> {

    // private Node customNode;
    private Consumer<LQCustomFormCell> updateValueConsumer;

    @Override
    protected N createEditGraghic() {
        return null;
    }
    
    @Override
    public LQFormCell clone() {
        LQCustomFormCell  cell = (LQCustomFormCell) super.clone();
        cell.setUpdateValueConsumer(updateValueConsumer);
        cell.setGraphic(NodeUtil.clone(this.getGraphic()));
        return cell;
    }
    
    @Override
    protected void updateValue(T t) {
        super.updateValue(t); //To change body of generated methods, choose Tools | Templates
        if (this.getUpdateValueConsumer() != null) {
            getUpdateValueConsumer().accept(this);
        }
    }

    /**
     * @return the updateValueConsumer
     */
    public Consumer<LQCustomFormCell> getUpdateValueConsumer() {
        return updateValueConsumer;
    }

    /**
     * @param updateValueConsumer the updateValueConsumer to set
     */
    public void setUpdateValueConsumer(Consumer<LQCustomFormCell> updateValueConsumer) {
        this.updateValueConsumer = updateValueConsumer;
    }
    
}
