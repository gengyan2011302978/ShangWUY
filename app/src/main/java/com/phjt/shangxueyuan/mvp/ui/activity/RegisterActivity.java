package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.di.component.DaggerRegisterComponent;
import com.phjt.shangxueyuan.mvp.contract.RegisterContract;
import com.phjt.shangxueyuan.mvp.presenter.RegisterPresenter;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.SlidingVerificationDialog;
import com.phsxy.utils.KeyboardUtils;
import com.phsxy.utils.RegexUtils;
import com.phsxy.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_TITLE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_URL;
import static com.phjt.shangxueyuan.utils.Constant.SP_TOKEN;


/**
 * The type Register activity.
 *
 * @author: Created by Template date: 03/27/2020 10:20 company: 普华集团 description: register page
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    /**
     * The Tv title.
     */
    @BindView(R.id.tv_title)
    TextView tvTitle;
    /**
     * The Tv mobile.
     */
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    /**
     * The Et mobile.
     */
    @BindView(R.id.et_mobile)
    EditText etMobile;
    /**
     * The Tv identify code.
     */
    @BindView(R.id.tv_identify_code)
    TextView tvIdentifyCode;
    /**
     * The Et identify code.
     */
    @BindView(R.id.et_identify_code)
    EditText etIdentifyCode;
    /**
     * The Tv get identify code.
     */
    @BindView(R.id.tv_get_identify_code)
    TextView tvGetIdentifyCode;
    /**
     * The Tv countdown.
     */
    @BindView(R.id.tv_countdown)
    TextView tvCountdown;
    /**
     * The Tv register.
     */
    @BindView(R.id.tv_register)
    TextView tvRegister;
    /**
     * The Et password.
     */
    @BindView(R.id.et_password)
    EditText etPassword;
    /**
     * The Check box.
     */
    @BindView(R.id.check_box)
    CheckBox checkBox;

    private boolean canRegister;
    /**
     * 总时间
     */
    private final long DEFAULT_MILLIS = 60000;
    private long millisInFuture = DEFAULT_MILLIS;
    private CountDownTimer verifyCountdown;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegisterComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        showGetCountdownView();
    }

    @SingleClick
    @OnClick({R.id.tv_get_identify_code, R.id.tv_register, R.id.iv_back, R.id.tv_terms_policy,R.id.tv_privacy})
    public void onViewClicked(View view) {
        checkInput();
        String mobile = etMobile.getText().toString().trim();
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
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
            case R.id.tv_register:
                if (canRegister) {
                    //请求接口，
                    KeyboardUtils.hideSoftInput(this);
                    if (mPresenter != null) {
                        mPresenter.userRegister(mobile, etIdentifyCode.getText().toString().trim(), etPassword.getText().toString().trim());
                    }


                } else {
                    checkInput(true);

                }
                break;

            case R.id.tv_terms_policy:
                Intent intent = new Intent(this, MyWebViewActivity.class);
                intent.putExtra(BUNDLE_WEB_TITLE, getString(R.string.terms_and_privacy_policy));
                intent.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_AGREEMENT));
                startActivity(intent);
                break;
            case R.id.tv_privacy:
                Intent intent2 = new Intent(this, MyWebViewActivity.class);
                intent2.putExtra(BUNDLE_WEB_TITLE, getString(R.string.terms_and_privacy));
                intent2.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PRIVACY));
                startActivity(intent2);
                break;
            default:
                break;
        }
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
    }


    private void startCountdown() {
        verifyCountdown = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (tvCountdown != null) {
                    int second = (int) (millisUntilFinished / 1000);
                    tvCountdown.setText(second + "s");
                }
            }

            @Override
            public void onFinish() {
                showGetCountdownView();
            }
        }.start();
    }


    private void checkInput() {
        checkInput(false);
    }

    private void checkInput(boolean showTips) {
        canRegister = false;
        String mobile = etMobile.getText().toString().trim();
        String code = etIdentifyCode.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();
        if (checkMobile(showTips)) {
            if (TextUtils.isEmpty(code)) {
                if (showTips) {
                    showTips(getString(R.string.tips_identify_code));
                }
            } else if (code.length() < 6) {
                if (showTips) {
                    showTips(getString(R.string.tips_identify_code_error));
                }
            } else if (TextUtils.isEmpty(pwd)) {
                if (showTips) {
                    showTips(getString(R.string.tips_password_reg));
                }
            } else if (!RegexUtils.isMatch("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$", pwd)) {
                if (showTips) {
                    showTips(getString(R.string.tips_password_reg));
                }
            } else if (!checkBox.isChecked()) {
                if (showTips) {
                    showTips(getString(R.string.tips_select_and_agree));
                }
            } else {
                canRegister = true;
                if (showTips) {
                    //注册成功，跳转应用首页;
                    if (mPresenter != null) {
                        mPresenter.userRegister(mobile, code, pwd);
                    }
                }
            }
        }

        if (canRegister) {
            tvRegister.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.shape_btn_login));
        } else {
            tvRegister.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.shape_bg_register));
        }
    }

    private boolean checkMobile(boolean showTips) {
        String mobile = etMobile.getText().toString().trim();
        boolean can = false;

        if (TextUtils.isEmpty(mobile)) {
            if (showTips) {
                showTips(getString(R.string.tips_mobile));
            }
        } else if (!RegexUtils.isMobileSimple(mobile)) {
            if (showTips) {
                showTips(getString(R.string.tips_mobile_error));
            }
        } else {
            can = true;
        }
        return can;
    }


    private void showTips(String msg) {
        TipsUtil.showTips(msg);
    }

    private void showTips(@StringRes int resId) {
        TipsUtil.showTips(getString(resId));
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
     * On text changed password.
     *
     * @param text the text
     */
    @OnTextChanged(R.id.et_password)
    void onTextChangedPassword(CharSequence text) {
        checkInput();
    }

    /**
     * On checked changed.
     *
     * @param checked the checked
     */
    @OnCheckedChanged(R.id.check_box)
    void onCheckedChanged(boolean checked) {
        checkInput();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (verifyCountdown != null) {
            verifyCountdown.cancel();
        }
    }

    @Override
    public void sliderValidationSuccess() {
        TipsUtil.showTips("验证码发送成功");
        showCountdownView();
        startCountdown();
    }

    @Override
    public void sliderValidationFailed(String msg) {
        showTips(msg);
    }

    @Override
    public void registerSuccess(String bean) {
        showTips("注册成功");
        SPUtils.getInstance().put(SP_TOKEN, bean);
        startActivity(new Intent(RegisterActivity.this, HomePagerActivity.class));
        finish();
    }

    @Override
    public void registerFailed(String msg) {
        showTips("注册失败：" + msg);

    }

    @Override
    public void getVerificationCodeSuccess() {
        TipsUtil.showTips("验证码发送成功");
        showCountdownView();
        startCountdown();
    }

    @Override
    public void getVerificationCodeFailed(String msg) {
        showTips(msg);
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

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
