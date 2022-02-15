package com.phjt.shangxueyuan.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.phjt.base.integration.AppManager;
import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.utils.ConstantWeb;


/**
 * @author: date: 2019/10/17 16:25
 * company: 普华集团
 * description: 描述
 */
public class SlidingVerificationDialog {
    WebView mWebContent;
    TextView mTvCancel;
    OnSlidingListener mOnSlidingListener;
    Context mContext;

    /**
     * 控制输入法
     */
    private InputMethodManager mImm;
    private Dialog dialog;

    public interface OnSlidingListener {
        void OnSlidingEnd(String sessionId);

        void onSlidingEnd(String csessionid, String sig, String token, String scene);
    }

    public SlidingVerificationDialog(@NonNull Context context, OnSlidingListener onSlidingListener) {
        mContext = context;
        mOnSlidingListener = onSlidingListener;
        create();
        show();
    }

    protected void onShow() {
        mImm.hideSoftInputFromWindow(mTvCancel.getWindowToken(), 0);
    }

    public void dismiss() {
        mImm.hideSoftInputFromWindow(mTvCancel.getWindowToken(), 0);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }


    protected void create() {
        dialog = new Dialog(mContext, R.style.custom_dialog);
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_sliding_verification, null);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) mContext.getResources().getDimension(R.dimen.dp_283);
        lp.height = (int) mContext.getResources().getDimension(R.dimen.dp_221);

        dialogWindow.setAttributes(lp);
        mWebContent = inflate.findViewById(R.id.web_content);
        mTvCancel = inflate.findViewById(R.id.tv_cancel);

        mImm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        mTvCancel.setOnClickListener(v -> {
            AppManager.getAppManager().getCurrentActivity().runOnUiThread(() -> dismiss());
        });
        mWebContent.getSettings().setDomStorageEnabled(true);
        mWebContent.getSettings().setBlockNetworkImage(false);
        mWebContent.getSettings().setAllowUniversalAccessFromFileURLs(true);
        //WebView里的字体不随系统字体大小设置发生变化
        mWebContent.getSettings().setTextZoom(100);
        mWebContent.getSettings().setAllowFileAccess(true);
        // 设置WebView组件支持加载JavaScript。
        mWebContent.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebContent.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebContent.setWebChromeClient(new WebChromeClient());
        mWebContent.setWebViewClient(mWebViewClient);
        // 建立JavaScript调用Java接口的桥梁。
        mWebContent.addJavascriptInterface(this, "testInterface");

        //7.0以下滑块出不来，用网页加载
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mWebContent.loadUrl("file:///android_asset/sliderVerification.html");
        } else {
            String url;
            if (TextUtils.isEmpty(ConstantWeb.getH5AddressById(ConstantWeb.H5_SLIDERVERIFICATION))) {
                url = BuildConfig.HOST_URL_H5 + "sliderVerification";
            } else {
                url = ConstantWeb.getH5AddressById(ConstantWeb.H5_SLIDERVERIFICATION);
            }
            mWebContent.loadUrl(url);
        }
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };

    @JavascriptInterface
    public void getSlideData(String callData) {
        AppManager.getAppManager().getCurrentActivity().runOnUiThread(() -> {
            dismiss();
            if (mOnSlidingListener != null) {
                //mOnSlidingListener.OnSlidingEnd(callData);
            }
        });
    }

    @JavascriptInterface
    public void getSlideDatas(String csessionid, String sig, String token, String scene) {
        AppManager.getAppManager().getCurrentActivity().runOnUiThread(() -> {
            dismiss();
            if (mOnSlidingListener != null) {
                mOnSlidingListener.onSlidingEnd(csessionid, sig, token, scene);
            }
        });
    }
}
