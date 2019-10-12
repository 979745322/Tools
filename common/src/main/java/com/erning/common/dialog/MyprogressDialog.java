package com.erning.common.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import com.erning.common.R;

/**
 * Created by 二宁 on 2017/11/23.
 */

public class MyprogressDialog extends Dialog {
    @SuppressLint("ResourceType")
    public MyprogressDialog(Context context) {
        super(context, R.style.progress_dialog);
        this.setContentView(R.layout.dialog);
        this.setCancelable(false);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
