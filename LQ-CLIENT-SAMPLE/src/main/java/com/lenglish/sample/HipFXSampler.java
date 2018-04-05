package com.lenglish.sample;

import fxsampler.FXSamplerProject;
import fxsampler.model.WelcomePage;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HipFXSampler implements FXSamplerProject {

    /**
     * 项目树根节点
     *
     * @return
     */
    @Override
    public String getProjectName() {
        return "HipApp-client-component";
    }

    /**
     * demo根目录
     *
     * @return
     */
    @Override
    public String getSampleBasePackage() {
        return "com.lenglish.sample";
    }

    /**
     * 首页
     *
     * @return
     */
    @Override
    public WelcomePage getWelcomePage() {
        java.io.BufferedInputStream fb3 = new java.io.BufferedInputStream(this.getClass().getResourceAsStream("/font/SimSunBold.ttf"));
        Font.loadFont(fb3, 12);
        VBox vBox = new VBox();
        ImageView imgView = new ImageView();
        imgView.setStyle("-fx-image: url('img/logo_1.png');");
        StackPane pane = new StackPane();
        pane.setPrefHeight(207);
        pane.setStyle("-fx-background-color: #246A89;");
        pane.getChildren().add(imgView);
        Label label = new Label();
        label.setWrapText(true);
        StringBuilder desc = new StringBuilder();
        desc.append("HipApp-client-component是医疗信息平台中自定义的javaFX组件项目。");
        desc.append("HIP-FX-Sample是HIP中所有自定义的控件和使用到的第三方控件演示项目。");
        label.setText(desc.toString());
        label.setStyle("-fx-font-size: 1.5em; -fx-padding: 20 0 0 5;");
        vBox.getChildren().addAll(pane, label);
        WelcomePage wPage = new WelcomePage("Welcome to HipApp-client-component!", vBox);
        return wPage;
    }
}
