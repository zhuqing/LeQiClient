/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.app;


import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.config.AppConfig;

import com.leqienglish.client.fw.uf.FXMLModel;
import com.leqienglish.client.fw.util.property.HipProperty;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.ay;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author duyi
 */
public abstract class AbstractApplication extends Application {

    private static FXMLModel rootModel;

    private static ClassPathXmlApplicationContext context;

    private static ClassPathXmlApplicationContext fwContext;

    private static AppConfig appConfig;

    private static Stage primaryStage;

    private static Scene scene;

    private static String nowBusinessCode;

    private static String toggleNavCode;

    private static Map<Object, Browser> browserMap = new HashMap<>();

    public static FXMLModel initRootModel() {
        if (rootModel == null) {
            rootModel = (FXMLModel) context.getBean("rootModel");
        }
        return rootModel;
    }

    public static FXMLModel getRootModel() {
        return rootModel;
    }

    public static void setRootModel(FXMLModel rootModel) {
        AbstractApplication.rootModel = rootModel;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static AppConfig getAppConfig() {
        return appConfig;
    }

   


    public static void setNowBusinessId(String nowBusinessCode) {
        AbstractApplication.nowBusinessCode = nowBusinessCode;
    }

    public static String getToggleNavCode() {
        return toggleNavCode;
    }

    public static void setToggleNavCode(String toggleNavCode) {
        AbstractApplication.toggleNavCode = toggleNavCode;
    }

    @Override
    public final void init() throws Exception {
        
   
        ApplicationContext configContext = new ClassPathXmlApplicationContext("/config.xml");
       
        context = new ClassPathXmlApplicationContext("/fwBeans.xml");
        initApp();
    }

    @Override
    public final void start(Stage primaryStage) throws Exception {
        rootModel = initRootModel();


        AbstractApplication.primaryStage = primaryStage;
        Scene scene = new Scene((StackPane) getRootModel().getRoot());
        ((StackPane) getRootModel().getRoot()).maxHeightProperty().bind(scene.heightProperty());
        ((StackPane) getRootModel().getRoot()).maxWidthProperty().bind(scene.widthProperty());
        AbstractApplication.scene = scene;
       
        configScene(scene);
        primaryStage.setScene(scene);
        configStage(primaryStage);
//        ScenicView.show(scene);
        primaryStage.setOnHidden((WindowEvent event) -> {
            close();
        });
     
        primaryStage.show();
    }

    protected abstract void configScene(Scene scene);

    protected abstract void configStage(Stage stage);

    protected abstract void initApp();

    public static void initApplicationContext() {
        fwContext = context;
        String amq = HipProperty.getHipAppProperty("AMQ");
        if (Boolean.TRUE.equals(Boolean.valueOf(amq))) {
            String[] configXMLs = new String[3];
            configXMLs[0] = "ActiveMQ.xml";
            configXMLs[1] = "beans.xml";
            configXMLs[2] = "classpath*:/config/uf/*.xml";
            context = new ClassPathXmlApplicationContext(configXMLs, fwContext);
        } else {
            String[] configXMLs = new String[2];
            configXMLs[0] = "beans.xml";
            configXMLs[1] = "classpath*:/config/uf/*.xml";
            context = new ClassPathXmlApplicationContext(configXMLs, fwContext);
        }
    }

    private void close() {
        for (Map.Entry<Object, Browser> entry : browserMap.entrySet()) {
            Browser value = entry.getValue();
            if (Objects.nonNull(value)) {
                value.dispose();
            }
        }
        EhCacheCacheManager cacheManager = (EhCacheCacheManager) context.getBean("cacheManager");
        cacheManager.getCacheManager().shutdown();
        Command.getThreadPool().shutdown();
      
     
        context.close();
        if (fwContext != null) {
            fwContext.close();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Scene getScene() {
        return scene;
    }

    public static Map<Object, Browser> getBrowserMap() {
        return browserMap;
    }

   

}
