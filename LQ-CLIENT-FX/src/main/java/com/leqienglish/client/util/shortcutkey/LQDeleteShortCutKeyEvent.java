/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.shortcutkey;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *删除快捷键事件
 * @author zhuqing
 */
public class LQDeleteShortCutKeyEvent extends LQShortCutKeyEvent{

   public LQDeleteShortCutKeyEvent(){
       this.setControlDown(Boolean.TRUE);
       this.setKeyCode(KeyCode.D);
       this.setEventType(KeyEvent.KEY_PRESSED);
   }

}
