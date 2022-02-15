package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;

import com.phjt.shangxueyuan.di.component.DaggerMyLearnCurrencyComponent;
import com.phjt.shangxueyuan.mvp.contract.MyLearnCurrencyContract;
import com.phjt.shangxueyuan.mvp.presenter.MyLearnCurrencyPresenter;

import com.phjt.shangxueyuan.R;


import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 06/21/2021 16:43
 * company: 普华集团
 * description: 模版请保持更新
 */
public class MyLearnCurrencyActivity extends BaseActivity<MyLearnCurrencyPresenter> implements MyLearnCurrencyContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyLearnCurrencyComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_learn_currency;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
