package com.phjt.shangxueyuan.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gyf.immersionbar.ImmersionBar;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.AppManager;
import com.phjt.base.integration.EventBusManager;
import com.phjt.base.mvp.IBaseView;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CollectionStatusBean;
import com.phjt.shangxueyuan.bean.InitIndexSiteInfoBean;
import com.phjt.shangxueyuan.bean.MyDiaryBean;
import com.phjt.shangxueyuan.bean.SaveGeneratePicturesBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.event.ChangeMainItemEvent;
import com.phjt.shangxueyuan.bean.event.CourseCommentStateBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.bean.event.UserInfoUpdateEvent;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.common.MyBusiness;
import com.phjt.shangxueyuan.interf.IWithoutImmersionBar;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.FileUtils;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.utils.SoftKeyBoardListener;
import com.phjt.shangxueyuan.utils.TimeUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.utils.WebCameraHelper;
import com.phjt.shangxueyuan.utils.encryption.ECDSAUtils;
import com.phjt.shangxueyuan.utils.encryption.PhoneInfoUtil;
import com.phjt.shangxueyuan.utils.encryption.SecurityCheckUtil;
import com.phjt.shangxueyuan.widgets.MyWebView;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.ApkUtils;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.SPUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_TRAINING_CAMP_ID;
import static com.phjt.shangxueyuan.utils.Constant.PUNCH_CARDS_COURSE_ID;

/**
 * @author: Roy
 * date:   2020/3/31
 * company: ????????????
 * description:
 */
public class MyWebViewActivity extends BaseActivity implements IBaseView, IWithoutImmersionBar {

    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;

    @BindView(R.id.ic_common_right)
    ImageView mIvRight;
    @BindView(R.id.ic_common_right_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;
    @BindView(R.id.include)
    View mTitleView;
    @BindView(R.id.iv_common_back)
    ImageView mIvCommonBack;

    private AgentWeb mAgentWeb;
    private String mTitle;
    private String url;

    private ShareBean mShareBean = null;
    private boolean mPermissions = false;
    private String shareUrl = "";
    /**
     * ??????type
     * 1.vip?????? 2.???????????? 3.???????????? 4.???????????? 5.???????????? 6.???????????? 7.????????????
     */
    private int pageType;
    /**
     * ?????????????????????????????????????????????
     */
    private String typeId;
    private int collectionId;
    private boolean collectionClick;
    private List<InitIndexSiteInfoBean> indexList;

    /**
     * ????????????
     * 1. ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * 2. ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * 3. BOC?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    private boolean showShare = true;

    private boolean tagShowOptions = true;
    /**
     * ??????id
     */
    private String participationPunchCardsId = "";
    /**
     * ???????????????ID
     */
    private String punchCardsCourseId = "";
    /**
     * ??????ID
     */
    private String diaryId = "";
    private boolean isShowAttestationDialog = false;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_web_view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //??????????????????????????????????????????
        ImmersionBar.with(this).fitsSystemWindows(true)
                .statusBarColor(R.color.color_white)
                .statusBarDarkFont(true)
                .keyboardEnable(true)
                .init();

        indexList = new ArrayList<>();
        initContentView();
        if (!TextUtils.isEmpty(url)) {
            MyWebView myWebView = new MyWebView(this);
            myWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    // super.onReceivedSslError(view, handler, error);
                    // ??????ssl??????
                    handler.proceed();
                }
            });
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(layout, new RelativeLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator(ContextCompat.getColor(MyWebViewActivity.this, R.color.color_4071FC))
                    .setWebChromeClient(webChromeClient)
                    .setWebView(myWebView)
                    .createAgentWeb()
                    .ready()
                    .go(url);

            //Javascriptd??????Java??????token
            mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new MyJavascriptInterface(this));

        } else {
            LogUtils.e("================" + "URL????????????");
        }
        setListenerToRootView();
    }

    @Override
    public void onResume() {
//        if (mAgentWeb != null) {
//            mAgentWeb.getWebLifeCycle().onResume();
//        }
        super.onResume();

//        if (isShowPaySuccessDialog) {
//            showPaySuccessDialog();
//        }
        if (mAgentWeb != null) {
            mAgentWeb.getJsAccessEntrace().quickCallJs("pageInit");
        }
    }

    private void initContentView() {
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra(Constant.BUNDLE_WEB_TITLE);
            url = intent.getStringExtra(Constant.BUNDLE_WEB_URL);
            typeId = intent.getStringExtra(Constant.COURSE_TYPE_ID);
            showShare = intent.getBooleanExtra(Constant.BUNDLE_WEB_SHOW_SHARE, true);
            participationPunchCardsId = intent.getStringExtra(Constant.PARTICIPATION_PUNCH_CARDSID);
            punchCardsCourseId = intent.getStringExtra(PUNCH_CARDS_COURSE_ID);
            diaryId = intent.getStringExtra(Constant.BUNDLE_ADD_DIARY_ID);
            if (!TextUtils.isEmpty(getIntent().getStringExtra("localType")) && !TextUtils.isEmpty(diaryId) && !TextUtils.isEmpty(participationPunchCardsId)) {
                clickSaveGeneratePictures(diaryId, participationPunchCardsId);
            }
            if ("????????????".equals(mTitle)) {
                mTitleView.setBackgroundColor(ContextCompat.getColor(this, R.color.color_2675FE));
                mIvCommonBack.setImageResource(R.drawable.ic_back_white);
                tvCommonTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
                mIvRight.setImageResource(R.drawable.share_white);
                mIvRight.setVisibility(View.VISIBLE);
                ImmersionBar.with(this).fitsSystemWindows(true)
                        .statusBarColor(R.color.color_2675FE)
                        .statusBarDarkFont(true)
                        .keyboardEnable(true)
                        .init();
            } else if ("????????????".equals(mTitle)) {
                mIvRight.setImageResource(R.drawable.share);
                mIvRight.setVisibility(View.VISIBLE);
            }
        }

        LogUtils.e("===================url:" + url);
    }

    public WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            tvCommonRight.setVisibility(View.GONE);
            if (view.getUrl() != null) {
                if (view.getUrl().contains(ConstantWeb.getH5AddressById(ConstantWeb.H5_CERTIFICATE_NAV))) {
                    showShare = false;
                } else if (view.getUrl().contains(ConstantWeb.getH5AddressById(ConstantWeb.H5_CERTIFICATE_BOC))) {
                    showShare = true;
                }

                //??????????????????????????????????????????????????????
                if (url.contains(ConstantWeb.getH5AddressById(ConstantWeb.H5_QIITONG_HOME)) || url.contains(Constant.Qiitong_SERVICE_URL)) {
                    if ("????????????".equals(title) || "????????????".equals(title)) {
                        Drawable mScreens = ContextCompat.getDrawable(MyWebViewActivity.this, R.drawable.ic_screens);
                        tvCommonRight.setVisibility(View.VISIBLE);
                        tvCommonRight.setText("??????");
                        tvCommonRight.setCompoundDrawablesRelativeWithIntrinsicBounds(mScreens, null, null, null);
                    }
                }

            }
            if (!TextUtils.isEmpty(title) && !title.startsWith("http")) {
                tvCommonTitle.setText(title);
                setShare(title);
                mTitle = title;
                //??????????????????
            } else {
                tvCommonTitle.setText(mTitle);
            }
            UmengUtil.onEventWebPage(MyWebViewActivity.this, tvCommonTitle.getText().toString());
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

    private void showSelectPicDialog() {
        if (ContextCompat.checkSelfPermission(MyWebViewActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // ??????WRITE_EXTERNAL_STORAGE??????
            setPermissions();
        } else if (!mTitle.contains("????????????")) {
            WebCameraHelper.getInstance().showOptions(MyWebViewActivity.this);
        }
    }

    @SuppressLint("CheckResult")
    @SingleClick
    private void setPermissions() {
        new RxPermissions(this).request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean && !mTitle.contains("????????????")) {
                    WebCameraHelper.getInstance().showOptions(MyWebViewActivity.this);
                } else {
                    getWindow().findViewById(android.R.id.content).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!mTitle.contains("????????????")) {
                                WebCameraHelper.getInstance().againPermissions(MyWebViewActivity.this);
                            }
                        }
                    }, 300);
                }
            }
        });
    }


    @SuppressLint("WrongConstant")
    private void setShare(String title) {
        //??????????????????????????????
        if (title.contains("????????????")) {
            setTypeAndImg(1);
        }
//        else if (title.contains("????????????")) {
//            setTypeAndImg(2);
//        }
        else if (title.contains("????????????")) {
            setTypeAndImg(3);
        } else if (title.contains("????????????")) {
            setTypeAndImg(4);
        } else if (title.contains("????????????")) {
            setTypeAndImg(5);
        } else if (title.contains("????????????")) {
            setTypeAndImg(6);
        } else if (title.contains("????????????")) {
            setTypeAndImg(7);
        } else if ("????????????".equals(mTitle) || "????????????".equals(mTitle)) {
            mIvRight.setVisibility(View.VISIBLE);
        } else {
            mIvRight.setVisibility(View.GONE);
        }
    }

    private void setTypeAndImg(int type) {
        //??????????????????????????????????????????
        mShareBean = null;
        pageType = type;
        mIvRight.setVisibility(showShare || type == 1 ? View.VISIBLE : View.GONE);
        mIvRight.setImageResource(R.drawable.share);
    }

    /**
     * ?????? WebView??????????????????
     */
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int REQUEST_SELECT_FILE = 10000;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
//        ShareInit.getInstance().onActivityResult(this, requestCode, resultCode, intent);
        WebCameraHelper.getInstance().onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
        //???????????????????????????????????????
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


    /**
     * js??????
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
        public String getUserId() {
            return SPUtils.getInstance().getString(Constant.SP_USER_ID);
        }


        @JavascriptInterface
        public String getHomePosition() {
            return "";
        }

        /**
         * ????????????????????????
         */
        @JavascriptInterface
        public void saveCodePic(String codePicUrl) {
            runOnUiThread(() -> doSaveCodePic(codePicUrl));
        }

        /**
         * ??????????????????
         */
        @JavascriptInterface
        public void jumpToPunchCalendar(String id) {
            Intent intent = new Intent(mContext, CalendarActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }

        /**
         * ?????????????????????
         */
        @JavascriptInterface
        public void jumpToPunchingList(String id) {
            Intent intent = new Intent(mContext, RankingActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }

        /**
         * ????????????????????????
         */
        @JavascriptInterface
        public void jumpToPunchingAllTopic(String id) {
            Intent intent = new Intent(mContext, AllThemeActivity.class);
            intent.putExtra("id", id);
            intent.putExtra(PUNCH_CARDS_COURSE_ID, punchCardsCourseId);
            intent.putExtra("trainingId", getIntent().getStringExtra(BUNDLE_TRAINING_CAMP_ID));
            startActivity(intent);
        }

        /**
         * ??????????????????
         */
        @JavascriptInterface
        public void jumpToPunchingTopic(String themeId, int id) {
            Intent intent = new Intent(mContext, HistoryThemeActivity.class);
            intent.putExtra("id", String.valueOf(id));
            intent.putExtra("punchCardId", themeId);
            startActivity(intent);
        }

        /**
         * ????????????????????????
         */
        @JavascriptInterface
        public void jumpSecurePassword() {
            Intent intent = new Intent(mContext, SecurePasswordActivity.class);
            startActivity(intent);
        }

        /**
         * ????????????????????????
         */
        @JavascriptInterface
        public void jumpChangeSecurityPassword() {
            Intent intent = new Intent(mContext, ChangeSecurityPasswordActivity.class);
            startActivity(intent);
        }


        /**
         * ??????????????????
         */
        @JavascriptInterface
        public void jumpAuthentication() {
            Intent intent = new Intent(mContext, AuthenticationActivity.class);
            startActivity(intent);
        }

        /**
         * ??????????????????????????????
         */
        @JavascriptInterface
        public void toUserInfoPage() {
            Intent intent = new Intent(mContext, BasicInfoActivity.class);
            startActivity(intent);
        }

        @JavascriptInterface
        public void gotoBuyVip(String commodityId, String name, String realPrice, String cardType, String activityState) {
            UmengUtil.onEventVipPage(MyWebViewActivity.this, cardType);
            Intent intent = new Intent(MyWebViewActivity.this, OrderConfirmActivity.class);
            intent.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, commodityId);
            intent.putExtra(Constant.BUNDLE_ORDER_NAME, name);
            intent.putExtra(Constant.BUNDLE_ORDER_REALPRICE, realPrice);
            intent.putExtra(Constant.BUNDLE_ORDER_CARDTYPE, cardType);
            intent.putExtra(Constant.BUNDLE_ORDER_COMMODITY_TYPE, 1);
            intent.putExtra(Constant.BUNDLE_ORDER_ACTIVITYSTATE, activityState == null ? "0" : activityState);
            startActivity(intent);
        }

        //????????????
        @JavascriptInterface
        public void getData() {
            Intent intent = new Intent(MyWebViewActivity.this, MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_TITLE, "????????????");
            intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
            startActivity(intent);
        }


        //??????????????????
        @JavascriptInterface
        public void onPageEvent(String key, String name) {
            UmengUtil.onUmengUtils(MyWebViewActivity.this, key, name);
        }

        /**
         * ??????????????????
         *
         * @param courseId ??????id
         */
        @JavascriptInterface
        public void toCourseDetail(String courseId) {
            Intent intent = new Intent(mContext, CourseDetailActivity.class);
            intent.putExtra(Constant.BUNDLE_COURSE_ID, courseId);
            startActivity(intent);
        }

        /**
         * ?????????????????????
         *
         * @param courseId ??????id
         */
        @JavascriptInterface
        public void jumpToMyCoin() {
            Intent intent = new Intent(mContext, MyPointsActivity.class);
            startActivity(intent);
        }

        /**
         * ??????APP??????
         */
        @JavascriptInterface
        public String getAppChannel() {
            return "Android";
        }

        /**
         * ??????APP?????????
         */
        @JavascriptInterface
        public String getVersionName() {
            return ApkUtils.getVersionName(mContext);
        }

        /**
         * ???????????? ????????????????????????
         */
        @JavascriptInterface
        public void toCourseList() {
            Intent intent = new Intent(mContext, CourseCategoryActivity.class);
            intent.putExtra(Constant.COURSE_TYPE_ID, SPUtils.getInstance().getString(Constant.SP_PRACTICE_ID));
            startActivity(intent);
        }


        /**
         * ??????????????????Json???????????????
         */
        @JavascriptInterface
        public void getInfoShareDate(String strShareDate) {
            LogUtils.e("======================strShareDate:" + strShareDate);
            if (!TextUtils.isEmpty(strShareDate)) {
                mShareBean = new Gson().fromJson(strShareDate, ShareBean.class);
                Activity activity = (Activity) mContext;
                activity.runOnUiThread(() -> {
                    mIvRight.setVisibility(View.VISIBLE);
                    mIvRight.setImageResource(R.drawable.share);
                });
            }
        }

        /**
         * ??????????????????????????????
         */
        @JavascriptInterface
        public void examinationSystem() {
            finish();
        }

        /**
         * ??????????????????
         */
        @JavascriptInterface
        public void showAttestationDialog(String state) {
            if (state.equals("1")) {
                isShowAttestationDialog = true;
            } else {
                isShowAttestationDialog = false;
            }
        }

        /**
         * ????????????
         */
        @JavascriptInterface
        public void changeUploader() {
//            setPermissions();
        }


        /**
         * ???????????????????????????
         */
        @JavascriptInterface
        public boolean isAliPayInstalled() {
            Uri uri = Uri.parse("alipays://platformapi/startApp");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            ComponentName componentName = intent.resolveActivity(mContext.getPackageManager());
            return componentName != null;
        }

        /**
         * ????????????
         */
        @JavascriptInterface
        public void specialDetail(int id) {
            Activity activity = (Activity) mContext;
            activity.runOnUiThread(() -> {
                if (id == -1) {
                    ivCollect.setVisibility(View.GONE);
                } else {
                    collectionId = id;
                    getCollectionStatus();
                }
            });
        }

        /**
         * ?????????????????????
         */
        @JavascriptInterface
        public void getImageMethod(String id, String cardId) {
            clickSaveGeneratePictures(id, cardId);
        }

        /**
         * ????????????-??????????????????
         */
        @JavascriptInterface
        public void deleteNoteMethod() {
            showExitDialog();
        }

        /**
         * ????????????
         */
        @JavascriptInterface
        public void editNoteMethod(String id) {
            setEditNoteMethod(id);
        }

        /**
         * ????????????id
         */
        @JavascriptInterface
        public void sendDailyId(String id) {
            diaryId = id;
        }

        /**
         * ????????????
         */
        @JavascriptInterface
        public void jumpToPunchAndEdit(String punchCardId, String calendarDate, int reissueCardType, String motifId) {
            Intent intent = new Intent(MyWebViewActivity.this, JournalActivity.class);
            intent.putExtra(Constant.BUNDLE_ADD_DIARY_ID, "");
            intent.putExtra(Constant.BUNDLE_ADD_PUNCH_CARD_ID, punchCardId);
            intent.putExtra(Constant.BUNDLE_ADD_REISSUE_CARD_TYPE, reissueCardType <= 0 ? 1 : 0);
            intent.putExtra(Constant.BUNDLE_ADD_MOTIF_ID, motifId);
            intent.putExtra(Constant.BUNDLE_ADD_CALENDAR_DATE, calendarDate);
            startActivity(intent);
        }


        /**
         * ??????????????????
         */
        @JavascriptInterface
        public void btnJump(int type, int id) {
            Activity activity = (Activity) mContext;
            activity.runOnUiThread(() -> {
                //type 1: ????????? 2????????????3??????????????????4???????????????, 5:??????????????? 6:??????????????? 7:??????
                Intent intent = null;
                switch (type) {
                    case 1:
                        intent = new Intent(MyWebViewActivity.this, MyWebViewActivity.class);
                        intent.putExtra(Constant.BUNDLE_WEB_TITLE, "????????????");
                        intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
                        startActivity(intent);
                        break;
                    case 2:
                    case 7:
                        intent = new Intent(MyWebViewActivity.this, CourseDetailActivity.class);
                        intent.putExtra(Constant.BUNDLE_COURSE_ID, String.valueOf(id));
                        startActivity(intent);
                        break;
                    case 4:
                        // id 1:???????????? ???2??????????????? ???3??????????????????4?????????
                        if (1 == id) {
                            inviteShare();
                        } else if (2 == id || 3 == id) {
                            getInitIndexSiteInfo(id);
                        } else if (4 == id) {
                            intent = new Intent(MyWebViewActivity.this, FeedbackActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 5:
                        intent = new Intent(mContext, CourseCategoryActivity.class);
                        intent.putExtra(Constant.COURSE_TYPE_ID, SPUtils.getInstance().getString(Constant.SP_PRACTICE_ID));
                        startActivity(intent);
                        finish();
                        break;
                    case 6:
                        intent = new Intent(mContext, ArticleClassifyActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        break;
                }
            });

        }

        /**
         * ????????????????????????
         *
         * @return
         */
        @JavascriptInterface
        public boolean isWeiXinInstalled() {
            //???????????????????????????????????????
            final PackageManager packageManager = mContext.getPackageManager();
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    if (pn.equals("com.tencent.mm")) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * H5 ??????token????????????
         */
        @JavascriptInterface
        public void tokenInvalid(String msg) {
            MyBusiness.cleanSp();
            ArchitectUtils.startActivity(LoginActivity.class);
            AppManager.getAppManager().killAll(LoginActivity.class);
            Activity activity = (Activity) mContext;
            activity.runOnUiThread(() -> {
                TipsUtil.show(msg);
            });
        }


        /**
         * ????????? ?????????Item??????
         */
        @JavascriptInterface
        public void shareLeaveWord(int type, int otherId, String content, String nickName, String photo, int shareType) {
            getShareItemData(type, String.valueOf(otherId), content, shareType);
        }

        /**
         * ?????????
         */
        @JavascriptInterface
        public void changeNum(String id, boolean likeState, int likeNum, int replyNum) {
            CourseCommentStateBean commentStateBean = new CourseCommentStateBean(id, likeState, likeNum, replyNum);
            EventBusManager.getInstance().post(commentStateBean);
        }

        /**
         * ?????????????????????tab
         */
        @JavascriptInterface
        public void goHomePage(int index) {
            EventBusManager.getInstance().post(new ChangeMainItemEvent(index));
            finish();
        }

        /**
         * ??????????????????
         *
         * @param id ?????????id
         */
        @JavascriptInterface
        public void toTraining(String id) {
            Intent trainingIntent = new Intent(mContext, TrainingCampDetailActivity.class);
            trainingIntent.putExtra(BUNDLE_TRAINING_CAMP_ID, id);
            startActivity(trainingIntent);
        }

        /**
         * ??????App
         *
         * @param str
         */
        @JavascriptInterface
        public void wakeUpApp(String str) {
            pullUpApp(str);
        }

        /**
         * ???????????????
         */
        @JavascriptInterface
        public void jumpToRecharge() {
            Intent intent = new Intent(mContext, CurrencyRechargeActivity.class);
            startActivity(intent);
        }

        /**
         * ??????????????????
         */
        @JavascriptInterface
        public void jumpConfirmOrder() {
            Intent intent = new Intent(mContext, OrderConfirmActivity.class);
            intent.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, getIntent().getStringExtra(Constant.BUNDLE_ORDER_COMMODITYID));
            intent.putExtra(Constant.BUNDLE_ORDER_NAME, getIntent().getStringExtra(Constant.BUNDLE_ORDER_NAME));
            intent.putExtra(Constant.BUNDLE_ORDER_REALPRICE, getIntent().getStringExtra(Constant.BUNDLE_ORDER_REALPRICE));
            startActivity(intent);
        }


        /**
         * ??????????????????
         */
        @JavascriptInterface
        public void openLivePreach(String id, String studyBean, String specialTitle, int type, int payType) {
            Intent intent = new Intent(mContext, OrderConfirmActivity.class);
            intent.putExtra(Constant.BUNDLE_ORDER_NAME, specialTitle);
            intent.putExtra(Constant.BUNDLE_ORDER_REALPRICE, studyBean);
            intent.putExtra(Constant.BUNDLE_ORDER_COMMODITY_TYPE, payType);
            intent.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, id);
            startActivity(intent);
        }


        //-------------------------------  ???????????????-----------------------------------------------------

        /**
         * ??????????????????
         */
        @JavascriptInterface
        public String transmitDeviceInfo() {
            String phone = SPUtils.getInstance().getString(Constant.SP_MOBILE);
            String mMac = PhoneInfoUtil.getMac(MyWebViewActivity.this);
            int isRoot = SecurityCheckUtil.isRoot() ? 1 : 0;
            return "" + phone + "&" + mMac + "&" + isRoot;
        }


        /**
         * ????????????
         */
        @JavascriptInterface
        public String getEncrypt(String jsonStr) {
            Activity currentActivity = AppManager.getAppManager().getCurrentActivity();
            JsonObject jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
//            jsonObject.addProperty("encryptionType", "H5_SEA");
//            jsonObject.addProperty("mac", PhoneInfoUtil.getMac(currentActivity));
            return ECDSAUtils.encrypt(jsonObject.toString());

        }

        //?????????????????????
        @JavascriptInterface
        public void qiitongCustomerService() {
            Intent intent = new Intent(MyWebViewActivity.this, MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_TITLE, "????????????");
            intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.Qiitong_SERVICE_URL);
            startActivity(intent);
        }

        /**
         * ???????????????
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


        //-------------------------------  ???????????????-----------------------------------------------------
    }

    /**
     * ???????????????
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

    public void goQiitong() {
        Intent intent = new Intent(this, MyWebViewActivity.class);
        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_QIITONG_HOME));
        startActivity(intent);
    }


    /**
     * ??????App
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
     * ??????APP???????????????
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
     * ????????????????????????
     *
     * @param url
     */
    private void downloadByBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????
     */
    @SuppressLint("CheckResult")
    public void getShareItemData(int type, String otherId, String content, int shareType) {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .getShareItemData(type, otherId, content, shareType, String.valueOf(0))
                .compose(RxUtils.applySchedulersWithLoading(this))
                .subscribe(baseBean -> {
                    if (baseBean.isOk()) {
                        ShareItemBean shareItemBean = baseBean.data;
                        if (shareItemBean != null) {
                            ShareBean shareBean = new ShareBean();
                            shareBean.setTitle(shareItemBean.getTitle());
                            shareBean.setContent(shareItemBean.getContent());
                            shareBean.setImgUrl(shareItemBean.getPhoto());
                            shareBean.setUrl(shareItemBean.getUrl());
                            showShareDialog(shareBean);
                        } else {
                            showMessage("????????????????????????");
                        }
                    } else {
                        showMessage(baseBean.msg);
                    }
                }, throwable -> LogUtils.e("????????????????????????"));
    }


    @SuppressLint("CheckResult")
    private void diaryShare() {
        if (null != diaryId) {
            if (diaryId.equals("0")) {
                diaryId = null;
            }
        }
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .diaryShare(participationPunchCardsId, punchCardsCourseId, null == getIntent().getStringExtra(BUNDLE_TRAINING_CAMP_ID) ? null : getIntent().getStringExtra(BUNDLE_TRAINING_CAMP_ID), diaryId)
                .compose(RxUtils.applySchedulersWithLoading(this))
                .subscribe(shareBaseBean -> {
                    if (shareBaseBean.isOk()) {
                        showShareDialog(shareBaseBean.data);
                    }
                }, throwable -> TipsUtil.showTips("????????????????????????"));
    }

    @SuppressLint("CheckResult")
    private void getShareCourseData(String pointId) {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .getCourseShareData(punchCardsCourseId, "", pointId)
                .compose(RxUtils.applySchedulersWithLoading(this))
                .subscribe(shareBaseBean -> {
                    if (shareBaseBean.isOk()) {
                        showShareDialog(shareBaseBean.data);
                    }
                }, throwable -> TipsUtil.showTips("????????????????????????"));
    }


    /**
     * ??????????????????
     */
    @SuppressLint("CheckResult")
    private void getCollectionStatus() {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .getCollectionStatus(collectionId)
                .compose(RxUtils.applySchedulersWithLoading(this))
                .subscribe(bean -> {
                    if (bean.isOk()) {
                        CollectionStatusBean beanStatus = bean.data;
                        if (beanStatus != null) {
                            ivCollect.setVisibility(View.VISIBLE);
                            mIvRight.setVisibility(View.VISIBLE);
                            mIvRight.setImageResource(R.drawable.share);
                            if (0 == beanStatus.getCollectionStatus()) {
                                collectionClick = false;
                                ivCollect.setImageResource(R.drawable.ic_collection_unselect);
                            } else {
                                collectionClick = true;
                                ivCollect.setImageResource(R.drawable.ic_collection_select);
                            }
                        }
                    }
                }, throwable -> {
                    LogUtils.e("????????????????????????");
                });

    }


    /**
     * ????????????
     */
    @SuppressLint("CheckResult")
    private void inviteShare() {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .inviteShareT()
                .compose(RxUtils.applySchedulersWithLoading(this))
                .subscribe(bean -> {
                    if (bean.isOk()) {
                        shareUrl = String.valueOf(bean.data);
                        setShare();
                    }
                }, throwable -> LogUtils.e("??????????????????"));

    }

    /**
     * ??????????????????
     */
    @SuppressLint("CheckResult")
    private void setShare() {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .inviteShare()
                .compose(RxUtils.applySchedulersWithLoading(this))
                .subscribe(bean -> {
                    if (bean.isOk()) {
                        BaseBean<List<ShareImgBean>> data = bean;
                        String mobile = SPUtils.getInstance().getString(Constant.SP_MOBILE);
                        String phoneNumber = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                        Intent intent = new Intent(MyWebViewActivity.this, ShareActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", data);
                        intent.putExtras(bundle);
                        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_SHAREWX) +
                                "?code=" + shareUrl + "&mobile=" + phoneNumber);
                        startActivity(intent);
                    }
                }, throwable -> LogUtils.e("??????????????????"));

    }

    /**
     * ????????????
     */
    @SuppressLint("CheckResult")
    private void getInitIndexSiteInfo(int couType) {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .getInitIndexSiteInfo()
                .compose(RxUtils.applySchedulersWithLoading(this))
                .subscribe(bean -> {
                    if (bean.isOk()) {
                        indexList = bean.data;
                        if (indexList != null && indexList.size() > 0 && couType == 3) {
                            Intent intent = new Intent(MyWebViewActivity.this, CourseCategoryActivity.class);
                            intent.putExtra(Constant.COURSE_TYPE_ID, indexList.get(0).getId());
                            startActivity(intent);
                        } else if (indexList != null && indexList.size() > 4 && couType == 2) {
                            Intent intent = new Intent(MyWebViewActivity.this, CourseClassifyActivity.class);
                            intent.putExtra(CourseClassifyActivity.COURSE_CLASSIFY_TYPE, 0);
                            intent.putExtra(Constant.COURSE_TYPE_ID, indexList.get(indexList.size() - 4).getId());
                            startActivity(intent);
                        }
                    }
                }, throwable -> LogUtils.e(" ??????????????????"));

    }


    /**
     * ???????????????????????????
     *
     * @param collectionId
     */
    @SuppressLint("CheckResult")
    private void getCollectionFavorite(int collectionId) {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .getFavoriteEdit(collectionId)
                .compose(RxUtils.applySchedulersWithLoading(this))
                .subscribe(baseBean -> {
                    if (baseBean.isOk()) {
                        if (collectionClick) {
                            collectionClick = false;
                            ivCollect.setImageResource(R.drawable.ic_collection_unselect);
                            TipsUtil.show("????????????");
                        } else {
                            collectionClick = true;
                            ivCollect.setImageResource(R.drawable.ic_collection_select);
                            TipsUtil.show("????????????");
                        }
                    }
                }, throwable -> LogUtils.e("???????????????????????????"));

    }

    @SuppressLint("CheckResult")
    private void getShareDate() {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .getVipShareContent(pageType)
                .compose(RxUtils.applySchedulersWithLoading(this))
                .subscribe(shareBaseBean -> {
                    if (shareBaseBean.isOk()) {
                        showShareDialog(shareBaseBean.data);
                    }
                }, throwable -> TipsUtil.showTips("????????????????????????"));

    }

    /**
     * ????????????
     *
     * @param shareBean ????????????
     */
    private void showShareDialog(ShareBean shareBean) {
        ShareUtils.showSharePop(this, shareBean);
    }

    /**
     * ??????????????????????????????
     */
    private boolean isShowPaySuccessDialog = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.PAY_SUCCESS && TextUtils.equals(eventBean.getMsg(), Constant.ORDER_PAY_SUCCESS)) {
                mAgentWeb.getJsAccessEntrace().quickCallJs("refreshVip");
                isShowPaySuccessDialog = true;
            } else {
                mAgentWeb.getUrlLoader().reload();
            }
        }
    }

    /**
     * ?????????????????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(UserInfoUpdateEvent userInfoUpdateEvent) {
        if (userInfoUpdateEvent != null && mAgentWeb != null) {
            mAgentWeb.getUrlLoader().reload();
        }
    }

    /**
     * ???????????????????????????
     */
    public void showPaySuccessDialog() {
//        isShowPaySuccessDialog = false;
//        DialogUtils.showWebVipSuccessDialog(this, new DialogUtils.OnCancelSureClick() {
//            @Override
//            public void clickSure() {
//                Intent intent = new Intent(MyWebViewActivity.this, CourseClassifyActivity.class);
//                intent.putExtra(CourseClassifyActivity.COURSE_CLASSIFY_TYPE, 0);
//                intent.putExtra(Constant.COURSE_TYPE_ID, SPUtils.getInstance().getString(Constant.SP_PRIMARY_COURSE_ID));
//                startActivity(intent);
//                EventBusManager.getInstance().post(new WebToStudyBean());
//            }
//        });
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * ????????????????????????
     */
    public void clickSaveGeneratePictures(String id, String cardId) {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .clickSaveGeneratePictures(id, cardId)
                .compose(RxUtils.applySchedulersWithLoading(this))
                .subscribe(new Observer<BaseBean<SaveGeneratePicturesBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<SaveGeneratePicturesBean> saveGeneratePicturesBeanBaseBean) {
                        if (saveGeneratePicturesBeanBaseBean.isOk()) {
                            DialogUtils.generatingSharedImages(MyWebViewActivity.this, saveGeneratePicturesBeanBaseBean.data);
                        } else {
                            TipsUtil.show("??????????????????");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        TipsUtil.show("??????????????????");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * ??????????????????
     */
    public void showExitDialog() {
        DialogUtils.showCancelSureDialog(MyWebViewActivity.this, "",
                getResources().getString(R.string.is_delete_diary), getResources().getString(R.string.quit_cancel),
                getResources().getString(R.string.quit_delete),
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        deleteNote();
                    }
                });

    }

    /**
     * ????????????-??????????????????
     */
    public void deleteNote() {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .delectDiary(diaryId)
                .compose(RxUtils.applySchedulersWithLoading(this))
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            finish();
                        } else {
                            TipsUtil.show("????????????");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        TipsUtil.show("????????????");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * ????????????
     */
    public void setEditNoteMethod(String id) {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .diaryDetails(id)
                .compose(RxUtils.applySchedulersWithLoading(this))
                .subscribe(new Observer<BaseBean<MyDiaryBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<MyDiaryBean> myDiaryBean) {
                        if (myDiaryBean.isOk()) {
                            Intent intent = new Intent(MyWebViewActivity.this, JournalActivity.class);
                            intent.putExtra(Constant.BUNDLE_ADD_DIARY_ID, myDiaryBean.data.getId());
                            intent.putExtra(Constant.BUNDLE_ADD_REISSUE_CARD_TYPE, 0);
                            intent.putExtra(Constant.BUNDLE_ADD_PUNCH_CARD_ID, myDiaryBean.data.getPunchCardId());
                            intent.putExtra(Constant.BUNDLE_ADD_MOTIF_ID, myDiaryBean.data.getMotifId());
                            intent.putExtra(Constant.BUNDLE_ADD_ONCE_MORE, 1);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void showMessage(@NonNull String message) {
        TipsUtil.showTips(message);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                //????????????????????????????????????????????????shouldShowRequestPermissionRationale??????false???
                // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    //?????????????????????????????????????????????????????????
                    if (mPermissions) {
                        setCancelSureDialog();
                    } else {
                        mPermissions = true;
                    }
                } else {
                    //?????????????????????????????????????????????????????????
                    mPermissions = false;
                }
                break;
            }
        }
        if (hasAllGranted && tagShowOptions) {
            //??????????????????
            if (!mTitle.contains("????????????")) {
                WebCameraHelper.getInstance().showOptions(MyWebViewActivity.this);
            }
        }
        tagShowOptions = true;
    }

    private void setCancelSureDialog() {
        DialogUtils.showCancelSureDialog(this, "??????????????????",
                "?????????????????????????????????????????????", getResources().getString(R.string.quit_cancel),
                getResources().getString(R.string.quit_sure),
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        //????????????????????????????????????
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void doSaveCodePic(String url) {
        tagShowOptions = false;
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        new FileUtils().onDownLoad(this, url, true);
                    } else {
//                        ToastUtils.show("????????????????????????");
                    }
                });
    }

    /**
     * ???????????????
     */
    private void setListenerToRootView() {
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //????????????
                if (mAgentWeb != null) {
                    mAgentWeb.getJsAccessEntrace().quickCallJs("displayKeyboards");
                }
            }

            @Override
            public void keyBoardHide(int height) {
                //????????????
                if (mAgentWeb != null) {
                    mAgentWeb.getJsAccessEntrace().quickCallJs("hiddenKeyboards");
                }
            }
        });

    }


    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.ic_common_right, R.id.ic_common_right_collect, R.id.tv_common_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                if (isShowAttestationDialog) {
                    mAgentWeb.getJsAccessEntrace().quickCallJs("hideAttestationDialog");
                    isShowAttestationDialog = false;
                } else {
                    onFinish();
                }
                break;
            case R.id.ic_common_right:
                UmengUtil.onUmengUtils(this, UmengUtil.SHARE, tvCommonTitle.getText().toString().trim() + "??????");
                if (tvCommonTitle.getText().toString().contains("????????????")) {
                    UmengUtil.umengCount(this, ConstantUmeng.INFORMATION_CLICK_SHARE);
                }
                if ("????????????".equals(mTitle) || "????????????".equals(mTitle)) {
                    diaryShare();
                } else {
                    //Vip??????
                    if (null != mShareBean) {
                        showShareDialog(mShareBean);
                        return;
                    }
                    getShareDate();
                }
                break;
            case R.id.ic_common_right_collect:
                if (collectionId != 0 && collectionClick) {
                    getCollectionFavorite(collectionId);
                } else if (collectionId != 0 && !collectionClick) {
                    getCollectionFavorite(collectionId);
                }

                break;

            case R.id.tv_common_right:
                if (mAgentWeb != null) {
                    mAgentWeb.getJsAccessEntrace().quickCallJs("androidFnHandler");
                }

                break;
            default:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isShowAttestationDialog) {
            mAgentWeb.getJsAccessEntrace().quickCallJs("hideAttestationDialog");
            isShowAttestationDialog = false;
            return false;
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                onFinish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ??????
     */
    private void onFinish() {
        if (mAgentWeb != null && !mAgentWeb.back()) {
            finish();
        }
    }

    @Override
    public void onPause() {
//        if (mAgentWeb != null) {
//            mAgentWeb.getWebLifeCycle().onPause();
//        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }


}
