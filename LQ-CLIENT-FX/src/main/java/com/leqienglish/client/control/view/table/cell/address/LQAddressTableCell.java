///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.leqienglish.client.control.view.table.cell.address;
//
//
//import com.leqienglish.client.control.view.table.cell.LQTableCell;
//import java.util.Map;
//import javafx.scene.control.TreeItem;
//
///**
// *
// * @author zhuqing
// */
//public class LQAddressTableCell<S> extends LQTableCell<S, Long> {
//
//    /**
//     * 地点字典名称
//     */
//    private String addressDictName = "AdministrativeDivesions";
//
//    private Map<Long, TreeItem<DictElement>> dictElementMap;
//
//    @Override
//    public void updateItem(Long item, boolean empty) {
//        if (item == null || empty) {
//            this.setText("");
//        } else {
//             this.setText(toText(item));
//        }
//    }
//
//    private String toText(Long id) {
//        if (this.getDictElementMap().containsKey(id)) {
//            TreeItem<DictElement> treeItem = getDictElementMap().get(id);
//            return toText(treeItem);
//        } else {
//            return "";
//        }
//    }
//
//    private String toText(TreeItem<DictElement> treeItem) {
//        if (treeItem == null) {
//            return "";
//        }
//        StringBuffer sb = new StringBuffer();
//        while (treeItem.getParent() != null) {
//            if (treeItem.getValue() == null) {
//                break;
//            }
//            sb.insert(0, treeItem.getValue().getElementName());
//            sb.insert(0, "/");
//            treeItem = treeItem.getParent();
//        }
//
//        sb.delete(0, 1);
//        return sb.toString();
//    }
//
//    @Override
//    public LQAddressTableCell clone() throws CloneNotSupportedException {
//
//        LQAddressTableCell<S> hipTableCell = (LQAddressTableCell<S>) super.clone();
//        if (hipTableCell == null) {
//            return null;
//        }
//        hipTableCell.setAddressDictName(addressDictName);
//        return hipTableCell;
//
//    }
//
//    /**
//     * @return the addressDictName
//     */
//    public String getAddressDictName() {
//        return addressDictName;
//    }
//
//    /**
//     * @param addressDictName the addressDictName to set
//     */
//    public void setAddressDictName(String addressDictName) {
//        this.addressDictName = addressDictName;
//    }
//
//    /**
//     * @return the dictElementMap
//     */
//    public Map<Long, TreeItem<DictElement>> getDictElementMap() {
//        if (dictElementMap == null) {
//            TreeItem<DictElement> tree = ETSRepertory.getEtsUtil().getDictElementTreeByDictCode(addressDictName);
//            dictElementMap = TreeItemUtil.createMap(tree.getChildren(), (DictElement dict) -> dict.getId());
//        }
//        return dictElementMap;
//    }
//
//    /**
//     * @param dictElementMap the dictElementMap to set
//     */
//    public void setDictElementMap(Map<Long, TreeItem<DictElement>> dictElementMap) {
//        this.dictElementMap = dictElementMap;
//    }
//
//}
