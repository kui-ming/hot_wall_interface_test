package com.example.csust_hot_wall.tools;

import java.util.Calendar;

public class CalendarUtil {

    /**
     * 如果同年同月同日则返回true
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static boolean compareByYearAndMonthAndDay(Calendar calendar1,Calendar calendar2){
        boolean[] booleans = compareOfYearAndMonthAndDay(calendar1, calendar2);
        return booleans[0]&&booleans[1]&&booleans[2];
    }

    /**
     * 判断是否是同一年同一月同一天
     * @param calendar1 第一个日历
     * @param calendar2 第二个日历
     * @return boolean[3] [0]为年,[1]为月,[2]为日
     */
    public static boolean[] compareOfYearAndMonthAndDay(Calendar calendar1,Calendar calendar2){
        return new boolean[]{
                calendar1.get(Calendar.YEAR)==calendar2.get(Calendar.YEAR),
                calendar1.get(Calendar.MONTH)==calendar2.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH)==calendar2.get(Calendar.DAY_OF_MONTH),
        };
    }

    /**
     * 判断是否是同一年
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static boolean compareOfYear(Calendar calendar1,Calendar calendar2){
        return calendar1.get(Calendar.YEAR)==calendar2.get(Calendar.YEAR);
    }

    /**
     * 判断是否是同一月
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static boolean compareOfMonth(Calendar calendar1,Calendar calendar2){
        return calendar1.get(Calendar.MONTH)==calendar2.get(Calendar.MONTH);
    }

    /**
     * 判断是否是同一月
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static boolean compareOfDay(Calendar calendar1,Calendar calendar2){
        return calendar1.get(Calendar.DAY_OF_MONTH)==calendar2.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 计算两个日期之间的实际天数，使用毫秒计算判断
     * @param start 开始日期
     * @param end 结束日期
     * @return
     */
    public static int calcActualDays(Calendar start,Calendar end){
        long time = end.getTimeInMillis() - start.getTimeInMillis();
        return (int) (time/(1000*60*60*24));
    }

    /**
     * 计算两个日期之间的天数
     * @param start
     * @param end
     * @return
     */
    public static int calcDays(Calendar start,Calendar end){
        // 将两个日期的顺序进行调整
        Calendar temp = start;
        start = start.after(end) ? end : start;
        end = temp.after(end) ? temp : end;
        // 赋值
        int startDay = start.get(Calendar.DAY_OF_YEAR);
        int endDay = end.get(Calendar.DAY_OF_YEAR);
        int day = endDay-startDay;
        // 判断年
        if (!compareOfYear(start,end)){ // 非同年
            for (int i = start.get(Calendar.YEAR); i < end.get(Calendar.YEAR); i++) {
                day += (i%4==0 && i%100!=0) || i%400==0 ? 366 : 365; // 是闰年加366，不是加365
            }
        }
        return day;
    }

    /**
     * 初始化一个日期对象
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @return
     */
    public static Calendar initCalendar(int year,int month,int day,int hour,int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day,hour,minute);
        return calendar;
    }



}
