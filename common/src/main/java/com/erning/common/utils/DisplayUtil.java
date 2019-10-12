package com.erning.common.utils;

import android.content.Context;

public class DisplayUtil
{

	/**
	 * dp转换px
	 */
	public static int dp2px(Context ctx, float dp)
	{
		float dpi = ctx.getResources().getDisplayMetrics().density;
		return (int) (dp * dpi + 0.5F);
	}
}
