/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.row;

import com.leqienglish.client.control.dragdrop.ClipboardContentUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;

import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.util.node.NodeUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Skin;
import javafx.scene.control.Tooltip;
import javafx.scene.image.WritableImage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 *
 * @author zhuqing
 * @param <T>
 */
public class LQTableRow<T, H extends LQTableView> extends TableRow<T> {

    private final H hipTableView;

    private final static String INSERT_STYLE = "CUSTOM-INSERT-TABLE-ROW";

    private Tooltip tooltip;

    public LQTableRow(H hipTableView) {
        this.hipTableView = hipTableView;
        init();
    }

    private void init() {
        this.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (hipTableView.getRowMouseEventHandler() != null) {
                    hipTableView.getRowMouseEventHandler().handle(event);
                }
                mouseClick(event);

            }
        });
        this.itemProperty().addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
                if (newValue == null) {
                    setTooltip(null);
                } else {
                    setTooltip(tooltip);
                }
                if (getHipTableView() == null || getHipTableView().getRowStylesCallback() == null) {
                    NodeUtil.addCustomStyles(Collections.EMPTY_LIST, LQTableRow.this);
                    return;
                }

                NodeUtil.addCustomStyles((List<String>) getHipTableView().getRowStylesCallback().call(newValue), LQTableRow.this);
            }
        });

        if (this.hipTableView.getRowTipTextCallback() != null) {
            tooltip = new Tooltip();
            tooltip.showingProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue != null && newValue) {
                        String text = (String) hipTableView.getRowTipTextCallback().call(getItem());
                        if (text == null) {
                            return;
                        }
                        tooltip.setText(text);
                    }
                }
            });
        }

        dragEventListener();

        if (this.getItem() != null) {
            this.setTooltip(tooltip);
        }

    }

    private void dragEventListener() {
        this.setOnDragDetected(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (hipTableView.getDragDelegate() == null) {
                    return;
                }

                TransferMode mode = hipTableView.getDragDelegate().dragMode(event, hipTableView, LQTableRow.this.getIndex());
                if (mode == null) {
                    return;
                }
                Dragboard dragboard = LQTableRow.this.startDragAndDrop(mode);
                Collection datas = hipTableView.getDragDelegate().dragData(event, hipTableView, LQTableRow.this.getIndex());

                dragboard.setContent(ClipboardContentUtil.create(hipTableView, LQTableRow.this.getIndex(), datas));
                WritableImage image = LQTableRow.this.snapshot(new SnapshotParameters(), null);
                dragboard.setDragView(image);
                event.consume();
            }
        });

        this.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (hipTableView.getDropDelegate() != null) {
                    NodeUtil.addCustomStyles(INSERT_STYLE, LQTableRow.this);
                }
            }
        });

        this.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (hipTableView.getDropDelegate() != null) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

            }
        });

        this.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {

            }

        });

        this.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (hipTableView.getDropDelegate() == null) {
                    return;
                }
                Object data = ClipboardContentUtil.getData(event.getDragboard());
                Integer currentIndex = LQTableRow.this.getIndex();

                try {

                    hipTableView.getDropDelegate().drop(hipTableView, currentIndex, (Collection) data);

                    event.setDropCompleted(true);
                    if (event.getAcceptedTransferMode() == TransferMode.MOVE) {
                        ClipboardContentUtil.removeDataFromView(event.getDragboard());
                    }

                } catch (Exception ex) {
                    Logger.getLogger(LQTableRow.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.err.println("setOnDragDropped");

            }

        });

        this.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (hipTableView.getDropDelegate() != null) {
                    LQTableRow.this.getStyleClass().remove(INSERT_STYLE);
                }
            }

        });
    }

    private int getSize(Object item) {
        if (item == null) {
            return -1;
        }
        if (item instanceof Collection) {
            return ((Collection) item).size() - 1;
        }
        return 0;
    }

    protected void mouseClick(MouseEvent event) {

    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LQTableRowSkin<>(this); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the hipTableView
     */
    public H getHipTableView() {
        return hipTableView;
    }

}
