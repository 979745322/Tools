package com.erning.common.sharedperference;

import android.content.Context;

import com.erning.common.BuildConfig;
import com.erning.common.R;

/**
 * Created by abs on 2018/2/8.
 */

public class CPPUtils {
    static {
        System.loadLibrary("native-lib");
    }
    public static native int getD4();

    public static String getSPKey(Context context){
//        LogUtils.d("密钥",d1()+d2()+d3(context)+d4());
        return d1()+d2()+d3(context)+d4();
    }


    private static String d1(){
        return BuildConfig.d1;
    }
    private static String d2(){
        return "Ning";
    }
    private static String d3(Context context){
        return context.getResources().getString(R.string.d3);
    }
    private static String d4(){
        return String.valueOf(getD4());
    }
}
