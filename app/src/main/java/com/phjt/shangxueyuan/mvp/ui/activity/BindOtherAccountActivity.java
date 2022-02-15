package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.di.component.DaggerBindOtherAccountComponent;
import com.phjt.shangxueyuan.mvp.contract.BindOtherAccountContract;
import com.phjt.shangxueyuan.mvp.presenter.BindOtherAccountPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.dialog.SlidingVerificationDialog;
import com.phsxy.utils.KeyboardUtils;
import com.phsxy.utils.RegexUtils;
import com.phsxy.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_LINK_ADDRESS;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_LINK_TYPE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_PLANET_PHONE;
import static com.phjt.shangxueyuan.utils.UmengUtil.onEventLoginPage;


/**
 * @author: Created by Template
 * date: 12/28/2020 17:51
 * company: 普华集团
 * description: 模版请保持更新
 */
public class BindOtherAccountActivity extends BaseActivity<BindOtherAccountPresenter> implements BindOtherAccountContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_identify_code)
    EditText etIdentifyCode;
    @BindView(R.id.tv_bind_confirm)
    TextView tvBindConfirm;
    @BindView(R.id.tv_get_identify_code)
    TextView tvGetIdentifyCode;
    @BindView(R.id.tv_countdown)
    TextView tvCountdown;

    private long millisInFuture = 60000;
    private String linkType;
    private String linkAddress;
    private String planetPhone;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBindOtherAccountComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bind_other_account;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("绑定其他账号");
        etMobile.setHint("请输入您要绑定的手机号");
        planetPhone = getIntent().getStringExtra(BUNDLE_PLANET_PHONE);
        linkType = getIntent().getStringExtra(BUNDLE_LINK_TYPE);
        linkAddress = getIntent().getStringExtra(BUNDLE_LINK_ADDRESS);
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.tv_get_identify_code, R.id.tv_bind_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
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

            case R.id.tv_bind_confirm:
                if (checkInput(true)) {
                    KeyboardUtils.hideSoftInput(this);
                    if (mPresenter != null) {
                        mPresenter.bindAndLogin(etMobile.getText().toString().trim(), planetPhone, etIdentifyCode.getText().toString().trim());
                    }
                }
                break;

            default:
                break;
        }
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
            tvBindConfirm.setBackground(ContextCompat.getDrawable(BindOtherAccountActivity.this, R.drawable.shape_btn_login));
        } else {
            tvBindConfirm.setBackground(ContextCompat.getDrawable(BindOtherAccountActivity.this, R.drawable.shape_bg_register));
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

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }


    /**
     * On text changed mobile.
     *
     * @param text the text
     */
    @OnTextChanged(R.id.et_mobile)
    void onTextChangedMobile(CharSequence text) {
        checkInput(false);
    }

    /**
     * On text changed identify.
     *
     * @param text the text
     */
    @OnTextChanged(R.id.et_identify_code)
    void onTextChangedIdentify(CharSequence text) {
        checkInput(false);
    }

    @Override
    public void sliderValidationSuccess() {
        TipsUtil.showTips("验证码发送成功");
        showCountdownView();
        startCountdown();
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
        new CountDownTimer(millisInFuture, 1000) {

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
    public void sliderValidationFailed(String msg) {
        TipsUtil.showTips(msg);
    }

    @Override
    public void bindAndLoginSuccess(String token) {
        SPUtils.getInstance().put(Constant.SP_TOKEN, token);
        SPUtils.getInstance().put(Constant.SP_MOBILE, etMobile.getText().toString());
        SPUtils.getInstance().remove(Constant.SP_TEMP_LOGOUT);
        Intent intent = new Intent(BindOtherAccountActivity.this, HomePagerActivity.class);
        intent.putExtra(BUNDLE_PLANET_PHONE, planetPhone);
        intent.putExtra(BUNDLE_LINK_TYPE, linkType);
        intent.putExtra(BUNDLE_LINK_ADDRESS, linkAddress);
        startActivity(intent);
        finish();
    }

    @Override
    public void quickLoginFailed(String msg) {
        TipsUtil.showTips(msg);
    }

}
