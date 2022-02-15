package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.di.component.DaggerForgetPasswordComponent;
import com.phjt.shangxueyuan.mvp.contract.ForgetPasswordContract;
import com.phjt.shangxueyuan.mvp.presenter.ForgetPasswordPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.CountDownTextView;
import com.phjt.shangxueyuan.widgets.dialog.SlidingVerificationDialog;
import com.phsxy.utils.KeyboardUtils;
import com.phsxy.utils.RegexUtils;
import com.phsxy.utils.SPUtils;
import com.phsxy.utils.ToastUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by Template
 * date: 03/27/2020 13:16
 * company: 普华集团
 * description: 忘记密码和绑定手机页
 */
public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordPresenter> implements ForgetPasswordContract.View {
    public static final int TYPE_FORGET_PASSWORD = 1;
    public static final int TYPE_BIND_MOBILE = 2;
    public static final int TYPE_CHANGE_PASSWORD = 3;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_identify_code)
    EditText etIdentifyCode;
    @BindView(R.id.tv_get_identify_code)
    CountDownTextView tvGetIdentifyCode;
    @BindView(R.id.tv_identify_code)
    TextView tvIdentifyCode;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private int pageType;
    private String openId;
    private String unionid;
    private String phone;
    private String profile_image_url;
    private String name;
    private String verificationCode;
    /**
     * "type":"1:短信登录   2:注册   3:忘记密码   4:绑定手机号"
     */
    private int codeType;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerForgetPasswordComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        pageType = getIntent().getIntExtra("page_type", 0);
        openId = getIntent().getStringExtra("openId");
        unionid = getIntent().getStringExtra("unionid");
        profile_image_url = getIntent().getStringExtra("profile_image_url");
        name = getIntent().getStringExtra("name");
        if (pageType == TYPE_FORGET_PASSWORD) {
            tvCommonTitle.setText("忘记密码");
            tvSubmit.setText("下一步");
            codeType = 3;
            setSpMobile();
        } else if (pageType == TYPE_BIND_MOBILE) {
            if (TextUtils.isEmpty(openId)) {
                ToastUtils.show("授权失败,请重新授权");
                finish();
                ArchitectUtils.startActivity(LoginActivity.class);
            }
            tvCommonTitle.setText("绑定手机号");
            tvSubmit.setText("确认绑定");
            codeType = 4;
        } else if (pageType == TYPE_CHANGE_PASSWORD) {
            tvCommonTitle.setText("修改密码");
            tvSubmit.setText("下一步");
            codeType = 5;
            setSpMobile();
        }


    }

    /**
     * 设置编辑手机号，禁用手机号编辑
     */
    private void setSpMobile() {
        String mobile = SPUtils.getInstance().getString(Constant.SP_MOBILE);
        if (RegexUtils.isMobileSimple(mobile)) {
            if (pageType == TYPE_CHANGE_PASSWORD) {
                // 修改密码时，手机号中间几位用*加密
                this.phone = mobile;
                // 把加密前的手机号保存
                mobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            }
            etMobile.setText(mobile);
            etMobile.setEnabled(false);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.tv_get_identify_code, R.id.iv_common_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_identify_code:
                if (checkMobile(true)) {
                    showSign(phone);
                }
                break;
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_submit:
                doSubmit();
                break;
            default:
                break;
        }
    }

    @SingleClick
    private void doSubmit() {
        if (checkInput(true)) {
            KeyboardUtils.hideSoftInput(this);
            if (pageType == TYPE_FORGET_PASSWORD || pageType == TYPE_CHANGE_PASSWORD && mPresenter != null) {
                mPresenter.validateCode(phone, verificationCode, codeType);
            } else if (pageType == TYPE_BIND_MOBILE && !TextUtils.isEmpty(openId) && !TextUtils.isEmpty(unionid) && mPresenter != null) {
                mPresenter.bindingPhone(phone, openId, verificationCode, profile_image_url, name, unionid);
            }
        }
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


    private void checkInput() {
        checkInput(false);
    }

    private boolean checkInput(boolean showTips) {
        boolean canSubmit = false;
        verificationCode = etIdentifyCode.getText().toString().trim();
        if (checkMobile(showTips)) {
            if (TextUtils.isEmpty(verificationCode)) {
                if (showTips) {
                    showTips(getString(R.string.tips_identify_code));
                }
            } else if (verificationCode.length() < 6) {
                if (showTips) {
                    showTips(getString(R.string.tips_identify_code_error));
                }
            } else {
                canSubmit = true;
            }
        }

        if (canSubmit) {
            tvSubmit.setBackground(ContextCompat.getDrawable(ForgetPasswordActivity.this, R.drawable.shape_btn_login));
        } else {
            tvSubmit.setBackground(ContextCompat.getDrawable(ForgetPasswordActivity.this, R.drawable.shape_bg_register));
        }
        if (pageType == TYPE_CHANGE_PASSWORD) {
            // v1.0.2只是对修改密码“下一步”置灰时不可点击，其他情况没说，所以其他还保持之前逻辑
            tvSubmit.setEnabled(canSubmit);
        }
        return canSubmit;
    }

    private boolean checkMobile(boolean showTips) {
        if (pageType != TYPE_CHANGE_PASSWORD) {
            // 修改密码时，手机号已用*加密，且加密前的手机号已保存到phone，无需再取
            this.phone = etMobile.getText().toString().trim();
        }
        boolean can = false;

        if (TextUtils.isEmpty(phone)) {
            if (showTips) {
                showTips(getString(R.string.tips_mobile));
            }
        } else if (!RegexUtils.isMobileSimple(phone)) {
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

    private void showSign(String mobile) {
        this.phone = mobile;
        new SlidingVerificationDialog(this, new SlidingVerificationDialog.OnSlidingListener() {
            @Override
            public void OnSlidingEnd(String sessionId) {

            }

            @Override
            public void onSlidingEnd(String sessionId, String sig, String token, String scene) {
                {
                    if (TextUtils.isEmpty(sessionId)) {
                        TipsUtil.showTips("验证失败,请稍后重试");
                    } else {
                        if (null != mPresenter) {
                            Objects.requireNonNull(mPresenter).sliderValidation(sessionId, sig, token, scene, phone, codeType);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void sliderValidationSuccess() {
        TipsUtil.showTips("验证码发送成功");
        tvGetIdentifyCode.startCountDown();

    }

    @Override
    public void sliderValidationFailed(String msg) {
        TipsUtil.showTips(msg);
    }

    @Override
    public void sendVerficationSuccess() {
        TipsUtil.showTips("验证码发送成功");
        tvGetIdentifyCode.startCountDown();
    }

    @Override
    public void sendVerficationFailed(String msg) {
        TipsUtil.showTips(msg);
    }

    @Override
    public void loginSuccess(String token) {
        SPUtils.getInstance().put(Constant.SP_TOKEN, token);
        SPUtils.getInstance().remove(Constant.SP_TEMP_LOGOUT);

        actionLogin();
    }

    @Override
    public void loginFailed(String msg) {
        TipsUtil.showTips(msg);
    }

    @Override
    public void validateCodeSuccess() {
        Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
        intent.putExtra("page_type", pageType);
        intent.putExtra("phone", phone);
        intent.putExtra("verificationCode", verificationCode);
        startActivityForResult(intent, 101);
    }

    @Override
    public void validateCodeFailed(String msg) {
        TipsUtil.showTips(msg);
    }

    private void actionLogin() {
        Intent intent = new Intent(ForgetPasswordActivity.this, HomePagerActivity.class);
        startActivity(intent);
        finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            finish();
        }
    }
}
