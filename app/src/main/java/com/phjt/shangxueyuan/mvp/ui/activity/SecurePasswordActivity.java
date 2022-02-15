package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerSecurePassworComponent;
import com.phjt.shangxueyuan.mvp.contract.SecurePassworContract;
import com.phjt.shangxueyuan.mvp.presenter.SecurePassworPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.CountDownTextView;
import com.phjt.shangxueyuan.widgets.dialog.SlidingVerificationDialog;
import com.phsxy.utils.KeyboardUtils;
import com.phsxy.utils.SPUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :
 * description :找回安全密码
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/12 11:18
 */
public class SecurePasswordActivity extends BaseActivity<SecurePassworPresenter> implements SecurePassworContract.View, TextWatcher {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.et_identify_code)
    EditText etIdentifyCode;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_affirm_password)
    EditText etAffirmPassword;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;

    @BindView(R.id.tv_get_identify_code)
    CountDownTextView tvGetIdentifyCode;


    @BindView(R.id.iv_display_new)
    ImageView ivDisplayNew;
    @BindView(R.id.iv_conceal_new)
    ImageView ivConcealNew;
    @BindView(R.id.iv_display_affirm)
    ImageView ivDisplayAffirm;
    @BindView(R.id.iv_conceal_affirm)
    ImageView ivConcealAffirm;

    private String verificationCode, mobile;
    private boolean newPasswordPassword, affirmPassword;
    private int mCertificationStatus;
    /**
     * "type":"1:短信登录   2:注册   3:忘记密码   4:绑定手机号 5:修改密码 6:ios设备绑定 7弟子群认证 8考试提醒 9 邀请注册，10找回安全密码"
     */
    private int codeType = 10;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerSecurePassworComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_secure_passwor;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("找回安全密码");
        tvSubmit.setEnabled(false);
        mobile = SPUtils.getInstance().getString(Constant.SP_MOBILE);
        if (!TextUtils.isEmpty(mobile)) {
            String phoneNumber = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            tvPhoneNum.setText(String.format("已绑定手机号 %s", phoneNumber));
        }
        tvGetIdentifyCode.setCircle(1);

        etIdentifyCode.addTextChangedListener(this);
        etAffirmPassword.addTextChangedListener(this);
        etNewPassword.addTextChangedListener(this);
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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


    @OnClick({R.id.iv_common_back, R.id.tv_get_identify_code, R.id.tv_submit, R.id.iv_display_new, R.id.iv_conceal_new, R.id.iv_display_affirm, R.id.iv_conceal_affirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_get_identify_code:
                if (!TextUtils.isEmpty(mobile)) {
                    showSign();
                }
                break;
            case R.id.tv_submit:
                checkMobile();
                break;
            case R.id.iv_display_new:
                setShowHide(etNewPassword, newPasswordPassword, 1);
                break;
            case R.id.iv_conceal_new:
                etNewPassword.setText("");
                break;
            case R.id.iv_display_affirm:
                setShowHide(etAffirmPassword, affirmPassword, 2);
                break;
            case R.id.iv_conceal_affirm:
                etAffirmPassword.setText("");
                break;
            default:
                break;
        }
    }

    /**
     * 密码的明文显示和隐藏
     *
     * @param editText
     * @param isShow
     * @param type
     */
    private void setShowHide(EditText editText, boolean isShow, int type) {
        if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
            if (isShow) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                editText.setSelection(editText.length());
                if (type == 1) {
                    newPasswordPassword = false;
                } else if (type == 2) {
                    affirmPassword = false;
                }

            } else {
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                editText.setSelection(editText.length());
                if (type == 1) {
                    newPasswordPassword = true;
                } else if (type == 2) {
                    affirmPassword = true;
                }
            }
        }

    }


    private void showSign() {

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
                            Objects.requireNonNull(mPresenter).sliderValidation(sessionId, sig, token, scene, mobile, codeType);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void sliderValidationSuccess() {
        tvGetIdentifyCode.startCountDown();

    }

    @Override
    public void sliderValidationFailed(String msg) {
        TipsUtil.showTips(msg);
    }

    @Override
    public void modifySuccess() {
        TipsUtil.show("新密码设置成功");
        finish();
    }

    @Override
    public void modifyFailed() {

    }

    private void checkMobile() {
        KeyboardUtils.hideSoftInput(this);
        String verificationCode = etIdentifyCode.getText().toString().trim();
        String mRegex = "(?![0-9A-Z]+$)(?![0-9a-z]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
        if (TextUtils.isEmpty(verificationCode)) {
            TipsUtil.show(getString(R.string.tips_identify_code));
            return;
        }
        if (verificationCode.length() < 6) {
            TipsUtil.show(getString(R.string.tips_identify_code_error));
            return;
        }
        String sAffirmPassword = etAffirmPassword.getText().toString().trim();
        String sNewPassword = etNewPassword.getText().toString().trim();
        if (!sNewPassword.matches(mRegex)) {
            TipsUtil.show("新密码需要输入8-20位 大小字母、数字组合");
            return;
        }

        if (!sAffirmPassword.equals(sNewPassword)) {
            TipsUtil.show("两次密码输入不一致");
            return;
        }
        if (mPresenter != null && !TextUtils.isEmpty(mobile)) {
            mPresenter.modifyPassword(mobile, "", sNewPassword, sAffirmPassword, verificationCode, 2);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String verificationCode = etIdentifyCode.getText().toString().trim();
        String sAffirmPassword = etAffirmPassword.getText().toString().trim();
        String sNewPassword = etNewPassword.getText().toString().trim();
        if (sNewPassword.length() > 0 && sAffirmPassword.length() > 0 && verificationCode.length() > 0) {
            tvSubmit.setBackgroundResource(R.drawable.shape_id_card);
            tvSubmit.setEnabled(true);
        } else {
            tvSubmit.setEnabled(false);
            tvSubmit.setBackgroundResource(R.drawable.shape_gray);
        }

        if (sNewPassword.length() > 0) {
            ivDisplayNew.setVisibility(View.VISIBLE);
            ivConcealNew.setVisibility(View.VISIBLE);

        } else {
            ivDisplayNew.setVisibility(View.GONE);
            ivConcealNew.setVisibility(View.GONE);
        }

        if (sAffirmPassword.length() > 0) {
            ivDisplayAffirm.setVisibility(View.VISIBLE);
            ivConcealAffirm.setVisibility(View.VISIBLE);
        } else {
            ivDisplayAffirm.setVisibility(View.GONE);
            ivConcealAffirm.setVisibility(View.GONE);
        }

    }
}
