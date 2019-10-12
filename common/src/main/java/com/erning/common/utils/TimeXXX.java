package com.erning.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间的转换
 * 精确到分钟
 * Created by abs on 2018/1/11.
 */

public class TimeXXX {
    /**
     * 显示 多长时间之前或多长时间之后
     * @param time
     * @param pattern
     * @return
     */
    public static String beforeTime(String time,String pattern){
        StringBuilder sb = new StringBuilder();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            long target = sdf.parse(time).getTime();
            long now = new Date().getTime();

            long res = Math.abs(target - now) / 1000 /60;//分钟
            if (res >= 365*24*60){
                //年
                sb.append(res/(365*24*60));
                sb.append("年");
            }else if (res >= 30*24*60){
                //月
                sb.append(res/(30*24*60));
                sb.append("月");
            }else if (res >= 24*60){
                //天
                sb.append(res/(24*60));
                sb.append("天");
            }else if (res >= 60){
                //小时
                sb.append(res/60);
                sb.append("小时");
            }else{
                //分钟
                sb.append(res);
                sb.append("分钟");
            }


            if (target > now){
                //后
                sb.append("后");
            }else if(target < now){
                //前
                sb.append("前");
            }else{
                //相等
                sb.append("前");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 当地时间 ---> UTC时间
     * @return
     */
    public static String Local2UTC(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = sdf.format(new Date());
        return gmtTime;
    }

    /**
     * UTC时间 ---> 当地时间
     * @param utcTime   UTC时间
     * @return
     */
    private static SimpleDateFormat utcFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//UTC时间格式
    public static String utc2Local(String utcTime) {
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        utcFormater.setTimeZone(TimeZone.getDefault());
        return utcFormater.format(gpsUTCDate.getTime());
    }

    /**
     * 计算两个时间的差值
     * @retrun 毫秒的差值
     */
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static long timeCalculate(String time1,String time2){
        try {
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            return date1.getTime() - date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}