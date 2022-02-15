package com.phjt.shangxueyuan.mvp.ui.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gyf.immersionbar.ImmersionBar;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.IBaseView;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.interf.IWithoutImmersionBar;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.FileUtils;
import com.phjt.shangxueyuan.utils.NavigationBarUtils;
import com.phjt.shangxueyuan.utils.TimeUtils;
import com.phjt.shangxueyuan.utils.WebCameraHelper;
import com.phjt.shangxueyuan.utils.encryption.ECDSAUtils;
import com.phjt.shangxueyuan.utils.encryption.PhoneInfoUtil;
import com.phjt.shangxueyuan.utils.encryption.SecurityCheckUtil;
import com.phjt.shangxueyuan.widgets.MyWebView;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.SPUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;


/**
 * @author: Roy
 * date:   2021/8/24
 * company: 普华集团
 * description:启富通
 */
public class QiitongWebViewActivity extends BaseActivity implements IBaseView, IWithoutImmersionBar {

    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.include)
    ConstraintLayout mTitile;
    @BindView(R.id.cl_my_web_view)
    ConstraintLayout clMyWebView;

    private AgentWeb mAgentWeb;
    private String url;

    private boolean mPermissions = false;
    private final int REQUEST_SELECT_FILE = 10000;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private boolean isShowAttestationDialog = false;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_my_web_view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //解决输入框与软键盘冲突的问题
        ImmersionBar.with(this).fitsSystemWindows(true)
                .statusBarColor(R.color.color_white)
                .statusBarDarkFont(true)
                .keyboardEnable(true)
                .init();
        loadData(getIntent());
        clMyWebView.setBackgroundResource(R.color.color_white);
        mTitile.setVisibility(View.GONE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadData(intent);
    }

    private void loadData(Intent intent) {
        initContentView();
        initWeb();
        NavigationBarUtils.assistActivity(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initWeb() {
        if (!TextUtils.isEmpty(url)) {
            MyWebView myWebView = new MyWebView(this);
            myWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    // super.onReceivedSslError(view, handler, error);
                    // 忽略ssl错误
                    handler.proceed();
                }
            });

            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(layout, new RelativeLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator(ContextCompat.getColor(QiitongWebViewActivity.this, R.color.color_4071FC))
                    .setWebChromeClient(webChromeClient)
                    .setWebView(myWebView)
                    .createAgentWeb()
                    .ready()
                    .go(url);
            //Javascriptd调用Java获取token
            mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new MyJavascriptInterface(this));
        } else {
            LogUtils.e("================" + "URL连接为空");
        }
    }

    private void initContentView() {
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra(Constant.BUNDLE_WEB_URL);
        }
        LogUtils.e("===================url:" + url);
    }

    public WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }

        // For Android < 3.0
        @Override
        public void openFileChooser(ValueCallback<Uri> valueCallback) {
            WebCameraHelper.getInstance().mUploadMessage = valueCallback;
            setPermissions();
        }

        // For Android  >= 3.0
        @Override
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
            WebCameraHelper.getInstance().mUploadMessage = valueCallback;
            setPermissions();
        }

        //For Android  >= 4.1
        @Override
        public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
            WebCameraHelper.getInstance().mUploadMessage = uploadFile;
            setPermissions();
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            WebCameraHelper.getInstance().mUploadCallbackAboveL = filePathCallback;
            showSelectPicDialog();
            setPermissions();
            return true;
        }
    };

    @Override
    public void showMessage(@NonNull @NotNull String message) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * js通信
     */
    public class MyJavascriptInterface {

        public Context mContext;

        public MyJavascriptInterface(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public String getToken() {
            return SPUtils.getInstance().getString(Constant.SP_TOKEN);
        }

        @JavascriptInterface
        public String getStatusBarHeight() {
            return String.valueOf(ImmersionBar.getStatusBarHeight(QiitongWebViewActivity.this));
        }


        @JavascriptInterface
        public String getUserId() {
            return SPUtils.getInstance().getString(Constant.SP_USER_ID);
        }

        /**
         * 传递设备信息
         */
        @JavascriptInterface
        public String transmitDeviceInfo() {
            String phone = SPUtils.getInstance().getString(Constant.SP_MOBILE);
            String mMac = PhoneInfoUtil.getMac(QiitongWebViewActivity.this);
            int isRoot = SecurityCheckUtil.isRoot() ? 1 : 0;
            return "" + phone + "&" + mMac + "&" + isRoot;
        }


        /**
         * 参数加密
         */
        @JavascriptInterface
        public String getEncrypt(String jsonStr) {
            Activity currentActivity = AppManager.getAppManager().getCurrentActivity();
            JsonObject jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
//            jsonObject.addProperty("encryptionType", "H5_SEA");
//            jsonObject.addProperty("mac", PhoneInfoUtil.getMac(currentActivity));
            return ECDSAUtils.encrypt(jsonObject.toString());

        }

        /**
         * 开启启富通
         */
        @JavascriptInterface
        public void isQiitong() {
            String strToday = TimeUtils.getStrToday();
            String strCountDownDay = SPUtils.getInstance().getString(Constant.SP_COUNT_DOWN_DAY);
            if (TextUtils.isEmpty(strCountDownDay) || strToday.equals(strCountDownDay)) {
                showQiitongDialog();
            } else {
                goQiitong();
            }
        }

        //启富通客服中心
        @JavascriptInterface
        public void qiitongCustomerService() {
            Intent intent = new Intent(mContext, MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_TITLE, "客服中心");
            intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.Qiitong_SERVICE_URL);
            startActivity(intent);
        }

        /**
         * 前往找回安全密码
         */
        @JavascriptInterface
        public void jumpSecurePassword() {
            Intent intent = new Intent(mContext, SecurePasswordActivity.class);
            startActivity(intent);
        }

        /**
         * 前往修改安全密码
         */
        @JavascriptInterface
        public void jumpChangeSecurityPassword() {
            Intent intent = new Intent(mContext, ChangeSecurityPasswordActivity.class);
            startActivity(intent);
        }


        /**
         * 前往身份认证
         */
        @JavascriptInterface
        public void jumpAuthentication() {
            Intent intent = new Intent(mContext, AuthenticationActivity.class);
            startActivity(intent);
        }

        /**
         * 保存二维码到相册
         */
        @JavascriptInterface
        public void saveCodePic(String codePicUrl) {
            runOnUiThread(() -> doSaveCodePic(codePicUrl));
        }

        /**
         * 关闭页面
         */
        @JavascriptInterface
        public void setFinish() {
            onFinish();
        }

        /**
         * 拉起App
         *
         * @param str
         */
        @JavascriptInterface
        public void wakeUpApp(String str) {
            pullUpApp(str);
        }

        /**
         * 确认交易弹框开启
         */
        @JavascriptInterface
        public void showAttestationDialog() {
            isShowAttestationDialog = true;
        }

        /**
         * 确认交易弹框关闭
         */
        @JavascriptInterface
        public void hideAttestationDialog() {
            isShowAttestationDialog = false;
        }

        /**
         * 调用拨号
         */
        @JavascriptInterface
        public void setCallPhone(String phone) {
            callPhone(phone);
        }
    }

    /**
     * 启富通弹窗
     */
    private void showQiitongDialog() {
        DialogUtils.showQiitongDialog(this, new DialogUtils.OnClickDialogListener() {
            @Override
            public void clickCancel(boolean selected) {
                if (selected) {
                    SPUtils.getInstance().put(Constant.SP_COUNT_DOWN_DAY, TimeUtils.countDownDay());
                }
            }

            @Override
            public void clickOk(boolean selected) {
                if (selected) {
                    SPUtils.getInstance().put(Constant.SP_COUNT_DOWN_DAY, TimeUtils.countDownDay());
                    goQiitong();
                } else {
                    goQiitong();
                }
            }
        });
    }

    public void callPhone(String telephone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goQiitong() {
        Intent intent = new Intent(this, QiitongWebViewActivity.class);
        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_QIITONG_HOME));
        startActivity(intent);
    }

    @SuppressLint("CheckResult")
    @SingleClick
    private void setPermissions() {
        new RxPermissions(this).request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    WebCameraHelper.getInstance().showOptions(QiitongWebViewActivity.this);
                }
            }
        });
    }

    /**
     * 退出
     */
    private void onFinish() {
        finish();
    }

    @SuppressLint("CheckResult")
    private void doSaveCodePic(String url) {
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        new FileUtils().onDownLoad(this, url, true);
                    } else {
//                        ToastUtils.show("请先同意存储权限");
                    }
                });
    }

    /**
     * 拉起App
     *
     * @param downUrl
     */
    public void pullUpApp(String downUrl) {
        if (!TextUtils.isEmpty(downUrl)) {
            Uri parse = Uri.parse("tingyun.://");
            String packageName = "co.btlux.broker.android";
            boolean isExist = checkAppExist(packageName);
            if (!isExist) {
                downloadByBrowser(downUrl);
            } else {
                Intent intent = new Intent();
                intent.setData(parse);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }


    /**
     * 判断APP是否已安装
     */
    public boolean checkAppExist(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            ApplicationInfo info = getPackageManager()
                    .getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

    }


    /**
     * 跳转到浏览器下载
     *
     * @param url
     */
    private void downloadByBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void showSelectPicDialog() {
        if (ContextCompat.checkSelfPermission(QiitongWebViewActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请WRITE_EXTERNAL_STORAGE权限
            setPermissions();
        } else {
            WebCameraHelper.getInstance().showOptions(QiitongWebViewActivity.this);
        }
    }


    private void setCancelSureDialog() {
        DialogUtils.showCancelSureDialog(this, "无法使用相册",
                "可以到手机系统“设置”中开启？", getResources().getString(R.string.quit_cancel),
                getResources().getString(R.string.quit_sure),
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        //引导用户至设置页手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        WebCameraHelper.getInstance().onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
        //处理返回的图片，并进行上传
        if (requestCode == REQUEST_SELECT_FILE) {
            if (null == uploadMessage && null == uploadMessageAboveL) {
                return;
            }
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, intent);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != REQUEST_SELECT_FILE || uploadMessageAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
        }
        if (results != null) {
            uploadMessageAboveL.onReceiveValue(results);
        }
        uploadMessageAboveL = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    //解释原因，并且引导用户至设置页手动授权
                    if (mPermissions) {
                        setCancelSureDialog();
                    } else {
                        mPermissions = true;
                    }
                } else {
                    //权限请求失败，但未选中“不再提示”选项
                    mPermissions = false;
                }
                break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isShowAttestationDialog) {
            mAgentWeb.getJsAccessEntrace().quickCallJs("androidBackClick");
            return false;
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                onFinish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }
}
