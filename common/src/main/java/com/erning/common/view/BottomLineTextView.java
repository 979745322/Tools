package com.erning.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.erning.common.utils.DisplayUtil;

/**
 * Created by 二宁 on 2018/6/8.
 */

public class BottomLineTextView extends TextView {
    private int w,h;
    private int lineWidth;
    private int lineColor = Color.argb(255,255,0,0);
    private Paint paint = new Paint();

    public BottomLineTextView(Context context) {
        super(context);
        init();
    }

    public BottomLineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BottomLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        lineWidth = DisplayUtil.dp2px(getContext(),1);
        paint.setColor(lineColor);
        paint.setStrokeWidth(lineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0,h-lineWidth,w,h-lineWidth,paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }
}
