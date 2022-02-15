package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.phjt.base.base.BaseLazyLoadFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerEliteComponent;
import com.phjt.shangxueyuan.mvp.contract.EliteContract;
import com.phjt.shangxueyuan.mvp.presenter.ElitePresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.MessageActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.MyWebView;
import com.phsxy.utils.ApkUtils;
import com.phsxy.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.UmengUtil.onEventMainPage;


/**
 * @author: Created by GengYan
 * date: 04/02/2020 18:58
 * company: 普华集团
 * description: 模版请保持更新
 */
public class EliteFragment extends BaseLazyLoadFragment<ElitePresenter> implements EliteContract.View {

    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.ic_common_right)
    ImageView icCommonRight;
    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.layout)
    RelativeLayout mLayout;

    private AgentWeb mAgentWeb;
    private String url;

    public static EliteFragment newInstance() {
        Bundle args = new Bundle();
        EliteFragment fragment = new EliteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerEliteComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_elite, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivCommonBack.setVisibility(View.VISIBLE);
        ivCommonBack.setImageResource(R.drawable.customer_service_icon);
        icCommonRight.setVisibility(View.VISIBLE);
        icCommonRight.setImageResource(R.drawable.ic_news);
        tvCommonTitle.setText("精英");
    }

    @Override
    public void lazyLoadData() {

        if (TextUtils.isEmpty(ConstantWeb.getH5AddressById(ConstantWeb.H5_ELITE))) {
            url = BuildConfig.HOST_URL_H5 + Constant.WEB_ELITE_PAGE;
        } else {
            url = ConstantWeb.getH5AddressById(ConstantWeb.H5_ELITE);
        }

        reLoad();
    }

    public void reLoad() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLayout, new RelativeLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(ContextCompat.getColor(mContext, R.color.color_4071FC))
                .setWebView(new MyWebView(mContext))
                .createAgentWeb()
                .ready()
                .go(url);

        //Javascriptd调用Java获取token
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new MyJavascriptInterface(mContext));
    }

    public class MyJavascriptInterface {

        private Context mContext;

        public MyJavascriptInterface(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public String getToken() {
            return SPUtils.getInstance().getString(Constant.SP_TOKEN);
        }

        /**
         * 获取APP版本号
         */
        @JavascriptInterface
        public String getVersionName() {
            return ApkUtils.getVersionName(mContext);
        }

        @JavascriptInterface
        public void getData() {
            Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_TITLE, "客服中心");
            intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
            startActivity(intent);
        }
    }

    @OnClick({R.id.ic_common_right, R.id.iv_common_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ic_common_right:
                Intent intent = new Intent(mContext, MessageActivity.class);
                startActivity(intent);
                onEventMainPage("精英-站内通知按钮", UmengUtil.EVENT_DBWD_MAIN);
                break;
            case R.id.iv_common_back:
                Intent intent2 = new Intent(getActivity(), MyWebViewActivity.class);
                intent2.putExtra(Constant.BUNDLE_WEB_TITLE, "客服中心");
                intent2.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
                startActivity(intent2);
                onEventMainPage("精英-客服按钮", UmengUtil.EVENT_JYKFAN_MAIN);
                break;
            default:
                break;
        }

    }

    /**
     * 未读消息总数
     */
    public void showUnReadCount(Integer count) {
        if (tvMainInfo != null) {
            if (count != null && count > 0) {
                tvMainInfo.setVisibility(View.VISIBLE);
                if (count<100){
                    tvMainInfo.setText(count+"");
                }else {
                    tvMainInfo.setText("99+");
                }
            } else {
                tvMainInfo.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipsUtil.showTips(message);
    }
}
