package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.base.utils.LogUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.UpdateAppBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.di.component.DaggerLoginComponent;
import com.phjt.shangxueyuan.mvp.contract.LoginContract;
import com.phjt.shangxueyuan.mvp.presenter.LoginPresenter;
import com.phjt.shangxueyuan.utils.AppUtils;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.LoadingDialog;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.GetConfig;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.shangxueyuan.widgets.dialog.SlidingVerificationDialog;
import com.phsxy.utils.KeyboardUtils;
import com.phsxy.utils.RegexUtils;
import com.phsxy.utils.SPUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.phjt.shangxueyuan.mvp.ui.activity.ForgetPasswordActivity.TYPE_BIND_MOBILE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_LINK_ADDRESS;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_LINK_TYPE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_PLANET_PHONE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_TITLE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_URL;
import static com.phjt.shangxueyuan.utils.UmengUtil.onEventLoginPage;


/**
 * @author: Created by Template
 * date: 03/26/2020 10:49
 * company: 普华集团
 * description: login page
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.tv_identify_code)
    TextView tvIdentifyCode;
    @BindView(R.id.et_identify_code)
    EditText etIdentifyCode;
    @BindView(R.id.tv_get_identify_code)
    TextView tvGetIdentifyCode;
    @BindView(R.id.tv_countdown)
    TextView tvCountdown;
    @BindView(R.id.iv_planet)
    TextView ivPlanet;
    @BindView(R.id.iv_agreement)
    ImageView mIvAgreement;

    /**
     * 总时间
     */
    private final long DEFAULT_MILLIS = 60000;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private long millisInFuture = DEFAULT_MILLIS;
    private CountDownTimer verifyCountdown;

    private String mOpenId = "";
    private String unionid = "";
    private String name = "";
    private String profile_image_url = "";
    private String linkType;
    private String linkAddress;
    private String planetPhone;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        showGetCountdownView();
        checkFromPlanet(getIntent().getStringExtra("planet_uuid"));
        planetPhone = getIntent().getStringExtra(BUNDLE_PLANET_PHONE);
        ivBack.setVisibility(TextUtils.isEmpty(planetPhone) ? View.GONE : View.VISIBLE);
        linkType = getIntent().getStringExtra(BUNDLE_LINK_TYPE);
        linkAddress = getIntent().getStringExtra(BUNDLE_LINK_ADDRESS);
        if (mPresenter != null) {
            mPresenter.checkLoginType();
            mPresenter.getCheckVersion();
        }
        ConstantWeb.getHttpWebAddressList(this);
    }

    private void checkFromPlanet(String planetUuid) {
        if (!TextUtils.isEmpty(planetUuid)) {
            if (!LoginActivity.this.isFinishing()) {
                LoadingDialog.getInstance().show(this);
            }
            if (mPresenter != null) {
                mPresenter.doLoginByPlanetUuid(planetUuid);
            }
        }
    }

    /**
     * On text changed mobile.
     *
     * @param text the text
     */
    @OnTextChanged(R.id.et_mobile)
    void onTextChangedMobile(CharSequence text) {
        checkInput();
    }

    /**
     * On text changed identify.
     *
     * @param text the text
     */
    @OnTextChanged(R.id.et_identify_code)
    void onTextChangedIdentify(CharSequence text) {
        checkInput();
    }


    /**
     * On view clicked.
     *
     * @param view the view
     */
    @SingleClick
    @OnClick({R.id.tv_login, R.id.tv_register, R.id.tv_forgot_password, R.id.iv_planet, R.id.iv_wechat, R.id.iv_by_mobile, R.id.tv_terms_policy,
            R.id.tv_get_identify_code, R.id.tv_privacy, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                if (agreementCheck() && checkInput(true)) {
                    KeyboardUtils.hideSoftInput(this);
                    if (mPresenter != null) {
                        mPresenter.quickLogin(etMobile.getText().toString().trim(), etIdentifyCode.getText().toString().trim());
                    }
                }
                break;
            case R.id.tv_register:
                actionRegister();
                break;
            case R.id.tv_forgot_password:
                onEventLoginPage(this, "忘记密码按钮", UmengUtil.EVENT_FORGET_LOGIN);
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class).putExtra("page_type", ForgetPasswordActivity.TYPE_FORGET_PASSWORD));
                break;
            case R.id.iv_planet:
                if (agreementCheck()) {
                    AppUtils.wakeUpPlanet(LoginActivity.this, true);
                }
                break;
            case R.id.iv_wechat:
                if (agreementCheck()) {
                    boolean isWeChatInstall = UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN);
                    onEventLoginPage(this, "微信登录按钮", UmengUtil.EVENT_WX_LOGIN);
                    if (isWeChatInstall) {
                        //微信登录
                        UMShareAPI.get(this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, mAuthListener);
                        /// mPresenter.weChatLogin("oWvy61NSZEjsT5vRSEdoTAU-qhio");
                    } else {
                        showTips("请先安装应用");
                    }
                }
                break;
            case R.id.iv_by_mobile:
                actionLoginByMobile();
                break;
            case R.id.tv_terms_policy:
                onEventLoginPage(this, "用户协议", UmengUtil.EVENT_USER_DOC);
                Intent intent = new Intent(this, MyWebViewActivity.class);
                intent.putExtra(BUNDLE_WEB_TITLE, getString(R.string.terms_and_privacy_policy));
                intent.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_AGREEMENT));
                startActivity(intent);
                break;
            case R.id.tv_privacy:
                onEventLoginPage(this, "隐私政策", UmengUtil.EVENT_USER_DOC);
                Intent intent2 = new Intent(this, MyWebViewActivity.class);
                intent2.putExtra(BUNDLE_WEB_TITLE, getString(R.string.terms_and_privacy));
                intent2.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PRIVACY));
                startActivity(intent2);
                break;
            case R.id.tv_get_identify_code:
                if (checkMobile(true)) {
                    new SlidingVerificationDialog(this, new SlidingVerificationDialog.OnSlidingListener() {
                        @Override
                        public void OnSlidingEnd(String sessionId) {

                        }

                        @Override
                        public void onSlidingEnd(String csessionid, String sig, String token, String scene) {

                            if (mPresenter != null) {
                                String mobile = etMobile.getText().toString().trim();
                                mPresenter.sliderValidation(csessionid, sig, token, scene, mobile);
                            }
                        }
                    });
                }
                break;
            case R.id.iv_back:
                AppUtils.wakeUpPlanet(LoginActivity.this, false);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.iv_agreement)
    public void onClick(View view) {
        if (mIvAgreement.isSelected()) {
            mIvAgreement.setSelected(false);
            mIvAgreement.setImageResource(R.drawable.chose_multiple);
        } else {
            mIvAgreement.setSelected(true);
            mIvAgreement.setImageResource(R.drawable.chose_multiple_right);
        }
    }

    UMAuthListener mAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            if (share_media != null) {
                LogUtils.warnInfo("onStart===================" + share_media.getName());
            }
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {


            if (!LoginActivity.this.isFinishing()) {
                LoadingDialog.getInstance().show(LoginActivity.this);
            }
            if (share_media != null && map != null) {
                LogUtils.warnInfo("onComplete===================" + share_media.getName() + "------------" + map.toString());
            }

            //获取openid，进行登录操作
            if (map != null) {
                mOpenId = map.get("openid");
                unionid = map.get("unionid");
                profile_image_url = map.get("profile_image_url");
                name = map.get("name");
                if (!TextUtils.isEmpty(mOpenId) && !TextUtils.isEmpty(unionid) && mPresenter != null) {
                    mPresenter.weChatLogin(mOpenId, unionid);
                } else {
                    showTips("授权失败");
                    if (!LoginActivity.this.isFinishing()) {
                        LoadingDialog.getInstance().dismiss();
                    }
                }
            } else {
                showTips("授权失败");
                LogUtils.warnInfo("onComplete===================" + "获取微信信息为空");
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            if (throwable != null) {
                LogUtils.warnInfo("onError===================" + throwable.toString());
                showTips("授权失败");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            if (share_media != null) {
                LogUtils.warnInfo("onCancel===================" + share_media.getName());
            }
        }
    };

    private boolean agreementCheck() {
        if (!mIvAgreement.isSelected()) {
            showMessage("请阅读并勾选页面协议");
            return false;
        }
        return true;
    }

    private void checkInput() {
        checkInput(false);
    }

    private boolean checkInput(boolean showTips) {
        boolean canLogin = false;
        String code = etIdentifyCode.getText().toString().trim();
        if (checkMobile(showTips)) {
            if (TextUtils.isEmpty(code)) {
                if (showTips) {
                    TipsUtil.showTips(getString(R.string.tips_identify_code));
                }
            } else if (code.length() < 6) {
                if (showTips) {
                    TipsUtil.showTips(getString(R.string.tips_identify_code_error));
                }
            } else {
                canLogin = true;
            }
        }
        if (canLogin) {
            tvLogin.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.shape_btn_login));
        } else {
            tvLogin.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.shape_bg_register));
        }
        return canLogin;
    }


    private boolean checkMobile(boolean showTips) {
        String mobile = etMobile.getText().toString().trim();
        boolean can = false;

        if (TextUtils.isEmpty(mobile)) {
            if (showTips) {
                TipsUtil.showTips(getString(R.string.tips_mobile));
            }
        } else if (!RegexUtils.isMobileSimple(mobile)) {
            if (showTips) {
                TipsUtil.showTips(getString(R.string.tips_mobile_error));
            }
        } else {
            can = true;
        }
        return can;
    }

    private void showCountdownView() {
        if (tvGetIdentifyCode != null) {
            tvGetIdentifyCode.setVisibility(View.GONE);
        }
        if (tvCountdown != null) {
            tvCountdown.setText((int) (millisInFuture / 1000) + "s");
            tvCountdown.setVisibility(View.VISIBLE);
        }
    }

    private void showGetCountdownView() {
        if (tvCountdown != null) {
            tvCountdown.setVisibility(View.GONE);
        }
        if (tvGetIdentifyCode != null) {
            tvGetIdentifyCode.setVisibility(View.VISIBLE);
        }
        onEventLoginPage(this, "注册页曝光", UmengUtil.EVENT_BG_LOGIN);
    }

    private void startCountdown() {
        verifyCountdown = new CountDownTimer(millisInFuture, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (tvCountdown != null) {
                    int second = (int) (millisUntilFinished / 1000);
                    tvCountdown.setText(String.format("%ds", second));
                }
            }

            @Override
            public void onFinish() {
                showGetCountdownView();
            }
        }.start();
    }

    @Override
    public void sliderValidationSuccess() {
        TipsUtil.showTips("验证码发送成功");
        showCountdownView();
        startCountdown();
    }

    @Override
    public void sliderValidationFailed(String msg) {
        TipsUtil.showTips(msg);
    }

    @Override
    public void quickLoginSuccess(String token) {
        onEventLoginPage(this, "登录注册按钮", UmengUtil.EVENT_BUTTON_LOGIN);
        SPUtils.getInstance().put(Constant.SP_TOKEN, token);
        SPUtils.getInstance().put(Constant.SP_MOBILE, etMobile.getText().toString());
        SPUtils.getInstance().remove(Constant.SP_TEMP_LOGOUT);
        actionLogin();
    }

    @Override
    public void quickLoginFailed(String msg) {
        TipsUtil.showTips(msg);
    }


    @Override
    public void getVerificationCodeSuccess() {
        TipsUtil.showTips("验证码发送成功");
        showCountdownView();
        startCountdown();
    }


    @Override
    public void getVerificationCodeFailed(String msg) {
        TipsUtil.showTips(msg);
    }

    @Override
    public void getUserInfoSuccess(UserInfoBean userInfoBean) {
        SPUtils.getInstance().put(Constant.SP_MOBILE, userInfoBean.getMobile());
        actionLogin();
    }

    @Override
    public void showUpdateDialog(UpdateAppBean count) {
        if (count.getVersionCode() > GetConfig.getVerCode(this)) {
            DialogUtils.AppUpdateDialog(this, "V" + count.getVersionName(), count.getDescription(), count.getVersionUrl());
        }
    }

    private void showTips(String msg) {
        TipsUtil.showTips(msg);
    }

    private void showTips(@StringRes int resId) {
        TipsUtil.showTips(getString(resId));
    }

    private void actionRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void actionLogin() {
        Intent intent = new Intent(LoginActivity.this, HomePagerActivity.class);
        intent.putExtra(BUNDLE_PLANET_PHONE, planetPhone);
        intent.putExtra(BUNDLE_LINK_TYPE, linkType);
        intent.putExtra(BUNDLE_LINK_ADDRESS, linkAddress);
        startActivity(intent);
        finish();
    }

    private void actionLoginByMobile() {
        Intent intent = new Intent(LoginActivity.this, MobileLoginActivity.class);
        startActivity(intent);
    }


    @Override
    public void loginSuccess(String token) {
        mPresenter.getUserInfo();
        SPUtils.getInstance().put(Constant.SP_TOKEN, token);
        SPUtils.getInstance().remove(Constant.SP_TEMP_LOGOUT);
    }

    @Override
    public void loginFailedByWx(String msg) {
        goBindMobile();
    }

    @Override
    public void doLoginFailed(String message) {
        showTips(message);
    }

    @Override
    public void doLoginSuccess(String token) {
        SPUtils.getInstance().put(Constant.SP_MOBILE, etMobile.getText().toString());
        SPUtils.getInstance().put(Constant.SP_TOKEN, token);
        SPUtils.getInstance().remove(Constant.SP_TEMP_LOGOUT);

        actionLogin();
    }

    @Override
    public void checkLoginTypeSuccess(boolean show) {
        ivPlanet.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void goBindMobile() {
        Intent it = new Intent(this, ForgetPasswordActivity.class);
        it.putExtra("openId", mOpenId);
        it.putExtra("unionid", unionid);
        it.putExtra("name", name);
        it.putExtra("profile_image_url", profile_image_url);
        it.putExtra("page_type", TYPE_BIND_MOBILE);
        startActivity(it);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (verifyCountdown != null) {
            verifyCountdown.cancel();
        }
        LoadingDialog.getInstance().dismiss();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        TipsUtil.showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        ArchitectUtils.startActivity(intent);
    }
}
