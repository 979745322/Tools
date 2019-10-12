package com.erning.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

public class ImageOriginPager extends ViewPager {

    public ImageOriginPager(Context context) {
        super(context);
    }

    public ImageOriginPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            return super.onTouchEvent(event);
        } catch (IllegalArgumentException  e) {
            Log.e( "ImageOriginPager-error" , "IllegalArgumentException 错误被活捉了！");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException  e) {
            Log.e( "ImageOriginPager-error" , "IllegalArgumentException 错误被活捉了！");
            e.printStackTrace();
        }
        return false ;
    }
}