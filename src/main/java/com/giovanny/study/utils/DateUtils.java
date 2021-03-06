package com.giovanny.study.utils;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @packageName: com.giovanny.study.utils
 * @className: DateUtils
 * @description: 日期工具类
 * @author: YangJun
 * @date: 2020/6/10 11:21
 * @version: v1.0
 **/
public class DateUtils {
    public interface DateFormat {
        DateTimeFormatter F_YMD_0 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter F_YMDHMSS_0 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
        DateTimeFormatter F_YMDHMS_0 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter F_YMDHM_0 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    /**
     * @param time 毫秒
     * @return
     */
    public static long getThisDayZeroTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * @param timeStamp 毫秒时间戳
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String timeStamp2Str(Long timeStamp, DateTimeFormatter formatter) {
        Instant ofEpochMilli = Instant.ofEpochMilli(timeStamp);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(ofEpochMilli, ZoneId.systemDefault());
        return formatter.format(localDateTime);
    }

    /**
     * 获取date所在月份的第一天
     *
     * @param date date
     * @return date
     */
    public static Date getFirstDayOfMonth(Date date) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        int dayOfMonth = localDate.getDayOfMonth();
        LocalDate plusDays = localDate.plusDays(-dayOfMonth + 1);
        return Date.from(plusDays.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取本周一日期
     *
     * @param dateString 日期格式是2018-09-09
     * @return LocalDate
     */
    public static LocalDate getFirstDayOfWeek(String dateString) {

        LocalDate parse = LocalDate.parse(dateString);
        int value = parse.getDayOfWeek().getValue();
        return parse.plusDays(-value + 1);
    }

    /**
     * 通过开始时间和结束时间得到时间间隔内的每一天的日期
     * 包含开始时间和结束时间  升序排序
     *
     * @param startDate yyyy/MM/dd
     * @param endDate   yyyy/MM/dd
     * @return [yyyy/MM/dd, yyyy/MM/dd]
     */
    public static List<String> getDateStrByTimeInterval(String startDate, String endDate) throws ParseException {
        ArrayList<String> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(startDate));
        for (long d = cal.getTimeInMillis(); d <= sdf.parse(endDate).getTime(); d = getDayPlus(cal)) {
            list.add(sdf.format(d));
        }
        return list;
    }

    private static long getDayPlus(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        return c.getTimeInMillis();
    }


    public static String getHexCurrentTimeUnix4byte() {
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(now.toEpochSecond(ZoneOffset.of("+8")));
        //1595936611000
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(String.valueOf(currentTimeMillis));
        String miliStr = String.valueOf(currentTimeMillis).substring(10);
        String six = Long.toHexString(currentTimeMillis / 1000);
        String hexString = Long.toHexString(System.currentTimeMillis());
        System.out.println("-----------:" + hexString);

        System.out.println(Thread.currentThread().getName());
        System.out.println(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        String six2 = Long.toHexString(9294967295L);
        String zero = "";
        for (int i = 0; i < 8; i++) {
            if (six.length() < 8) {
                zero += "0";
            }
        }

        // 线程安全的Long
        LongAdder longAdder = new LongAdder();

        System.out.println("six2=" + six2);
        return zero + six;
    }

    public static void main(String[] args) {
        Boolean flag = true;
        Boolean flag1 = false;
        String flagStr = String.valueOf(flag);
        String flagStr1 = String.valueOf(flag1);
        System.out.println("sss:" + flagStr + "ssss:" + flagStr1);
    }
}
