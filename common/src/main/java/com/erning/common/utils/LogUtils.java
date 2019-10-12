package com.erning.common.utils;

import android.util.Log;

import com.erning.common.BuildConfig;

/**
 * Created by abs on 2018/1/19.
 */

public class LogUtils {
    public static void d(String TAG,String s){
        if(BuildConfig.DEBUG && s!=null)
            Log.d(TAG,s);
    }
    public static void i(String TAG,String s){
        Log.i(TAG,s);
    }
    public static void w(String TAG,String s){
        Log.w(TAG,s);
    }
    public static void e(String TAG,String s){
        Log.e(TAG,s);
    }
    public static void v(String TAG,String s){
        Log.v(TAG,s);
    }
}
