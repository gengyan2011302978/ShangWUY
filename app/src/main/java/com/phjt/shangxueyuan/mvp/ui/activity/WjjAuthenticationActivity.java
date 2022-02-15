package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerWjjAuthenticationComponent;
import com.phjt.shangxueyuan.mvp.contract.WjjAuthenticationContract;
import com.phjt.shangxueyuan.mvp.presenter.WjjAuthenticationPresenter;
import com.phsxy.utils.KeyboardUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;

/**
 * @author: Roy
 * date:    05/06/2020 18:54
 * company: 普华集团
 * dedication: 挖掘机认证
 */
public class WjjAuthenticationActivity extends BaseActivity<WjjAuthenticationPresenter> implements WjjAuthenticationContract.View {


    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.et_wjj_mobile)
    EditText etWjjMobile;
    @BindView(R.id.et_wjj_password)
    EditText etWjjPassword;
    @BindView(R.id.tv_auth)
    TextView tvAuth;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerWjjAuthenticationComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ajj_authentication;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("财富挖掘机认证");
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

    @OnClick({R.id.iv_common_back, R.id.tv_auth})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_auth:
                KeyboardUtils.hideSoftInput(this);
                String mAccount = etWjjMobile.getText().toString().trim();
                String mPassWord = etWjjPassword.getText().toString().trim();
                if (TextUtils.isEmpty(mAccount)) {
                    showTips("请输入财富挖掘机账号");
                    return;
                }
                if (TextUtils.isEmpty(mPassWord)) {
                    showTips("请输入财富挖掘机账号");
                    return;
                }
                if (mPresenter != null) {
                    mPresenter.getWjjAuth(WjjAuthenticationActivity.this, mAccount, mPassWord);
                }

                break;
            default:break;
        }
    }

    private void initEditText() {
        etWjjMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tvWjjMobile = etWjjMobile.getText().toString().trim();
                String tvWjjPassword = etWjjPassword.getText().toString().trim();
                if (tvWjjMobile.length() != 0 && tvWjjPassword.length()!=0) {
                    tvAuth.setEnabled(true);
                    tvAuth.setBackgroundResource(R.drawable.shape_bg_register_can);
                }else {
                    tvAuth.setEnabled(false);
                    tvAuth.setBackgroundResource(R.drawable.shape_bg_register);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        etWjjPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tvWjjMobile = etWjjMobile.getText().toString().trim();
                String tvWjjPassword = etWjjPassword.getText().toString().trim();
                if (tvWjjMobile.length() != 0 && tvWjjPassword.length()!=0) {
                    tvAuth.setEnabled(true);
                    tvAuth.setBackgroundResource(R.drawable.shape_bg_register_can);
                }else {
                    tvAuth.setEnabled(false);
                    tvAuth.setBackgroundResource(R.drawable.shape_bg_register);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

}
