package com.phjt.shangxueyuan.utils.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.phjt.shangxueyuan.utils.OnPayResultListener;

import java.util.Map;

/**
 * Created by ptt on 2018/7/5.
 * 支付宝工具，支付宝支持回调
 */
public class AliPayUtil {
    private Context mContext;
    private OnPayResultListener mOnPayStatusListener;

    private static final int SDK_PAY_FLAG = 1;//支付
//    private static final int SDK_AUTH_FLAG = 2;//授权

    public AliPayUtil(Context context) {
        mContext = context;
    }

    public void aliPay(final String orderInfo, OnPayResultListener onPayStatusListener) {
        this.mOnPayStatusListener = onPayStatusListener;
        pay(orderInfo);
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    private void pay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.e("string----3----", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    AliPayResult payResult = new AliPayResult((Map<String, String>) msg.obj);
                    /*对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。*/
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        if (null != mOnPayStatusListener) {
                            mOnPayStatusListener.onPaySuccess();
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            if (null != mOnPayStatusListener) {
                                mOnPayStatusListener.onPayError("支付结果确认中");
                            }
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            if (null != mOnPayStatusListener) {
                                mOnPayStatusListener.onPayError("支付失败");
                            }
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
}
