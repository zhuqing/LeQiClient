/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.view.table.cell.datetime;

import com.leqienglish.client.control.view.table.cell.LQTableCell;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author zhangyingchuang
 */
public class LQDataTimeTableCell<S, T> extends LQTableCell<S, T> {

    private String dateFormat;

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        if (item == null || empty) {
            this.setText("");
            return;
        }
        toText(item);

    }

    protected String toText(T item) {
        Date date;
        if (item instanceof Date) {
            date = (Date) item;
        } else if (item instanceof Calendar) {
            Calendar calendar = (Calendar) item;
            date = calendar.getTime();
        } else if (item instanceof Long) {
            Long longValue = (Long) item;
            date = new Date(longValue);
        } else if (item instanceof Double) {
            Double doubleValue = (Double) item;
            date = new Date(doubleValue.longValue());
        } else {
            date = new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(getDateFormat());
        this.setText(formatter.format(date));
        
        return "";
    }

    public String getDateFormat() {
        if (dateFormat == null) {
            dateFormat = "yyyy-MM-dd";
        }
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public LQDataTimeTableCell clone() throws CloneNotSupportedException {
        LQTableCell lqTableCell = super.clone();
        if (lqTableCell != null) {
            LQDataTimeTableCell dataTimeTableCell = (LQDataTimeTableCell) lqTableCell;
            dataTimeTableCell.setDateFormat(this.getDateFormat());
            return dataTimeTableCell;
        } else {
            return null;
        }
    }

}
