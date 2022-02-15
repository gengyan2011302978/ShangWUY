package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;

import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.di.component.DaggerDiscipleGroupComponent;
import com.phjt.shangxueyuan.mvp.contract.DiscipleGroupContract;
import com.phjt.shangxueyuan.mvp.presenter.DiscipleGroupPresenter;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.CountDownTextView;
import com.phjt.shangxueyuan.widgets.dialog.SlidingVerificationDialog;
import com.phsxy.utils.KeyboardUtils;
import com.phsxy.utils.RegexUtils;


import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/03 17:45
 */
public class DiscipleGroupActivity extends BaseActivity<DiscipleGroupPresenter> implements DiscipleGroupContract.View {


    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.et_wjj_mobile)
    EditText etDiscipleGroupMobile;
    @BindView(R.id.et_wjj_password)
    EditText etDiscipleGroupPassword;
    @BindView(R.id.tv_auth)
    TextView tvAuth;
    @BindView(R.id.tv_send_code)
    CountDownTextView tvGetIdentifyCode;

    /**
     * "type":"1:短信登录   2:注册   3:忘记密码   4:绑定手机号" 5:修改密码  6:ios设备绑定 7弟子群认证 "
     */
    private int codeType = 7;
    private String phone;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerDiscipleGroupComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_disciple_group;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("预选弟子群认证");
        tvAuth.setEnabled(false);
        initEditText();

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

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.tv_auth, R.id.tv_send_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_send_code:
                String mobile = etDiscipleGroupMobile.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {
                    showTips("请输入弟子群账号");
                    return;
                }
                if (!RegexUtils.isMobileSimple(mobile)) {
                    showTips(getString(R.string.tips_mobile_error));
                    return;
                }
                showSign(mobile);
                break;
            case R.id.tv_auth:
                KeyboardUtils.hideSoftInput(this);
                String phoneNum = etDiscipleGroupMobile.getText().toString().trim();
                String verificationCode = etDiscipleGroupPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNum)) {
                    showTips("请输入弟子群账号");
                    return;
                }
                if (!RegexUtils.isMobileSimple(phoneNum)) {
                    showTips(getString(R.string.tips_mobile_error));
                    return;
                }

                if (TextUtils.isEmpty(verificationCode)) {
                    showTips("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(verificationCode)) {
                    showTips(getString(R.string.tips_identify_code));
                    return;

                }
                if (verificationCode.length() < 6) {
                    showTips(getString(R.string.tips_identify_code_error));
                    return;
                }
                if (mPresenter != null) {
                    mPresenter.getDiscipleGroupAuth(this, phoneNum, verificationCode);
                }

                break;
            default:
                break;
        }
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
                            Objects.requireNonNull(mPresenter).sliderValidation(sessionId, sig, token, scene,phone);
                        }
                    }
                }
            }
        });
    }


    @Override
    public void sliderValidationSuccess() {
        TipsUtil.showTips("验证码发送成功");
        tvGetIdentifyCode.startCountDownDiscipleGroup(DiscipleGroupActivity.this);
    }

    @Override
    public void sliderValidationFailed(String msg) {
        TipsUtil.showTips(msg);
    }

    @Override
    public void sendVerficationSuccess() {

    }

    @Override
    public void sendVerficationFailed(String msg) {
        TipsUtil.showTips(msg);
    }

    private void initEditText() {
        etDiscipleGroupMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tvDiscipleGroupMobile = etDiscipleGroupMobile.getText().toString().trim();
                String tvDiscipleGroupPassword = etDiscipleGroupPassword.getText().toString().trim();
                if (tvDiscipleGroupMobile.length() != 0 && tvDiscipleGroupPassword.length() != 0) {
                    tvAuth.setEnabled(true);
                    tvAuth.setBackgroundResource(R.drawable.shape_bg_register_can);
                } else {
                    tvAuth.setEnabled(false);
                    tvAuth.setBackgroundResource(R.drawable.shape_bg_register);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        etDiscipleGroupPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tvDiscipleGroupMobile = etDiscipleGroupMobile.getText().toString().trim();
                String tvDiscipleGroupPassword = etDiscipleGroupPassword.getText().toString().trim();
                if (tvDiscipleGroupMobile.length() != 0 && tvDiscipleGroupPassword.length() != 0) {
                    tvAuth.setEnabled(true);
                    tvAuth.setBackgroundResource(R.drawable.shape_bg_register_can);
                } else {
                    tvAuth.setEnabled(false);
                    tvAuth.setBackgroundResource(R.drawable.shape_bg_register);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
