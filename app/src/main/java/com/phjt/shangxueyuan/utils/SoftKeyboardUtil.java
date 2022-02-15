package com.phjt.shangxueyuan.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.phsxy.utils.LogUtils;

/**
 * @author : austen
 * company    : 普华
 * date       : 2019/4/25 10:03
 * description:
 */
public class SoftKeyboardUtil {

    /**
     * 隐藏软件盘
     *
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        View v = activity.getWindow().peekDecorView();
        if (v != null) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * fragment 隐藏虚拟键盘
     * @param v
     */
    public static void setHideKeyboard(View v) {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) ) {
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );
        }
    }
    /**
     * 显示软键盘
     *
     * @param view 获取焦点的view
     */
    public static void showKeyboard(EditText view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }


    /**
     * 根据传入进来的View(这个View 一定是一个ViewGroup)
     * 然后递归查找View 并且判断View是否是EditText
     *
     * @param view 布局
     */
    public static void recursiveFindEditAndSetOnTouchListener(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                if (v.getContext() instanceof Activity) {
                    SoftKeyboardUtil.hideSoftKeyboard((Activity) v.getContext());
                    view.clearFocus();
                }
                return false;
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                recursiveFindEditAndSetOnTouchListener(innerView);
            }
        }
    }

    public static void hideKeyboardForAll(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //isOpen若返回true，则表示输入法打开
        boolean isOpen = imm.isActive();
        if (isOpen) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
