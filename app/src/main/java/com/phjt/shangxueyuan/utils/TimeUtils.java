package com.phjt.shangxueyuan.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: gengyan
 * date:    2020/5/10 15:12
 * company: 普华集团
 * description: 描述
 */
public class TimeUtils {

    /**
     * 获取观看时长百分比
     *
     * @param courseWatchDuration 观看时长
     * @param sumTimeLong         总时长
     * @return 观看百分比
     */
    public static String getStudyLook(Long courseWatchDuration, Long sumTimeLong) {
        String percent;
        if (courseWatchDuration >= sumTimeLong) {
            percent = "100";
        } else if (sumTimeLong > 0) {
            // 设置精确到小数点后0位
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(0);
            percent = numberFormat.format((float) courseWatchDuration / (float) sumTimeLong * 100);
        } else {
            percent = "0";
        }
        return percent;
    }

    /**
     * 获取当前时间 精确到天
     */
    public static String getStrToday() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(Calendar.getInstance().getTime());
    }

    public static String countDownDay() {
        String lastDay = "";
        // 时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 现在的时间
        Date now = new Date();
        // 计算某一月份的最大天数
        Calendar cal = Calendar.getInstance();
        // Date转化为Calendar
        cal.setTime(now);
        // 一月后的1天前
        cal.add(java.util.Calendar.DAY_OF_MONTH, 30);
        //服务开始日期：//当前时间
        df.format(now);
        //服务结束日期：//一个月后的时间
        lastDay = df.format(cal.getTime());
        return lastDay;
    }

    public static String longToDateYMD(long time) {
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(date);
    }

    public static String longToMM(long time) {
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sd.format(date);
    }
    public static String longToDateMDHM(long time) {
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sd.format(date);

    }
}
