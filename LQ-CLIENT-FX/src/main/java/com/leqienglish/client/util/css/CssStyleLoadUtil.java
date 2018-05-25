/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.css;

import com.leqienglish.client.util.sourceitem.SourceItem;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 *
 * @author zhangyingchuang
 */
public class CssStyleLoadUtil {

    private final Properties userStyle = new OrderedProperties();
    private File userfile;

    private final Properties properties = new OrderedProperties();

    private static CssStyleLoadUtil cssStyleLoadUtil;

    private final List<SourceItem> sourcesItems = new ArrayList<>();

    public static CssStyleLoadUtil getInstance() {
        if (cssStyleLoadUtil == null) {
            cssStyleLoadUtil = new CssStyleLoadUtil();
        }
        return cssStyleLoadUtil;
    }

    public void updateProperty(String cssStyle) throws Exception {
        userStyle.setProperty("用户样式", cssStyle);
        OutputStream fos = new FileOutputStream(userfile);
        userStyle.store(new OutputStreamWriter(fos, "utf-8"), "更新用户样式");
    }

    public void loadCssProperty() throws Exception {
        String userDirectory = System.getProperties().getProperty("user.home");
        String cssfile = userDirectory + "//HipApp//CssStyle.properties";
        userfile = new File(cssfile);
        if (!userfile.exists()) {
            if (!userfile.getParentFile().exists()) {
                userfile.getParentFile().mkdirs();
            }
            userfile.createNewFile();
        }
        InputStream in = new BufferedInputStream(new FileInputStream(userfile));
        userStyle.load(new InputStreamReader(in, "utf-8"));
        String cssStyle = userStyle.getProperty("用户样式");
        if (cssStyle == null) {
            userStyle.setProperty("用户样式", "css/hipapp.css");
            OutputStream fos = new FileOutputStream(userfile);
            userStyle.store(new OutputStreamWriter(fos, "utf-8"), "更新用户样式");
        }
    }

    public List<SourceItem> builderSourceItems() throws Exception {
        InputStream in = this.getClass().getResourceAsStream("/css/csslist.properties");
        properties.load(new InputStreamReader(in, "utf-8"));
        for (String key : properties.stringPropertyNames()) {
            if (key.equals("用户样式")) {
                continue;
            }
            SourceItem item = new SourceItem();
            item.setId(UUID.randomUUID().toString());
            item.setValue(properties.getProperty(key));
            item.setDisplay(key);
            sourcesItems.add(item);
        }
        if (sourcesItems.isEmpty()) {
            SourceItem item = new SourceItem();
            item.setId(UUID.randomUUID().toString());
            item.setValue("css/hipapp.css");
            item.setDisplay("默认样式");
            sourcesItems.add(item);
        }

        return sourcesItems;
    }

    public String getUserCssStyle() throws Exception {
        if (userStyle.isEmpty()) {
            loadCssProperty();
        }
        String path = userStyle.getProperty("用户样式");
        if (this.getClass().getResource("/"+path) == null) {
            path = "css/hipapp.css";
        }
        return path;
    }

    public SourceItem getSelectItem(String value) {
        for (SourceItem sourcesItem : sourcesItems) {
            if (sourcesItem.getValue().equals(value)) {
                return sourcesItem;
            }
        }
        return null;
    }
}
