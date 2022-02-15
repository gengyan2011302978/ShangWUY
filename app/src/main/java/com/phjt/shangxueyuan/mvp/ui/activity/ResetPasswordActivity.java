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
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.AppManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.common.MyBusiness;
import com.phjt.shangxueyuan.di.component.DaggerResetPasswordComponent;
import com.phjt.shangxueyuan.mvp.contract.ResetPasswordContract;
import com.phjt.shangxueyuan.mvp.presenter.ResetPasswordPresenter;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phsxy.utils.KeyboardUtils;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.RegexUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.mvp.ui.activity.ForgetPasswordActivity.TYPE_CHANGE_PASSWORD;
import static com.phjt.shangxueyuan.mvp.ui.activity.ForgetPasswordActivity.TYPE_FORGET_PASSWORD;


/**
 * @author: Created by Template
 * date: 03/27/2020 13:40
 * company: 普华集团
 * description: 重置密码页面（忘记或修改密码二级页面）
 */
public class ResetPasswordActivity extends BaseActivity<ResetPasswordPresenter> implements ResetPasswordContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.etv_new_password)
    EditText etvNewPassword;
    @BindView(R.id.etv_new_password_confirm)
    EditText etvNewPasswordConfirm;
    private String phone;
    private int pageType;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerResetPasswordComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        pageType = getIntent().getIntExtra("page_type", 0);
        phone = getIntent().getStringExtra("phone");
        tvCommonTitle.setText(pageType == TYPE_FORGET_PASSWORD ? "忘记密码" : "修改密码");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.iv_common_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            if (mPresenter != null) {
                if (pageType == TYPE_FORGET_PASSWORD) {
                    mPresenter.resetPassword(phone, etvNewPasswordConfirm.getText().toString().trim());
                } else if (pageType == TYPE_CHANGE_PASSWORD) {
                    mPresenter.changePassword(phone, etvNewPasswordConfirm.getText().toString().trim());
                }
            }
        }
    }

    private boolean checkInput() {
        return checkInput(false);
    }

    private boolean checkInput(boolean showTips) {
        boolean canSubmit = false;
        boolean canClick = true;
        String newPassword = etvNewPassword.getText().toString().trim();
        String newPasswordConfirm = etvNewPasswordConfirm.getText().toString().trim();
        if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(newPasswordConfirm)) {
            canClick = false;
        } else if (!RegexUtils.isMatch("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$", newPassword)) {
            if (showTips) {
                showTips(R.string.tips_password_form);
            }
//        } else if (newPasswordConfirm.length() < 6) {
//            if (showTips) {
//                showTips(R.string.tips_new_password_confirm);
//            }
        } else if (!newPassword.equals(newPasswordConfirm)) {
            if (showTips) {
                showTips(R.string.tips_password_not_same);
            }
        } else {
            canSubmit = true;
        }

        if (canClick) {
            tvSubmit.setEnabled(true);
            tvSubmit.setBackground(ContextCompat.getDrawable(ResetPasswordActivity.this, R.drawable.shape_btn_login));
        } else {
            tvSubmit.setEnabled(false);
            tvSubmit.setBackground(ContextCompat.getDrawable(ResetPasswordActivity.this, R.drawable.shape_bg_register));
        }

        return canSubmit;
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

    @Override
    public void submitSuccess() {
        TipsUtil.showTips("修改成功");
        if (pageType == TYPE_CHANGE_PASSWORD && mPresenter != null) {
            // 修改密码，修改成功后需要退出登录
            mPresenter.outLogin(ResetPasswordActivity.this);
        } else {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void submitFailed(String msg) {
        TipsUtil.showTips(msg);
    }

    @Override
    public void loadOutLoginSuccess() {
        logoutIm();
    }

    @Override
    public void loadOutLoginFailed() {
        logoutIm();
    }

    @OnTextChanged(R.id.etv_new_password)
    void onTextChangedPassword(CharSequence text) {
        checkInput();
    }

    @OnTextChanged(R.id.etv_new_password_confirm)
    void onTextChangedPasswordConfirm(CharSequence text) {
        checkInput();
    }

    private void showTips(@StringRes int resId) {
        TipsUtil.showTips(getString(resId));
    }

    /**
     * 退出登录状态
     */
    private void logoutIm() {
        MyBusiness.loginOut();
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(ResetPasswordActivity.this).setShareConfig(config);

        UMShareAPI.get(ResetPasswordActivity.this).deleteOauth(ResetPasswordActivity.this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
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
    }

}
