package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerAboutComponent;
import com.phjt.shangxueyuan.mvp.contract.AboutContract;
import com.phjt.shangxueyuan.mvp.presenter.AboutPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phsxy.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_TITLE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_URL;

/**
 * @author: Roy
 * date:    2020/03/27
 * company: 普华集团
 * description: 关于我们
 */
public class AboutActivity extends BaseActivity<AboutPresenter> implements AboutContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_wxcode)
    TextView tvWxcode;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerAboutComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("在线班主任");
        tvWxcode.setText(SPUtils.getInstance().getString("WXCODE"));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipsUtil.showTips(message);
    }

    /**
     * View 层的默认方法 可以不实现 直接在 P 层 调用 此方法
     * Demo
     *
     * @param intent {@code intent} 不能为 {@code null}
     */
    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    @OnClick(R.id.iv_common_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @OnClick({R.id.fl_about_wechat, R.id.tv_copy_mailbox, R.id.tv_about_explain, R.id.tv_explain})
    public void onViewClicked2(View view) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        switch (view.getId()) {
            case R.id.fl_about_wechat:
//                ClipData mClipData = ClipData.newPlainText("Label", SPUtils.getInstance().getString("WXCODE"));
//                cm.setPrimaryClip(mClipData);
//                TipsUtil.showTips("客服微信已复制到粘贴板");
                Intent intent = new Intent(this, MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "客服中心");
                intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
                startActivity(intent);
                UmengUtil.umengCount(this, ConstantUmeng.MINE_CLICK_SETTINGS_ABOUT_US_WECHAT_COPY);
                break;
            case R.id.tv_copy_mailbox:
                ClipData mClipMailbox = ClipData.newPlainText("Label", getString(R.string.puhua_mailbox));
                cm.setPrimaryClip(mClipMailbox);
                TipsUtil.showTips("邮箱已复制到粘贴板");
                UmengUtil.umengCount(this, ConstantUmeng.MINE_CLICK_SETTINGS_ABOUT_US_MAILBOX_COPY);
                break;
            case R.id.tv_about_explain:
                Intent intent2 = new Intent(this, MyWebViewActivity.class);
                intent2.putExtra(BUNDLE_WEB_TITLE, getString(R.string.terms_and_privacy_policy));
                intent2.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_AGREEMENT));
                startActivity(intent2);
                UmengUtil.umengCount(this, ConstantUmeng.MINE_CLICK_SETTINGS_ABOUT_US_AGREEMENT);
                break;
            case R.id.tv_explain:
                Intent intent3 = new Intent(this, MyWebViewActivity.class);
                intent3.putExtra(BUNDLE_WEB_TITLE, getString(R.string.terms_and_privacy));
                intent3.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PRIVACY));
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}
