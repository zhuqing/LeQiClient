/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.shortcutkey;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *保存快捷键事件
 * @author zhuqing
 */
public class LQCancelShortCutKeyEvent extends LQShortCutKeyEvent{

   public LQCancelShortCutKeyEvent(){
       this.setAltDown(Boolean.FALSE);
       this.setControlDown(Boolean.TRUE);
       this.setKeyCode(KeyCode.X);
       this.setEventType(KeyEvent.KEY_PRESSED);
   }

}
