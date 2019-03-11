package com.mezzsy.copyzhihunews.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mezzsy on 2019/3/11
 * Describe:
 */
public class DateHelper {
    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    public static void main(String[] args) {
        String today = getTodayDate();
        System.out.println("今天是" + today);
        String yesterday = getSpecifiedDayBefore(today);
        System.out.println("昨天是" + yesterday);
        System.out.println(getDate("20190310"));
    }

    /**
     * 获取今天的日期，格式为yyyyMMdd
     *
     * @return date of today
     */
    private static String getTodayDate() {
        String today = "";
        Date date = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat simpleDateFormat = getDateFormat();
        today = simpleDateFormat.format(date);
        return today;
    }

    /**
     * 获取指定日期的昨天
     *
     * @param specifiedDay 指定日期，格式为yyyyMMdd
     * @return
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = getDateFormat().parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        return getDateFormat().format(c.getTime());
    }

    /**
     * 判断是否为今天
     *
     * @param day 传入的时间，格式为yyyyMMdd
     * @return true今天 false不是
     */
    public static boolean isToday(String day) {
        return day.equals(getTodayDate());
    }

    /**
     * 获取知乎日报日期标题格式的日期，如03月09日 星期六
     * 如果是今天就返回今日热闻
     *
     * @param date，格式为yyyyMMdd
     * @return
     */
    public static String getDate(String date) {
        if (isToday(date))
            return "今日热闻";
        StringBuilder sb = new StringBuilder();
        String m = date.substring(4, 6);
        sb.append(m).append("月");
        String d = date.substring(6);
        sb.append(d).append("日 星期");
        sb.append(dayForWeek(date));
        return sb.toString();
    }

    private static String dayForWeek(String pTime) {
        SimpleDateFormat format = getDateFormat();
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        switch (dayForWeek) {
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "日";
        }
        return "";
    }

    /**
     * 获取日期格式
     *
     * @return
     */
    private static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyyMMdd", Locale.CHINA));
        }
        return DateLocal.get();
    }
}
