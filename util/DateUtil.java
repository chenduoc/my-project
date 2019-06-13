package com.chen.my_project.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * 时间工具类
 * 
 * @author ChenDuochuang
 * @date 2018年11月6日
 */
public final class DataUtils {

    private DataUtils() {
    }

    /**
     * 日期格式化：yy-MM-dd HH:mm:ss
     */
    private static final String STR_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前系统时间
     * @return
     * @author ChenDuochuang
     */
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STR_DATETIME_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    /**
     * 将毫秒级时间戳转为日期字符串
     * @param stamp
     * @return
     * @author ChenDuochuang
     */
    public static String stampToDate(long stamp) {
        Date date = new Date(stamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STR_DATETIME_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    /**
     * 将日期字符串转为时间戳
     * @param dateString
     * @return
     * @throws ParseException
     * @author ChenDuochuang
     */
    public static long dateToStamp(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STR_DATETIME_FORMAT, Locale.getDefault());
        Date date = simpleDateFormat.parse(dateString);
        return date.getTime();
    }

    /**
     * 转换日期，格式：年-月-日
     * @param data
     * @return
     * @author ChenDC
     */
    public static String getDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 将时间减去特定秒数，时间格式：yyyy-MM-dd HH:mm:ss
     * @param dataTime
     * @param seconds
     * @return
     * @author ChenDC
     * @throws ParseException
     */
    public static String reduceDataTime(String dataTime, int seconds) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STR_DATETIME_FORMAT, Locale.getDefault());
        long timeStamp = simpleDateFormat.parse(dataTime).getTime();
        Date date = new Date(timeStamp - seconds * 1000);
        return simpleDateFormat.format(date);
    }

    /**
     * 判断2个时间段是否有重叠
     * @param firstStartTime
     * @param firstEndTime
     * @param secondStartTime
     * @param secondEndTime
     * @return
     * @throws ParseException
     */
    public static boolean isTimeOverLap(String firstStartTime, String firstEndTime, String secondStartTime,
            String secondEndTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date firstStartDate = simpleDateFormat.parse(firstStartTime);
        Date firstEndDate = simpleDateFormat.parse(firstEndTime);
        Date secondStartDate = simpleDateFormat.parse(secondStartTime);
        Date secondEndDate = simpleDateFormat.parse(secondEndTime);

        return firstStartDate.getTime() <= secondEndDate.getTime()
                && firstEndDate.getTime() >= secondStartDate.getTime() ? true : false;
    }

    /**
     * 判断时间戳是星期几
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public static String dayOfWeek(String dateTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = 0;
        calendar.setTime(simpleDateFormat.parse(dateTime));
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            dayOfWeek = 7;
        } else {
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        return String.valueOf(dayOfWeek);
    }

    /**
     * 判断当前时间是否在一段时间内
     * @param nowTime 时间格式： HH:mm
     * @param startTime 时间格式： HH:mm
     * @param endTime 时间格式： HH:mm
     * @return
     * @throws ParseException
     */
    public static boolean isInEffectiveTime(String nowTime, String startTime, String endTime) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date nowDate = simpleDateFormat.parse(nowTime);
        Date startDate = simpleDateFormat.parse(startTime);
        Date endDate = simpleDateFormat.parse(endTime);

        if (nowDate.getTime() == startDate.getTime() || nowDate.getTime() == endDate.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        date.setTime(nowDate);
        begin.setTime(startDate);
        end.setTime(endDate);

        return date.after(begin) && date.before(end);
    }

    /**
     * 判断当前星期是否在有效星期范围内，星期格式：1234567字符串格式
     * @param nowWeek
     * @param effectiveWeek
     * @return
     */
    public static boolean isInEffectiveWeek(String nowWeek, String effectiveWeek) {
        return (effectiveWeek.indexOf(nowWeek) == -1) ? false : true;
    }

    /**
     * 计算两个日期相差天数
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     * @author ChenDuochuang
     */
    public static int differentDays(String startTime, String endTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STR_DATETIME_FORMAT, Locale.getDefault());
        Date startDateTime = simpleDateFormat.parse(startTime);
        Date endDateTime = simpleDateFormat.parse(endTime);

        return (int) ((endDateTime.getTime() - startDateTime.getTime()) / (24 * 3600 * 1000));
    }

    /**
     * 验证日期格式是否正确
     * 
     * <pre>
     * true:正确
     * false:错误
     * </pre>
     * @param datetimeStr
     * @param dateFormatStr
     * @return
     * @author ChenDuochuang
     */
    public static boolean isValidateDate(String datetimeStr, String dateFormatStr) {
        boolean convertSuccess = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            dateFormat.setLenient(false);
            Date date = dateFormat.parse(datetimeStr);
            return dateFormat.format(date).equals(datetimeStr);
        } catch (Exception e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 获取特定日期
     * 
     * <pre>
     * field参数说明
     *      1: 表示操作年份
     *      2: 表示月份
     *      3: 表示操作周
     *      5: 表示操作天
     * amount参数说明：
     *      正数则为向后
     *      负数则为向前
     * </pre>
     * @param date
     * @param field
     * @param amount
     * @return
     * @author ChenDuochuang
     */
    public static Date getSpecificDate(Date date, int field, int amount) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(field, amount);
        return gc.getTime();
    }

    /**
     * 获取开始日期到结束日期的内的每个日期
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     * @author ChenDuochuang
     */
    public static List<String> getEverydayDate(String startDate, String endDate) throws ParseException {
        List<String> stringList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (calendar.getTime().compareTo(end) < 1) {
            stringList.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
        return stringList;
    }

    public static void test(String datetimeStr, String dateFormatStr) {

        if (!isValidateDate(datetimeStr, dateFormatStr)) {
            System.out.println("错误");
            return;
        }

        System.out.println("正常");
    }

    /**
     * 主方法
     * @param args
     * @author ChenDuochuang
     */
    public static void main(String[] args) {
        String datetime = "2019-04-2000:00:00";
        test(datetime, STR_DATETIME_FORMAT);
    }
}
