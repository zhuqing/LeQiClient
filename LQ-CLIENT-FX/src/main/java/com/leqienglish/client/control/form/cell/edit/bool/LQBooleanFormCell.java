/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.bool;


import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.util.bool.BooleanUtil;
import javafx.scene.Node;
import javafx.util.Callback;

/**
 *
 * @author zhangyingchuang
 */
public class LQBooleanFormCell<S> extends LQEditableFormCell<S, Object, Node> {

    private Node node;

    private String valueType;

    private Callback<Object, Boolean> toBooleanCallback;

    protected static final String BOOLEAN_VALUE = "booleanValue";
    protected static final String OBJECT_VALUE = "objectValue";

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    @Override
    protected Node createEditGraghic() {
        return null;
    }

    @Override
    protected void updateValue(Object t) {
        super.updateValue(t);

        Class clazz = this.getFieldType(getPropertyName());
        if (clazz == null) {
            clazz = Boolean.class;
        }
        String typename = clazz.getSimpleName();
        switch (typename) {
            case "boolean":
            case "Boolean":
                setValueType(BOOLEAN_VALUE);
                break;
            default:
                setValueType(OBJECT_VALUE);
                break;
        }
    }

    protected boolean getBoolean(Object t) {
        if (t == null) {
            return false;
        } else if (t instanceof Boolean) {
            return (Boolean) t;
        } else if (getToBooleanCallback() != null) {
            return getToBooleanCallback().call(t);
        } else {
            return BooleanUtil.getBoolean(toText(t));
        }
    }

    protected void commitBooleanValue(boolean result) {
        if (result) {
            commitValue(BOOLEAN_VALUE.equals(getValueType()) ? result : (short) 1);
        } else {
            commitValue(BOOLEAN_VALUE.equals(getValueType()) ? result : (short) 0);
        }
    }

    public LQFormCell clone() {
        LQBooleanFormCell cell = (LQBooleanFormCell) super.clone();
        cell.setToBooleanCallback(toBooleanCallback);
        cell.setValueType(valueType);
        return cell;
    }

    public Callback<Object, Boolean> getToBooleanCallback() {
        return toBooleanCallback;
    }

    public void setToBooleanCallback(Callback<Object, Boolean> toBooleanCallback) {
        this.toBooleanCallback = toBooleanCallback;
    }
}
