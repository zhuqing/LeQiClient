/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.text;


import com.leqienglish.util.text.TextUtil;
import com.leqienglish.util.text.TextUtil.FILL_DIRACTION;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.util.function.Consumer;
import javafx.event.EventType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author zhuqing
 */
public class AutoFillTextField extends TextField {

    /**
     * 最大长度
     */
    private int maxLength = 10;

    /**
     * 填充文本
     */
    private String fillText = "*";

    /**
     * 填充方向
     */
    private FILL_DIRACTION fillDiraction = FILL_DIRACTION.HEAD;
    
    /**
     *执行完成后的提交 
     */
    private Consumer commit;

    public AutoFillTextField() {
        initListener();
    }

    private void initListener() {
        JavaFxObservable.eventsOf(this, KeyEvent.KEY_PRESSED)
                .filter((key) -> key.getCode() == KeyCode.ENTER)
                .subscribe((key) -> fillText());
        JavaFxObservable.nullableValuesOf(this.focusedProperty())
                .filter((f) -> !f.orElse(false))
                .subscribe((f) -> fillText());
    }

    private void fillText() {
        if (this.getText() == null || this.getText().isEmpty()) {
            return;
        }
        String text = TextUtil.fillText(this.getText(), this.getFillText(), this.getMaxLength(), this.getFillDiraction());
        this.setText(text);
        
        if(this.getCommit() !=null){
            this.getCommit().accept(this);
        }
    }

    /**
     * 最大长度
     *
     * @return the maxLength
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * 最大长度
     *
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * 填充文本
     *
     * @return the fillText
     */
    public String getFillText() {
        return fillText;
    }

    /**
     * 填充文本
     *
     * @param fillText the fillText to set
     */
    public void setFillText(String fillText) {
        this.fillText = fillText;
    }

    /**
     * 填充方向
     *
     * @return the fillDiraction
     */
    public FILL_DIRACTION getFillDiraction() {
        return fillDiraction;
    }

    /**
     * 填充方向
     *
     * @param fillDiraction the fillDiraction to set
     */
    public void setFillDiraction(FILL_DIRACTION fillDiraction) {
        this.fillDiraction = fillDiraction;
    }

    /**
     * 执行完成后的提交
     * @return the commit
     */
    public Consumer getCommit() {
        return commit;
    }

    /**
     * 执行完成后的提交
     * @param commit the commit to set
     */
    public void setCommit(Consumer commit) {
        this.commit = commit;
    }

    

}
