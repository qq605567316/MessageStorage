package com.tt.msg.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName DateString
 * @Description 时间工具类
 * @Author tanjiang
 * @CreateTime 2019/3/8 20:05
 * @Version 1.0
 **/

public class DateString {
    Date _date;
    static String fullPattern = "yyyy-MM-dd HH:mm:ss";
    static String defaultPattern = "yyyy-MM-dd HH:mm";
    static String otaPattern = "yyyyMMdd HH:mm:ss";
    static String simpPatn = "yyyy-MM-dd";
    static String smallPatn = "yyyyMMdd";
    static String smallMonth = "yyyyMM";
    static String smalllestPatn = "yyMMdd";
    static String dayInWeek = "EEE";
    static String loginAllowTimePattern = "HH";
    static String filePattern = "yyyyMMddHHmmss";

    public DateString(Date _date) {
        this._date = _date;
    }

    public static String getString(Date time, String pattern) {
        SimpleDateFormat sp = new SimpleDateFormat(pattern);
        return sp.format(time);
    }

    public static String getFullString(Date time) {
        SimpleDateFormat sp = new SimpleDateFormat(fullPattern);
        return sp.format(time);
    }

    public static String getDefaultString(Date time) {
        SimpleDateFormat sp = new SimpleDateFormat(defaultPattern);
        return sp.format(time);
    }

    public static String getFileDefaultString(Date time) {
        SimpleDateFormat sp = new SimpleDateFormat(filePattern);
        return sp.format(time);
    }

    public static String getMyString(Date time) {
        SimpleDateFormat sp = new SimpleDateFormat(otaPattern);
        return sp.format(time);
    }

    public static String getSimpString(Date time) {
        SimpleDateFormat sp = new SimpleDateFormat(simpPatn);
        return sp.format(time);
    }

    public static String getSmallMonth(Date time) {
        SimpleDateFormat sp = new SimpleDateFormat(smallMonth);
        return sp.format(time);
    }

    public static java.sql.Date getSqlDate(String date) {
        Date newDate = getDate(date);
        if (newDate != null) {
            return new java.sql.Date(newDate.getTime());
        } else {
            return null;
        }
    }

    private static String validatorDate(String str) {
        if (str.length() <= 10) {
            return str;
        }
        String newStr = str.substring(0, 5);
        str = str.substring(5);
        if (str.indexOf("-") == 1) {
            newStr += "0" + str.substring(0, 2);
            str = str.substring(2);
        } else {
            newStr += str.substring(0, 3);
            str = str.substring(3);
        }

        if (str.indexOf(" ") == 1) {
            newStr += "0" + str.substring(0, 2);
            str = str.substring(2);
        } else {
            newStr += str.substring(0, 3);
            str = str.substring(3);
        }

        newStr += str;

        return newStr;
    }

    public static Date getDate(String str) {
        str = validatorDate(str);
        Calendar ca = Calendar.getInstance();
        //System.out.println(str);
        if (str.length() >= 19) {
            String year = str.substring(0, 4);
            String mon = str.substring(5, 7);
            String day = str.substring(8, 10);
            String hh = str.substring(11, 13);
            String mi = str.substring(14, 16);
            String ss = str.substring(17, 19);
            ca.set(Integer.parseInt(year), Integer.parseInt(mon) - 1, Integer.parseInt(day),
                    Integer.parseInt(hh), Integer.parseInt(mi), Integer.parseInt(ss));
        } else if (str.length() >= 10) {
            String year = str.substring(0, 4);
            String mon = str.substring(5, 7);
            String day = str.substring(8, 10);
            ca.set(Integer.parseInt(year), Integer.parseInt(mon) - 1, Integer.parseInt(day));
        } else {
            return null;
        }
        //System.out.println(ca.getTime());
        return ca.getTime();

    }

    public static Date addByDay(String begin, int len) {
        Date temp = null;
        if (begin.length() >= 10) {
            temp = getDate(begin);
        }
        Calendar ca = Calendar.getInstance();
        if (temp != null) {
            ca.setTime(temp);
            ca.add(Calendar.DATE, len);
        }
        return ca.getTime();
    }

    public static Date getFirstDate(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getLastDate(Calendar cal) {
        int day = cal.getMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static long getUTC(Date time) {
        return time.getTime() / 1000;
    }


    public static String getSmallString(Date time) {
        SimpleDateFormat sp = new SimpleDateFormat(smallPatn);
        return sp.format(time);
    }

    public static String getSmalllestString(Date time) {
        SimpleDateFormat sp = new SimpleDateFormat(smalllestPatn);
        return sp.format(time);
    }

    public static Date smallStringToDate(String str) {
        //str = validatorDate( str ) ;
        Calendar ca = Calendar.getInstance();
        //System.out.println(str);
        if (str.length() >= 14) {
            String year = str.substring(0, 4);
            String mon = str.substring(4, 6);
            String day = str.substring(6, 8);
            String hh = str.substring(8, 10);
            String mi = str.substring(10, 12);
            String ss = str.substring(12, 14);
            ca.set(Integer.parseInt(year), Integer.parseInt(mon) - 1, Integer.parseInt(day),
                    Integer.parseInt(hh), Integer.parseInt(mi), Integer.parseInt(ss));
        } else if (str.length() >= 8) {
            String year = str.substring(0, 4);
            String mon = str.substring(4, 6);
            String day = str.substring(6, 8);
            ca.set(Integer.parseInt(year), Integer.parseInt(mon) - 1, Integer.parseInt(day));
        } else
            return null;
        //System.out.println(ca.getTime());
        return ca.getTime();

    }

    public static String getDayInWeek(Date time) {
        SimpleDateFormat sp = new SimpleDateFormat(dayInWeek);
        return sp.format(time);
    }

    public static Timestamp nowTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Date nowDate() {
        return new Date();
    }

    public static void main(String[] args) {
        System.out.println(DateString.getDayInWeek(new Date()));
    }

    public static String getLoginAllowTime(Date time) {
        SimpleDateFormat sp = new SimpleDateFormat(loginAllowTimePattern);
        return sp.format(time);
    }

    /**
     * 将字符串转换成指定格式的日期.
     *
     * @param str
     *            日期字符串.
     * @param dateFormat
     *            日期格式. 如果为空，默认为:yyyy-MM-dd HH:mm:ss.
     * @return
     * @author liboc
     */
    public static Date strToDate(final String str, String dateFormat) {
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        try {
            if (dateFormat == null || dateFormat.length() == 0) {
                dateFormat = "yyyy-MM-dd HH:mm:ss";
            }
            DateFormat fmt = new SimpleDateFormat(dateFormat);

            return fmt.parse(str.trim());
        } catch (Exception ex) {
            /*
             * log.error("将字符串(" + str + ")转换成指定格式(" + dateFormat +
             * ")的日期时失败！错误原因：" + ex.getMessage());
             */
            return null;
        }
    }

    /**
     * 将字符串转换成指定格式的日期字符串.
     *
     * @param str 日期字符串.
     * @return 字符串
     * @author TANGYU
     * 2013-04-04
     */
    public static String Str2Date(String str) {
        String result;
        if (str.length() >= 14) {
            String year = str.substring(0, 4);
            String mon = str.substring(4, 6);
            String day = str.substring(6, 8);
            String hh = str.substring(8, 10);
            String mi = str.substring(10, 12);
            String ss = str.substring(12, 14);
            result = year + "-" + mon + "-" + day + " " + hh + ":" + mi + ":" + ss;
        } else if (str.length() >= 8) {
            String year = str.substring(0, 4);
            String mon = str.substring(4, 6);
            String day = str.substring(6, 8);
            result = year + "-" + mon + "-" + day;
        } else {
            return null;
        }
        //System.out.println(ca.getTime());
        return result;

    }

    /**
     * 获取当月第一天
     * @param month 月份
     * @param dateFormat 返回日期格式
     * @return 默认YYYYMMDD
     */
    public static String getMonthFirstDay(String month, String dateFormat) {
        if (StringUtils.isEmpty(dateFormat)) {
            dateFormat = "yyyyMMdd";
        }
        String monthDateFormat = "yyyyMM";
        month = month.replaceAll("-", "");
        Date monthDate = DateString.strToDate(month, monthDateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(monthDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date date = calendar.getTime();
        return DateString.getString(date, dateFormat);
    }

    /**
     * 获取当月最后一天
     * @param month 月份
     * @param dateFormat 返回日期格式
     * @return 默认YYYYMMDD
     */
    public static String getMonthLastDay(String month, String dateFormat) {
        if (StringUtils.isEmpty(dateFormat)) {
            dateFormat = "yyyyMMdd";
        }
        String monthDateFormat = "yyyyMM";
        month = month.replaceAll("-", "");
        Date monthDate = DateString.strToDate(month, monthDateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(monthDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = calendar.getTime();
        return DateString.getString(date, dateFormat);
    }

    /**
     * 获取天的时间段
     * @param startDay
     * @param endDay
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static List<String> getDayList(String startDay, String endDay, String pattern)
            throws ParseException {
        List<String> list = new ArrayList<String>();
        String curr = startDay;
        while (curr.compareTo(endDay) < 1) {
            list.add(curr);
            Date date = DateUtils.addDays(DateUtils.parseDate(curr, new String[] {pattern}), 1);
            curr = DateFormatUtils.format(date, pattern);
        }
        return list;
    }

    /**
     * 获取月份的时间段
     * @param startMonth
     * @param endMonth
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static List<String> getMonthList(String startMonth, String endMonth, String pattern)
            throws ParseException {
        List<String> list = new ArrayList<String>();
        String curr = startMonth;
        while (curr.compareTo(endMonth) < 1) {
            list.add(curr);
            Date date = DateUtils.addMonths(DateUtils.parseDate(curr, new String[] {pattern}), 1);
            curr = DateFormatUtils.format(date, pattern);
        }
        return list;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static Timestamp getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strTime = format.format(today);
        strTime += " 00:00:00";
        Timestamp result = Timestamp.valueOf(strTime);
        return result;
    }

    /**
     * 获取过去第几天的日期
     * @param past
     * @return
     */
    public static Timestamp getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(simpPatn);
        String strTime = format.format(today);
        strTime += " 00:00:00";
        Timestamp result = Timestamp.valueOf(strTime);
        return result;
    }

    /**
     * 获取过去第几天的日期
     * @param past
     * @return
     */
    public static String getPast(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(simpPatn);
        return format.format(today);
    }

    /**
     * 获取过去最近num天的data的String
     * @param num
     * @return
     */
    public static ArrayList<String> getX(int num){
        ArrayList<String> list = new ArrayList<String>();
        for (int i = num-1; i >=0; i--) {
            list.add(getPast(i));
        }
        return list;
    }

    /**
     * 获取过去 任意天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static ArrayList<Timestamp> getPastDays(int intervals) {
        ArrayList<Timestamp> pastDaysList = new ArrayList<Timestamp>();
        for (int i = 0; i <intervals; i++) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取未来 任意天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static ArrayList<Timestamp> getFetureDays(int intervals) {
        ArrayList<Timestamp> feturetDaysList = new ArrayList<Timestamp>();
        for (int i = 0; i <intervals; i++) {
            feturetDaysList.add(getFetureDate(i));
        }
        return feturetDaysList;
    }

    /**
     * 获取最近intervals天 的开始与结束时间段
     * @param intervals
     * @return
     */
    public static ArrayList<Map<String,Object>> getDays(int intervals){
        ArrayList<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
        for (int i = intervals-1; i >= 0; i++) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("startDate",getPastDate(i));
            map.put("endDate",getPastDate(i-1));
            lists.add(map);
        }
        return lists;
    }
}
