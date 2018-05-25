/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.combobox;


import com.leqienglish.client.util.focus.FocusUtil;
import com.leqienglish.client.util.sourceitem.SourceItem;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Skin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

/**
 *
 * @author zhuqing
 */
public class LQComboBox<T extends SourceItem> extends ComboBox<T> {

    /**
     * 焦点切换到下一个节点
     */
    private Consumer<Boolean>  focusNext;
    /**
     * 数据源
     */
    private ObservableList<T> sourceItems;

    private final ListChangeListener<T> sourceItemsChangeListener = new ListChangeListener<T>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends T> c) {
            sourceItemsChange();
        }
    };

    public LQComboBox() {
        this.setConverter(new HipStringConverter<T>());
        this.getSourceItems().addListener(sourceItemsChangeListener);

        this.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if (k.getCode() != KeyCode.DOWN || LQComboBox.this.isShowing()) {
                    return;
                }
                LQComboBox.this.show();
            }
        });

        this.getEditor().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() != KeyCode.ENTER) {
                    return;
                }

                if (getFocusNext() != null) {
                    getFocusNext().accept(true);
                }
            }
        });

        JavaFxObservable.changesOf(this.showingProperty())
                .filter(c -> !c.getNewVal())
                .filter((c) -> getSelectionModel().getSelectedItem() != null)
                .subscribe((c) -> focusToNext());

    }

    /**
     * @return the sourceItems
     */
    public ObservableList<T> getSourceItems() {
        if (sourceItems == null) {
            sourceItems = FXCollections.observableArrayList();
        }
        return sourceItems;
    }

    protected void sourceItemsChange() {
        this.setItems(sourceItems);
    }

    protected void focusToNext() {
        if (this.getFocusNext() != null) {
            this.getFocusNext().accept(false);
            return;
        }

        FocusUtil.focusToNext(this.getEditor());
    }

    /**
     * 重写默认皮肤
     *
     * @return
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new LQComboBoxSkin<T>(this);
    }

    /**
     * @param sourceItems the sourceItems to set
     */
    public void setSourceItems(List<T> sourceItems) {
        this.getSourceItems().setAll(sourceItems);
    }

    class HipStringConverter<T extends SourceItem> extends StringConverter<T> {

        @Override
        public String toString(T object) {
            if (object == null) {
                return "";
            }
            return object.getDisplay();
        }

        @Override
        public T fromString(String diaplay) {
            for (Object item : getSourceItems()) {
                T t = (T) item;
                if (Objects.equals(t.getDisplay(), diaplay)) {
                    return t;
                }
            }
            return null;
        }

    }

    /**
     * 焦点切换到下一个节点
     *
     * @return the focusNext
     */
    public Consumer<Boolean> getFocusNext() {
        return focusNext;
    }

    /**
     * 焦点切换到下一个节点
     *
     * @param focusNext the focusNext to set
     */
    public void setFocusNext(Consumer<Boolean> focusNext) {
        this.focusNext = focusNext;
    }

}
