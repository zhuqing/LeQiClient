package com.leqienglish.client.control.number;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.text.NumberFormat;
import java.text.ParseException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author zhangyingchuang
 */
public class LQNumber extends TextField {

    private NumberFormat numberFormat;
    private final ObjectProperty<Number> number = new SimpleObjectProperty<>();

    public final Number getNumber() {
        if (number.get() == null) {
            return 0;
        }
        return number.get();
    }

    public final void setNumber(Number value) {
        number.set(value);
    }

    public ObjectProperty<Number> numberProperty() {
        return number;
    }

    public LQNumber() {
        this(null);
    }

    public LQNumber(Number value) {
        this(value, null);
    }

    public LQNumber(Number value, NumberFormat nf) {
        super();
        this.numberFormat = nf;
        initHandlers();
        setNumber(value);
        this.getStyleClass().add("number-text-field");
    }

    private void initHandlers() {
        JavaFxObservable.eventsOf(this, KeyEvent.KEY_PRESSED)
                .filter((event) -> event.getCode() == KeyCode.ENTER && !event.isAltDown() && !event.isControlDown() && !event.isShiftDown())
                .subscribe((event) -> parseAndFormatInput());

        this.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    LQNumber.this.selectAll();
                }
            }
        });

        JavaFxObservable.changesOf(focusedProperty())
                .filter((change) -> !change.getNewVal())
                .subscribe((change) -> parseAndFormatInput());

        JavaFxObservable.nullableValuesOf(numberProperty())
                .subscribe((change) -> {
                    Number text = change.orElse(null);
                    if (text == null) {
                        setText("");
                    } else {
                        setText(getNumberFormat().format(text));
                    }
                });

    }

    /**
     * Tries to parse the user input to a number according to the provided
     * NumberFormat
     */
    public void parseAndFormatInput() {
        try {
            String input = getText();
            if (input == null || input.length() == 0) {
                setNumber(null);
                return;
            }
            Number parsedNumber = getNumberFormat().parse(input);
            if (parsedNumber.equals(getNumber())) {
                setText(getNumberFormat().format(parsedNumber));
            } else {
                setNumber(parsedNumber);
            }
            selectAll();
        } catch (ParseException ex) {
            // If parsing fails keep old number
            if (number.get() == null) {
                setText(null);
            } else {
                setText(getNumberFormat().format(number.get()));
            }
        }
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public NumberFormat getNumberFormat() {
        if (numberFormat == null) {
            numberFormat = NumberFormat.getInstance();
        }
        return numberFormat;
    }

}
