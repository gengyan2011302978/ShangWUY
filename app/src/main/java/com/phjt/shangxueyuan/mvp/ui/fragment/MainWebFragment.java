package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;
import com.phjt.base.base.BaseLazyLoadFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.TrainingCampDetailActivity;
import com.phjt.shangxueyuan.utils.BannerUtils;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.FileUtils;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.ExtendedWebView;
import com.phsxy.utils.ApkUtils;
import com.phsxy.utils.SPUtils;
import com.phsxy.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;

import static com.phjt.shangxueyuan.mvp.ui.fragment.MainFragment.getIdByType;

/**
 * @author: Created by shaopengfei
 * date: 2020/12/28 11:51
 * company: 普华集团
 * description: 描述
 */
public class MainWebFragment extends BaseLazyLoadFragment {

    @BindView(R.id.layout)
    RelativeLayout mLayout;

    private String mUrl;
    private AgentWeb mAgentWeb;

    /**
     * 信息化运营官
     */
    public static final int TYPE_INFORMATION_OPERATION_OFFICER = 1;
    /**
     * 新营销架构师
     */
    public static final int TYPE_NEW_MARKETING_ARCHITECT = 2;

    public static MainWebFragment newInstance(int type) {
        MainWebFragment fragment = new MainWebFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void lazyLoadData() {
        reLoad();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_web, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            return;
        }
        int type = getArguments().getInt("type", -1);
        if (TYPE_INFORMATION_OPERATION_OFFICER == type) {
            mUrl = ConstantWeb.getH5AddressById(ConstantWeb.H5_CERTIFICATE_INFORMATION);
        } else if (TYPE_NEW_MARKETING_ARCHITECT == type) {
            mUrl = ConstantWeb.getH5AddressById(ConstantWeb.H5_CERTIFICATE_MARKETING);
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    public void reLoad() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLayout, new RelativeLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(ContextCompat.getColor(mContext, R.color.color_4071FC))
                .setWebView(new ExtendedWebView(mContext))
                .createAgentWeb()
                .ready()
                .go(mUrl);

        //Javascriptd调用Java获取token
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new MyJavascriptInterface(mContext));
    }

    private class MyJavascriptInterface {

        private Context mContext;

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
            return "home";
        }

        /**
         * 保存二维码到相册
         */
        @JavascriptInterface
        public void saveCodePic(String codePicUrl) {
            if (getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(() -> doSaveCodePic(codePicUrl));
        }

        @JavascriptInterface
        public void openNewWebView(String url) {
            Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_URL, url);
            intent.putExtra(Constant.BUNDLE_WEB_SHOW_SHARE, false);
            startActivity(intent);
        }

        //领取资料
        @JavascriptInterface
        public void getData() {
            Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_TITLE, "客服中心");
            intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
            startActivity(intent);
        }

        //友盟事件统计
        @JavascriptInterface
        public void onPageEvent(String key, String name) {
            UmengUtil.onUmengUtils(getActivity(), key, name);
        }

        /**
         * 获取APP渠道
         */
        @JavascriptInterface
        public String getAppChannel() {
            return "Android";
        }

        /**
         * 获取APP版本号
         */
        @JavascriptInterface
        public String getVersionName() {
            return ApkUtils.getVersionName(mContext);
        }

        /**
         * banner 跳转
         */
        @JavascriptInterface
        public void jumpTrainCampMethod(String strBannerBean) {
            ListBannerBean bannerBean = new Gson().fromJson(strBannerBean, ListBannerBean.class);
            if (bannerBean != null) {
                BannerUtils.bannerClick(mContext, bannerBean,getIdByType(9));
            }
        }

        /**
         * 跳转到训练营
         *
         * @param id 训练营id
         */
        @JavascriptInterface
        public void toTraining(String id) {
            Intent trainingIntent = new Intent(mContext, TrainingCampDetailActivity.class);
            trainingIntent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, id);
            startActivity(trainingIntent);
        }
    }

    @SuppressLint("CheckResult")
    private void doSaveCodePic(String url) {
        if (getActivity() == null) {
            return;
        }
        new RxPermissions(getActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        new FileUtils().onDownLoad(getActivity(), url, true);
                    } else {
                        ToastUtils.show("请先同意存储权限");
                    }
                });
    }
}
