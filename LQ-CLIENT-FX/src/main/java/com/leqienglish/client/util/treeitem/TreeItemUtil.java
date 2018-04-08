/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.treeitem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import javafx.scene.control.*;
import org.apache.http.client.utils.CloneUtils;

/**
 *
 * @author zhuqing
 */
public class TreeItemUtil {

    /**
     * clone 只克隆TreeItem，但不克隆，value
     *
     * @param <T>
     * @param item
     * @return
     */
    public static <T> TreeItem<T> clone(TreeItem<T> item) {
        TreeItem<T> newItem = new TreeItem<T>();
        newItem.setExpanded(item.isExpanded());
        newItem.setValue(item.getValue());
        if (!item.getChildren().isEmpty()) {
            newItem.getChildren().addAll(clone(item.getChildren()));
        }
        return item;
    }

    /**
     * TreeItem和value都不克隆
     *
     * @param <T>
     * @param item
     * @return
     */
    public static <T> TreeItem<T> deepClone(TreeItem<T> item) {
        TreeItem<T> newItem = new TreeItem<T>();
        newItem.setExpanded(item.isExpanded());
        if (item.getValue() instanceof Serializable) {
            try {
                newItem.setValue((T) CloneUtils.clone(item.getValue()));
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(TreeItemUtil.class.getName()).log(Level.SEVERE, null, ex);
                newItem.setValue(item.getValue());
            }
        } else if (item.getValue() instanceof TreeItem) {
            newItem.setValue((T) deepClone((TreeItem<T>) item.getValue()));
        } else {
            newItem.setValue(item.getValue());
        }

        if (!item.getChildren().isEmpty()) {
            newItem.getChildren().addAll(clone(item.getChildren()));
        }
        return item;
    }

    public static <T> List<TreeItem<T>> deepClone(List<TreeItem<T>> items) {
        List<TreeItem<T>> lists = new ArrayList<>();
        for (TreeItem<T> item : items) {
            lists.add(deepClone(item));
        }

        return lists;
    }

    public static <T> List<TreeItem<T>> clone(List<TreeItem<T>> items) {
        List<TreeItem<T>> lists = new ArrayList<>();
        for (TreeItem<T> item : items) {
            lists.add(clone(item));
        }

        return lists;
    }

    public static <T> List<TreeItem<T>> expandChildren(List<TreeItem<T>> items) {
        List<TreeItem<T>> lists = new ArrayList<>();
        for (TreeItem<T> item : items) {
            lists.add(item);
            if (!item.getChildren().isEmpty()) {
                lists.addAll(item.getChildren());
            }
        }
        return lists;
    }

    public static void expendAllParent(TreeItem treeItem, boolean isExpend) {
        if (treeItem == null) {
            return;
        }
        treeItem.setExpanded(isExpend);
        expendAllParent(treeItem.getParent(), isExpend);
    }

    public static <T> void copyExpended(TreeItem<T> oldTreeItems, TreeItem<T> newTreeItems, Callback<T, Object> keyCallback) {
        copyExpended(Arrays.asList(oldTreeItems), Arrays.asList(newTreeItems), keyCallback);
    }

    public static <T> void copyToRoot(TreeItem<T> root, List<TreeItem<T>> items, Comparator<T> comparator) {
        ObservableList<TreeItem<T>> children = root.getChildren();

        if (children.isEmpty()) {
            root.getChildren().setAll(items);
            return;
        }

        for (TreeItem<T> item : items) {
            boolean has = false;
            for (TreeItem<T> child : children) {
                if (comparator.compare(item.getValue(), child.getValue()) == 0) {
                    has = true;
                    copyToRoot(child, item.getChildren(), comparator);
                    break;
                }
            }

            if (!has) {
                root.getChildren().add(item);
            }
        }

    }

    /**
     * 拷贝TreeItem中的expended属性
     *
     * @param <T>
     * @param oldTreeItems
     * @param newTreeItems
     * @param keyCallback
     */
    public static <T> void copyExpended(List<TreeItem<T>> oldTreeItems, List<TreeItem<T>> newTreeItems, Callback<T, Object> keyCallback) {
        if (keyCallback == null) {
            return;
        }
        Map<Object, TreeItem<T>> oldTreeItemMap = createMap(oldTreeItems, keyCallback);
        Map<Object, TreeItem<T>> newTreeItemMap = createMap(newTreeItems, keyCallback);
        newTreeItemMap.keySet().forEach((Object key) -> {
            TreeItem oldTreeItem = oldTreeItemMap.get(key);
            TreeItem newTreeItem = newTreeItemMap.get(key);

            if (oldTreeItem == null || newTreeItem == null) {
                return;
            }

            newTreeItem.setExpanded(oldTreeItem.isExpanded());
        });
    }

    public static <T, K> Map<K, TreeItem<T>> createMap(List<TreeItem<T>> treeItems, Callback<T, K> keyCallback) {
        Map<K, TreeItem<T>> map = new HashMap<>();

        for (TreeItem<T> treeItem : treeItems) {
            map.put(keyCallback.call(treeItem.getValue()), treeItem);
            if (!treeItem.getChildren().isEmpty()) {
                map.putAll(createMap(treeItem.getChildren(), keyCallback));
            }
        }
        return map;
    }

    /**
     * 把树转换成List，从父到子
     *
     * @param <T>
     * @param item
     * @return
     */
    public static <T> List<T> getListFromParent2Child(TreeItem<T> item) {
        List<T> items = new ArrayList<>();
        while (item != null && item.getValue() != null) {
            items.add(item.getValue());
            item = item.getParent();
        }
        Collections.reverse(items);
        return items;

    }

    /**
     * 把树转换成List，从父到子
     *
     * @param <T>
     * @param item
     * @return
     */
    public static <T> List<TreeItem<T>> getTreeItemsFromParent2Child(TreeItem<T> item) {
        List<TreeItem<T>> items = new ArrayList<>();
        while (item != null && item.getValue() != null) {
            items.add(item);
            item = item.getParent();
        }
        Collections.reverse(items);
        return items;

    }

    public static <T> List<TreeItem<T>> getAllTops(List<TreeItem<T>> leafs) {
        //  List<TreeItem<T>> tops = new ArrayList<>();

        return leafs.stream().map((c) -> getTop(c)).collect(Collectors.toList());
    }

    public static <T> TreeItem<T> getTop(TreeItem<T> leaf) {
        TreeItem<T> node = leaf;
        do {
            if (node.getParent() == null) {
                return node;
            }
            node = node.getParent();
        } while (true);

    }

//    public static <T> List<TreeItem<T>> getAllLeaf(List<TreeItem<T>> items) {
//        List<TreeItem<T>> leafs = new ArrayList<>();
//
//        getAllLeaf(items, leafs);
//
//        return leafs;
//    }
//
//    private static <T> void getAllLeaf(List<TreeItem<T>> items, List<TreeItem<T>> leafs) {
//        for (TreeItem<T> item : items) {
//            if (item.getChildren().isEmpty()) {
//                leafs.add(item);
//            } else {
//                getAllLeaf(item.getChildren(), leafs);
//            }
//
//        }
//    }
    /**
     * 获取所有叶子节点
     *
     * @param <T>
     * @param items
     * @return
     */
    public static <T> List<TreeItem<T>> getAllLeaf(List<TreeItem<T>> items) {
        List<TreeItem<T>> leafs = new ArrayList<>();

        for (TreeItem<T> item : items) {
            if (item.getChildren().isEmpty()) {
                leafs.add(item);
            } else {
                leafs.addAll(getAllLeaf(item.getChildren()));
            }
        }

        return leafs;
    }

    public static <T> void deleteChildren(TreeItem<T> root, Function<T, Boolean> deletefunction) {
        if (deletefunction == null || root == null || root.getChildren().isEmpty()) {
            return;
        }

        List<TreeItem<T>> items = root.getChildren();
        for (int i = 0; i < items.size(); i++) {
            if (deletefunction.apply(items.get(i).getValue())) {
                root.getChildren().remove(items.get(i));
                i--;
                continue;
            }
            if (!items.get(i).getChildren().isEmpty()) {
                deleteChildren(items.get(i), deletefunction);
            }

        }
    }

    /**
     * 从items中过滤出想要的数据
     *
     * @param <T>
     * @param items
     * @param predicate
     * @return
     */
    public static <T> List<TreeItem<T>> filter(List<TreeItem<T>> items, Predicate<T> predicate) {
        if (items == null || predicate == null) {
            return Collections.EMPTY_LIST;
        }
        return items.stream().filter((tree) -> predicate.test(tree.getValue())).collect(Collectors.toList());
    }

}
