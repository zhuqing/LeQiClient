/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * 时间工具类
 * @author zhuleqi
 */
public class DateUtil {

    private static final DateUtil instance = new DateUtil();

    /**
     * --------------------------- 时间格式 ---------------------------
     */
    /**
     * 默认日期格式：yyyy年M月dd日（十月之前）
     */
    public final static String YYYYMMDD_CHN1 = "yyyy年M月dd日";

    /**
     * 默认日期格式：yyyy年MM月dd日（十月之后）
     */
    public final static String YYYYMMDD_CHN2 = "yyyy年MM月dd日";

    /**
     * HH:mm:ss时间格式
     */
    public final static String HHmmSS = "HH:mm:ss";

    /**
     * yyyy-MM-dd时间格式
     */
    public final static String YYYYMMDD = "yyyy-MM-dd";

    /**
     * HH:mm时间格式
     */
    public final static String HHmm = "HH:mm";

    /**
     * yyyyMMdd时间格式
     */
    public final static String YYYYMMDD1 = "yyyyMMdd";

    /**
     * yyyy-MM-dd HH:mm:ss时间格式
     */
    public final static String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm时间格式
     */
    public final static String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";

    /**
     * yyyy-MM-dd HH时间格式
     */
    public final static String YYYYMMDDHH = "yyyy-MM-dd HH";

    /**
     * yyyyMMddHHmmss时间格式
     */
    public final static String YYYYMMDDHHMMSS1 = "yyyyMMddHHmmss";

    /**
     * yyyy年MM月dd日HH时mm分ss秒时间格式
     */
    public final static String YYYYMMDDHHMMSS_ch = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * yyyyMM时间格式
     */
    public final static String YYYYMM = "yyyyMM";

    /**
     * yyyy年MM月dd日HH时中文格式
     */
    public final static String YYYYMMDDHH_ch1 = "yyyy年MM月dd日HH时";

    /**
     * MM月dd日 HH小时格式
     */
    public final static String MMDDHH = "MM月dd日 HH时";
    /**
     * --------------------------- 类型 ---------------------------
     */
    /**
     * 世界通用
     */
    public final static int WORLD = 0;
    /**
     * 中国
     */
    public final static int CHINA = 1;

    /**
     * 一天毫秒数
     */
    public final static Long oneDayMillis = 86400000L;

    /**
     * --------------------------- 当前时间 ---------------------------
     */
    /**
     * 获取当前时间的Calender格式
     *
     * @return
     */
    public static Calendar getNow() {
        return Calendar.getInstance();
    }

    public static long getNowLong() {
        Clock clock = Clock.systemDefaultZone();
        return clock.millis();

    }

    /**
     * 获取当前日期的默认格式日期
     *
     * @return
     */
    public static String getDate() {
        return toDateFmt(getNow());
    }

    /**
     * 获取当前日期的指定格式日期
     *
     * @return
     */
    public static String getDate(String format) {
        return toDateFmt(getNow());
    }

    /**
     * --------------------------- 格式转换 ---------------------------
     */
    /**
     * cal按默认格式转换为日期格式
     *
     * @param cal
     * @return
     */
    public static String toDateFmt(Calendar cal) {
        if (cal.get(cal.MONTH) < 10) {
            return toDateFmt(cal, YYYYMMDD_CHN1);
        } else {
            return toDateFmt(cal, YYYYMMDD_CHN2);
        }
    }

    /**
     * Calendar按指定格式转换为日期格式
     *
     * @param cal
     * @param format
     * @return
     */
    public static String toDateFmt(Calendar cal, String format) {
        return toDateFmt(cal.getTime(), format);
    }

    /**
     * date按默认格式转换为默认日期格式
     *
     * @param date
     * @return
     */
    public static String toDateFmt(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.clear();// 在使用set方法之前，必须先clear一下，否则很多信息会继承自系统当前时间
        cal.setTime(date);
        if (cal.get(Calendar.MONTH) < 10) {
            return toDateFmt(date, YYYYMMDD_CHN1);
        } else {
            return toDateFmt(date, YYYYMMDD_CHN2);
        }
    }

    /**
     * date按默认格式转换为指定日期格式
     *
     * @param date
     * @param format
     * @return
     */
    public static String toDateFmt(Date date, String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(date);
    }

    /**
     * long按默认格式转换为默认日期格式
     *
     * @param time
     * @return
     */
    public static String toDateFmt(long time) {
        return toDateFmt(new Date(time));
    }

    /**
     * long按默认格式转换为指定日期格式
     *
     * @param time
     * @param format
     * @return
     */
    public static String toDateFmt(long time, String format) {
        return toDateFmt(new Date(time), format);
    }

    /**
     * double按默认格式转换为默认日期格式
     *
     * @param spendMillis
     * @return
     */
    public static String toDateFmt(double spendMillis) {
        return toDateFmt(new Date(toLongTime(spendMillis)));
    }

    /**
     * double按默认格式转换为指定日期格式
     *
     * @param spendMillis
     * @return
     */
    public static String toDateFmt(double spendMillis, String format) {
        return toDateFmt(new Date(toLongTime(spendMillis)), format);
    }

    /**
     * 将指定格式的日期转换为默认格式日期
     *
     * @param time
     * @param format
     * @return
     * @throws ParseException
     */
    public static String toDateFmt(String time, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return toDateFmt(sdf.parse(time));
    }

    /**
     * 将默认格式的日期转换为Calendar
     *
     * @param time
     * @return
     */
    public static Calendar toCalendar(String time) {
        if (time == null || "".equals(time)) {
            return null;
        }
        String month = time.substring(6, 7);
        if ("月".endsWith(month)) {
            return toCalendar(time, YYYYMMDD_CHN1);
        } else {
            return toCalendar(time, YYYYMMDD_CHN2);
        }
    }

    /**
     * 将指定格式的日期转换为Calendar
     *
     * @param time
     * @param format
     * @return
     */
    public static Calendar toCalendar(String time, String format) {
        return toCalendar(toDate(time, format));
    }

    /**
     * 将Date类型时间转换为Calendar
     *
     * @param date
     * @return
     */
    public static Calendar toCalendar(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = new GregorianCalendar();
        cal.clear();// 在使用set方法之前，必须先clear一下，否则很多信息会继承自系统当前时间
        cal.setTime(date);
        return cal;
    }

    /**
     * 将long类型时间转换为Calendar
     *
     * @param time
     * @return
     */
    public static Calendar toCalendar(long time) {
        Calendar cal = new GregorianCalendar();
        cal.clear();// 在使用set方法之前，必须先clear一下，否则很多信息会继承自系统当前时间
        cal.setTimeInMillis(time);
        return cal;
    }

    /**
     * 将double类型时间转换为Calendar
     *
     * @param time
     * @return
     */
    public static Calendar toCalendar(double time) {
        return toCalendar(toLongTime(time));
    }

    /**
     * 将long类型时间转换为Date
     *
     * @param time
     * @return
     */
    public static Date toDate(long time) {
        return new Date(time);
    }

    /**
     * 将double类型时间转换为Date
     *
     * @param time
     * @return
     */
    public static Date toDate(double time) {
        return new Date(toLongTime(time));
    }

    /**
     * 将Calendar类型时间转换为Date
     *
     * @param cal
     * @return
     */
    public static Date toDate(Calendar cal) {
        return cal.getTime();
    }

    /**
     * 将默认格式日期转换为Date
     *
     * @param time
     * @return
     */
    public static Date toDate(String time) {
        String month = time.substring(6, 7);
        if ("月".endsWith(month)) {
            return toDate(time, YYYYMMDD_CHN1);
        } else {
            return toDate(time, YYYYMMDD_CHN2);
        }
    }

    /**
     * 将指定格式日期转换为Date
     *
     * @param time
     * @param format
     * @return
     */
    public static Date toDate(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将Date类型时间转换为long类型
     *
     * @param date
     * @return
     */
    public static long toLongTime(Date date) {
        return date.getTime();
    }

    /**
     * 将Calendar类型时间转换为long类型
     *
     * @param cal
     * @return
     */
    public static long toLongTime(Calendar cal) {
        return cal.getTimeInMillis();
    }

    /**
     * 将double类型时间转换为long类型(按四舍五入)
     *
     * @param time
     * @return
     */
    public static long toLongTime(double time) {
        return Math.round(time);
    }

    /**
     * 将默认格式日期转换为long类型
     *
     * @param time
     * @return
     */
    public static long toLongTime(String time) {
        return toDate(time).getTime();
    }

    /**
     * 将指定格式日期转换为long类型
     *
     * @param time
     * @param format
     * @return
     */
    public static long toLongTime(String time, String format) {
        return toDate(time, format).getTime();
    }

    /**
     * 将Date类型时间转换为double类型
     *
     * @param date
     * @return
     */
    public static double toDoubleTime(Date date) {
        return date.getTime();
    }

    /**
     * 将Calendar类型时间转换为double类型
     *
     * @param cal
     * @return
     */
    public static double toDoubleTime(Calendar cal) {
        return cal.getTimeInMillis();
    }

    /**
     * 将long类型时间转换为double类型
     *
     * @param time
     * @return
     */
    public static double toDoubleTime(long time) {
        return (double) time;
    }

    /**
     * 将默认格式日期转换为double类型
     *
     * @param time
     * @return
     */
    public static double toDoubleTime(String time) {
        return toDate(time).getTime();
    }

    /**
     * 将指定格式日期转换为double类型
     *
     * @param time
     * @param format
     * @return
     */
    public static double toDoubleTime(String time, String format) {
        return toDate(time, format).getTime();
    }

    /**
     * --------------------------- 时间运算 ---------------------------
     */
    /**
     * 复制日期
     *
     * @param cal 日期
     * @return 复制日期结果
     */
    public static Calendar copy(Calendar cal) {
        Calendar c = new GregorianCalendar();
        c.clear();// 在使用set方法之前，必须先clear一下，否则很多信息会继承自系统当前时间
        c.setTime(cal.getTime());
        return c;
    }

    /**
     * 修改日期,增添或减少天数
     *
     * @param cal 日期
     * @param day 天数（增加为+,减少为-）
     * @return 修改后的日期
     */
    public static Calendar addDays(Calendar cal, int day) {
        Calendar c = copy(cal);
        c.add(Calendar.DAY_OF_YEAR, day);
        return c;
    }

    /**
     * 修改日期,增添或减少天数
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDays(Date date, int day) {
        Calendar cal = toCalendar(date);
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    /**
     * 修改日期,增添或减少天数
     *
     * @param time
     * @param day
     * @return
     */
    public static String addDays(String time, int day) {
        Calendar cal = toCalendar(time);
        cal.add(Calendar.DAY_OF_YEAR, day);
        return toDateFmt(cal);
    }

    /**
     * 修改日期,增添或减少天数
     *
     * @param time
     * @param format
     * @param day
     * @return
     */
    public static String addDays(String time, String format, int day) {
        Calendar cal = toCalendar(time, format);
        cal.add(Calendar.DAY_OF_YEAR, day);
        return toDateFmt(cal, format);
    }

    /**
     * 修改日期,增添或减少年数
     *
     * @param cal
     * @param year
     * @return
     */
    public static Calendar addYears(Calendar cal, int year) {
        Calendar c = copy(cal);
        c.add(Calendar.YEAR, year);
        return c;
    }

    /**
     * 修改日期,增添或减少年数
     *
     * @param date
     * @param year
     * @return
     */
    public static Date addYears(Date date, int year) {
        Calendar cal = toCalendar(date);
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }

    /**
     * 修改日期,增添或减少年数
     *
     * @param time
     * @param year
     * @return
     */
    public static String addYears(String time, int year) {
        Calendar cal = toCalendar(time);
        cal.add(Calendar.YEAR, year);
        return toDateFmt(cal);
    }

    /**
     * 修改日期,增添或减少年数
     *
     * @param time
     * @param format
     * @param year
     * @return
     */
    public static String addYears(String time, String format, int year) {
        Calendar cal = toCalendar(time, format);
        cal.add(Calendar.YEAR, year);
        return toDateFmt(cal, format);
    }

    /**
     * 修改日期,增添或减少月数
     *
     * @param cal 日期
     * @param month
     * @return 修改后的日期
     */
    public static Calendar addMonths(Calendar cal, int month) {
        Calendar c = copy(cal);
        c.add(Calendar.MONTH, month);
        return c;
    }

    /**
     * 修改日期,增添或减少月数
     *
     * @param date
     * @param month
     * @return 修改后的日期
     */
    public static Date addMonths(Date date, int month) {
        Calendar cal = toCalendar(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    /**
     * 修改日期,增添或减少月数
     *
     * @param time
     * @param month
     * @return
     */
    public static String addMonths(String time, int month) {
        Calendar cal = toCalendar(time);
        cal.add(Calendar.MONTH, month);
        return toDateFmt(cal);
    }

    /**
     * 修改日期,增添或减少月数
     *
     * @param time
     * @param format
     * @param month
     * @return
     */
    public static String addMonths(String time, String format, int month) {
        Calendar cal = toCalendar(time, format);
        cal.add(Calendar.MONTH, month);
        return toDateFmt(cal, format);
    }

    /**
     * 修改日期,增添或减少月数
     *
     * @param cal 日期
     * @param week
     * @return 修改后的日期
     */
    public static Calendar addWeek(Calendar cal, int week) {
        Calendar c = copy(cal);
        c.add(Calendar.DAY_OF_YEAR, week * 7);
        return c;
    }

    /**
     * 修改日期,增添或减少月数
     *
     * @param date
     * @param week
     * @return 修改后的日期
     */
    public static Date addWeek(Date date, int week) {
        Calendar cal = toCalendar(date);
        cal.add(Calendar.DAY_OF_YEAR, week * 7);
        return cal.getTime();
    }

    /**
     * 修改日期,增添或减少月数
     *
     * @param time
     * @param week
     * @return
     */
    public static String addWeek(String time, int week) {
        Calendar cal = toCalendar(time);
        cal.add(Calendar.DAY_OF_YEAR, week * 7);
        return toDateFmt(cal);
    }

    /**
     * 修改日期,增添或减少月数
     *
     * @param time
     * @param format
     * @param week
     * @return
     */
    public static String addWeek(String time, String format, int week) {
        Calendar cal = toCalendar(time, format);
        cal.add(Calendar.DAY_OF_YEAR, week * 7);
        return toDateFmt(cal, format);
    }

    /**
     * 修改日期,增添或减少月数，天数，小时数，不改变时可以设置为0
     *
     * @param cal 预修改日期
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return 修改后的日期
     */
    public static Calendar addTime(Calendar cal, int year, int month, int day, int hour, int minute, int second) {
        Calendar c = copy(cal);
        goTime(c, year, month, day, hour, minute, second);
        return c;
    }

    /**
     * 修改日期,增添或减少月数，天数，小时数，不改变时可以设置为0
     *
     * @param date 预修改日期
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return 修改后的日期
     */
    public static Date addTime(Date date, int year, int month, int day, int hour, int minute, int second) {
        Calendar c = toCalendar(date);
        goTime(c, year, month, day, hour, minute, second);
        return toDate(c);
    }

    /**
     * 修改日期,增添或减少月数，天数，小时数，不改变时可以设置为0
     *
     * @param time
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return 修改后的日期
     */
    public static String addTime(String time, int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = toCalendar(time);
        goTime(cal, year, month, day, hour, minute, second);
        return toDateFmt(cal);
    }

    /**
     * 修改日期,增添或减少月数，天数，小时数，不改变时可以设置为0
     *
     * @param time
     * @param format
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return 修改后的日期
     */
    public static String addTime(String time, String format, int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = toCalendar(time, format);
        goTime(cal, year, month, day, hour, minute, second);
        return toDateFmt(cal, format);
    }

    /**
     * 修改日期,增添或减少月数，天数，小时数，不改变时可以设置为0.(改变参数的值)
     *
     * @param cal
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     */
    public static void goTime(Calendar cal, int year, int month, int day, int hour, int minute, int second) {
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DAY_OF_YEAR, day);
        cal.add(Calendar.HOUR_OF_DAY, hour);
        cal.add(Calendar.MINUTE, minute);
        cal.add(Calendar.SECOND, second);
    }

    /**
     * 比较两个默认格式时间字符串
     *
     * @param date 时间串
     * @param compared 比较的串
     * @return 如果date晚于date则返回为真
     */
    public static Boolean dateCompare(String date, String compared) {
        Calendar dateCal = toCalendar(date);
        Calendar comparedCal = toCalendar(compared);
        return dateCompare(dateCal, comparedCal);
    }

    /**
     * 比较两个指定格式时间字符串
     *
     * @param date 时间串
     * @param compared 比较的串
     * @return 如果date晚于date则返回为真
     */
    public static Boolean dateCompare(String date, String compared, String dateFormat, String comparedFormat) {
        Calendar dateCal = toCalendar(date, dateFormat);
        Calendar comparedCal = toCalendar(compared, comparedFormat);
        return dateCompare(dateCal, comparedCal);
    }

    /**
     * 比较两个指定格式时间字符串
     *
     * @param date 时间串
     * @param compared 比较的串
     * @return 如果date晚于date则返回为真
     */
    public static Boolean dateCompare(Date date, Date compared) {
        Calendar dateCal = toCalendar(date);
        Calendar comparedCal = toCalendar(compared);
        return dateCompare(dateCal, comparedCal);
    }

    /**
     * 比较两个指定格式时间字符串
     *
     * @param date 时间串
     * @param compared 比较的串
     * @return 如果date晚于date则返回为真
     */
    public static Boolean dateCompare(long date, long compared) {
        Calendar dateCal = toCalendar(date);
        Calendar comparedCal = toCalendar(compared);
        return dateCompare(dateCal, comparedCal);
    }

    /**
     * 比较两个指定格式时间字符串
     *
     * @param date 时间串
     * @param compared 比较的串
     * @return 如果date晚于date则返回为真
     */
    public static Boolean dateCompare(double date, double compared) {
        Calendar dateCal = toCalendar(date);
        Calendar comparedCal = toCalendar(compared);
        return dateCompare(dateCal, comparedCal);
    }

    /**
     * 比较两个指定格式时间字符串
     *
     * @param date 时间串
     * @param compared 比较的串
     * @return 如果date晚于compared则返回为真如果date早于compared则返回为false
     * 如果date相同compared则返回为null
     */
    public static Boolean dateCompare(Calendar date, Calendar compared) {
        if (date.compareTo(compared) == 0) {
            return null;
        }
        return date.compareTo(compared) > 0;
    }

    /**
     * 计算两个时间之间相隔天数
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间之间相隔天数
     */
    public static int getIntervalDays(Calendar start, Calendar end) {
        return getIntervalHours(start, end) / 24;
    }

    /**
     * 计算两个时间之间相隔小时
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间之间相隔天数
     */
    public static int getIntervalHours(Calendar start, Calendar end) {
        return getIntervalMinutes(start, end) / 60;
    }

    /**
     * 计算两个时间之间相隔分钟
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间之间相隔天数
     */
    public static int getIntervalMinutes(Calendar start, Calendar end) {
        return getIntervalSeconds(start, end) / 60;
    }

    /**
     * 计算两个时间之间相隔秒数
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间之间相隔天数
     */
    public static int getIntervalSeconds(Calendar start, Calendar end) {
        return getIntervalSeconds(start.getTimeInMillis(), end.getTimeInMillis());
    }

    /**
     * 计算两个时间之间相隔天数
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间之间相隔天数
     */
    public static int getIntervalDays(Date start, Date end) {
        return getIntervalHours(start, end) / 24;
    }

    /**
     * 计算两个时间之间相隔小时
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间之间相隔天数
     */
    public static int getIntervalHours(Date start, Date end) {
        return getIntervalMinutes(start, end) / 60;
    }

    /**
     * 计算两个时间之间相隔分钟
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间之间相隔天数
     */
    public static int getIntervalMinutes(Date start, Date end) {
        return getIntervalSeconds(start, end) / 60;
    }

    /**
     * 计算两个时间之间相隔秒数
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间之间相隔天数
     */
    public static int getIntervalSeconds(Date start, Date end) {
        return getIntervalSeconds(start.getTime(), end.getTime());
    }

    /**
     * 计算两个时间之间相隔天数
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间之间相隔天数
     */
    public static int getIntervalDays(long start, long end) {
        return getIntervalHours(start, end) / 24;
    }

    /**
     * 计算两个时间之间相隔小时
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间之间相隔数
     */
    public static int getIntervalHours(long start, long end) {
        return getIntervalMinutes(start, end) / 60;
    }

    /**
     * 计算两个时间之间相隔分钟
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间之间相隔天数
     */
    public static int getIntervalMinutes(long start, long end) {
        return getIntervalSeconds(start, end) / 60;
    }

    /**
     * 计算两个时间之间相隔秒数
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间之间相隔天数
     */
    public static int getIntervalSeconds(long start, long end) {
        // 确保startday在endday之前
        if (start > end) {
            long date = start;
            start = end;
            end = date;
        }
        long ei = end - start;
        // 根据毫秒数计算间隔天数
        return (int) (ei / 1000);
    }

    /**
     * long方式表示的两个时点的差
     *
     * @param start 起始时间
     * @param end 结束时间
     * @return
     */
    public static long timespan(Calendar start, Calendar end) {
        return end.getTimeInMillis() - start.getTimeInMillis();
    }

    /**
     * long方式表示的两个时点的差
     *
     * @param start 起始时间
     * @param end 结束时间
     * @return
     */
    public static long timespan(Date start, Date end) {
        return end.getTime() - start.getTime();
    }

    /**
     * long方式表示的两个时点的差
     *
     * @param start 起始时间
     * @param end 结束时间
     * @return
     */
    public static long timespan(Calendar start, Date end) {
        return end.getTime() - start.getTimeInMillis();
    }

    /**
     * long方式表示的两个时点的差
     *
     * @param start 起始时间
     * @param end 结束时间
     * @return
     */
    public static long timespan(Date start, Calendar end) {
        return end.getTimeInMillis() - start.getTime();
    }

    /**
     * 合并时间
     *
     * @param date 日期 年月日
     * @param time 时间 时分秒
     * @return 合并后时间
     */
    public static Calendar mergeDate(Calendar date, Calendar time) {
        if (date == null && time == null) {
            return null;
        }
        if (date == null) {
            return time;
        }
        if (time == null) {
            return date;
        }

        return mergeDateNotNull(date, time);
    }

    /**
     * 合并时间
     *
     * @param date 日期 年月日
     * @param time 时间 时分秒
     * @return 合并后时间
     */
    public static Date mergeDate(Date date, Date time) {
        if (date == null && time == null) {
            return null;
        }
        if (date == null) {
            return time;
        }
        if (time == null) {
            return date;
        }
        return mergeDateNotNull(toCalendar(date), toCalendar(time)).getTime();
    }

    /**
     * 合并时间
     *
     * @param Date 日期 年月日
     * @param time 时间 时分秒
     * @return 合并后时间
     */
    private static Calendar mergeDateNotNull(Calendar date, Calendar time) {
        Calendar cal = copy(date);
        cal.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, time.get(Calendar.SECOND));
        return cal;
    }

    /**
     * --------------------------- 年工具 ---------------------------
     */
    /**
     * 判断是否闰年
     *
     * @param yearNum
     * @return
     */
    public static boolean isLeapYear(int yearNum) {
        boolean isLeep = false;
        /**
         * 判断是否为闰年，赋值给一标识符flag
         */
        if ((yearNum % 4 == 0) && (yearNum % 100 != 0)) {
            isLeep = true;
        } else {
            isLeep = yearNum % 400 == 0;
        }
        return isLeep;
    }

    /**
     * 返回指定年总天数
     *
     * @return
     */
    public static int maxDaysOfYear() {
        if (isLeapYear(getYear())) {
            return 366;
        }
        return 365;
    }

    /**
     * 返回指定年总天数
     *
     * @param year
     * @return
     */
    public static int maxDaysOfYear(int year) {
        if (isLeapYear(year)) {
            return 366;
        }
        return 365;
    }

    /**
     * 返回指定时间所在年总天数
     *
     * @param cal
     * @return
     */
    public static int maxDaysOfYear(Calendar cal) {
        return maxDaysOfYear(getYear(cal));
    }

    /**
     * 返回当前年份
     *
     * @return
     */
    public static int getYear() {
        return getYear(getNow());
    }

    /**
     * 返回指定时间所在年份
     *
     * @param cal
     * @return
     */
    public static int getYear(Calendar cal) {
        return cal.get(Calendar.YEAR);
    }

    /**
     * 返回指定时间所在年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        return getYear(toCalendar(date));
    }

    /**
     * 返回指定时间所在年份
     *
     * @param time
     * @return
     */
    public static int getYear(long time) {
        return getYear(toCalendar(time));
    }

    /**
     * 返回指定时间所在年份
     *
     * @param time
     * @return
     */
    public static int getYear(String time) {
        return getYear(toCalendar(time));
    }

    /**
     * 返回指定时间所在年份
     *
     * @param time
     * @param format
     * @return
     */
    public static int getYear(String time, String format) {
        return getYear(toCalendar(time, format));
    }

    /**
     * --------------------------- 月工具 ---------------------------
     */
    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getMonth() {
        Calendar cal = getNow();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定时间月份
     *
     * @param cal
     * @return
     */
    public static int getMonth(Calendar cal) {
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定时间月份
     *
     * @param time
     * @return
     */
    public static int getMonth(String time) {
        Calendar cal = toCalendar(time);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定时间月份
     *
     * @param time
     * @param format
     * @return
     */
    public static int getMonth(String time, String format) {
        Calendar cal = toCalendar(time, format);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定时间月份
     *
     * @param time
     * @return
     */
    public static int getMonth(long time) {
        Calendar cal = toCalendar(time);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定时间月份
     *
     * @param time
     * @return
     */
    public static int getMonth(double time) {
        Calendar cal = toCalendar(time);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定时间月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = toCalendar(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取本月第一天的日期
     *
     * @return 本月第一天的日期
     */
    public static Calendar getFirstDayOfMonth() {
        Calendar calendar = getNow();
        goFirstDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 获取指定时间所在月第一天的日期
     *
     * @param cal
     * @return 本月第一天的日期
     */
    public static Calendar getFirstDayOfMonth(Calendar cal) {
        Calendar calendar = copy(cal);
        goFirstDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 获取指定时间所在月第一天的日期
     *
     * @param year
     * @param month
     * @return 本月第一天的日期
     */
    public static Calendar getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);// 注意,Calendar对象默认一月为0
        goFirstDayOfMonth(cal);
        return cal;
    }

    /**
     * 获取指定时间所在月第一天的日期
     *
     * @param date
     * @return 本月第一天的日期
     */
    public static Calendar getFirstDayOfMonth(Date date) {
        Calendar calendar = toCalendar(date);
        goFirstDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 获取指定时间所在月第一天的日期
     *
     * @param time
     * @return 本月第一天的日期
     */
    public static Calendar getFirstDayOfMonth(long time) {
        Calendar calendar = toCalendar(time);
        goFirstDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 获取指定时间所在月第一天的日期
     *
     * @param time
     * @return 本月第一天的日期
     */
    public static Calendar getFirstDayOfMonth(double time) {
        Calendar calendar = toCalendar(time);
        goFirstDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 获取指定时间所在月第一天的日期
     *
     * @param time
     * @return 本月第一天的日期
     */
    public static Calendar getFirstDayOfMonth(String time) {
        Calendar calendar = toCalendar(time);
        goFirstDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 获取指定时间所在月第一天的日期
     *
     * @param time
     * @param format
     * @return 本月第一天的日期
     */
    public static Calendar getFirstDayOfMonth(String time, String format) {
        Calendar calendar = toCalendar(time, format);
        goFirstDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 跳转到本月第一天
     *
     * @param cal
     */
    public static void goFirstDayOfMonth(Calendar cal) {
        while (cal.get(Calendar.DAY_OF_MONTH) != 1) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
    }

    /**
     * 获取本月最后一天的日期
     *
     * @return 本月最后一天的日期
     */
    public static Calendar getLastDayOfMonth() {
        Calendar cal = getNow();
        goLastDayOfMonth(cal);
        return cal;
    }

    /**
     * 获取指定时间所在月的最后一天
     *
     * @param cal
     * @return 本月最后一天的日期
     */
    public static Calendar getLastDayOfMonth(Calendar cal) {
        Calendar calendar = copy(cal);
        goLastDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 获取指定时间所在月的最后一天
     *
     * @param year
     * @param month
     * @return 本月最后一天的日期
     */
    public static Calendar getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);// 注意,Calendar对象默认一月为0
        goLastDayOfMonth(cal);
        return cal;
    }

    /**
     * 获取指定时间所在月的最后一天
     *
     * @param time
     * @return 本月最后一天的日期
     */
    public static Calendar getLastDayOfMonth(String time) {
        Calendar calendar = toCalendar(time);
        goLastDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 获取指定时间所在月的最后一天
     *
     * @param time
     * @param format
     * @return 本月最后一天的日期
     */
    public static Calendar getLastDayOfMonth(String time, String format) {
        Calendar calendar = toCalendar(time, format);
        goLastDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 获取指定时间所在月的最后一天
     *
     * @param time
     * @return 本月最后一天的日期
     */
    public static Calendar getLastDayOfMonth(Date time) {
        Calendar calendar = toCalendar(time);
        goLastDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 获取指定时间所在月的最后一天
     *
     * @param time
     * @return 本月最后一天的日期
     */
    public static Calendar getLastDayOfMonth(long time) {
        Calendar calendar = toCalendar(time);
        goLastDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 获取指定时间所在月的最后一天
     *
     * @param time
     * @return 本月最后一天的日期
     */
    public static Calendar getLastDayOfMonth(double time) {
        Calendar calendar = toCalendar(time);
        goLastDayOfMonth(calendar);
        return calendar;
    }

    /**
     * 跳转到本月最后一天
     *
     * @param cal
     */
    public static void goLastDayOfMonth(Calendar cal) {
        int month = cal.get(Calendar.MONTH);
        while (cal.get(Calendar.MONTH) == month) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        cal.add(Calendar.DAY_OF_MONTH, -1);
    }

    /**
     * 获取当前月最大天数
     *
     * @return
     */
    public static int maxDaysOfMonth() {
        Calendar time = Calendar.getInstance();
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);// 本月份的天数
        return day;
    }

    /**
     * 获取指定月份，最大天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int maxDaysOfMonth(int year, int month) {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, month - 1);// 注意,Calendar对象默认一月为0
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);// 本月份的天数
        return day;
    }

    /**
     * 获取指定时间所在月份，最大天数
     *
     * @param cal
     * @return
     */
    public static int maxDaysOfMonth(Calendar cal) {
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);// 本月份的天数
        return day;
    }

    /**
     * --------------------------- 日工具 ---------------------------
     */
    /**
     * 判断是否是今天
     *
     * @param cal
     * @return
     */
    public static boolean isToday(Calendar cal) {
        Calendar now = getNow();
        if (now.get(Calendar.YEAR) != cal.get(Calendar.YEAR)) {
            return false;
        }
        return now.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取当前几号
     *
     * @return
     */
    public static int getDay() {
        return getDay(getNow());
    }

    /**
     * 获取默认格式时间为几号
     *
     * @param time
     * @return
     */
    public static int getDay(String time) {
        return getDay(toCalendar(time));
    }

    /**
     * 获取指定格式时间为几号
     *
     * @param time
     * @param format
     * @return
     */
    public static int getDay(String time, String format) {
        return getDay(toCalendar(time, format));
    }

    /**
     * 获取指定时间为几号
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        return getDay(toCalendar(date));
    }

    /**
     * 获取指定时间为几号
     *
     * @param cal
     * @return
     */
    public static int getDay(Calendar cal) {
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取指定时间为几号
     *
     * @param time
     * @return
     */
    public static int getDay(long time) {
        return getDay(toCalendar(time));
    }

    /**
     * 获取指定时间为几号
     *
     * @param time
     * @return
     */
    public static int getDay(double time) {
        return getDay(toCalendar(time));
    }

    /**
     * --------------------------- 周工具 ---------------------------
     */
    /**
     * 判断是否是周末
     *
     * @return
     */
    public static boolean isWeekEnd(Calendar cal) {
        int i = getWeekDay(cal, 0);
        return i == Calendar.SUNDAY || i == Calendar.SATURDAY;
    }

    /**
     * 判断是否是周末
     *
     * @return
     */
    public static boolean isWeekEnd(Date date) {
        int i = getWeekDay(toCalendar(date), 0);
        return i == Calendar.SUNDAY || i == Calendar.SATURDAY;
    }

    /**
     * 判断是否是周日
     *
     * @return
     */
    public static boolean isSunDay(Calendar cal) {
        int i = getWeekDay(cal, 0);
        return i == Calendar.SUNDAY;
    }

    /**
     * 判断是否是周六
     *
     * @return
     */
    public static boolean isSaturDay(Calendar cal) {
        int i = getWeekDay(cal, 0);
        return i == Calendar.SATURDAY;
    }

    /**
     * 判断是否是周日
     *
     * @return
     */
    public static boolean isSunDay(Date date) {
        int i = getWeekDay(toCalendar(date), 0);
        return i == Calendar.SUNDAY;
    }

    /**
     * 判断是否是周六
     *
     * @return
     */
    public static boolean isSaturDay(Date date) {
        int i = getWeekDay(toCalendar(date), 0);
        return i == Calendar.SATURDAY;
    }

    /**
     * 获取当前时间为周几. 国内习惯（1=周一，2=周二，3=周三，4=周四，5=周五，6=周六，7=周日）
     *
     * @return
     */
    public static int getWeekDay() {
        Calendar cal = getNow();
        return getWeekDay(cal, CHINA);
    }

    /**
     * 获取当前时间为周几. type：0 国外习惯（1=周日，2=周一，3=周二，4=周三，5=周四，6=周五，7=周六） type：1
     * 国内习惯（1=周一，2=周二，3=周三，4=周四，5=周五，6=周六，7=周日）
     *
     * @param type
     * @return
     */
    public static int getWeekDay(int type) {
        Calendar cal = getNow();
        return getWeekDay(cal, type);
    }

    /**
     * 获取当前时间为周几. 国内习惯（1=周一，2=周二，3=周三，4=周四，5=周五，6=周六，7=周日）
     *
     * @param cal
     * @return
     */
    public static int getWeekDay(Calendar cal) {
        return getWeekDay(cal, CHINA);
    }

    /**
     * 获取当前时间为周几. type：0 国外习惯（1=周日，2=周一，3=周二，4=周三，5=周四，6=周五，7=周六） type：1
     * 国内习惯（1=周一，2=周二，3=周三，4=周四，5=周五，6=周六，7=周日）
     *
     * @param cal
     * @param type
     * @return
     */
    public static int getWeekDay(Calendar cal, int type) {
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        if (type == 0) {
            return weekDay;
        }
        weekDay = weekDay - 1;
        if (weekDay == 0) {
            return 7;
        }
        return weekDay;
    }

    /**
     * 获得当前周周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek() {
        return getFirstDayOfWeek(1);
    }

    /**
     * 获得当前周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(int type) {
        Calendar cal = getNow();
        return goFirstDayOfWeek(cal, type);
    }

    /**
     * 获得当前周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(long time) {
        Calendar calendar = toCalendar(time);
        return goFirstDayOfWeek(calendar, CHINA);
    }

    /**
     * 获得当前周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(double time) {
        Calendar calendar = toCalendar(time);
        return goFirstDayOfWeek(calendar, CHINA);
    }

    /**
     * 获得当前周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(String time) {
        Calendar calendar = toCalendar(time);
        return goFirstDayOfWeek(calendar, CHINA);
    }

    /**
     * 获得当前周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(String time, int type) {
        Calendar calendar = toCalendar(time);
        return goFirstDayOfWeek(calendar, type);
    }

    /**
     * 获得当前周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(String time, String format, int type) {
        Calendar calendar = toCalendar(time, format);
        return goFirstDayOfWeek(calendar, type);
    }

    /**
     * 获得当前周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(String time, String format) {
        Calendar calendar = toCalendar(time, format);
        return goFirstDayOfWeek(calendar, CHINA);
    }

    /**
     * 获得当前周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(double time, int type) {
        Calendar calendar = toCalendar(time);
        return goFirstDayOfWeek(calendar, type);
    }

    /**
     * 获得当前周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(long time, int type) {
        Calendar calendar = toCalendar(time);
        return goFirstDayOfWeek(calendar, type);
    }

    /**
     * 指定时间所在周的周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(Calendar cal) {
        return getFirstDayOfWeek(cal, CHINA);
    }

    /**
     * 指定时间所在周的周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(Date date) {
        return getFirstDayOfWeek(date, CHINA);
    }

    /**
     * 指定时间所在周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(Date date, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        goFirstDayOfWeek(calendar, type);
        return calendar;
    }

    /**
     * 指定时间所在周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(Calendar cal, int type) {
        Calendar calendar = copy(cal);
        goFirstDayOfWeek(calendar, type);
        return calendar;
    }

    /**
     * 指定周的周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(int year, int weekNum) {
        return getFirstDayOfWeek(year, weekNum, CHINA);
    }

    /**
     * 指定周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getFirstDayOfWeek(int year, int weekNum, int type) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);
        return goFirstDayOfWeek(cal, type);
    }

    /**
     * 指定时间跳转到所在周的周一
     *
     * @return
     */
    public static Calendar goFirstDayOfWeek(Calendar cal) {
        return goFirstDayOfWeek(cal, CHINA);
    }

    /**
     * 指定时间跳转到所在周的第一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar goFirstDayOfWeek(Calendar cal, int type) {
        int i = Calendar.MONDAY;
        if (type == 0) {
            i = Calendar.SUNDAY;
        }
        cal.set(Calendar.DAY_OF_WEEK, i);
        return cal;
    }

    /**
     * 获得本周的周日
     *
     * @return
     */
    public static Calendar getLastDayOfWeek() {
        return getLastDayOfWeek(1);
    }

    /**
     * 获得本周的最后一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getLastDayOfWeek(int type) {
        return goLastDayOfWeek(getNow(), type);
    }

    /**
     * 获得指定时间所在周的周日
     *
     * @return
     */
    public static Calendar getLastDayOfWeek(Calendar cal) {
        return getLastDayOfWeek(cal, CHINA);
    }

    /**
     * 获得指定时间所在周的最后一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getLastDayOfWeek(Calendar cal, int type) {
        Calendar calendar = copy(cal);
        return goLastDayOfWeek(calendar, type);
    }

    /**
     * 获得指定周的周日
     *
     * @return
     */
    public static Calendar getLastDayOfWeek(int year, int weekNum) {
        return getLastDayOfWeek(year, weekNum, CHINA);
    }

    /**
     * 获得指定周的最后一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar getLastDayOfWeek(int year, int weekNum, int type) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);
        return goLastDayOfWeek(cal, type);
    }

    /**
     * 指定时间跳转到所在周的周日
     *
     * @return
     */
    public static Calendar goLastDayOfWeek(Calendar cal) {
        return goLastDayOfWeek(cal, CHINA);
    }

    /**
     * 指定时间跳转到所在周的最后一天 type：0 周日 type：1 周一
     *
     * @return
     */
    public static Calendar goLastDayOfWeek(Calendar cal, int type) {
        int i = Calendar.SUNDAY;
        if (type == 0) {
            i = Calendar.SATURDAY;
        }
        cal.set(Calendar.DAY_OF_WEEK, i);
        return cal;
    }

    /**
     * 获得当天属于当年第几周
     *
     * @return
     */
    public static int getWeekNumOfYear() {
        Calendar calendar = Calendar.getInstance();
        return getWeekNumOfYear(calendar);
    }

    /**
     * 获得指定时间所在周属于那一年的第几周
     *
     * @param cal
     * @return
     */
    public static int getWeekNumOfYear(Calendar cal) {
        int iWeekNum = cal.get(Calendar.WEEK_OF_YEAR);
        return iWeekNum;
    }

    /**
     * 获得当月的第一天为周几
     *
     * @return
     */
    public static int getFirstWeekDayOfMonth() {
        return getFirstWeekDayOfMonth(1);
    }

    /**
     * 获得指定时间所在月的第一天为周几
     *
     * @param cal
     * @return
     */
    public static int getFirstWeekDayOfMonth(Calendar cal) {
        return getFirstWeekDayOfMonth(cal, CHINA);
    }

    /**
     * 获得指定月的第一天为周几
     *
     * @param year
     * @param month
     * @return
     */
    public static int getFirstWeekDayOfMonth(int year, int month) {
        return getWeekDay(getFirstDayOfMonth(year, month), CHINA);
    }

    /**
     * 获得当月的第一天为周几
     *
     * @param type
     * @return
     */
    public static int getFirstWeekDayOfMonth(int type) {
        return getWeekDay(getFirstDayOfMonth(), type);
    }

    /**
     * 获得指定时间所在月的第一天为周几
     *
     * @param cal
     * @param type
     * @return
     */
    public static int getFirstWeekDayOfMonth(Calendar cal, int type) {
        return getWeekDay(getFirstDayOfMonth(cal), type);
    }

    /**
     * 获得指定月的第一天为周几
     *
     * @param year
     * @param month
     * @param type
     * @return
     */
    public static int getFirstWeekDayOfMonth(int year, int month, int type) {
        return getWeekDay(getFirstDayOfMonth(year, month), type);
    }

    /**
     * --------------------------- 年龄工具 ---------------------------
     */
    /**
     * 根据生日取得当前年龄
     *
     * @param birthday
     * @return
     */
    public static String getAge(String birthday) {
        return getAge(toCalendar(birthday));
    }

    /**
     * 根据生日取得当前年龄
     *
     * @param birthday
     * @param format
     * @return
     */
    public static String getAge(String birthday, String format) {
        return getAge(toCalendar(birthday, format));
    }

    /**
     * 根据生日取得当前年龄
     *
     * @param birthday
     * @return
     */
    public static String getAge(Date birthday) {
        return getAge(toCalendar(birthday));
    }

    /**
     * 根据生日取得当前年龄
     *
     * @param birthday
     * @return
     */
    public static String getAge(long birthday) {
        return getAge(toCalendar(birthday));
    }

    /**
     * 根据生日取得当前年龄
     *
     * @param birthday
     * @return
     */
    public static String getAge(double birthday) {
        return getAge(toCalendar(birthday));
    }

    /**
     * 根据生日取得当前年龄
     *
     * @param birthday
     * @return
     */
    public static String getAge(Calendar birthday) {
        int birthyear = birthday.get(Calendar.YEAR);
        int birthmonth = birthday.get(Calendar.MONTH);
        int birthdate = birthday.get(Calendar.DATE);
        int birthhour = birthday.get(Calendar.HOUR_OF_DAY);
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int nowyear = now.get(Calendar.YEAR);
        int nowmonth = now.get(Calendar.MONTH);
        int nowdate = now.get(Calendar.DATE);
        int nowhour = now.get(Calendar.HOUR_OF_DAY);

        int ageyear = nowyear - birthyear;
        int agemonth = nowmonth - birthmonth;
        int agedate = nowdate - birthdate;
        int agehour = nowhour - birthhour;

        int mArray[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        // 判断闰年的情况 ，2月份有29天；
        if ((nowyear % 400 == 0) || ((nowyear % 100 != 0) && (nowyear % 4 == 0))) {
            mArray[1] = 29;
        }

        if (agedate < 0) {
            agedate = agedate + mArray[nowmonth];
            agemonth = agemonth - 1;
        }
        if (agemonth < 0) {
            agemonth = agemonth + 12;
            ageyear = ageyear - 1;

        }
        if (ageyear >= 5) {
            return ageyear + "岁";
        } else if (ageyear >= 1) {
            return ageyear + "岁" + agemonth + "月";
        } else if (agemonth >= 1) {
            return agemonth + "月" + agedate + "天";
        } else if (agedate >= 1) {
            return agedate + "天";
        } else {
            return agehour + "时";
        }
    }

    /**
     * 年月日时分秒转换成字符串
     *
     * @return
     */
    public static String yearSecondToString() {
        LocalDateTime t = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        int[] array = new int[6];
        array[0] = t.getYear();
        array[1] = t.getMonthValue();
        array[2] = t.getDayOfMonth();
        array[3] = t.getHour();
        array[4] = t.getMinute();
        array[5] = t.getSecond();
        for (int i : array) {
            if (i < 10) {
                sb.append(0);
            }
            sb.append(i);
        }
        return sb.toString();
    }

    public Long countThisDayStart() {
        LocalDate ld = LocalDate.now();
        Calendar c = Calendar.getInstance();
        c.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth(), 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    public Long countNextDayStart() {
        return this.countThisDayStart() + oneDayMillis;
    }

    public Long countThisWeekStart() {
        LocalDate ld = LocalDate.now();
        DayOfWeek dayOfWeek = ld.getDayOfWeek();
        Long dayStart = this.countThisDayStart();
        return dayStart - (dayOfWeek.getValue() - 1) * oneDayMillis;
    }

    public Long countNextWeekStart() {
        Long weekStart = this.countThisWeekStart();
        return weekStart + oneDayMillis * 7;
    }

    public Long countThisMonthStart() {
        LocalDate ld = LocalDate.now();
        int year = ld.getYear();
        int month = ld.getMonthValue();

        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, 1, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTimeInMillis();
    }

    public Long countNextMonthStart() {
        LocalDate ld = LocalDate.now();
        Month month = ld.getMonth();
        int length = month.length(ld.isLeapYear());
        return this.countThisMonthStart() + oneDayMillis * length;
    }

    public Long countThisYearStart() {
        LocalDate ld = LocalDate.now();
        int year = ld.getYear();

        Calendar c = Calendar.getInstance();
        c.set(year, 0, 1, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTimeInMillis();
    }

    public Long countNextYearStart() {
        LocalDate ld = LocalDate.now();
        Long thisYearStart = this.countThisYearStart();
        if (ld.isLeapYear()) {
            return thisYearStart + oneDayMillis * 366;
        } else {
            return thisYearStart + oneDayMillis * 365;
        }
    }

    /**
     * @return the instance
     */
    public static DateUtil getInstance() {
        return instance;
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    public static Date toDateValue(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

}
