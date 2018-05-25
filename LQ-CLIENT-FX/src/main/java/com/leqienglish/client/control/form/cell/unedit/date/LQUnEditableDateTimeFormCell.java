/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.unedit.date;

import com.leqienglish.client.control.form.cell.LQFormCell;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author zhangyingchuang
 */
public class LQUnEditableDateTimeFormCell<S, T> extends LQFormCell<S, T> {

    private String dateFormat;

    @Override
    protected void updateValue(T t) {
        if (t == null) {
            setText("");
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getDateFormat());
        Date date;
        if (t instanceof Date) {
            date = (Date) t;
        } else if (t instanceof Calendar) {
            Calendar calendar = (Calendar) t;
            date = calendar.getTime();
        } else if (t instanceof Long) {
            Long longValue = (Long) t;
            date = new Date(longValue);
        } else if (t instanceof Double) {
            Double doubleValue = (Double) t;
            date = new Date(doubleValue.longValue());
        } else if (t instanceof LocalDateTime) {
            date = LocalDateTimeToUdate((LocalDateTime) t);
        } else {
            date = new Date();
        }
        setText(simpleDateFormat.format(date));
    }

    public Date LocalDateTimeToUdate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public String getDateFormat() {
        if (dateFormat == null || dateFormat.isEmpty()) {
            dateFormat = "yyyy-MM-dd";
        }
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public LQUnEditableDateTimeFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQUnEditableDateTimeFormCell dateTimeFormCell = (LQUnEditableDateTimeFormCell) formCell;
            dateTimeFormCell.setDateFormat(this.getDateFormat());
            return dateTimeFormCell;
        }
        return null;
    }

}
