/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.combobox;


import com.leqienglish.client.control.pop.LQPopupControl;
import com.leqienglish.client.util.sourceitem.SourceItem;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import static com.sun.javafx.scene.control.skin.ComboBoxPopupControl.COMBO_BOX_STYLE_CLASS;
import javafx.beans.value.ObservableValue;
import javafx.css.Styleable;
import javafx.scene.AccessibleAttribute;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import javafx.scene.control.Skinnable;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

/**
 *
 * @author zhuqing
 */
public class LQComboBoxSkin<T extends SourceItem> extends ComboBoxListViewSkin<T> {

    public LQComboBoxSkin(ComboBox<T> comboBox) {
        super(comboBox);
    }

    @Override
    protected PopupControl getPopup() {
        if (popup == null) {
            createPopup();
        }
        return popup;
    }

    private void createPopup() {
        popup = new LQPopupControl() {

            @Override
            public Styleable getStyleableParent() {
                return LQComboBoxSkin.this.getSkinnable();
            }

            {
                setSkin(new Skin<Skinnable>() {
                    @Override
                    public Skinnable getSkinnable() {
                        return LQComboBoxSkin.this.getSkinnable();
                    }

                    @Override
                    public Node getNode() {
                        return getPopupContent();
                    }

                    @Override
                    public void dispose() {
                    }
                });
            }

        };
        popup.getStyleClass().add(COMBO_BOX_STYLE_CLASS);
        popup.setConsumeAutoHidingEvents(false);
        popup.setAutoHide(true);
        popup.setAutoFix(true);
        popup.setHideOnEscape(true);
        popup.setOnAutoHide(e -> {
            getBehavior().onAutoHide();
        });
        popup.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            // RT-18529: We listen to mouse input that is received by the popup
            // but that is not consumed, and assume that this is due to the mouse
            // clicking outside of the node, but in areas such as the
            // dropshadow.
            getBehavior().onAutoHide();
        });
        popup.addEventHandler(WindowEvent.WINDOW_HIDDEN, t -> {
            // Make sure the accessibility focus returns to the combo box
            // after the window closes.
            getSkinnable().notifyAccessibleAttributeChanged(AccessibleAttribute.FOCUS_NODE);
        });

       

        // RT-36966 - if skinnable's scene becomes null, ensure popup is closed
        getSkinnable().sceneProperty().addListener(o -> {
            if (((ObservableValue) o).getValue() == null) {
                hide();
            }
        });

    }

}
