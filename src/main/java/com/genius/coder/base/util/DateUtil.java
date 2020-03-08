package com.genius.coder.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/6
 */
public class DateUtil {
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal();
    private static final ThreadLocal<SimpleDateFormat> DATE_TIME_FORMAT = new ThreadLocal();

    private DateUtil() {
    }

    public static String getCurrentDateStr(String format) {
        return (new SimpleDateFormat(format)).format(getCurrentDate());
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static String formatDate(Date date, String format) {
        return (new SimpleDateFormat(format)).format(date);
    }

    public static SimpleDateFormat getDateFormat() {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat)DATE_FORMAT.get();
        if (null == simpleDateFormat) {
            DATE_FORMAT.set(new SimpleDateFormat("yyyy-MM-dd"));
        }

        return (SimpleDateFormat)DATE_FORMAT.get();
    }

    public static SimpleDateFormat getDateTimeFormat() {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat)DATE_TIME_FORMAT.get();
        if (null == simpleDateFormat) {
            DATE_TIME_FORMAT.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        }

        return (SimpleDateFormat)DATE_TIME_FORMAT.get();
    }

    public static Date getStartTime(int day) {
        Calendar c = Calendar.getInstance();
        c.add(5, -day + 1);

        try {
            return getDateTimeFormat().parse(getDateFormat().format(c.getTime()) + " 00:00:00");
        } catch (ParseException var3) {
            var3.printStackTrace();
            return c.getTime();
        }
    }

    public static Date getEndTime() {
        Calendar c = Calendar.getInstance();

        try {
            return getDateTimeFormat().parse(getDateFormat().format(c.getTime()) + " 23:59:59");
        } catch (ParseException var2) {
            var2.printStackTrace();
            return c.getTime();
        }
    }

    public static Date getStartTime(Date date) {
        try {
            return getDateTimeFormat().parse(getDateFormat().format(date) + " 00:00:00");
        } catch (ParseException var2) {
            var2.printStackTrace();
            return date;
        }
    }

    public static Date getEndTime(Date date) {
        try {
            return getDateTimeFormat().parse(getDateFormat().format(date) + " 23:59:59");
        } catch (ParseException var2) {
            var2.printStackTrace();
            return date;
        }
    }
}
