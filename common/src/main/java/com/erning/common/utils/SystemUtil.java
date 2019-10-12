package com.erning.common.utils;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;

import java.util.Locale;

import static android.content.Context.BATTERY_SERVICE;

/**
 * 系统工具类
 * Created by zhuwentao on 2016-07-18.
 */
public class SystemUtil {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 点亮
     */
    public static String getSystemPower(Context context){
        int battery = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            BatteryManager batteryManager = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
            battery = batteryManager != null ? batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) : 0;
        }

        if (battery == 0)
            return "N/A";
        return String.valueOf(battery);
    }
}