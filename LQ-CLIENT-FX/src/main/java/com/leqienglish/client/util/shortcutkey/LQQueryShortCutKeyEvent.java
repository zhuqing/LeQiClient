/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.shortcutkey;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *查询快捷键事件
 * @author zhuqing
 */
public class LQQueryShortCutKeyEvent extends LQShortCutKeyEvent{

    public LQQueryShortCutKeyEvent() {
    
       //this.setControlDown(Boolean.TRUE);
       this.setKeyCode(KeyCode.F5);
       this.setEventType(KeyEvent.KEY_PRESSED);
   }

}
