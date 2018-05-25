/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.buttonpane;

import com.leqienglish.client.control.buttonpane.ButtonPane.ButtonSet;
import com.leqienglish.client.control.skin.LQSkin;
import java.util.ArrayList;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 按钮面板的skin类
 *
 * @author duyi
 * @version 2013-6-20
 * @param <T>
 */
public class ButtonPaneSkin<T extends ButtonBase> extends LQSkin<ButtonPane<T>, ButtonPaneBehavior<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ButtonPaneSkin.class);
    ToggleGroup toggleGroup = new ToggleGroup();

    /**
     * 构造方法,将网格布局面板作为子节点加入到ButtonPaneSkin中。
     *
     * @param c ButtonPane的control类
     */
    public ButtonPaneSkin(ButtonPane<T> c) {
        super(c, new ButtonPaneBehavior<>(c));
        getChildren().add(getSkinnable().getRoot());
    }

    /**
     * 初始化按钮 在线程池中运行
     */
    @Override
    protected void initSkin() {
        super.initSkin();
        createButtons();
        LOGGER.debug("运行initSkin，初始化构造按钮。");
    }

    public void createButtons() {
        getSkinnable().getButtonObservableList().clear();
        List<ButtonPane<T>.ButtonSet> btnSetList = getSkinnable().getButtonSetList();
        List<T> btss = new ArrayList<T>();
        for (int i = 0; i < btnSetList.size(); i++) {
            T btn = createButton(btnSetList.get(i));
            btss.add(btn);

            LOGGER.debug("构造按钮：" + btn.getId());
        }

        getSkinnable().getButtonObservableList().setAll(btss);
    }

    /**
     * 显示按钮 在Application中运行
     */
    @Override
    public void showSkin() {
        getSkinnable().getRoot().getChildren().clear();
        int start = getSkinnable().getShowStart();
        int end = getSkinnable().getShowEnd();
        ObservableList<T> btnList = getSkinnable().getButtonObservableList();
        List<ButtonPane<T>.ButtonSet> btnSetList = getSkinnable().getButtonSetList();
        end = btnList.size() > end ? end : btnList.size();
        //循环将按钮添加到ButtonPane中显示
        for (int i = start - 1; i < end; i++) {
            getSkinnable().getRoot().add((T) btnList.get(i),
                    btnSetList.get(i).getColumnId(),
                    btnSetList.get(i).getRowId(),
                    btnSetList.get(i).getColSpan(),
                    btnSetList.get(i).getRowSpan());
            reStyleClass(btnList.get(i), btnSetList.get(i));
            btnList.get(i).setText(btnSetList.get(i).getButtonValue());
            btnList.get(i).setDisable(btnSetList.get(i).isDisable());
            //按钮事件处理必须在Application中运行
        }
        getSkinnable().getRoot().layout();
        LOGGER.debug("运行showSkin，显示按钮。");
    }

    /**
     * 构造按钮
     *
     * @param buttonSet
     * @return
     */
    private T createButton(ButtonSet buttonSet) {
        T btn = null;
        try {
            btn = (T) getSkinnable().getButtonType().getClass().newInstance();
            if (btn instanceof ToggleButton) {
                ((ToggleButton) btn).setToggleGroup(toggleGroup);
                if (buttonSet.isIsSelected()) {
                    ((ToggleButton) btn).setSelected(true);
                }
            }
            if (buttonSet.isIsSelected()) {
                btn.requestFocus();
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            LOGGER.error(ex.toString(), ex);
            return null;
        }
        btn.setPrefSize(getSkinnable().getCellWidth() * buttonSet.getColSpan(), getSkinnable().getCellHight() * buttonSet.getRowSpan());
        btn.setId(buttonSet.getButtonId());
        btn.setText(buttonSet.getButtonValue());
        reStyleClass(btn, buttonSet);
        btn.setOnAction(new EventHandler() {
            @Override
            public void handle(Event t) {
                getSkinnable().getButtonEventHander().handle(t);
                LOGGER.debug(t.getSource() + "处理ButtonPane的事件");
            }
        });

        btn.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (KeyCode.ENTER.equals(event.getCode())) {
                    getSkinnable().getButtonEventHander().handle(event);
                }
            }
        });
//        KeyEventHandlerUtil.regist(btn, new Consumer<KeyEvent>(){
//            @Override
//            public void accept(KeyEvent t) {
//                 getSkinnable().getButtonEventHander().handle(t);
//            }
//        }, KeyCode.ENTER);
        btn.setUserData(buttonSet.getButtonUserData());
        btn.setDisable(buttonSet.isDisable());
        LOGGER.debug("构造按钮：" + btn.getId());
        return btn;
    }

    /**
     * 重新为按钮加载样式
     *
     * @param btn
     * @param buttonSet
     */
    public void reStyleClass(ButtonBase btn, ButtonSet buttonSet) {
        if (getSkinnable().getBtnStyleClass() != null
                && !btn.getStyleClass().contains(getSkinnable().getBtnStyleClass())) {
            btn.getStyleClass().addAll(getSkinnable().getBtnStyleClass());
        }
        if (buttonSet.getStyleid() != null
                && !btn.getStyleClass().contains(buttonSet.getStyleid())) {
            btn.getStyleClass().add(buttonSet.getStyleid());
        }
    }

}
