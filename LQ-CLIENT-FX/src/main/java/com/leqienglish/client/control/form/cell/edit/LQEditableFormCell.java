/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit;

import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.util.focus.FocusUtil;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import com.leqienglish.client.util.thread.DelayRunner;
import com.leqienglish.util.verification.VerificationResult;
import com.leqienglish.util.verification.VerificationUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;

/**
 *
 * @author zhuqing
 * @param <S>
 * @param <T> 从S中反射出来的值
 * @param <N> 编辑节点
 */
public abstract class LQEditableFormCell<S, T, N extends Node> extends LQFormCell<S, T> {

    /**
     * 错误样式
     */
    private String errorStyle;
    /**
     * 警告样式
     */
    private String warningStyle;

    private CommitTypeEnum commitType;

    private Boolean emptyValidation;

    /**
     * 数据提交时调用
     */
    private Consumer<T> commitConsumer;

    /**
     * 跳转到下一个节点
     */
    private Consumer<Node> focusNext;

    public LQEditableFormCell() {
        this.setFocusTraversable(true);

    }

    @Override
    public LQFormCell clone() {
        LQEditableFormCell cell = (LQEditableFormCell) super.clone();
        if (cell == null) {
            return null;
        }
        cell.setCommitConsumer(commitConsumer);
        cell.setCommitType(commitType);
        cell.setFocusNext(focusNext);
        cell.setEmportStyle(this.errorStyle);
        cell.setEmptyValidation(emptyValidation);
        cell.setWarningStyle(warningStyle);
        return cell;
    }

    /**
     * 提交编辑后的值
     *
     * @param value
     */
    protected void commitValue(T value) {
        if (this.getItem() == null) {
            return;
        }
        commitValue(this.getPropertyName(), value);

    }

    /**
     * 提交值
     *
     * @param propertyName 属性名
     * @param value 值
     */
    protected final void commitValue(String propertyName, T value) {
        if (this.getItem() == null) {
            return;
        }
        if (this.getCommitType() != CommitTypeEnum.COMMIT_BY_DELAY) {
            commit(propertyName, value);
        } else {
            DelayRunner.delayRunner(propertyName, 700L, () -> commit(propertyName, value));
        }

    }

    /**
     * 提交
     *
     * @param propertyName
     * @param value
     */
    private void commit(String propertyName, T value) {

        //map值的提交
        if (this.getItem() instanceof Map) {
            Map map = (Map) this.getItem();
            map.put(propertyName, value);

        } else {
            try {
                PropertyReflectUtil.setValue(this.getItem(), propertyName, value);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                Logger.getLogger(LQEditableFormCell.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (this.getCommitConsumer() != null) {
            this.getCommitConsumer().accept(value);
        }
        validation(propertyName);
    }

    @Override
    public void requestFocus() {
        if (this.getGraphic() == null) {
            this.graphicProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (oldValue == null && newValue != null) {
                        FocusUtil.focusAfterGetSceneOnce(getGraphic());
                    }
                }
            });
            return;
        }

        FocusUtil.focusAfterGetSceneOnce(this.getGraphic());

    }

    /**
     * 验证数据是否正确
     *
     * @param propertyName
     */
    protected void validation(String propertyName) {

        this.getStyleClass().remove(this.getErrorStyle());
        this.getStyleClass().remove(this.getWarningStyle());

        VerificationResult result = VerificationUtil.verification(this.getItem(), this.getItem().getClass(), propertyName);
        if (result == null) {
            return;
        }

        this.getStyleClass().removeAll(this.getErrorStyle(), this.getWarningStyle());
        switch (result.getVerificationLV()) {
            case ERROR:
                this.getStyleClass().add(this.getErrorStyle());
                break;
            case WARNING:
                this.getStyleClass().add(this.getWarningStyle());
                break;
        }
    }

    @Override
    public void updateItem(S item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        initGraghic();
    }

    @Override
    protected void updateValue(T t) {
        this.setEmportStyle(t);
    }

    protected void setEmportStyle(T t) {
        if (t == null) {
            return;
        }

        if (this.getErrorStyle() == null || this.getErrorStyle().isEmpty()) {
            return;
        }

        if (!this.isEmptyValidation()) {
            return;
        }

        VerificationResult result = VerificationUtil.verificationNonEmpty(this.getItem(), null, getPropertyName());
        if (result != null) {
            this.getStyleClass().add(this.getErrorStyle());
        } else {
            this.getStyleClass().remove(this.getErrorStyle());
        }

    }

    protected void initGraghic() {
        if (this.getGraphic() != null) {
            FocusUtil.registEnterToNextFocus(this);
            return;
        }

        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setGraphic(this.createEditGraghic());
        FocusUtil.registEnterToNextFocus(this);
    }

    /**
     * 获取显示的节点
     *
     * @return
     */
    protected abstract N createEditGraghic();

    /**
     * @return the commitConsumer
     */
    public Consumer<T> getCommitConsumer() {
        return commitConsumer;
    }

    /**
     * @param commitConsumer the commitConsumer to set
     */
    public void setCommitConsumer(Consumer<T> commitConsumer) {
        this.commitConsumer = commitConsumer;
    }

    /**
     * @return the errorStyle
     */
    public String getErrorStyle() {
        if (this.errorStyle == null) {
            this.errorStyle = "error";
        }
        return errorStyle;
    }

    /**
     * @param errorStyle the errorStyle to set
     */
    public void setErrorStyle(String errorStyle) {
        this.errorStyle = errorStyle;
    }

    /**
     * @return the warningStyle
     */
    public String getWarningStyle() {
        if (this.warningStyle == null) {
            this.warningStyle = "warning";
        }
        return warningStyle;
    }

    /**
     * @param warningStyle the warningStyle to set
     */
    public void setWarningStyle(String warningStyle) {
        this.warningStyle = warningStyle;
    }

    /**
     * @return the commitType
     */
    public CommitTypeEnum getCommitType() {
        if (commitType == null) {
            commitType = CommitTypeEnum.COMMIT_BY_DEFAULT;
        }
        return commitType;
    }

    /**
     * @param commitType the commitType to set
     */
    public void setCommitType(CommitTypeEnum commitType) {
        this.commitType = commitType;
    }

    public void getId(String cardCode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the focusNext
     */
    public Consumer<Node> getFocusNext() {
        return focusNext;
    }

    /**
     * @param focusNext the focusNext to set
     */
    public void setFocusNext(Consumer<Node> focusNext) {
        this.focusNext = focusNext;
    }

    /**
     * @return the emptyValidation
     */
    public Boolean isEmptyValidation() {
        if (this.emptyValidation == null) {
            emptyValidation = false;
        }
        return emptyValidation;
    }

    /**
     * @param emptyValidation the emptyValidation to set
     */
    public void setEmptyValidation(Boolean emptyValidation) {
        this.emptyValidation = emptyValidation;
    }

    public enum CommitTypeEnum {
        COMMIT_BY_DEFAULT("失去焦点提交"),
        COMMIT_BY_DELAY("延时提交"),
        COMMIT_BY_MANUAL("手动提交");

        /**
         * 提交方式的描述
         */
        private final String description;

        /**
         * 构造
         *
         * @param description 描述
         */
        CommitTypeEnum(String description) {
            this.description = description;
        }

        /**
         * 获取提交方式的描述
         *
         * @return
         */
        public String getDescription() {
            return description;
        }

    }
}
