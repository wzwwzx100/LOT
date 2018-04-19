package com.mogu.LOT.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FE on 2016/6/28.
 */
public class DateUtil {

    public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_YY_MM_DD = "yyyy-MM-dd";

    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";

    public static Date addDay(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);// 让日期加1
        calendar.get(Calendar.DATE);// 加1之后的日期Top
        return calendar.getTime();
    }

    /**
     * 返回两个日期相差的天数
     *
     * @param fDate
     * @param oDate
     * @return
     */
    public static long daySub(Date fDate, Date oDate) {
        long to = fDate.getTime();
        long from = oDate.getTime();
        long dayTime = 1000 * 60 * 60 * 24;
        return (to - from) / (dayTime);
    }


    /**
     * 获取月份
     * 注意：最大间隔一年
     *
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getMonthSpace(Date date1, Date date2)
            throws ParseException {
        int result = 0;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year = Math.abs(c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR));

        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        if (year == 0) return result == 0 ? 1 : Math.abs(result);
        if (result < 0) result += 12;
        else result -= 12;
        return result == 0 ? 1 : Math.abs(result);
    }


    public static Date nexMonth(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.add(Calendar.MONTH, +1);
        return c1.getTime();
//        System.out.println(formatDate(c1.getTime()));
    }

    public static Date nextYear(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.add(Calendar.YEAR, +1);
        return c1.getTime();
//        System.out.println(formatDate(c1.getTime()));
    }


    /**
     * 返回两个日期相差的秒数
     *
     * @param fDate
     * @param oDate
     * @return
     */
    public static long daySubToSecond(Date fDate, Date oDate) {
        long to = fDate.getTime();
        long from = oDate.getTime();
        long dayTime = 1000;
        return (to - from) / (dayTime);
    }

    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * @param _date
     * @return
     */
    public static String formatDate(Date _date) {
        if (_date == null) {
            return "1970-01-01";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(_date);
    }


    /**
     * @param _date
     * @return
     */
    public static String formatDate(Date _date, String par) {
        if (_date == null) {
            return "1970-01-01";
        }
        return new SimpleDateFormat(par).format(_date);
    }

    /**
     * 下个小时
     * @return
     */
    public static String nextHour(Date _date,String par){
        Calendar cl = Calendar.getInstance();
        cl.setTime(_date);
        cl.set(Calendar.HOUR_OF_DAY,cl.get(Calendar.HOUR_OF_DAY)+1);
        return formatDate(cl.getTime(),par);
    }


    /**
     * @param _date
     * @return
     */
    public static String formatSimple(Date _date, String patten) {
        if (_date == null || StringUtils.isBlank(patten)) {
            throw new NullPointerException();
        }
        return new SimpleDateFormat(patten).format(_date);
    }


    /**
     * 获取两个时间的时间查 如1天2小时30分钟
     */
    public static Map<String, Integer> subDateMap(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new NullPointerException("时间不能为空！");
        }
        Map<String, Integer> dateMap = new HashMap<String, Integer>();
        long dayTimes = 1000 * 24 * 60 * 60;
        long hourTimes = 1000 * 60 * 60;
        long mineTimes = 1000 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少天
        int day = (int) (diff / dayTimes);
        // 计算差多少小时
        int hour = (int) (diff % dayTimes / hourTimes);
        // 计算差多少分钟
        int min = (int) (diff % dayTimes % hourTimes / mineTimes);
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        dateMap.put("days", day);
        dateMap.put("hour", hour);
        dateMap.put("min", min);
        dateMap.put("compareTo", endDate.compareTo(startDate));
        return dateMap;
    }

    /**
     * 格式化时间戳(秒数)，并按指定格式返回
     *
     * @param timestamp
     * @param pattern
     * @return
     */
    public static final String formatTimeStamp(long timestamp, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(timestamp * 1000));
    }

    //    public static void main(String[] args) throws Exception {
//        System.out.println(subDateMap(new SimpleDateFormat("yyyy-MM-dd").parse("2016-08-05"), new SimpleDateFormat("yyyy-MM-dd").parse("2016-08-05")));
//    }
    public static final Date stringToDate(String s,String  pattern) {
        if (StringUtils.isEmpty(pattern))pattern = "yyyy-MM-dd HH:mm:ss";
        //为空处理
        if (null == s || StringUtils.isBlank(s)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
//            System.out.println("发生异常：" + e.getMessage());
        }
        return null;
    }



    public static final Date stringToDateWithoutSecond(String s) {
        //为空处理
        if (null == s || StringUtils.isBlank(s)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
//            System.out.println("发生异常：" + e.getMessage());
        }
        return null;
    }


    public static  long getDayStart(Date time) throws ParseException {
        long dayStart = parseDateToLong(formatTimeStamp(time.getTime() / 1000, "yyyy-MM-dd"), "yyyy-MM-dd");
        return dayStart;
    }

    public static  Date getDayStart1(Date time) {
        long dayStart = 0;
        try {
            dayStart = parseDateToLong(formatTimeStamp(time.getTime() / 1000, "yyyy-MM-dd"), "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(dayStart * 1000);
    }

    public static final long parseDateToLong(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date dt = sdf.parse(dateStr);
        return dt.getTime() / 1000;
    }


    /**
     * 当前月开始时间
     */
    public static Date monthOfStartDay(){
        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        cl.set(Calendar.DAY_OF_MONTH,1);
        cl.set(Calendar.HOUR_OF_DAY,0);
        cl.set(Calendar.MINUTE,0);
        cl.set(Calendar.SECOND,0);

        return cl.getTime();
    }


    /**
     * 月末时间  配合monthOfStartDay使用
     * @param st
     * @return
     */
    public static Date monthOfEdDay(Date st){
        Calendar cl = Calendar.getInstance();
        cl.setTime(st);
        cl.set(Calendar.MONTH,cl.get(Calendar.MONTH) + 1);
        return new Date(cl.getTime().getTime() - 1000);
    }
}
