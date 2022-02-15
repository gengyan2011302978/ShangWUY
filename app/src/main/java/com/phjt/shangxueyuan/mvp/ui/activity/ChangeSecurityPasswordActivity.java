package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.phjt.shangxueyuan.di.component.DaggerChangeSecurityPasswordComponent;
import com.phjt.shangxueyuan.mvp.contract.ChangeSecurityPasswordContract;
import com.phjt.shangxueyuan.mvp.presenter.ChangeSecurityPasswordPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phsxy.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :Roy
 * description :修改安全密码
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/12 09:54
 */
public class ChangeSecurityPasswordActivity extends BaseActivity<ChangeSecurityPasswordPresenter> implements ChangeSecurityPasswordContract.View, TextWatcher {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_text_explain)
    TextView tvExplain;
    @BindView(R.id.tv_original_password)
    TextView tvOriginalPassword;
    @BindView(R.id.tv_new_password)
    TextView tvNewPassword;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @BindView(R.id.tv_text_end)
    TextView tvEnd;
    @BindView(R.id.tv_click_get_back)
    TextView tvClickGetBack;
    @BindView(R.id.tv_explain)
    TextView tvExplainAccomplish;

    @BindView(R.id.cl_accomplish)
    ConstraintLayout clAccomplish;

    @BindView(R.id.et_original_password)
    EditText etOriginalPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_affirm_password)
    EditText etAffirmPassword;
    @BindView(R.id.iv_display_old)
    ImageView ivDisplayOld;
    @BindView(R.id.iv_conceal_old)
    ImageView ivConcealOld;
    @BindView(R.id.iv_display_new)
    ImageView ivDisplayNew;
    @BindView(R.id.iv_conceal_new)
    ImageView ivConcealNew;
    @BindView(R.id.iv_display_affirm)
    ImageView ivDisplayAffirm;
    @BindView(R.id.iv_conceal_affirm)
    ImageView ivConcealAffirm;

    @BindView(R.id.view_original_password)
    View viewOriginalPassword;
    @BindView(R.id.view_new_password)
    View viewNewPassword;
    @BindView(R.id.view_affirm_password)
    View viewAffirmPassword;

    private boolean encryptionPassword, newPasswordPassword, affirmPassword;
    private int mCertificationStatus;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerChangeSecurityPasswordComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_change_security_password;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvSubmit.setEnabled(false);
        tvCommonTitle.setText("修改安全密码");
        mCertificationStatus = getIntent().getIntExtra(Constant.CERTIFICATION_STATUS, 0);
        etAffirmPassword.addTextChangedListener(this);
        etOriginalPassword.addTextChangedListener(this);
        etNewPassword.addTextChangedListener(this);
        tvClickGetBack.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
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

    @OnClick({R.id.iv_common_back, R.id.iv_conceal_old, R.id.iv_display_old, R.id.tv_back_to_homepage, R.id.tv_click_get_back, R.id.tv_submit,
            R.id.iv_display_new, R.id.iv_conceal_new, R.id.iv_display_affirm, R.id.iv_conceal_affirm})
    public void onClick(View view) {
        String sOriginalPassword = etOriginalPassword.getText().toString().trim();
        String sAffirmPassword = etAffirmPassword.getText().toString().trim();
        String sNewPassword = etNewPassword.getText().toString().trim();
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_back_to_homepage:
                finish();
                break;
            case R.id.tv_click_get_back:
                //找回安全密码
                Intent intent = new Intent(this, SecurePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_submit:
                String mRegex = "(?![0-9A-Z]+$)(?![0-9a-z]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
                if (mCertificationStatus == 2) {
                    if (!sOriginalPassword.matches(mRegex)) {
                        TipsUtil.show("原密码需要输入8-20位 大小字母、数字组合");
                        return;
                    }
                } else {
                    sOriginalPassword = "";
                }

                if (!sNewPassword.matches(mRegex)) {
                    TipsUtil.show("新密码需要输入8-20位 大小字母、数字组合");
                    return;
                }

                if (!sAffirmPassword.equals(sNewPassword)) {
                    TipsUtil.show("两次密码输入不一致");
                    return;
                }

                String mobile = SPUtils.getInstance().getString(Constant.SP_MOBILE);
                if (mPresenter != null && !TextUtils.isEmpty(mobile)) {
                    mPresenter.modifyPassword(mobile, sOriginalPassword, sNewPassword, sAffirmPassword, "", 1);
                }
                break;

            case R.id.iv_conceal_old:
                etOriginalPassword.setText("");
                break;
            case R.id.iv_display_old:
                setShowHide(etOriginalPassword, encryptionPassword, 0);
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
                if (type == 0) {
                    encryptionPassword = false;
                } else if (type == 1) {
                    newPasswordPassword = false;
                } else if (type == 2) {
                    affirmPassword = false;
                }

            } else {
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                editText.setSelection(editText.length());
                if (type == 0) {
                    encryptionPassword = true;
                } else if (type == 1) {
                    newPasswordPassword = true;
                } else if (type == 2) {
                    affirmPassword = true;
                }
            }
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

        String sAffirmPassword = etAffirmPassword.getText().toString().trim();
        String sOriginalPassword = etOriginalPassword.getText().toString().trim();
        String sNewPassword = etNewPassword.getText().toString().trim();
        if (sOriginalPassword.length() > 0) {
            ivDisplayOld.setVisibility(View.VISIBLE);
            ivConcealOld.setVisibility(View.VISIBLE);
        } else {
            ivDisplayOld.setVisibility(View.GONE);
            ivConcealOld.setVisibility(View.GONE);
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

        if (mCertificationStatus == 2 && sOriginalPassword.length() > 0 && sNewPassword.length() > 0 && sAffirmPassword.length() > 0) {
            tvSubmit.setBackgroundResource(R.drawable.shape_id_card);
            tvSubmit.setEnabled(true);
        } else if (mCertificationStatus != 2 && sNewPassword.length() > 0 && sAffirmPassword.length() > 0) {
            tvSubmit.setBackgroundResource(R.drawable.shape_id_card);
            tvSubmit.setEnabled(true);
        } else {
            tvSubmit.setEnabled(false);
            tvSubmit.setBackgroundResource(R.drawable.shape_gray);
        }
    }


    @Override
    public void modifySuccess() {
        tvExplain.setVisibility(View.GONE);
        tvOriginalPassword.setVisibility(View.GONE);
        tvNewPassword.setVisibility(View.GONE);
        tvForgetPwd.setVisibility(View.GONE);
        tvEnd.setVisibility(View.GONE);
        tvSubmit.setVisibility(View.GONE);
        etOriginalPassword.setVisibility(View.GONE);
        etAffirmPassword.setVisibility(View.GONE);
        etNewPassword.setVisibility(View.GONE);
        ivDisplayNew.setVisibility(View.GONE);
        ivConcealNew.setVisibility(View.GONE);
        ivDisplayAffirm.setVisibility(View.GONE);
        ivConcealAffirm.setVisibility(View.GONE);
        ivDisplayOld.setVisibility(View.GONE);
        ivConcealOld.setVisibility(View.GONE);
        tvClickGetBack.setVisibility(View.GONE);
        viewOriginalPassword.setVisibility(View.GONE);
        viewNewPassword.setVisibility(View.GONE);
        viewAffirmPassword.setVisibility(View.GONE);

        clAccomplish.setVisibility(View.VISIBLE);
        tvExplainAccomplish.setText("修改成功");

    }

    @Override
    public void modifyFailed() {

    }
}
