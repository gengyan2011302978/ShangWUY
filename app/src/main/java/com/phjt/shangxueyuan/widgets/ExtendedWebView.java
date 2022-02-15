package com.phjt.shangxueyuan.widgets;

/**
 * @author: Created by shaopengfei
 * date: 2021/1/5 17:13
 * company: 普华集团
 * description: 描述
 */

import android.app.Activity;
import android.content.Context;
import androidx.core.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

import me.jessyan.autosize.AutoSize;

/**
 * 解决viewPager嵌套webView横向滚动问题
 */
public class ExtendedWebView extends WebView {

    private boolean isScrollX = false;

    public ExtendedWebView(Context context) {
        super(context);
        this.getSettings().setTextZoom(100);
    }

    public ExtendedWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (MotionEventCompat.getPointerCount(event) == 1) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isScrollX = false;
                        //事件由webview处理
                        getParent().getParent()
                                .requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //嵌套Viewpager时
                        getParent().getParent()
                                .requestDisallowInterceptTouchEvent(!isScrollX);
                        break;
                    default:
                        getParent().getParent()
                                .requestDisallowInterceptTouchEvent(false);
                }
            } else {
                //使webview可以双指缩放（前提是webview必须开启缩放功能，并且加载的网页也支持缩放）
                getParent().getParent().
                        requestDisallowInterceptTouchEvent(true);
            }
        } catch (Exception ignored) {

        }
        return super.onTouchEvent(event);
    }

    /**
     * 当webview滚动到边界时执行
     */
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        isScrollX = clampedX;
    }

    /**
     * 解决 WebView初始化的时候会还原 density的值导致适配失效
     */
    @Override
    public void setOverScrollMode(int mode) {
        super.setOverScrollMode(mode);
        AutoSize.autoConvertDensityOfGlobal((Activity) getContext());
    }
}
