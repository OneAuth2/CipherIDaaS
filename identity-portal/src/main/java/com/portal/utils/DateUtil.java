package com.portal.utils;

import com.portal.enums.TimeTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // format.setLenient(false);
        Date date1 = format.parse("2019-03-20 18:30:00");
        Date date2 = new Date();
        // 计算差值，分钟数
        long minutes=(date2.getTime()-date1.getTime())/(1000*60)*60;
        System.out.println(minutes);
    }

    public static long getSecond(Date startTime,Date endTime){
         long second=(startTime.getTime()-endTime.getTime())/(1000*60)*60;
         return  second;
    }

    public static int calLastedTime(Date startTime,Date endTime) {
        long a = System.currentTimeMillis();
        long b = endTime.getTime();
        int c = (int)((a - b) / 1000);
        return c;
    }

    /**
     * 计算时间差，以天数为单位。如：2018-08-08 和 2018-08-05 相差3天
     *
     * */
    public static int getDistanceTime(Date startTime, Date endTime) {
        int days = 0;
        long time1 = startTime.getTime();
        long time2 = endTime.getTime();

        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        days = (int) (diff / (24 * 60 * 60 * 1000));
        return days;
    }

    /**
     * 计算时间差，以小时为单位。如：2018-08-08 和 2018-08-07 相差24h
     *
     * */
    public double getDistanceTime2(Date startTime, Date endTime) {
        double hour = 0;
        long time1 = startTime.getTime();
        long time2 = endTime.getTime();

        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        hour = (diff / (60 * 60 * 1000));
        return hour;
    }

    public static Long getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return min;
    }

    /**
     * 计算两个时间之间的时间间隔
     *
     * @param date1
     * @param date2
     * @param timeTypeEnum
     */
    public static double calTimeInterval(Date date1, Date date2, TimeTypeEnum timeTypeEnum) {
        long timeInterval = date1.getTime() - date2.getTime();
        timeInterval = Math.abs(timeInterval); // 区绝对值

        double result = 0;
        if (timeTypeEnum == TimeTypeEnum.SECOND) {// 秒
            result = timeInterval * 1.0 / 1000;
        } else if (timeTypeEnum == TimeTypeEnum.MINUTE) {// 分
            result = timeInterval * 1.0 / (1000 * 60);
        } else if (timeTypeEnum == TimeTypeEnum.HOUR) {// 小时
            result = timeInterval * 1.0 / (1000 * 60 * 60);
        }

        return result;
    }
}