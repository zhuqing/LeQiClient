/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date.pick;


import com.leqienglish.client.control.buttonpane.ButtonPane;
import com.leqienglish.client.control.buttonpane.ButtonPane.ButtonSet;
import com.leqienglish.client.control.buttonpane.ButtonPaneBuilder;
import com.leqienglish.client.control.date.DisplayStyleEnum;
import com.leqienglish.client.control.skin.LQSkin;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NumberPicker的皮肤类
 *
 * @author niuben
 * @version 2013-6-20(review:huangjijun 2014-3-4 9:38:50)
 */
public class NumberPickerSkin extends LQSkin<NumberPicker, NumberPickerBehavior> {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberPickerSkin.class);
    /**
     * 布局控件
     */
    private final VBox loyoutVBx = new VBox();
    /**
     * 按钮面板
     */
    private ButtonPane btnPn;
    /**
     * 按钮构造生成器
     */
    private final ButtonPaneBuilder BtnPnBuilder = new ButtonPaneBuilder();

    /**
     * 数字面板skin构造方法
     *
     * @param c 数字面板控件
     */
    public NumberPickerSkin(NumberPicker c) {
        super(c, new NumberPickerBehavior(c));
    }

    /**
     * 显示皮肤
     */
    @Override
    protected void showSkin() {
        getChildren().add(loyoutVBx);
    }

    /**
     * 初始化皮肤
     */
    @Override
    protected void initSkin() {
        skin(getSkinnable().getDisplayStyle());
        getSkinnable().displayStyleProperty().addListener(new ChangeListener<DisplayStyleEnum>() {

            @Override
            public void changed(ObservableValue<? extends DisplayStyleEnum> observable, DisplayStyleEnum oldValue, DisplayStyleEnum newValue) {
                if (newValue == null) {
                    return;
                }
                skin(newValue);
            }
        });
    }

    /**
     * 皮肤初始化方法
     *
     * @param skinType 皮肤类型(即数字面板显示类型，奇数：4行3列，偶数：2行6列)
     */
    private void skin(DisplayStyleEnum skinType) {
        loyoutVBx.getChildren().removeAll(btnPn);

        //构造面板两种类型的内容
        String styleClass;
        String contentString;
        short row;
        short col;
        if (skinType == DisplayStyleEnum.H) {
            contentString = "1234567890.<";
            row = 2;
            col = 6;
            styleClass = "number_vbox2";
        } else {
            contentString = "123456789.0<";
            row = 4;
            col = 3;
            styleClass = "number_vbox1";
        }

        //初始化面板
        initNumBtnPn(contentString, col, row);

        loyoutVBx.getStyleClass().add(styleClass);
        loyoutVBx.getChildren().add(btnPn);

    }

    /**
     * 初始化面板按钮
     *
     * @param contentString 按钮显示字符串
     * @param row 行
     * @param col 列
     */
    private void initNumBtnPn(String contentString, short row, short col) {
        List<ButtonSet> buttonSetList = initButtonSetList(contentString, row, col);
        btnPn = BtnPnBuilder.buttonType(new Button())
                .cellHight(16d)
                .cellWidth(16d)
                .buttonSetList(buttonSetList)
                .buttonEventHander(new EventHandler() {
                    @Override
                    public void handle(Event t) {
                        btnHandler(((ButtonBase) t.getSource()));
                    }
                })
                .build();
        btnPn.getRoot().getStyleClass().add("number_pane");
    }

    /**
     * 根据行列来初始化ButtonSet列表
     *
     * @param contentString 数字面板显示字符集合连接
     * @param col 列数
     * @param row 行数
     * @return ButtonSet集合
     */
    public List<ButtonSet> initButtonSetList(String contentString, short col, short row) {
        if (col < 1 || row < 1) {
            throw new ExceptionInInitializerError("数字面板行或者列不能小于1");
        }
        List<ButtonSet> buttonSetList = new ArrayList<>();
        char[] contents = contentString.toCharArray();
        ButtonPane tmpBtnPn = new ButtonPane();
        LOGGER.debug("面板按钮配置信息开始->contents:" + contentString + "\trow:" + row + "\tcol:" + col + "\tlen:" + contents.length);
        for (int i = 0; i < row; i++) {
            //循环列 
            for (int j = 0; j < col; j++) {
                int index = i * col + j;
                if (index >= contents.length) {
                    continue;
                }
                String content = contents[index] + "";
                if (".".equals(content)) {

                }
                ButtonSet buttonSet = tmpBtnPn.new ButtonSet(i, j, 1, 1, getSkinnable().getId() + "/" + (".".equals(content) ? "point" : content), content, "Button");
                //循环出来的值 要传过去的参数是第几行，第几列，合并几行，合并几列，id，value，styleid 
                if (content.equals("<")) {
                    buttonSet.setButtonId("NumberPicker-clearButton");
                }
                buttonSetList.add(buttonSet);
            }
        }
        LOGGER.debug("面板按钮配置信息完成->ButtonSetList:" + buttonSetList + "\tlen:" + buttonSetList.size());
        return buttonSetList;
    }

    /**
     * 按钮事件方法
     *
     * @param btn 触发事件的按钮
     */
    private void btnHandler(ButtonBase btn) {

        if (btn == null) {
            return;
        }
        //获取数字面板依附的数值输入域，光标位置及输入域中内容
        TextInputControl numInput = getSkinnable().getNumInput();
        if (numInput == null) {
            throw new ExceptionInInitializerError("数字面板必需在输入域中使用!!!");
        }
        IndexRange indexRange = numInput.getSelection();
        int pos = numInput.getCaretPosition();

        Boolean btnDisable = btn.isDisable();
        btn.setDisable(false);

        //按钮显示内容
        String btnContent = btn.getText().trim();
        StringBuffer stringBuffer = new StringBuffer();
        if (numInput.getText() != null) {
            stringBuffer.append(numInput.getText().trim());
        }

        //是否是回退按钮
        Boolean isBackBtn = btnContent.equals("<");
        Boolean hasSelected = !numInput.getSelectedText().isEmpty();

        //如果是回退按钮，删除输入域中最后一位字符
        if (hasSelected) {
            stringBuffer.delete(indexRange.getStart(), indexRange.getEnd());
            pos = indexRange.getStart();
        }

        if (isBackBtn) {
            if (hasSelected) {
                //stringBuffer.delete(indexRange.getStart(), indexRange.getEnd());
                numInput.setText(stringBuffer.toString());
                numInput.selectRange(pos, pos);
            } else {
                if (pos == 0) {
                    return;
                }
                stringBuffer.deleteCharAt(pos - 1);
                numInput.setText(stringBuffer.toString());
                numInput.selectRange(pos - 1, pos - 1);
            }

        } else {
            //不是回退按钮，重置新值
            stringBuffer.insert(pos, btnContent);
            numInput.setText(stringBuffer.toString());
            numInput.selectRange(pos + 1, pos + 1);
            btn.setDisable(btnDisable);
        }

    }

}
