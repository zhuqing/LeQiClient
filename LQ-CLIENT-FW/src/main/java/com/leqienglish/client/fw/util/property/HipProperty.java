/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.util.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author duyi
 */
@Lazy
@Component("hipProperty")
public class HipProperty {

    private final static Logger logger = LoggerFactory.getLogger(HipProperty.class);
    private final static Properties HIP_APP = new Properties();

    private static void init() {
        if (HIP_APP.keySet() == null || HIP_APP.keySet().isEmpty()) {
            try {
                String userDir = System.getProperties().getProperty("user.dir");
                File file = new File(userDir + "/hipApp.properties");
                if (file.exists()) {
                    InputStream is = new FileInputStream(file);
                    HIP_APP.load(is);
                    is.close();
                } else {
                    InputStream is = HipProperty.class.getResourceAsStream("/hipApp.properties");
                    HIP_APP.load(is);
                    is.close();
                }
            } catch (IOException ex) {
                logger.error("加载Property配置文件失败！！！", ex);
            }
        }
    }

    public static String getHipAppProperty(String key) {
        init();
        return HIP_APP.getProperty(key);
    }

    public static String getHipAppProperty(String key, String defaultValue) {
        init();
        return HIP_APP.getProperty(key);
    }
}
