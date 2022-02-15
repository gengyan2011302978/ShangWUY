package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
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
import com.phjt.shangxueyuan.di.component.DaggerMobileLoginComponent;
import com.phjt.shangxueyuan.mvp.contract.MobileLoginContract;
import com.phjt.shangxueyuan.mvp.presenter.MobileLoginPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.LoadingDialog;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phsxy.utils.KeyboardUtils;
import com.phsxy.utils.RegexUtils;
import com.phsxy.utils.SPUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.mvp.ui.activity.ForgetPasswordActivity.TYPE_BIND_MOBILE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_TITLE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_URL;
import static com.phjt.shangxueyuan.utils.UmengUtil.onEventLoginPage;


/**
 * @author: Created by Template
 * date: 03/30/2020 10:42
 * company: 普华集团
 * description: login by mobile
 */
public class MobileLoginActivity extends BaseActivity<MobileLoginPresenter> implements MobileLoginContract.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.iv_agreement)
    ImageView mIvAgreement;


    private String mOpenId = "";
    private String unionid = "";
    private String name = "";
    private String profile_image_url = "";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMobileLoginComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_mobile_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @SingleClick
    @OnClick({R.id.iv_back, R.id.tv_login, R.id.tv_register, R.id.tv_forgot_password, R.id.tv_terms_policy, R.id.iv_wechat, R.id.iv_by_mobile, R.id.tv_privacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.iv_by_mobile:
                finish();
                break;

            case R.id.tv_login:
                if (agreementCheck() && checkInput(true)) {
                    KeyboardUtils.hideSoftInput(this);
                    onEventLoginPage(this, "密码登录按钮", UmengUtil.EVENT_PASSWORD_LOGIN);
                    if (mPresenter != null) {
                        mPresenter.doLogin(etMobile.getText().toString().trim(), etPassword.getText().toString().trim());
                    }
                }
                break;
            case R.id.tv_register:
                actionRegister();
                break;
            case R.id.tv_forgot_password:
                actionForgotPassword();
                onEventLoginPage(this, "忘记密码按钮", UmengUtil.EVENT_FORGET_LOGIN);
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

            case R.id.iv_wechat:
                if (agreementCheck()) {
                    boolean isWeChatInstall = UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN);
                    if (isWeChatInstall) {
                        //微信登录
                        UMShareAPI.get(this).getPlatformInfo(MobileLoginActivity.this, SHARE_MEDIA.WEIXIN, mAuthListener);
                        /// mPresenter.weChatLogin("oWvy61NSZEjsT5vRSEdoTAU-qhio");
                    } else {
                        showTips("请先安装应用");
                    }
                }
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

            LoadingDialog.getInstance().show(MobileLoginActivity.this);
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
                    LoadingDialog.getInstance().dismiss();
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
        String mobile = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            if (showTips) {
                showTips(R.string.tips_mobile);
            }
        } else if (!RegexUtils.isMobileSimple(mobile)) {
            if (showTips) {
                showTips(R.string.tips_mobile_error);
            }
        } else if (TextUtils.isEmpty(password)) {
            if (showTips) {
                showTips(R.string.tips_password);
            }
        } else if (password.length() < 6) {
            if (showTips) {
                showTips(R.string.tips_password);
            }
        } else if (!RegexUtils.isMatch("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$", password)) {
            if (showTips) {
                showTips(R.string.tips_password_error);
                ///etPassword.setText("");
            }
        } else {
            canLogin = true;
        }

        if (canLogin) {
            tvLogin.setBackground(ContextCompat.getDrawable(MobileLoginActivity.this, R.drawable.shape_btn_login));
        } else {
            tvLogin.setBackground(ContextCompat.getDrawable(MobileLoginActivity.this, R.drawable.shape_bg_register));
        }

        return canLogin;
    }

    private void showTips(String msg) {
        TipsUtil.showTips(msg);
    }

    private void showTips(@StringRes int resId) {
        TipsUtil.showTips(getString(resId));
    }


    private void actionRegister() {
        Intent intent = new Intent(MobileLoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    private void actionLogin() {
        Intent intent = new Intent(MobileLoginActivity.this, HomePagerActivity.class);
        startActivity(intent);
        finish();
    }

    private void actionForgotPassword() {
        startActivity(new Intent(MobileLoginActivity.this, ForgetPasswordActivity.class).putExtra("page_type", ForgetPasswordActivity.TYPE_FORGET_PASSWORD));
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
     * On text changed password.
     *
     * @param text the text
     */
    @OnTextChanged(R.id.et_password)
    void onTextChangedPassword(CharSequence text) {
        checkInput();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void loginSuccess(String token) {
        SPUtils.getInstance().put(Constant.SP_TOKEN, token);
        SPUtils.getInstance().remove(Constant.SP_TEMP_LOGOUT);
        actionLogin();
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
        SPUtils.getInstance().put(Constant.SP_TOKEN, token);
        SPUtils.getInstance().remove(Constant.SP_TEMP_LOGOUT);

        actionLogin();
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
