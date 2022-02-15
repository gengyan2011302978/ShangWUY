package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerBindPlanetAccountComponent;
import com.phjt.shangxueyuan.mvp.contract.BindPlanetAccountContract;
import com.phjt.shangxueyuan.mvp.presenter.BindPlanetAccountPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phsxy.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_LINK_ADDRESS;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_LINK_TYPE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_PLANET_PHONE;


/**
 * @author: Created by Template
 * date: 12/28/2020 13:47
 * company: 普华集团
 * description: 模版请保持更新
 */
public class BindPlanetAccountActivity extends BaseActivity<BindPlanetAccountPresenter> implements BindPlanetAccountContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_planet_account)
    TextView tvPlanetAccount;
    @BindView(R.id.tv_swy_account)
    TextView tvSwyAccount;
    @BindView(R.id.tv_bind_confirm)
    TextView tvBindConfirm;
    @BindView(R.id.tv_bind_other_account)
    TextView tvBindOtherAccount;
    private String linkType;
    private String linkAddress;
    private String planetPhone;
    private String phone;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBindPlanetAccountComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bind_planet_account;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("绑定账号");
        planetPhone = getIntent().getStringExtra(BUNDLE_PLANET_PHONE);
        linkType = getIntent().getStringExtra(BUNDLE_LINK_TYPE);
        linkAddress = getIntent().getStringExtra(BUNDLE_LINK_ADDRESS);
        phone = getIntent().getStringExtra("logined_phone");
        if (TextUtils.isEmpty(phone)) {
            phone = SPUtils.getInstance().getString(Constant.SP_MOBILE);
        }
        if (!TextUtils.isEmpty(planetPhone)){
            tvPlanetAccount.setText(planetPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        }
        if (!TextUtils.isEmpty(phone)) {
            tvSwyAccount.setText(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.iv_common_back, R.id.tv_bind_confirm, R.id.tv_bind_other_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_bind_confirm:
                mPresenter.bindPlanetAccount(phone, planetPhone);
                break;
            case R.id.tv_bind_other_account:
                Intent intent = new Intent(this, BindOtherAccountActivity.class);
                intent.putExtra(BUNDLE_PLANET_PHONE, planetPhone);
                intent.putExtra(BUNDLE_LINK_TYPE, linkType);
                intent.putExtra(BUNDLE_LINK_ADDRESS, linkAddress);
                startActivity(intent);
                break;
            default:
                break;
        }
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

    @Override
    public void bindPlanetSuccess(String bean) {
        Intent intent = new Intent(BindPlanetAccountActivity.this, HomePagerActivity.class);
        intent.putExtra(BUNDLE_PLANET_PHONE, planetPhone);
        intent.putExtra(BUNDLE_LINK_TYPE, linkType);
        intent.putExtra(BUNDLE_LINK_ADDRESS, linkAddress);
        startActivity(intent);
        finish();
    }

    @Override
    public void bindPlanetFail(String bean) {

    }
}
