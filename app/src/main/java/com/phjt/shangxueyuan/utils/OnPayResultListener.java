package com.phjt.shangxueyuan.utils;

/**
 * Created by ptt on 2019/3/22.
 * 支付回调 --> listener
 */
public interface OnPayResultListener {
    void onPaySuccess();

    void onPayError(String error);

    void onPayCancel();
}
