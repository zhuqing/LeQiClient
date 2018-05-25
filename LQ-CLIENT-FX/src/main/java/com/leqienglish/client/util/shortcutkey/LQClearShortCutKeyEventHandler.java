/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.shortcutkey;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author zhuqing
 */
public class LQClearShortCutKeyEventHandler extends LQShortCutKeyEvent{
      public LQClearShortCutKeyEventHandler(){
       this.setKeyCode(KeyCode.C);
       this.setControlDown(Boolean.TRUE);
       this.setEventType(KeyEvent.KEY_PRESSED);
   }
}
