/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.app;

import com.leqienglish.client.fw.app.AbstractApplication;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyi
 */
public class LQApplication extends AbstractApplication {

    private final static Logger logger = LoggerFactory.getLogger(LQApplication.class);

    public static void main(final String... args) throws UnknownHostException {
     //   AbstractApplication.initApplicationContext();
        launch(LQApplication.class);
    }

    @Override
    protected void initApp() {
    }

    @Override
    protected void configStage(Stage stage) {
        stage.initStyle(StageStyle.DECORATED);
    }

    @Override
    protected void configScene(Scene scene) {
    }

}
