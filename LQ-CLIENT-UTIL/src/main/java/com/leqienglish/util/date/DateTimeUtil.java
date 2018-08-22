/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zhangyingchuang
 */
public class DateTimeUtil {

    /**
     * 是否是相同的年月日
     * @param time1
     * @param time2
     * @return 
     */
    public static boolean isSameDate(long time1, long time2) {
        Calendar c1 = DateUtil.toCalendar(time1);
        Calendar c2 = DateUtil.toCalendar(time2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);

    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    /**
     * Object 转 Date
     *
     * @param value
     * @param dateFormat 时间格式
     * @return 返回的Date
     */
    public static Date toDate(Object value, String dateFormat) {
        if (value == null) {
            return null;
        }
        String dataType = value.getClass().getSimpleName().toLowerCase();
        Date date = null;
        switch (dataType) {
            case "date":
                date = (Date) value;
                break;
            case "calendar":
                Calendar calendar = (Calendar) value;
                date = calendar.getTime();
                break;
            case "long":
                Long longValue = (Long) value;
                date = new Date(longValue);
                break;
            case "double":
                Double doubleValue = (Double) value;
                date = new Date(doubleValue.longValue());
                break;
            case "localdatetime":
                date = localDateTimeToDate((LocalDateTime) value);
                break;
            case "string":
                String strValue = (String) value;
                if (strValue.isEmpty()) {
                    date = null;
                    break;
                }
                if (dateFormat == null) {
                    dateFormat = DateUtil.YYYYMMDD;
                }
                SimpleDateFormat dataTime = new SimpleDateFormat(dateFormat);
                try {
                    date = dataTime.parse((String) value);
                } catch (ParseException ex) {
                    Logger.getLogger(DateTimeUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:

        }
        return date;
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Calendar localTimeDateToCalender(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        Date date = localDateTimeToDate(localDateTime);
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 获取起始时间
     *
     * @param limit_start_time 起始时间
     * @param limitStart 间隔
     * @param isStart 是否是开始时间 //false为结束时间
     * @return
     */
    public static LocalDateTime getLimitLocalDateTime(LocalDateTime limit_start_time, String limitStart, boolean isStart) {
        if (limitStart == null) {
            return limit_start_time;
        }
        if (limit_start_time == null) {
            limit_start_time = LocalDateTime.now();
        }
        String start;
        if (limitStart.contains("y")) {
            start = limitStart.substring(0, limitStart.indexOf("y"));
            limitStart = limitStart.substring(limitStart.indexOf("y") + 1, limitStart.length());
            if (isStart) {
                limit_start_time = limit_start_time.minusYears(Long.parseLong(start));
            } else {
                limit_start_time = limit_start_time.plusYears(Long.parseLong(start));
            }
        }
        if (limitStart.contains("M")) {
            start = limitStart.substring(0, limitStart.indexOf("M"));
            limitStart = limitStart.substring(limitStart.indexOf("M") + 1, limitStart.length());
            if (isStart) {
                limit_start_time = limit_start_time.minusMonths(Long.parseLong(start));
            } else {
                limit_start_time = limit_start_time.plusMonths(Long.parseLong(start));
            }
        }
        if (limitStart.contains("d")) {
            start = limitStart.substring(0, limitStart.indexOf("d"));
            if (isStart) {
                limit_start_time = limit_start_time.minusDays(Long.parseLong(start));
            } else {
                limit_start_time = limit_start_time.plusDays(Long.parseLong(start));
            }
        }
        return limit_start_time;
    }

}
