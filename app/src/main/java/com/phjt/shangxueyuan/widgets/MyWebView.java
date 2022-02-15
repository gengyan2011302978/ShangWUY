package com.phjt.shangxueyuan.widgets;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import me.jessyan.autosize.AutoSize;

/**
 * @author: gengyan
 * date:    2019/6/28
 * company: 普华集团
 * description: 自定义WebView
 */
public class MyWebView extends WebView {

    public MyWebView(Context context) {
        super(context);
        setWebContentsDebuggingEnabled(true);
        this.getSettings().setTextZoom(100);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
