package com.example.csust_hot_wall.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String[] simple_data_format_values = {"yyyy年MM月dd日 HH点mm分ss秒","yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"};
    public static SimpleDateFormat simple_data_format = new SimpleDateFormat(simple_data_format_values[0]);

    public static String getCurrentDateOfString(){
        return simple_data_format.format(new Date());
    }

    public static String DateToString(Date data){
        return simple_data_format.format(data);
    }

    public static String DateToString(Date data,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(data);
    }

    public static String DateToString(Long time,String format){
        return DateToString(new Date(time),format);
    }

    /**
     * 将字符串解析为Date格式，默认使用第一种模式，如果解析失败，循环整个simple_data_format_values找到合适的解析格式
     * @param date String:yyyy年MM月dd日 HH点mm分ss秒
     * @return 字符串表示日期的Date格式
     * @throws ParseException
     */
    public static Date StringToDate(String date) throws ParseException {
        SimpleDateFormat sdf = simple_data_format;
        for (int i=1;i<simple_data_format_values.length;i++){
            try {
                return sdf.parse(date);
            }catch (Exception e){
                sdf = new SimpleDateFormat(simple_data_format_values[i]);
            }
        }
        return sdf.parse(date);
    }

    public static Date StringToDate(String date,String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }

}
