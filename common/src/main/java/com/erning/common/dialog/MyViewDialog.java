package com.erning.common.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;

import androidx.annotation.NonNull;
import com.erning.common.R;

/**
 * Created by abs on 2018/1/15.
 */

public class MyViewDialog extends Dialog{
    public Object tag = null;
    public int what = -1;

    @SuppressLint("ResourceType")
    public MyViewDialog(@NonNull Context context, View view) {
        super(context, R.style.ActionSheetDialogStyle);
        if (view == null)
            return;
        this.setContentView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setCanceledOnTouchOutside(true);
    }

    @SuppressLint("ResourceType")
    public MyViewDialog(@NonNull Context context,int view) {
        super(context, R.style.ActionSheetDialogStyle);
        if (view == 0)
            return;
        this.setContentView(getLayoutInflater().inflate(view,null,false),new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setCanceledOnTouchOutside(true);
    }

    public void setView2(View view){
        this.setContentView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setCanceledOnTouchOutside(true);
    }
    public void setView2(int view){
        setView2(getLayoutInflater().inflate(view,null,false));
    }
}