package com.erning.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 显示或隐藏软键盘
 * Created by abs on 2018/1/9.
 */

public class ShowKeyBoardUtil {
    public static void hideSoftKeyboard(EditText editText, Context context) {
        if (editText != null && context != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }
    public static void showSoftKeyboard(EditText editText, Context context) {
        if (editText != null && context != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, 0);
        }
    }
}
