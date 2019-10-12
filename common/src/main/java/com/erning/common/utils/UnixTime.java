package com.erning.common.utils;

/**
 * Created by 二宁 on 2017/11/23.
 */

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UnixTime {

    @SuppressLint("SimpleDateFormat")
    public static String unixTime2Simplese(long unixtime, String format) {
        if (unixtime == 0) {
            return "";
        }
        Long timestamp = unixtime * 1000;
        return new SimpleDateFormat(format).format(new Date(timestamp));
    }

    @SuppressLint("SimpleDateFormat")
    public static long simpleTime2Unix(String simpletime, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(simpletime);
        } catch (ParseException e) {
            return -1;
        }
        return date.getTime() / 1000;
    }

    public static String getStrCurrentUnixTime() {
        return System.currentTimeMillis() / 1000 + "";
    }

    public static long getIntCurrentUnixTime() {
        return System.currentTimeMillis() / 1000;
    }

    public static String getStrCurrentSimleTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getStrCurrentSimleTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getImgNameTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
}