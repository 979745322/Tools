package com.erning.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 在ScrollView中能显示全的ListView
 *
 * 没有详细测试过
 *
 * Created by 二宁 on 2017/11/17.
 */

public class ListViewForScrollView extends ListView {

    public ListViewForScrollView(Context context) {
        super(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 只需要重写这个方法即可
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}