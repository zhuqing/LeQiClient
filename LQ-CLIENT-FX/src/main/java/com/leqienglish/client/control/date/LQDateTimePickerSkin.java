/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.date;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 *
 * @author zhuqing
 */
public class LQDateTimePickerSkin extends DatePickerSkin {

    private LQDateTimePickerContent timeContent;
    private LQDateTimePicker hipDateTimePicker;

    private final ChangeListener<LocalTime> timeContentLocalTimeChangeListener = new ChangeListener<LocalTime>() {
        @Override
        public void changed(ObservableValue<? extends LocalTime> observable, LocalTime oldValue, LocalTime newValue) {
            hipDateTimePicker.setLastValidTime(newValue);
        }
    };

    public LQDateTimePickerSkin(LQDateTimePicker datePicker) {
        super(datePicker);
        hipDateTimePicker = datePicker;
        
    }

    @Override
    public Node getPopupContent() {
        VBox content = (VBox) super.getPopupContent(); //To change body of generated methods, choose Tools | Templates.
        if (!hipDateTimePicker.isShowTime()) {
            return content;
        }
        if (!content.getChildren().contains(getTimeContent())) {
            content.getChildren().add(getTimeContent());
        }
        return content;
    }

    /**
     * @return the timeContent
     */
    public LQDateTimePickerContent getTimeContent() {
        if (this.timeContent == null) {
            this.timeContent = new LQDateTimePickerContent();
            this.timeContent.setLocalTime(hipDateTimePicker.getLastValidTime());
            this.timeContent.updateTime(hipDateTimePicker.getLastValidTime());
            this.timeContent.localTimeProperty().addListener(timeContentLocalTimeChangeListener);
            this.timeContent.setSubmitActionConsumer(e -> {
                handleControlPropertyChanged("VALUE");
                hipDateTimePicker.hidePop();
            });

            this.timeContent.setCacelActionConsumer(e -> hipDateTimePicker.hidePop());
            this.timeContent.setTodayActionConsumer((e) -> {
                hipDateTimePicker.setValue(LocalDate.now());
                handleControlPropertyChanged("VALUE");
                hipDateTimePicker.hidePop();

            });

        }
        return timeContent;
    }

}
