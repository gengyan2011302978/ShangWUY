package com.phjt.shangxueyuan.utils;

import android.webkit.JavascriptInterface;

public class WebAppInterface {
    String mStr;

    public void setmStr(String mStr) {
        this.mStr = mStr;
    }

    @JavascriptInterface
    public String getString() {
        return mStr;
    }
}
