package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.AppManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.app.AppLifecyclesImpl;
import com.phjt.shangxueyuan.bean.UpdateAppBean;
import com.phjt.shangxueyuan.common.MyBusiness;
import com.phjt.shangxueyuan.di.component.DaggerSetUpComponent;
import com.phjt.shangxueyuan.mvp.contract.SetUpContract;
import com.phjt.shangxueyuan.mvp.presenter.SetUpPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.SlideButton;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.SPUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;

/**
 * @author: Roy
 * date:    2020/03/27
 * company: 普华集团
 * description: 设置页面
 */
public class SetUpActivity extends BaseActivity<SetUpPresenter> implements SetUpContract.View {

    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_info_avatar_label)
    TextView tvInfoAvatarLabel;
    @BindView(R.id.tv_out_login)
    TextView tvOutLogin;
    @BindView(R.id.iv_updates)
    ImageView ivUpdates;
    @BindView(R.id.tv_updates)
    TextView tvUpdates;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @BindView(R.id.fl_authentication)
    FrameLayout flAuthentication;
    @BindView(R.id.tv_authentication)
    TextView tvAuthentication;

    @BindView(R.id.sb_set_wifi)
    SlideButton mSlideButton;
    private RotateAnimation rotate;
    private int mCertificationStatus;

    public int state = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerSetUpComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_set_up;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("设置");
        tvVersion.setText("当前版本号V" + BuildConfig.VERSION_NAME);
        flAuthentication.setEnabled(true);
        setSlideButton();
        initAnimation();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.isUserAuth(1);
            mPresenter.isUserAuth(2);
        }
    }

    private void setSlideButton() {
        mSlideButton.setSmallCircleModel(ContextCompat.getColor(this, R.color.color_4E7EF9),
                ContextCompat.getColor(this, R.color.color_4E7EF9),
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.color_CCCCCC));
        boolean ifWifi = SPUtils.getInstance().getBoolean(Constant.SP_SET_WIFI_DOWNLOAD, false);
        if (ifWifi) {
            mSlideButton.setChecked(true);
        } else {
            mSlideButton.setChecked(false);
        }

        mSlideButton.setOnCheckedListener(new SlideButton.SlideButtonOnCheckedListener() {
            @Override
            public void onCheckedChangeListener(boolean isChecked) {
                if (isChecked) {
                    showWifiDownloadDialog();
                } else {
                    SPUtils.getInstance().put(Constant.SP_SET_WIFI_DOWNLOAD, false);
                }
            }
        });

    }

    private void initAnimation() {
        if (rotate == null) {
            rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            LinearInterpolator lin = new LinearInterpolator();
            rotate.setInterpolator(lin);
            //设置动画持续周期
            rotate.setDuration(2000);
            //设置重复次数
            rotate.setRepeatCount(-1);
            //动画执行完后是否停留在执行完的状态
            rotate.setFillAfter(true);
            //执行前的等待时间
            rotate.setStartOffset(10);
        }
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


    @OnClick({R.id.iv_common_back, R.id.fl_change_password, R.id.fl_about_us, R.id.tv_out_login,
            R.id.iv_updates, R.id.tv_updates, R.id.tv_version, R.id.fl_authentication, R.id.fl_change_security_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.fl_change_password:
                //修改密码
                startActivity(new Intent(SetUpActivity.this, ForgetPasswordActivity.class)
                        .putExtra("page_type", ForgetPasswordActivity.TYPE_CHANGE_PASSWORD));
                UmengUtil.umengCount(this, ConstantUmeng.MINE_CLICK_SETTINGS_CHANGE_PWD);
                break;
            case R.id.fl_about_us:
                //关于我们
                goAboutUs();
                UmengUtil.umengCount(this, ConstantUmeng.MINE_CLICK_SETTINGS_ABOUT_US);
                break;
            case R.id.tv_out_login:
                showExitDialog();
                break;

            case R.id.iv_updates:
            case R.id.tv_updates:
            case R.id.tv_version:
                //检查更新
                if (mPresenter != null) {
                    tvUpdates.setEnabled(false);
                    ivUpdates.setEnabled(false);
                    tvVersion.setEnabled(false);
                    mPresenter.getCheckVersion(SetUpActivity.this);
                    ivUpdates.startAnimation(rotate);
                }
                break;

            case R.id.fl_authentication:
                //身份认证
                Intent intent = new Intent(this, AuthenticationActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_change_security_password:
                //修改安全密码
                Intent intent2 = new Intent(this, ChangeSecurityPasswordActivity.class);
                intent2.putExtra(Constant.CERTIFICATION_STATUS, mCertificationStatus);
                startActivity(intent2);

                break;
            default:
                break;
        }
    }

    private void goAboutUs() {
        Intent intent = new Intent(SetUpActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    /**
     * 清流量下载
     */
    public void showWifiDownloadDialog() {
        DialogUtils.showWifiDownloadDialog(this, "运营商网络下载可能会消耗大量流量，确认开启？", "关闭", "开启",
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickCancel() {
                        mSlideButton.setChecked(false);
                        SPUtils.getInstance().put(Constant.SP_SET_WIFI_DOWNLOAD, false);
                    }

                    @Override
                    public void clickSure() {
                        mSlideButton.setChecked(true);
                        SPUtils.getInstance().put(Constant.SP_SET_WIFI_DOWNLOAD, true);
                        UmengUtil.umengCount(SetUpActivity.this, ConstantUmeng.MINE_CLICK_SETTINGS_MOBILE_NETWORK);
                    }
                });
    }

    /**
     * 退出弹框提示
     */
    public void showExitDialog() {
        DialogUtils.showCancelSureDialog(this, "提示",
                "你确定要退出登录？", getResources().getString(R.string.quit_cancel),
                getResources().getString(R.string.quit_sure),
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        if (mPresenter != null) {
                            mPresenter.outLogin(SetUpActivity.this);
                        }

                    }
                });

    }

    @Override
    public void loadOutLoginSuccess() {
        logoutIm();
    }

    @Override
    public void loadOutLoginFailed() {
        logoutIm();
    }

    @Override
    public void passwordSuccess(int status) {
        if (status == 0) {
            mCertificationStatus = 2;
        }
    }

    @Override
    public void userAuthSuccess(int status) {
        if (status == 0) {
            flAuthentication.setEnabled(false);
            tvAuthentication.setText("已认证");
            tvAuthentication.setTextColor(ContextCompat.getColor(this, R.color.color_FF999999));
        }
    }

    private void logoutIm() {
        MyBusiness.loginOut();
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(SetUpActivity.this).setShareConfig(config);

        UMShareAPI.get(SetUpActivity.this).deleteOauth(SetUpActivity.this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                LogUtils.d("onStart: " + share_media);

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                LogUtils.d("onComplete: " + share_media);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                LogUtils.d("onError: " + share_media);

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                LogUtils.d("onCancel: " + share_media);

            }
        });
        AppManager.getAppManager().killAll();
        ArchitectUtils.startActivity(LoginActivity.class);

        //友盟推送——移除Tag
        AppLifecyclesImpl.getPushAgent().getTagManager().deleteTags((b, result) -> LogUtils.e("===============删除Tag绑定" + b),
                Constant.PUSH_IS_LOGIN);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (rotate != null) {
            rotate.cancel();
        }

    }

    @Override
    public void showUpdateDialog(UpdateAppBean bean) {
        findViewById(android.R.id.content).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ivUpdates != null) {
                    ivUpdates.clearAnimation();
                    tvUpdates.setEnabled(true);
                    ivUpdates.setEnabled(true);
                    tvVersion.setEnabled(true);
                    if (bean.getVersionCode() > BuildConfig.VERSION_CODE) {
                        DialogUtils.AppUpdateDialog(SetUpActivity.this, "V" + bean.getVersionName(), bean.getDescription(), bean.getVersionUrl());
                    } else {
                        showTips("已是最新版本");
                    }
                }
            }
        }, 2000);
    }

    @Override
    public void LoadFailed() {
        findViewById(android.R.id.content).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ivUpdates != null) {
                    ivUpdates.clearAnimation();
                    tvUpdates.setEnabled(true);
                    ivUpdates.setEnabled(true);
                    tvVersion.setEnabled(true);
                }
            }
        }, 2000);

    }



}
