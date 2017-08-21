package com.s.z.j.logs;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyTime {
    private static String TAG = "MyTime";

    /**
     * 获得当前日期，yyyy-MM-dd
     *
     * @return
     */
    public static String getTime() {
        Date cunDate = new Date(System.currentTimeMillis());// 获得当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(cunDate);
        return date;
    }

    /**
     * 获得当前日期，yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getFullTime() {
        Date cunDate = new Date(System.currentTimeMillis());// 获得当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(cunDate);
        return date;
    }

    /**
     * 获得当前日期，yyyyMMdd
     *
     * @return
     */
    public static String getNowTime() {
        Date cunDate = new Date(System.currentTimeMillis());// 获得当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(cunDate);
        return date;
    }

    /**
     * 获得当前年份
     *
     * @return
     */
    public static int getYear() {
        String date = getTime();
        String[] temp = date.split("-");
        int year = Integer.parseInt(temp[0]);
        return year;
    }

    /**
     * 获得当前月份
     *
     * @return
     */
    public static int getMonth() {
        String date = getTime();
        String[] temp = date.split("-");
        int year = Integer.parseInt(temp[1]);
        return year;
    }

    /**
     * 获得当前日
     *
     * @return
     */
    public static int getDay() {
        String date = getTime();
        String[] temp = date.split("-");
        int year = Integer.parseInt(temp[2]);
        return year;
    }

    /**
     * 获得当前的日期，如2015-12-13
     *
     * @return
     */
    public static java.util.Date getDate() {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());// 获得当前时间
        return date;
    }

    /**
     * 获得时间戳
     *
     * @return
     */
    public static long getTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间的GMT Unix时间戳  到秒
     */
    public static long getGMTUnixTimeByCalendar() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前时区下日期时间对应的时间戳
        long unixTime = calendar.getTimeInMillis();

        return (unixTime) / 1000;
    }

    /**
     * 获取指定算法的字符串
     * 共 14位
     */
    public static long getDeviceCode() {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        Long nowData = Long.valueOf(date.getTime() + "" + Code.createCode(1));//当前时间后面加一位随机数
        long data = StrToDate("2015-09-16 00:00:00").getTime();//指定时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String str = format.format(date.getTime());
        long nowYear = Long.valueOf(str);//求出当前年份
        return nowData - data / 1000 * nowYear;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static java.util.Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取10分钟后的时间戳，到秒
     *
     * @return
     */
    public static long getUnixTimeByCalendar() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前时区下日期时间对应的时间戳
        long unixTime = calendar.getTimeInMillis();

        return (unixTime - 36000) / 1000;
    }
}
