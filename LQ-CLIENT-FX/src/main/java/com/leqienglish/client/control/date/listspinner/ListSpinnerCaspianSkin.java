/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date.listspinner;


import com.leqienglish.client.control.date.listspinner.ListSpinner.ArrowDirection;
import com.leqienglish.client.control.date.listspinner.ListSpinner.ArrowPosition;
import com.leqienglish.client.control.date.pick.YearAndMonthPanel;
import com.leqienglish.client.control.pop.LQPopup;
import com.leqienglish.client.control.skin.LQSkin;
import com.leqienglish.client.util.timer.Timer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import com.sun.javafx.scene.control.behavior.BehaviorBase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author weipengwei
 * @version 2013-07-05
 */
public class ListSpinnerCaspianSkin<T> extends LQSkin<ListSpinner<T>, BehaviorBase<ListSpinner<T>>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListSpinnerCaspianSkin.class);

    private GridPane gridPane;
    private Region decrementArrow;
    private Region incrementArrow;
    private Region selectArrow;

    private BorderPane valueGroup;
    private LQPopup popup;//2012-6-9
    private YearAndMonthPanel yearAndMonthPanel;//2012-6-9

    final private Timer unclickTimer = new Timer(new Runnable() {
        @Override
        public void run() {
            unclickArrows();
        }
    }).withDelay(Duration.millis(100)).withRepeats(false);
    //持续按下鼠标时减少的定时器
    final private Timer repeatDecrementClickTimer = new Timer(new Runnable() {
        @Override
        public void run() {
            getSkinnable().decrement();
        }
    }).withDelay(Duration.millis(500)).withCycleDuration(Duration.millis(50));
    // 持续按下增加的定时器
    final private Timer repeatIncrementClickTimer = new Timer(new Runnable() {
        @Override
        public void run() {
            getSkinnable().increment();
        }
    }).withDelay(Duration.millis(500)).withCycleDuration(Duration.millis(50));
    private TextField textField;

    /**
     * skin构造
     *
     * @param control list
     */
    public ListSpinnerCaspianSkin(ListSpinner<T> control) {
        super(control, new ListSpinnerBehavior<>(control));
    }

    @Override
    protected void showSkin() {
        getChildren().add(gridPane);
    }

    @Override
    protected void initSkin() {
        super.initSkin();
        construct();
        initEventHandelr();

    }

    private void initEventHandelr() {
    }

    /**
     * 构造
     */
    private void construct() {

        createNodes();

        getSkinnable().editableProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                replaceValueNode();
            }
        });
        replaceValueNode();

        // react to value changes in the model
        getSkinnable().valueProperty().addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T oldValue, T newValue) {
                refreshValue();
            }
        });
        refreshValue();

        // react to value changes in the model
        getSkinnable().arrowDirectionProperty().addListener(new ChangeListener<ListSpinner.ArrowDirection>() {
            @Override
            public void changed(ObservableValue<? extends ArrowDirection> observableValue, ArrowDirection oldValue, ArrowDirection newValue) {
                setArrowCSS();
                layoutGridPane();
            }
        });
        setArrowCSS();
        layoutGridPane();

        // react to value changes in the model
        getSkinnable().alignmentProperty().addListener(new ChangeListener<Pos>() {
            @Override
            public void changed(ObservableValue<? extends Pos> observableValue, Pos oldValue, Pos newValue) {
                alignValue();
            }
        });
        alignValue();
    }

    /*
     * 
     */
    private void refreshValue() {
        // if editable
        if (getSkinnable().isEditable() == true) {
            // update textfield
            T lValue = getSkinnable().getValue();

            textField.setText(getSkinnable().getPrefix() + getSkinnable().getStringConverter().toString(lValue) + getSkinnable().getPostfix());
        } else {
            // get node for this value
            Node lNode = getSkinnable().getCellFactory().call(getSkinnable());
        }
    }

    /**
     * 构造所有节点，并放在GridPane中
     */
    private void createNodes() {
        // left arrow
        decrementArrow = new Region();
        decrementArrow.getStyleClass().add("idle");

        // plvalueGroupace holder for showing the value
        valueGroup = new BorderPane();
        valueGroup.getStyleClass().add("valuePane");

        // right arrow
        incrementArrow = new Region();
        incrementArrow.getStyleClass().add("idle");

        selectArrow = new Region();
        selectArrow.getStyleClass().add("idle");
        // construct a gridpane
        gridPane = new GridPane();
        //

        yearAndMonthPanel = new YearAndMonthPanel();
        yearAndMonthPanel.setType(getSkinnable().getselectArrowType());
        yearAndMonthPanel.setVgap(3);
        yearAndMonthPanel.setLocale(getSkinnable().getLocale());//如果是 month的画，此属性用于根据本地化初始化数据
        //如果是年份的根据年份的开始--结束
        yearAndMonthPanel.setIndex(getSkinnable().getIndex());
        yearAndMonthPanel.setYearEnd(getSkinnable().getYearEnd());//结束年份
        yearAndMonthPanel.setYearStart(getSkinnable().getYearStart());//其实年份
        yearAndMonthPanel.setMonthStart(getSkinnable().getMonthStart());
        yearAndMonthPanel.setMonthEnd(getSkinnable().getMonthEnd());
        yearAndMonthPanel.setColumnSize(2);
        yearAndMonthPanel.setRowSize(10);
        yearAndMonthPanel.selectValueProperty.addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> ov, T t, T t1) {
                getSkinnable().setValue(t1);
                if (popup != null) {
                    popup.hide();
                }
            }
        });
        //当ListSpinner的选中改变时，同时改变YearAndMontPanel的选中
        getSkinnable().indexProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                if (t1 != null) {
                    yearAndMonthPanel.setIndex(t1);

                }
            }
        });
        // we're not catching the mouse events on the individual children, but let it bubble up to the parent and handle it there, this makes our life much more simple
        // process mouse clicks
        gridPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                // if click was the in the greater vicinity of the decrement arrow
                if (mouseEventOverArrow(evt, decrementArrow)) {
                    // left
                    unclickArrows();
                    decrementArrow.getStyleClass().add("clicked");
                    getSkinnable().decrement();
                    unclickTimer.restart();
                    return;
                }

                // if click was the in the greater vicinity of the increment arrow
                if (mouseEventOverArrow(evt, incrementArrow)) {
                    // right
                    unclickArrows();
                    incrementArrow.getStyleClass().add("clicked");
                    getSkinnable().increment();
                    unclickTimer.restart();
                    return;
                }
                if (mouseEventOverArrow(evt, selectArrow)) {
                    // right
                    unclickArrows();
                    selectArrow.getStyleClass().add("clicked");
                    //getSkinnable().increment();
                    showpanel();
                    unclickTimer.restart();
                    return;
                }
            }
        });
        // process mouse holds
        gridPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                // if click was the in the greater vicinity of the decrement arrow
                if (mouseEventOverArrow(evt, decrementArrow)) {
                    // left
                    decrementArrow.getStyleClass().add("clicked");
                    repeatDecrementClickTimer.restart();
                    return;
                }

                // if click was the in the greater vicinity of the increment arrow
                if (mouseEventOverArrow(evt, incrementArrow)) {
                    // right
                    incrementArrow.getStyleClass().add("clicked");
                    repeatIncrementClickTimer.restart();
                    return;
                }
                // if click was the in the greater vicinity of the select arrow
                if (mouseEventOverArrow(evt, selectArrow)) {
                    // right
                    selectArrow.getStyleClass().add("clicked");
                    repeatIncrementClickTimer.restart();
                    return;
                }
            }
        });
        gridPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                unclickArrows();
                repeatDecrementClickTimer.stop();
                repeatIncrementClickTimer.stop();
            }
        });
        gridPane.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                unclickArrows();
                repeatDecrementClickTimer.stop();
                repeatIncrementClickTimer.stop();
            }
        });
        // mouse wheel
        gridPane.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent evt) {
                // if click was the in the greater vicinity of the decrement arrow
                if (evt.getDeltaY() < 0 || evt.getDeltaX() < 0) {
                    // left
                    unclickArrows();
                    decrementArrow.getStyleClass().add("clicked");
                    getSkinnable().decrement();
                    unclickTimer.restart();
                    return;
                }

                // if click was the in the greater vicinity of the increment arrow
                if (evt.getDeltaY() > 0 || evt.getDeltaX() > 0) {
                    // right
                    unclickArrows();
                    incrementArrow.getStyleClass().add("clicked");
                    getSkinnable().increment();
                    unclickTimer.restart();
                    return;
                }
            }
        });
        // add to self
//        this.getStyleClass().add(this.getClass().getSimpleName()); // always add self as style class, because CSS should relate to the skin not the control
//        getChildren().add(gridPane);
    }

    /**
     * Check if the mouse event is considered to have happened over the arrow
     *
     * @param evt
     * @param region
     * @return
     */
    private boolean mouseEventOverArrow(MouseEvent evt, Region region) {
        // if click was the in the greater vicinity of the decrement arrow
        Point2D lClickInRelationToArrow = region.sceneToLocal(evt.getSceneX(), evt.getSceneY());
        if (lClickInRelationToArrow.getX() >= 0.0 && lClickInRelationToArrow.getX() <= region.getWidth()
                && lClickInRelationToArrow.getY() >= 0.0 && lClickInRelationToArrow.getY() <= region.getHeight()) {
            return true;
        }
        return false;
    }

    /**
     * Remove clicked CSS styling from the arrows
     */
    private void unclickArrows() {
        decrementArrow.getStyleClass().remove("clicked");
        incrementArrow.getStyleClass().remove("clicked");
        selectArrow.getStyleClass().remove("clicked");//2013-6-9
    }

    /**
     * Put the correct node for the value's place holder: - either the TextField
     * when in editable mode, - or a node generated by the cell factory when in
     * readonly mode.
     */
    private void replaceValueNode() {
        // clear
        valueGroup.getChildren().clear();

        // if not editable
        if (getSkinnable().isEditable() == false) {
            // use the cell factory
            Node lNode = getSkinnable().getCellFactory().call(getSkinnable());

            valueGroup.setCenter(lNode);
            if (lNode.getStyleClass().contains("value") == false) {
                lNode.getStyleClass().add("value");
            }
            if (lNode.getStyleClass().contains("readonly") == false) {
                lNode.getStyleClass().add("readonly");
            }
        } else {
            // use the textfield
            if (textField == null) {
                textField = new TextField();
                //  textField.setMaxWidth(50);
                textField.setAlignment(Pos.CENTER);
                textField.getStyleClass().add("value");
                textField.getStyleClass().add("editable");

                // process text entry
                textField.focusedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable arg0) {
                        if (textField.isFocused() == false) {
                            parse(textField);
                        }
                    }
                });
                textField.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent evt) {
                        parse(textField);
                    }
                });

                textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent t) {
                        if (t.getCode() == KeyCode.ESCAPE) {
                            // refresh
                            refreshValue();

                        }
                        if (t.getCode() == KeyCode.ENTER) {
                            parse(textField);

                        }
                    }
                });

                // alignment
                textField.alignmentProperty().bind(getSkinnable().alignmentProperty());
            } else {
                LOGGER.info("textfield不为空！");
            }

            valueGroup.setCenter(textField);
        }
        // align
        alignValue();
    }

    /**
     * align the value inside the plave holder
     */
    private void alignValue() {
        // valueGroup always only holds one child (the value)

        BorderPane.setAlignment(valueGroup.getChildren().get(0), getSkinnable().alignmentProperty().getValue());
    }

    /**
     * Parse the contents of the textfield
     *
     * @param textField TextField
     */
    protected void parse(TextField textField) {
        // get the text to parse
        String lText = textField.getText();
        T tvalue = getSkinnable().getStringConverter().fromString(lText);
        if (getSkinnable().getItems().indexOf(tvalue) > -1) {
            getSkinnable().setValue(tvalue);
        }
        // refresh
        refreshValue();
        return;
    }

    /**
     * Lays out the spinner, depending on the location and direction of the
     * arrows.
     */
    private void layoutGridPane() {
        // get the things we decide on
        ArrowDirection lArrowDirection = getSkinnable().getArrowDirection();
        ArrowPosition lArrowPosition = getSkinnable().getArrowPosition();

        // get helper values
        ColumnConstraints lColumnValue = new ColumnConstraints(valueGroup.getMinWidth(), valueGroup.getPrefWidth(), Double.MAX_VALUE);
        lColumnValue.setHgrow(Priority.ALWAYS);
        ColumnConstraints lColumnArrow = new ColumnConstraints(10);
        ColumnConstraints lColumnArrowDate = new ColumnConstraints(22);
        ColumnConstraints lColumnArrowDown = new ColumnConstraints(16);

        // get helper values
        RowConstraints lRowValue = new RowConstraints(valueGroup.getMinHeight(), valueGroup.getPrefHeight(), Double.MAX_VALUE);
        lRowValue.setVgrow(Priority.ALWAYS);
        RowConstraints lRowArrow = new RowConstraints(10);

        // clear the grid
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        //gridPane.setGridLinesVisible(true);

        if (lArrowDirection == ArrowDirection.HORIZONTAL) {
            if (lArrowPosition == ArrowPosition.LEADING) {
                // construct a gridpane: one row, three columns: arrow, arrow, value
                gridPane.setHgap(3);
                gridPane.setVgap(0);
                gridPane.add(decrementArrow, 0, 0);
                gridPane.add(incrementArrow, 1, 0);
                gridPane.add(valueGroup, 2, 0);
                gridPane.add(selectArrow, 3, 0);//2013-6-9
                gridPane.getColumnConstraints().addAll(lColumnArrow, lColumnArrow, lColumnValue, lColumnArrow);
            }
            if (lArrowPosition == ArrowPosition.TRAILING) {
                // construct a gridpane: one row, three columns: value, arrow, arrow
                gridPane.setHgap(3);
                gridPane.setVgap(0);
                gridPane.add(valueGroup, 0, 0);
                gridPane.add(selectArrow, 1, 0);//2013-6-9 
                gridPane.add(decrementArrow, 2, 0);
                gridPane.add(incrementArrow, 3, 0);
                gridPane.getColumnConstraints().addAll(lColumnValue, lColumnArrow, lColumnArrow, lColumnArrow);//2013-6-9 
            }
            if (lArrowPosition == ArrowPosition.SPLIT) {
                // construct a gridpane: one row, three columns: arrow, value, arrow
                gridPane.setHgap(0);
                gridPane.setVgap(0);
                gridPane.add(decrementArrow, 0, 0);
                gridPane.add(valueGroup, 1, 0);
                gridPane.add(selectArrow, 2, 0);//2013-6-9
                gridPane.add(incrementArrow, 3, 0);
                gridPane.getColumnConstraints().addAll(lColumnArrowDate, lColumnValue, lColumnArrowDown, lColumnArrowDate);
            }
        }
        if (lArrowDirection == ArrowDirection.VERTICAL) {
            if (lArrowPosition == ArrowPosition.LEADING) {
                // construct a gridpane: two rows, two columns: arrows on top, value
                gridPane.setHgap(3);
                gridPane.setVgap(0);
                gridPane.add(incrementArrow, 0, 0);
                gridPane.add(decrementArrow, 0, 1);
                gridPane.add(valueGroup, 1, 0);//2013-6-9
                //gridPane.add(valueGroup, 1, 0, 1, 2);
                gridPane.add(selectArrow, 2, 0);//2013-6-9
                gridPane.getColumnConstraints().addAll(lColumnArrow, lColumnValue, lColumnArrow); //2013-6-9
                gridPane.getRowConstraints().addAll(lRowArrow, lRowArrow);
            }
            if (lArrowPosition == ArrowPosition.TRAILING) {
                // construct a gridpane: two rows, two columns: value, arrows on top
                gridPane.setHgap(3);
                gridPane.setVgap(0);
                //gridPane.add(valueGroup, 0, 0, 1, 2);
                gridPane.add(valueGroup, 0, 0);//2013-6-9
                gridPane.add(selectArrow, 0, 1);//2013-6-9
                gridPane.add(incrementArrow, 1, 0);
                gridPane.add(decrementArrow, 1, 1);
                gridPane.getColumnConstraints().addAll(lColumnValue, lColumnArrow);
                gridPane.getRowConstraints().addAll(lRowArrow, lRowArrow);
            }
            if (lArrowPosition == ArrowPosition.SPLIT) {
                // construct a gridpane: three rows, one columns: arrow, value, arrow
                gridPane.setHgap(3);
                gridPane.setVgap(0);
                //gridPane.add(incrementArrow, 0, 0);
                gridPane.add(incrementArrow, 0, 0, 1, 2);
                //gridPane.add(valueGroup, 0, 1);
                gridPane.add(valueGroup, 0, 0);//2013-6-9
                gridPane.add(selectArrow, 0, 1);//2013-6-9
                //gridPane.add(decrementArrow, 0, 2);
                gridPane.add(decrementArrow, 0, 2, 1, 2);//2013-6-9
                gridPane.getColumnConstraints().addAll(lColumnValue, lColumnArrow);
                gridPane.getRowConstraints().addAll(lRowArrow, lRowValue, lRowArrow);
            }
        }
    }

    /**
     * Set the CSS according to the direction of the arrows, so the correct
     * arrows are shown
     */
    private void setArrowCSS() {
        if (getSkinnable().getArrowDirection().equals(ListSpinner.ArrowDirection.HORIZONTAL)) {
            decrementArrow.getStyleClass().add("left-arrow");
            incrementArrow.getStyleClass().add("right-arrow");
            selectArrow.getStyleClass().add("down-arrow");
        } else {
            decrementArrow.getStyleClass().add("down-arrow");
            incrementArrow.getStyleClass().add("up-arrow");
            selectArrow.getStyleClass().add("right-arrow");
        }
    }

    private void showpanel() {
        if (popup == null) {
            popup = new LQPopup();

            popup.setAutoFix(true);
            popup.setAutoHide(true);
            // popup.setHideOnEscape(true);
            BorderPane borderPane = new BorderPane();
            if (valueGroup != null) {
                // borderPane.setPrefWidth(valueGroup.getBoundsInLocal().getWidth());
                // yearAndMonthPanel.setPrefWidth(valueGroup.getBoundsInLocal().getWidth());
            }
            borderPane.setCenter(yearAndMonthPanel);
            popup.getContent().add(borderPane);
        }

        //popup.show(valueGroup,  NodeUtil.screenX(valueGroup.getCenter()), NodeUtil.screenY(valueGroup) + valueGroup.getHeight());
        popup.showV(valueGroup);

    }
}
