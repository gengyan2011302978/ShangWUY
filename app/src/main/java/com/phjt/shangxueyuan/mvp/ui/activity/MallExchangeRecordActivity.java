package com.phjt.shangxueyuan.mvp.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerMallExchangeRecordComponent;
import com.phjt.shangxueyuan.mvp.contract.MallExchangeRecordContract;
import com.phjt.shangxueyuan.mvp.presenter.MallExchangeRecordPresenter;
import com.phjt.shangxueyuan.mvp.ui.fragment.MallListFragment;
import com.phjt.shangxueyuan.utils.TipsUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/23 13:42
 * @description :商城-兑换记录
 */
public class MallExchangeRecordActivity extends BaseActivity<MallExchangeRecordPresenter> implements MallExchangeRecordContract.View {

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.fl_record_layout)
    FrameLayout mFlRecordLayout;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMallExchangeRecordComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_mall_exchange_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvCommonTitle.setText("兑换记录");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_record_layout, MallListFragment.newInstance(-1));
        transaction.commit();
    }

    @OnClick(R.id.iv_common_back)
    public void onClick() {
        finish();
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
        TipsUtil.show(message);
    }

}