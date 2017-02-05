package com.s.z.j.newUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.s.z.j.newUtils.ConstUtils.*;
import static com.s.z.j.newUtils.ConstUtils.DAY;
import static com.s.z.j.newUtils.ConstUtils.HOUR;
import static com.s.z.j.newUtils.ConstUtils.MIN;
import static com.s.z.j.newUtils.ConstUtils.MSEC;
import static com.s.z.j.newUtils.ConstUtils.SEC;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : ʱ����ع�����
 * </pre>
 */
public class TimeUtils {

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * <p>�ڹ������о���ʹ�õ�������ĸ�ʽ�������������Ҫ��һ�����ڵĲ����࣬������־��ʽ��Ҫʹ�� SimpleDateFormat�Ķ����ʽ.</p>
     * ��ʽ���������£� ���ں�ʱ��ģʽ <br>
     * <p>���ں�ʱ���ʽ�����ں�ʱ��ģʽ�ַ���ָ���������ں�ʱ��ģʽ�ַ����У�δ�����ŵ���ĸ 'A' �� 'Z' �� 'a' �� 'z'
     * ������Ϊģʽ��ĸ��������ʾ���ڻ�ʱ���ַ���Ԫ�ء��ı�����ʹ�õ����� (') ��������������н��͡�"''"
     * ��ʾ�����š����������ַ��������ͣ�ֻ���ڸ�ʽ��ʱ�����Ǽ򵥸��Ƶ�����ַ����������ڷ���ʱ�������ַ�������ƥ�䡣
     * </p>
     * ����������ģʽ��ĸ�����������ַ� 'A' �� 'Z' �� 'a' �� 'z' ������������ <br>
     * <table border="1" cellspacing="1" cellpadding="1" summary="Chart shows pattern letters, date/time component,
     * presentation, and examples.">
     * <tr>
     * <th align="left">��ĸ</th>
     * <th align="left">���ڻ�ʱ��Ԫ��</th>
     * <th align="left">��ʾ</th>
     * <th align="left">ʾ��</th>
     * </tr>
     * <tr>
     * <td><code>G</code></td>
     * <td>Era ��־��</td>
     * <td>Text</td>
     * <td><code>AD</code></td>
     * </tr>
     * <tr>
     * <td><code>y</code> </td>
     * <td>�� </td>
     * <td>Year </td>
     * <td><code>1996</code>; <code>96</code> </td>
     * </tr>
     * <tr>
     * <td><code>M</code> </td>
     * <td>���е��·� </td>
     * <td>Month </td>
     * <td><code>July</code>; <code>Jul</code>; <code>07</code> </td>
     * </tr>
     * <tr>
     * <td><code>w</code> </td>
     * <td>���е����� </td>
     * <td>Number </td>
     * <td><code>27</code> </td>
     * </tr>
     * <tr>
     * <td><code>W</code> </td>
     * <td>�·��е����� </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>D</code> </td>
     * <td>���е����� </td>
     * <td>Number </td>
     * <td><code>189</code> </td>
     * </tr>
     * <tr>
     * <td><code>d</code> </td>
     * <td>�·��е����� </td>
     * <td>Number </td>
     * <td><code>10</code> </td>
     * </tr>
     * <tr>
     * <td><code>F</code> </td>
     * <td>�·��е����� </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>E</code> </td>
     * <td>�����е����� </td>
     * <td>Text </td>
     * <td><code>Tuesday</code>; <code>Tue</code> </td>
     * </tr>
     * <tr>
     * <td><code>a</code> </td>
     * <td>Am/pm ��� </td>
     * <td>Text </td>
     * <td><code>PM</code> </td>
     * </tr>
     * <tr>
     * <td><code>H</code> </td>
     * <td>һ���е�Сʱ����0-23�� </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>k</code> </td>
     * <td>һ���е�Сʱ����1-24�� </td>
     * <td>Number </td>
     * <td><code>24</code> </td>
     * </tr>
     * <tr>
     * <td><code>K</code> </td>
     * <td>am/pm �е�Сʱ����0-11�� </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>h</code> </td>
     * <td>am/pm �е�Сʱ����1-12�� </td>
     * <td>Number </td>
     * <td><code>12</code> </td>
     * </tr>
     * <tr>
     * <td><code>m</code> </td>
     * <td>Сʱ�еķ����� </td>
     * <td>Number </td>
     * <td><code>30</code> </td>
     * </tr>
     * <tr>
     * <td><code>s</code> </td>
     * <td>�����е����� </td>
     * <td>Number </td>
     * <td><code>55</code> </td>
     * </tr>
     * <tr>
     * <td><code>S</code> </td>
     * <td>������ </td>
     * <td>Number </td>
     * <td><code>978</code> </td>
     * </tr>
     * <tr>
     * <td><code>z</code> </td>
     * <td>ʱ�� </td>
     * <td>General time zone </td>
     * <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code> </td>
     * </tr>
     * <tr>
     * <td><code>Z</code> </td>
     * <td>ʱ�� </td>
     * <td>RFC 822 time zone </td>
     * <td><code>-0800</code> </td>
     * </tr>
     * </table>
     * <pre>
     *                          HH:mm    15:44
     *                         h:mm a    3:44 ����
     *                        HH:mm z    15:44 CST
     *                        HH:mm Z    15:44 +0800
     *                     HH:mm zzzz    15:44 �й���׼ʱ��
     *                       HH:mm:ss    15:44:40
     *                     yyyy-MM-dd    2016-08-12
     *               yyyy-MM-dd HH:mm    2016-08-12 15:44
     *            yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *       yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 �й���׼ʱ��
     *  EEEE yyyy-MM-dd HH:mm:ss zzzz    ������ 2016-08-12 15:44:40 �й���׼ʱ��
     *       yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *   yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 ��Ԫ at 15:44:40 CST
     *                         K:mm a    3:44 ����
     *               EEE, MMM d, ''yy    ������, ���� 12, '16
     *          hh 'o''clock' a, zzzz    03 o'clock ����, �й���׼ʱ��
     *   yyyyy.MMMMM.dd GGG hh:mm aaa    02016.����.12 ��Ԫ 03:44 ����
     *     EEE, d MMM yyyy HH:mm:ss Z    ������, 12 ���� 2016 15:44:40 +0800
     *                  yyMMddHHmmssZ    160812154440+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    ������ DATE(2016-08-12) TIME(15:44:40) �й���׼ʱ��
     * </pre>
     */
    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


    /**
     * ��ʱ���תΪʱ���ַ���
     * <p>��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param milliseconds ����ʱ���
     * @return ʱ���ַ���
     */
    public static String milliseconds2String(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }

    /**
     * ��ʱ���תΪʱ���ַ���
     * <p>��ʽΪ�û��Զ���</p>
     *
     * @param milliseconds ����ʱ���
     * @param format       ʱ���ʽ
     * @return ʱ���ַ���
     */
    public static String milliseconds2String(long milliseconds, SimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }

    /**
     * ��ʱ���ַ���תΪʱ���
     * <p>��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return ����ʱ���
     */
    public static long string2Milliseconds(String time) {
        return string2Milliseconds(time, DEFAULT_SDF);
    }

    /**
     * ��ʱ���ַ���תΪʱ���
     * <p>��ʽΪ�û��Զ���</p>
     *
     * @param time   ʱ���ַ���
     * @param format ʱ���ʽ
     * @return ����ʱ���
     */
    public static long string2Milliseconds(String time, SimpleDateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * ��ʱ���ַ���תΪDate����
     * <p>��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return Date����
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_SDF);
    }

    /**
     * ��ʱ���ַ���תΪDate����
     * <p>��ʽΪ�û��Զ���</p>
     *
     * @param time   ʱ���ַ���
     * @param format ʱ���ʽ
     * @return Date����
     */
    public static Date string2Date(String time, SimpleDateFormat format) {
        return new Date(string2Milliseconds(time, format));
    }

    /**
     * ��Date����תΪʱ���ַ���
     * <p>��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time Date����ʱ��
     * @return ʱ���ַ���
     */
    public static String date2String(Date time) {
        return date2String(time, DEFAULT_SDF);
    }

    /**
     * ��Date����תΪʱ���ַ���
     * <p>��ʽΪ�û��Զ���</p>
     *
     * @param time   Date����ʱ��
     * @param format ʱ���ʽ
     * @return ʱ���ַ���
     */
    public static String date2String(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * ��Date����תΪʱ���
     *
     * @param time Date����ʱ��
     * @return ����ʱ���
     */
    public static long date2Milliseconds(Date time) {
        return time.getTime();
    }

    /**
     * ��ʱ���תΪDate����
     *
     * @param milliseconds ����ʱ���
     * @return Date����ʱ��
     */
    public static Date milliseconds2Date(long milliseconds) {
        return new Date(milliseconds);
    }

    /**
     * ����ʱ�����λת������λ��unit��
     *
     * @param milliseconds ����ʱ���
     * @param unit         <ul>
     *                     <li>{@link TimeUnit#MSEC}: ����</li>
     *                     <li>{@link TimeUnit#SEC }: ��</li>
     *                     <li>{@link TimeUnit#MIN }: ��</li>
     *                     <li>{@link TimeUnit#HOUR}: Сʱ</li>
     *                     <li>{@link TimeUnit#DAY }: ��</li>
     *                     </ul>
     * @return unitʱ���
     */
    private static long milliseconds2Unit(long milliseconds, TimeUnit unit) {
        switch (unit) {
            case MSEC:
                return milliseconds / MSEC;
            case SEC:
                return milliseconds / SEC;
            case MIN:
                return milliseconds / MIN;
            case HOUR:
                return milliseconds / HOUR;
            case DAY:
                return milliseconds / DAY;
        }
        return -1;
    }

    /**
     * ��ȡ����ʱ����λ��unit��
     * <p>time1��time2��ʽ��Ϊyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0 ʱ���ַ���1
     * @param time1 ʱ���ַ���2
     * @param unit  <ul>
     *              <li>{@link TimeUnit#MSEC}: ����</li>
     *              <li>{@link TimeUnit#SEC }: ��</li>
     *              <li>{@link TimeUnit#MIN }: ��</li>
     *              <li>{@link TimeUnit#HOUR}: Сʱ</li>
     *              <li>{@link TimeUnit#DAY }: ��</li>
     *              </ul>
     * @return unitʱ���
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit) {
        return getIntervalTime(time0, time1, unit, DEFAULT_SDF);
    }

    /**
     * ��ȡ����ʱ����λ��unit��
     * <p>time1��time2��ʽ��Ϊformat</p>
     *
     * @param time0  ʱ���ַ���1
     * @param time1  ʱ���ַ���2
     * @param unit   <ul>
     *               <li>{@link TimeUnit#MSEC}: ����</li>
     *               <li>{@link TimeUnit#SEC }: ��</li>
     *               <li>{@link TimeUnit#MIN }: ��</li>
     *               <li>{@link TimeUnit#HOUR}: Сʱ</li>
     *               <li>{@link TimeUnit#DAY }: ��</li>
     *               </ul>
     * @param format ʱ���ʽ
     * @return unitʱ���
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit, SimpleDateFormat format) {
        return Math.abs(milliseconds2Unit(string2Milliseconds(time0, format)
                - string2Milliseconds(time1, format), unit));
    }

    /**
     * ��ȡ����ʱ����λ��unit��
     * <p>time1��time2��ΪDate����</p>
     *
     * @param time0 Date����ʱ��1
     * @param time1 Date����ʱ��2
     * @param unit  <ul>
     *              <li>{@link TimeUnit#MSEC}: ����</li>
     *              <li>{@link TimeUnit#SEC }: ��</li>
     *              <li>{@link TimeUnit#MIN }: ��</li>
     *              <li>{@link TimeUnit#HOUR}: Сʱ</li>
     *              <li>{@link TimeUnit#DAY }: ��</li>
     *              </ul>
     * @return unitʱ���
     */
    public static long getIntervalTime(Date time0, Date time1, TimeUnit unit) {
        return Math.abs(milliseconds2Unit(date2Milliseconds(time1)
                - date2Milliseconds(time0), unit));
    }

    /**
     * ��ȡ��ǰʱ��
     *
     * @return ����ʱ���
     */
    public static long getCurTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * ��ȡ��ǰʱ��
     * <p>��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @return ʱ���ַ���
     */
    public static String getCurTimeString() {
        return date2String(new Date());
    }

    /**
     * ��ȡ��ǰʱ��
     * <p>��ʽΪ�û��Զ���</p>
     *
     * @param format ʱ���ʽ
     * @return ʱ���ַ���
     */
    public static String getCurTimeString(SimpleDateFormat format) {
        return date2String(new Date(), format);
    }

    /**
     * ��ȡ��ǰʱ��
     * <p>Date����</p>
     *
     * @return Date����ʱ��
     */
    public static Date getCurTimeDate() {
        return new Date();
    }

    /**
     * ��ȡ�뵱ǰʱ��Ĳ��λ��unit��
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @param unit <ul>
     *             <li>{@link TimeUnit#MSEC}:����</li>
     *             <li>{@link TimeUnit#SEC }:��</li>
     *             <li>{@link TimeUnit#MIN }:��</li>
     *             <li>{@link TimeUnit#HOUR}:Сʱ</li>
     *             <li>{@link TimeUnit#DAY }:��</li>
     *             </ul>
     * @return unitʱ���
     */
    public static long getIntervalByNow(String time, TimeUnit unit) {
        return getIntervalByNow(time, unit, DEFAULT_SDF);
    }

    /**
     * ��ȡ�뵱ǰʱ��Ĳ��λ��unit��
     * <p>time��ʽΪformat</p>
     *
     * @param time   ʱ���ַ���
     * @param unit   <ul>
     *               <li>{@link TimeUnit#MSEC}: ����</li>
     *               <li>{@link TimeUnit#SEC }: ��</li>
     *               <li>{@link TimeUnit#MIN }: ��</li>
     *               <li>{@link TimeUnit#HOUR}: Сʱ</li>
     *               <li>{@link TimeUnit#DAY }: ��</li>
     *               </ul>
     * @param format ʱ���ʽ
     * @return unitʱ���
     */
    public static long getIntervalByNow(String time, TimeUnit unit, SimpleDateFormat format) {
        return getIntervalTime(getCurTimeString(), time, unit, format);
    }

    /**
     * ��ȡ�뵱ǰʱ��Ĳ��λ��unit��
     * <p>timeΪDate����</p>
     *
     * @param time Date����ʱ��
     * @param unit <ul>
     *             <li>{@link TimeUnit#MSEC}: ����</li>
     *             <li>{@link TimeUnit#SEC }: ��</li>
     *             <li>{@link TimeUnit#MIN }: ��</li>
     *             <li>{@link TimeUnit#HOUR}: Сʱ</li>
     *             <li>{@link TimeUnit#DAY }: ��</li>
     *             </ul>
     * @return unitʱ���
     */
    public static long getIntervalByNow(Date time, TimeUnit unit) {
        return getIntervalTime(getCurTimeDate(), time, unit);
    }

    /**
     * �ж�����
     *
     * @param year ���
     * @return {@code true}: ����<br>{@code false}: ƽ��
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }
}