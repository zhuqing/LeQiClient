///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.leqienglish.client.control.form.cell.edit.text;
//
//import com.bjgoodwill.hip.client.fx.control.choose.item.SourceItem;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.util.Callback;
//
///**
// *
// * @author zyc
// * @param <S>
// * @param <T>
// */
//public class HipCustomTextInputFormCell<S> extends HipTextInputFormCell<S> {
//
//    private List<SourceItem<String>> sourceItems;
//
//    private Map<String, SourceItem<String>> sourceItemsCodeMap;
//    private Map<String, SourceItem<String>> sourceItemsNameMap;
//
//    private Callback<S, List<SourceItem<String>>> customDataSourceCallback;
//
//    private ChangeListener<String> textChangeListener = new ChangeListener<String>() {
//        @Override
//        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//            if (getTextField().getText() == null || getTextField().getText().trim().isEmpty()) {
//                commitValue(null);
//            } else {
//                commitValue(getValuesByName(getTextField().getText()));
//            }
//        }
//    };
//
//    @Override
//    protected String toText(String code) {
//
//        StringBuilder textBuilder = new StringBuilder();
//        if (code == null || code.isEmpty()) {
//            return "";
//        }
//        String[] codeArray = code.split(";");
//        if (getSourceItemsCodeMap().isEmpty()) {
//            builderSourceItemMap();
//        }
//        for (String item : codeArray) {
//            textBuilder.append(getSourceItemsCodeMap().get(item).getDisplay()).append(";");
//        }
//        return textBuilder.toString();
//    }
//
//    private void builderSourceItemMap() {
//        getSourceItemsCodeMap().clear();
//        getSourceItemsNameMap().clear();
//        if (getCustomDataSourceCallback() == null) {
//            sourceItems = new ArrayList<>();
//        } else {
//            sourceItems = getCustomDataSourceCallback().call(this.getItem());
//        }
//        for (SourceItem<String> sourceItem : sourceItems) {
//            getSourceItemsCodeMap().put(sourceItem.getValue(), sourceItem);
//            getSourceItemsNameMap().put(sourceItem.getDisplay(), sourceItem);
//        }
//    }
//
//    private String getValuesByName(String name) {
//        String[] namesArray = name.split(";");
//        StringBuilder valueBuilder = new StringBuilder();
//        if (getSourceItemsNameMap().isEmpty()) {
//            builderSourceItemMap();
//        }
//        for (String item : namesArray) {
//            SourceItem sourceItem = getSourceItemsNameMap().get(item);
//            if (sourceItem != null) {
//                valueBuilder.append(sourceItem.getValue()).append(";");
//            }
//        }
//        return valueBuilder.toString();
//    }
//
//    public Map<String, SourceItem<String>> getSourceItemsCodeMap() {
//        if (sourceItemsCodeMap == null) {
//            sourceItemsCodeMap = new HashMap<>();
//        }
//        return sourceItemsCodeMap;
//    }
//
//    public Map<String, SourceItem<String>> getSourceItemsNameMap() {
//        if (sourceItemsNameMap == null) {
//            sourceItemsNameMap = new HashMap<>();
//        }
//        return sourceItemsNameMap;
//    }
//
//    public Callback<S, List<SourceItem<String>>> getCustomDataSourceCallback() {
//        return customDataSourceCallback;
//    }
//
//    public void setCustomDataSourceCallback(Callback<S, List<SourceItem<String>>> customDataSourceCallback) {
//        this.customDataSourceCallback = customDataSourceCallback;
//    }
//
//    @Override
//    protected ChangeListener<String> getTextChangeListener() {
//        return textChangeListener; //To change body of generated methods, choose Tools | Templates.
//    }
//
//}
