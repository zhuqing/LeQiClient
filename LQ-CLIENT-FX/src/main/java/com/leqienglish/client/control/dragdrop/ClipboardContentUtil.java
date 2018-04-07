/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.dragdrop;



//import com.bjgoodwill.hip.client.util.treeitem.TreeItemUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;

/**
 *
 * @author zhuqing
 */
public class ClipboardContentUtil {

    public static Map<String, Object> dragFromNode = new HashMap<>();

    public static final DataFormat DATA = new DataFormat("DATA/HIP");

    public static final DataFormat OLD_DATA = new DataFormat("OLD_DATA/HIP");

    public static final DataFormat VIEW = new DataFormat("VIEW/HIP");

    public static final DataFormat INDEX = new DataFormat("INDEX/HIP");

    public static ClipboardContent create(Node view, Collection datas) {

        return create(view, -1, datas);
    }

    private static <T> Collection<T> clone(Collection<T> datas) {
        List<T> lists = new ArrayList<T>();
        for (T item : datas) {
            if (item instanceof Serializable) {
                T t = item;
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(item);

                    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    t = (T) ois.readObject();

                } catch (Exception ex) {
                    Logger.getLogger(ClipboardContentUtil.class.getName()).log(Level.SEVERE, null, ex);
                    t = item;
                }
                lists.add(t);
            } else if (item instanceof TreeItem) {
               // T t = (T) TreeItemUtil.deepClone((TreeItem) item);
               // lists.add(t);
            } else {
                lists.add(item);
            }
        }
        return lists;
    }

    public static <T> ClipboardContent create(Node view, int index, Collection<T> datas) {
        ClipboardContent clipboardContent = new ClipboardContent();
        dragFromNode.clear();
        String viewId = UUID.randomUUID().toString();
        String dataId = UUID.randomUUID().toString();
        String oldDataId = UUID.randomUUID().toString();

        dragFromNode.put(viewId, view);
        dragFromNode.put(dataId, clone(datas));
        dragFromNode.put(oldDataId, datas);

        clipboardContent.put(VIEW, viewId);
        clipboardContent.put(DATA, dataId);
        clipboardContent.put(OLD_DATA, oldDataId);
        clipboardContent.put(INDEX, index);
        return clipboardContent;
    }

    public static Integer getIndex(Dragboard content) {
        if (content.hasContent(INDEX)) {
            return (Integer) content.getContent(INDEX);
        } else {
            return null;
        }

    }

    public static Object getView(Dragboard content) {
        String viewId = (String) content.getContent(VIEW);
        return dragFromNode.get(viewId);
    }

    public static Object getData(Dragboard content) {
        String viewId = (String) content.getContent(DATA);
        return dragFromNode.get(viewId);
    }

    public static void removeDataFromView(Dragboard content) {
        String viewId = (String) content.getContent(VIEW);
        String oldDataId = (String) content.getContent(OLD_DATA);

        Object view = dragFromNode.get(viewId);
        Object data = dragFromNode.get(oldDataId);
        if (view == null) {
            return;
        }
//        if (view instanceof HipTableView) {
//            deleteData((HipTableView) view, data);
//            return;
//        }
//
//        if (view instanceof HipFormView) {
//            return;
//        }
//
//        if (view instanceof HipGridView) {
//            deleteData((HipGridView) view, data);
//            return;
//        }
//
//        if (view instanceof HipListView) {
//            deleteData((HipListView) view, data);
//            return;
//        }
    }

//    private static void deleteData(HipGridView tableView, Object data) {
//        deleteData(tableView.getItems(), data);
//    }
//
//    private static void deleteData(HipListView tableView, Object data) {
//        deleteData(tableView.getItems(), data);
//    }

    private static void deleteData(Collection items, Object data) {
        if (data instanceof Collection) {
            items.removeAll((Collection) data);
        } else {
            items.remove(data);
        }
    }

//    private static void deleteData(HipTableView tableView, Object data) {
//
//        if (data instanceof Collection) {
//
//            tableView.getItems().removeAll((Collection) data);
//        } else {
//            tableView.getItems().remove(data);
//        }
//    }
}
