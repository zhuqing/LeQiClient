/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.form.cell.edit.date;



import com.leqienglish.client.control.date.DateTimeField;
import com.leqienglish.client.control.date.DateTimeFieldSkin.DateTimeDecorateField;
import com.leqienglish.client.control.form.cell.LQFormCell;
import com.leqienglish.client.control.form.cell.edit.LQEditableFormCell;
import com.leqienglish.client.util.focus.FocusUtil;
import com.leqienglish.util.date.DateTimeUtil;
import com.leqienglish.util.date.DateUtil;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 *
 * @author zhangyingchuang
 */
public class LQCalendarFormCell<S> extends LQEditableFormCell<S, Object, Node> {

    private DateTimeField dateTimeField;

    private String dateFormat;

    private String dataType;

    /**
     * 日期重新设置
     */
    private String valueResetFormat;

    /**
     * 限定日期开始间隔 示例： 5y 、3M 、5d、5y5M5d、5y5d、5M5d y:代表年 M:代表月 d:代表天
     *
     */
    private String limit_start;
    /**
     * 限定日期结束间隔
     */
    private String limit_end;

    /*
    *限定开始时间
     */
    private LocalDateTime limit_start_time;

    /**
     * 限定结束时间
     */
    private LocalDateTime limit_end_time;

    /**
     * 刷新监听
     */
    private StringProperty rebuild;

    private final ChangeListener<String> rebuildChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            getDateTimeField().setLimit_start_time(getLimit_start_time());
            getDateTimeField().setLimit_end_time(getLimit_end_time());
            getDateTimeField().setRebuild(getRebuild());
        }
    };

    private void commitDateValue(LocalDateTime newValue) {
        if (newValue == null) {
            commitValue(null);
            return;
        }
        Date date = DateUtil.toDateValue(newValue);
        switch (dataType) {
            case "Date":
                commitValue(date);
                break;
            case "Calendar":
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                commitValue(calendar);
                break;
            case "Long":
            case "long":
                long datetime = date.getTime();
                commitValue(datetime);
                break;
            case "Double":
            case "double":
                datetime = date.getTime();
                commitValue(new Double(datetime));
                break;
            case "String":
                SimpleDateFormat dataTime = new SimpleDateFormat(this.getDateFormat());
                String value = dataTime.format(date);
                commitValue(value);
                break;
            default:
                commitValue(newValue);
        }
    }

    @Override
    protected Node createEditGraghic() {
        if (dateTimeField == null) {
            dateTimeField = new DateTimeField();
            dateTimeField.setValueFormat(getDateFormat());
            dateTimeField.setDisplayFormat(getDateFormat());
            dateTimeField.setValueResetFormat(this.getValueResetFormat());
            dateTimeField.setCommitConsumer((v) -> {
                commitDateValue(v);
            });
            dateTimeField.setLimit_start_time(getLimit_start_time());
            dateTimeField.setLimit_end_time(getLimit_end_time());
            dateTimeField.setRebuild(getRebuild());
            dateTimeField.setPromptText(this.getPromptText());
            this.rebuild.addListener(rebuildChangeListener);
        }

        return dateTimeField;
    }

    @Override
    protected void updateValue(Object t) {
        super.updateValue(t);
        updateDataType();
        LocalDateTime ldt = null;
        if (t == null) {
            getDateTimeField().setValue(null);
        } else if (t instanceof LocalDateTime) {
            ldt = (LocalDateTime) t;
        } else {
            ldt = DateTimeUtil.toLocalDateTime(DateTimeUtil.toDate(t, getDateFormat()));
        }
        getDateTimeField().setValue(getDateTimeField().reSetDate(ldt, getValueResetFormat()));
        if (ldt != null && !ldt.equals(getDateTimeField().getValue())) {//判断是否重置时间，重置后需要重新提交数据 否则取得时间还是旧值
            commitDateValue(getDateTimeField().getValue());
        }
    }

    private void updateDataType() {
        Class clazz = this.getFieldType(getPropertyName());
        if (clazz == null) {
            dataType = Date.class.getSimpleName();
        } else {
            dataType = clazz.getSimpleName();
        }
    }

    @Override
    public void requestFocus() {
        if (this.dateTimeField == null) {
            return;
        }

        this.dateTimeField.getChildrenUnmodifiable()
                .stream()
                .filter((n) -> n instanceof DateTimeDecorateField)
                .map((n) -> (DateTimeDecorateField) n)
                .forEach((DateTimeDecorateField n) -> FocusUtil.requestFocus(n.getChildren(), TextField.class));
    }

    public DateTimeField getDateTimeField() {
        if (dateTimeField == null) {
            createEditGraghic();
        }
        return dateTimeField;
    }

    public String getDateFormat() {
        if (dateFormat == null || dateFormat.isEmpty()) {
            dateFormat = DateUtil.YYYYMMDD;
        }
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public LQCalendarFormCell clone() {
        LQFormCell formCell = super.clone();
        if (formCell != null) {
            LQCalendarFormCell dateTimeFormCell = (LQCalendarFormCell) formCell;
            dateTimeFormCell.setDateFormat(this.getDateFormat());
            dateTimeFormCell.setValueResetFormat(this.getValueResetFormat());
            dateTimeFormCell.setLimit_end(this.getLimit_end());
            dateTimeFormCell.setLimit_start(this.getLimit_start());
            dateTimeFormCell.setLimit_end_time(this.getLimit_end_time());
            dateTimeFormCell.setLimit_start_time(this.getLimit_start_time());
            dateTimeFormCell.setRebuild(this.getRebuild());
            return dateTimeFormCell;

        }
        return null;
    }

    /**
     * 日期重新设置
     *
     * @return the valueResetFormat
     */
    public String getValueResetFormat() {
        return valueResetFormat;
    }

    /**
     * 日期重新设置
     *
     * @param valueResetFormat the valueResetFormat to set
     */
    public void setValueResetFormat(String valueResetFormat) {
        this.valueResetFormat = valueResetFormat;
    }

    public String getLimit_start() {
        return limit_start;
    }

    public void setLimit_start(String limit_start) {
        this.limit_start = limit_start;
    }

    public String getLimit_end() {
        return limit_end;
    }

    public void setLimit_end(String limit_end) {
        this.limit_end = limit_end;
    }

    public LocalDateTime getLimit_start_time() {
        return DateTimeUtil.getLimitLocalDateTime(limit_start_time, getLimit_start(), true);
    }

    public void setLimit_start_time(LocalDateTime limit_start_time) {
        this.limit_start_time = limit_start_time;
    }

    public LocalDateTime getLimit_end_time() {
        return DateTimeUtil.getLimitLocalDateTime(limit_end_time, getLimit_end(), false);
    }

    public void setLimit_end_time(LocalDateTime limit_end_time) {
        this.limit_end_time = limit_end_time;
    }

    public StringProperty rebuildProperty() {
        if (rebuild == null) {
            rebuild = new SimpleStringProperty();
        }
        return rebuild;
    }

    public String getRebuild() {
        return rebuildProperty().getValue();
    }

    public void setRebuild(String rebuild) {
        rebuildProperty().setValue(rebuild);
    }
}
