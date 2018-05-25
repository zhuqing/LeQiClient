/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.choose.pop;


import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.control.pop.query.LQQueryTablePopup;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.util.focus.FocusUtil;
import com.leqienglish.client.util.reflect.PropertyReflectUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Callback;

/**
 *
 * @author zhuqing
 */
public class LQTableViewChooseFormCell<S, T> extends LQEditableFormCell<S, T, LQQueryTablePopup> {

    private LQQueryTablePopup quertablePopup;

    private String commitPropertyName;

    private LQTableView lqTableView;

    private Callback<String, List> queryCallback;

    private Callback<Object, Boolean> commitCallback;

    /**
     * 查询延时
     */
    private Long delay;

    /**
     * @return the commitPropertyName
     */
    public String getCommitPropertyName() {
        return commitPropertyName;
    }

    /**
     * @param commitPropertyName the commitPropertyName to set
     */
    public void setCommitPropertyName(String commitPropertyName) {
        this.commitPropertyName = commitPropertyName;
    }

    public LQTableViewChooseFormCell() {
        //失去焦点后关闭弹出窗
        JavaFxObservable.changesOf(this.focusedProperty())
                .filter((c) -> !c.getNewVal() && quertablePopup != null)
                .subscribe((c) -> quertablePopup.close());
    }

    @Override
    protected void updateValue(T t) {
        super.updateValue(t);
        String text = this.toText(t);
        if (this.quertablePopup == null) {
            createEditGraghic();
        }
        quertablePopup.setStopQuery(true);
        this.quertablePopup.getTextField().setText(text);
        quertablePopup.setStopQuery(false);
    }

    @Override
    protected LQQueryTablePopup createEditGraghic() {
        if (quertablePopup == null) {
            quertablePopup = new LQQueryTablePopup();
            if (this.getCommitType() != CommitTypeEnum.COMMIT_BY_DEFAULT) {
                quertablePopup.setCommitType(this.getCommitType());
            }
            quertablePopup.setDelay(delay);
            quertablePopup.setContent(this.getLqTableView());
            this.quertablePopup.setQueryCallback(this.getQueryCallback());
            this.quertablePopup.setCommitCallback((value) -> {
                if (getCommitCallback() == null) {
                    return true;
                }
                commitSelectData(value);
                boolean isclose = getCommitCallback().call(value);
                if (isclose) {
                    FocusUtil.focusToNext(LQTableViewChooseFormCell.this);
                }
                return isclose;

            });
        }

        return quertablePopup;
    }

  
    private void commitSelectData(Object value) {
        if (value == null) {
            return;
        }

        try {
            T v = (T) PropertyReflectUtil.getValue(value, this.getCommitPropertyName());
            this.commitValue(v);
            this.updateItem(this.getItem(), this.getItem() == null);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(LQTableViewChooseFormCell.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

      public LQFormCell clone(){
        LQTableViewChooseFormCell cell = (LQTableViewChooseFormCell) super.clone();
        cell.setCommitCallback(commitCallback);
        cell.setCommitPropertyName(commitPropertyName);
        cell.setDelay(delay);
        try {
            cell.setLqTableView(getLqTableView().clone());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(LQTableViewChooseFormCell.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cell.setQueryCallback(queryCallback);

        return cell;
    }
    
   

    /**
     * @return the queryCallback
     */
    public Callback<String, List> getQueryCallback() {
        return queryCallback;
    }

    /**
     * @param queryCallback the queryCallback to set
     */
    public void setQueryCallback(Callback<String, List> queryCallback) {
        this.queryCallback = queryCallback;
    }

    /**
     * @return the commitCallback
     */
    public Callback<Object, Boolean> getCommitCallback() {
        return commitCallback;
    }

    /**
     * @param commitCallback the commitCallback to set
     */
    public void setCommitCallback(Callback<Object, Boolean> commitCallback) {
        this.commitCallback = commitCallback;
    }

    /**
     * 查询延时
     *
     * @return the delay
     */
    public Long getDelay() {
        return delay;
    }

    /**
     * 查询延时
     *
     * @param delay the delay to set
     */
    public void setDelay(Long delay) {
        this.delay = delay;
    }

    /**
     * @return the lqTableView
     */
    public LQTableView getLqTableView() {
        return lqTableView;
    }

    /**
     * @param lqTableView the lqTableView to set
     */
    public void setLqTableView(LQTableView lqTableView) {
        this.lqTableView = lqTableView;
    }

}
