package com.phjt.shangxueyuan.utils;

import android.content.Context;

import com.phjt.shangxueyuan.utils.alipay.AliPayUtil;
import com.phsxy.paylibrary.wechatpay.WeChatPayUtils;
import com.phsxy.paylibrary.wechatpay.WxPayBean;

/**
 * Created by ptt on 2018/7/5.
 * 支付工具类
 */
public class PayUtil {
    private Context mContext;
    public static final int PAY_TYPE_ALI = 1;
    public static final int PAY_TYPE_WX = 2;

    public PayUtil(Context context) {
        mContext = context;
    }

    /**
     * orderInfo PAY_TYPE_ALI:订单信息 PAY_TYPE_WX：APP_ID
     */
    public void pay(int payType, String orderInfo, WxPayBean wxBean, OnPayResultListener onPayStatusListener) {
        switch (payType) {
            case PAY_TYPE_ALI:
                aliPay(orderInfo, onPayStatusListener);
                break;
            case PAY_TYPE_WX:
                weChatPay(orderInfo, wxBean);
                break;
            default:
                break;
        }
    }

    /**
     * 支付宝支付
     */
    public void aliPay(String orderInfo, OnPayResultListener onPayStatusListener) {
        new AliPayUtil(mContext).aliPay(orderInfo, onPayStatusListener);
    }

    /**
     * 微信支付
     */
    public void weChatPay(String wxAppId, WxPayBean wxBean) {
        new WeChatPayUtils(mContext, wxAppId).wxPay(wxBean);
    }
}
