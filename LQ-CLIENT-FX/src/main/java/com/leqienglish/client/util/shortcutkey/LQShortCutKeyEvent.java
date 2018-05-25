/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.shortcutkey;

import java.util.Objects;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author zhuqing
 */
public class LQShortCutKeyEvent {

    public final static String SHORTCUT_KEY = "SHORTCUT_KEY";

    /**
     * 按键
     */
    private KeyCode keyCode;

    /**
     * shift键是否按下
     */
    private Boolean shiftDown;
    /**
     * control键是否按下
     */
    private Boolean controlDown;
    /**
     * alter键是否按下
     */
    private Boolean altDown;
    /**
     * META键是否按下
     */
    private Boolean metaDown;

    /**
     * 键盘事件类型
     */
    private EventType<KeyEvent> eventType = KeyEvent.KEY_RELEASED;
    /**
     * 键盘事件处理
     */
    private EventHandler<KeyEvent> eventHandler;

    /**
     * @return the keyCode
     */
    public KeyCode getKeyCode() {
        return keyCode;
    }

    /**
     * @param keyCode the keyCode to set
     */
    public void setKeyCode(KeyCode keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * @return the shiftDown
     */
    public Boolean getShiftDown() {
        if (this.shiftDown == null) {
            return false;
        }
        return shiftDown;
    }

    /**
     * @param shiftDown the shiftDown to set
     */
    public void setShiftDown(Boolean shiftDown) {
        this.shiftDown = shiftDown;
    }

    /**
     * @return the controlDown
     */
    public Boolean getControlDown() {
        if (this.controlDown == null) {
            return false;
        }
        return controlDown;
    }

    /**
     * @param controlDown the controlDown to set
     */
    public void setControlDown(Boolean controlDown) {
        this.controlDown = controlDown;
    }

    /**
     * @return the altDown
     */
    public Boolean getAltDown() {
        if (this.altDown == null) {
            return false;
        }
        return altDown;
    }

    /**
     * @param altDown the altDown to set
     */
    public void setAltDown(Boolean altDown) {
        this.altDown = altDown;
    }

    /**
     * @return the metaDown
     */
    public Boolean getMetaDown() {
        if (this.metaDown == null) {
            return false;
        }
        return metaDown;
    }

    /**
     * @param metaDown the metaDown to set
     */
    public void setMetaDown(Boolean metaDown) {
        this.metaDown = metaDown;
    }

    /**
     * @return the eventType
     */
    public EventType<KeyEvent> getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(EventType<KeyEvent> eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the eventHandler
     */
    public EventHandler<KeyEvent> getEventHandler() {
        return eventHandler;
    }

    /**
     * @param eventHandler the eventHandler to set
     */
    public void setEventHandler(EventHandler<KeyEvent> eventHandler) {
        this.eventHandler = eventHandler;
    }

    public boolean equalsKeyEvent(KeyEvent keyEvent) {
     
        if (keyEvent == null) {
            return false;
        }
        if (!Objects.equals(this.eventType.getName(), keyEvent.getEventType().getName())) {
            return false;
        }
        if (this.keyCode != keyEvent.getCode()) {
            return false;
        }

        if (this.shiftDown!=null && shiftDown) {
            if (!keyEvent.isShiftDown()) {
                return false;
            }
        }

        if (controlDown !=null && controlDown) {
            if (!keyEvent.isControlDown()) {
                return false;
            }
        }

        if (altDown!=null && altDown) {
            if (!keyEvent.isAltDown()) {
                return false;
            }
        }
        if (metaDown!=null && metaDown) {
            if (!keyEvent.isMetaDown()) {
                return false;
            }
        }

        return true;
    }

}
