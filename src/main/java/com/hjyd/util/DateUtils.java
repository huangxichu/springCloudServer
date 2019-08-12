package com.hjyd.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program：
 * @description：时间处理工具
 * @author：黄细初、林黎明
 * @create：2019-05-29 17:37
 */
public class DateUtils {

    public static final String FORMAT_PATTERN_SHORT = "yyyyMMdd";

    public final static String DATE_FORMAT = "yyyy-MM-dd";

    public final static String TIME_FORMAT = "HH:mm:ss";

    public final static String YEARS_MONTHS_FORMAT = "yyyyMM";

    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String FULL_FORMAT_SPEPARATE = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * @Description：获取当前时间，自定义格式
     * @Param：format：时间格式
     * @return：时间字符串
     * @Author：黄细初
     * @Date：2019/5/29
     */
    public static String getNowDateStr(String format) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    /**
     * @Description：获取当前日期，默认格式
     * @return：日期字符串
     * @Author：黄细初
     * @Date：2019/5/29
     */
    public static String getNowDateStr() {
        return getNowDateStr(DateUtils.DATE_FORMAT);
    }

    /**
     * @Description：获取当前时间，默认格式
     * @return：时间字符串
     * @Author：黄细初
     * @Date：2019/5/29
     */
    public static String getNowDateTimeStr() {
        return getNowDateStr(DATE_TIME_FORMAT);
    }

    /**
     * @Description：日期转指定格式字符串
     * @return：时间字符串
     * @Author：黄细初
     * @Date：2019/5/29
     */
    public static String date2string(Date date, String format) {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * @Description：判定是否时间格式
     * @Param：date：时间字符串
     * @return：是否
     * @Author：黄细初
     * @Date：2019/5/29
     */
    public static boolean isDate(String date) {
        Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher matcher = pattern.matcher(date);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证 日期 date1 是否大于 日期 date2 ，常用于验证是否到期。
     *
     * @return Boolean
     */
    public static Boolean timePk(Date date1, Date date2) {
        return date1.getTime() > date2.getTime();
    }

    //	    String转Date类型，日期格式为 yyyy-MM-dd
    public static Date getDate(String stringDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        date = dateFormat.parse(stringDate);
        return date;
    }

    /**
     * 获取当前星期几
     *
     * @return 返回类型：String
     * @格式为 星期一
     */
    public static String getSunday() {
        String[] sundays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int sundayIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (sundayIndex < 0) {
            sundayIndex = 0;
        }
        return sundays[sundayIndex];
    }

    /**
     * 获取该时间是当年第几周
     * 传入 Date 类型 2018-01-07
     * 返回 int 2
     *
     * @throws ParseException
     */
    public static int getWeeklyDate(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weeklyNum = cal.get(Calendar.WEEK_OF_YEAR);
        return weeklyNum;
    }

    /**
     * 获取当前系统时间
     *
     * @return String
     * @格式为 2017-05-26 17:36:38
     */
    public static String getDateTime() {
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sd.format(date);
        return dateTime;
    }

    /**
     * 获取该时间是当年第几周
     * 传入 String 类型 2018-01-07
     * 返回 int 2
     *
     * @throws ParseException
     */
    public static int getWeekString(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateString);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }


    /**
     * 获取当前日期String
     *
     * @return
     * @格式为 2018-01-05
     */
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(new Date());
    }

    /**
     * 获得当前年份
     *
     * @return
     */
    public static String getCurrentYear() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String currentDate = format.format(new Date());
        Date date;
        String sYear = "";
        try {
            date = format.parse(currentDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            sYear = String.valueOf(year);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sYear;
    }

    /**
     * 获得当前年份
     *
     * @return
     */
    public static String getCurrentMonth() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String currentDate = format.format(new Date());
        Date date;
        String sMonth = "";
        try {
            date = format.parse(currentDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH);
            sMonth = String.valueOf(month + 1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sMonth;
    }

    /**
     * 获取制定毫秒数之前的日期
     *
     * @param timeDiff
     * @return
     */
    public static String getDesignatedDate(long timeDiff) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        long nowTime = System.currentTimeMillis();
        long designTime = nowTime - timeDiff;
        return format.format(designTime);
    }


    /**
     * 日期转换成字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN_SHORT);
        return format.format(date);
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }

    public static String getTime(String text) {
        String datestr = "";
        try {
            DateFormat df = new SimpleDateFormat(text);
            datestr = df.format(new Date());
        } catch (Exception ex) {
        }
        return datestr;
    }

    /**
     * 字符串转换日期
     *
     * @param str
     * @return
     */
    public static Date stringToDate(String str) {
        //str =  " 2008-07-10 19:20:00 " 格式
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        if (!str.equals("") && str != null) {
            try {
                return format.parse(str);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date stringToDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 毫秒转换成时间
     *
     * @param mss 要转换的毫秒数
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author Linking
     */

    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;

        return days + " 天 " + hours + " 时 " + minutes + " 分 "
                + seconds + " 秒 ";

    }

    /**
     * 两个时间差转换成时间计量
     *
     * @param begin 时间段的开始
     * @param end   时间段的结束
     * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     * @author Linking
     */

    public static String formatDuring(Date begin, Date end) {

        return formatDuring(end.getTime() - begin.getTime());

    }

    /**
     * 获取某月的最后一天
     *
     * @throws
     * @Title:getLastDayOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }

    /**
     * 修正时间字符串
     *
     * @param strDate 要修正的时间字符串
     * @return
     */
    public static String getStrDate(String strDate) {
        String result = "";
        if (StringUtils.isNotEmpty(strDate)) {
            if (strDate.length() > 19) {
                result = strDate.substring(0, 19);
            } else {
                result = strDate;
            }
        } else {
            return strDate;
        }
        return result;
    }

    public static void main(String[] args) {
        getNowDateStr(DateUtils.DATE_TIME_FORMAT);
    }

}
