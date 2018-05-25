/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date;


import com.leqienglish.client.util.focus.FocusUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Skin;
import javafx.util.StringConverter;

/**
 *
 * @author zhuqing
 */
public class LQDateTimePicker extends DatePicker {

    private ObjectProperty<LocalTime> lastValidTime;

    private ObjectProperty<LocalDateTime> localDateTime;

    private final ChangeListener<LocalTime> lastValidTimeChangeListener = new ChangeListener<LocalTime>() {
        @Override
        public void changed(ObservableValue<? extends LocalTime> observable, LocalTime oldValue, LocalTime newValue) {

            if (getValue() == null || newValue == null) {
                return;
            }
            setLocalDateTime(LocalDateTime.of(getValue(), newValue));
        }
    };

    private final ChangeListener<LocalDate> localDateChangeListener = new ChangeListener<LocalDate>() {
        @Override
        public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
            if (newValue == null) {
                return;
            }
            setLocalDateTime(LocalDateTime.of(newValue, getLastValidTime()));
        }
    };

    /**
     * 日期格式
     */
    private String dataFormat;

    public LQDateTimePicker() {

        // super(LocalDate.now());
        this.valueProperty().addListener(localDateChangeListener);
        this.lastValidTimeProperty().addListener(lastValidTimeChangeListener);

        this.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                SimpleDateFormat dataTime = new SimpleDateFormat(getDataFormat());
                if (localDate == null) {
                    return "";
                }
                LocalDateTime localDateTime = LocalDateTime.of(localDate, getLastValidTime());
                Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                return dataTime.format(date);

            }

            @Override
            public LocalDate fromString(String string) {
                try {
                    LocalDateTime localDateTime = null;
                    if (string == null || string.isEmpty()) {
                        localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
                    } else {
                        SimpleDateFormat dataTime = new SimpleDateFormat(getDataFormat());

                        Date date = dataTime.parse(string);
                        localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                    }

                    setLastValidTime(localDateTime.toLocalTime());
                    return localDateTime.toLocalDate();
                } catch (ParseException ex) {
                    Logger.getLogger(LQDateTimePicker.class.getName()).log(Level.SEVERE, null, ex);
                }

                return null;
            }
        });
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LQDateTimePickerSkin(this);
    }

    public boolean isShowTime() {
        return this.getDataFormat().contains(":");
    }

    @Override
    public void hide() {
        if (!isShowTime()) {
            super.hide();
            FocusUtil.focusToNext(LQDateTimePicker.this.getEditor());
        }

    }

    public void hidePop() {
        super.hide();
        FocusUtil.focusToNext(LQDateTimePicker.this.getEditor());
    }

    /**
     * @return the dataFormat
     */
    public String getDataFormat() {
        if (dataFormat == null) {
            dataFormat = "yyyy-MM-dd";
        }
        return dataFormat;

    }

    /**
     * @param dataFormat the dataFormat to set
     */
    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    /**
     * @return the localDateTime
     */
    public LocalDateTime getLocalDateTime() {
        return localDateTimeProperty().getValue();
    }

    /**
     * @return the localDateTime
     */
    public ObjectProperty<LocalDateTime> localDateTimeProperty() {
        if (localDateTime == null) {
            localDateTime = new SimpleObjectProperty<LocalDateTime>();
        }
        return localDateTime;
    }

    /**
     * @param localDateTime the localDateTime to set
     */
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTimeProperty().setValue(localDateTime);
    }

    /**
     * @return the lastValidTime
     */
    public LocalTime getLastValidTime() {
        return lastValidTimeProperty().getValue();
    }

    /**
     * @return the lastValidTime
     */
    public ObjectProperty<LocalTime> lastValidTimeProperty() {
        if (lastValidTime == null) {
            lastValidTime = new SimpleObjectProperty<LocalTime>(LocalTime.now());
        }
        return lastValidTime;
    }

    /**
     * @param lastValidTime the lastValidTime to set
     */
    public void setLastValidTime(LocalTime lastValidTime) {
        lastValidTimeProperty().setValue(lastValidTime);
    }
}
