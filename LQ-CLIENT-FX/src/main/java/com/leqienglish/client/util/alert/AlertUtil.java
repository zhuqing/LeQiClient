/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.alert;

import com.leqienglish.client.util.css.CssStyleLoadUtil;
import com.leqienglish.client.util.reflect.MethodReflectUtil;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author zhuqing
 */
public class AlertUtil {

    public static final ButtonType YES = new ButtonType("是");
    public static final ButtonType NO = new ButtonType("否");
    public static final ButtonType CONFIRM = new ButtonType("确认");
    public static final ButtonType CANCEL = new ButtonType("取消");

    public static void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        addEnterEvent(alert);
        alert.showAndWait();
    }

    public static void showAlert(String message, Alert.AlertType type) {

        Alert alert = new Alert(type);
        alert.setContentText(message);
        addEnterEvent(alert);
        alert.showAndWait();
    }

    /**
     * 创建Alert
     *
     * @param alertType
     * @param message
     * @param buttonTypes
     * @return
     */
    public static ButtonType showAlert(Alert.AlertType alertType, String message, ButtonType... buttonTypes) {

        Alert alert = create(alertType, message, buttonTypes);

        return alert.showAndWait().orElse(null);

    }

    private static Alert create(Alert.AlertType alertType, String message, ButtonType... buttonTypes) {
        Alert alert = new Alert(alertType, message, buttonTypes);
        addEnterEvent(alert);

        return alert;
    }

    /**
     * 创建Alert
     *
     * @param alertType
     * @param message
     * @param width
     * @param height
     * @param buttonTypes
     * @return
     */
    public static ButtonType showAlert(Alert.AlertType alertType, String message, double width, double height, ButtonType... buttonTypes) {

        Alert alert = create(alertType, message, buttonTypes);
        alert.setWidth(width);
        alert.setHeight(height);
        return alert.showAndWait().orElse(null);

    }

    /**
     * 设置Alert的键盘事件
     *
     * @param alert
     */
    public static void addEnterEvent(Dialog alert) {
        for (ButtonType buttonType : alert.getDialogPane().getButtonTypes()) {
            Node node = alert.getDialogPane().lookupButton(buttonType);
            try {
                alert.getDialogPane().getStylesheets().add(CssStyleLoadUtil.getInstance().getUserCssStyle());
            } catch (Exception ex) {
                Logger.getLogger(AlertUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            node.addEventHandler(KeyEvent.KEY_PRESSED, ea -> {
                if (ea.getCode() != KeyCode.ENTER) {
                    return;
                }
                Method method = MethodReflectUtil.getMethod(Dialog.class, "impl_setResultAndClose", ButtonType.class, boolean.class);
                MethodReflectUtil.executeMethod(method, alert, buttonType, true);
            });
        }
    }
}
