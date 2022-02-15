package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerMyClockInComponent;
import com.phjt.shangxueyuan.mvp.contract.MyClockInContract;
import com.phjt.shangxueyuan.mvp.presenter.MyClockInPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyHomeAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.ParticipatingPunchFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.PublishedDiaryFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :
 * description :个人中心——我的打卡
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:01
 */
public class MyClockInActivity extends BaseActivity<MyClockInPresenter> implements MyClockInContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.stl_my_clock_in)
    SlidingTabLayout stlMyClockIn;
    @BindView(R.id.vp_my_clock_in)
    ViewPager vpMyClockIn;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerMyClockInComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_clock_in;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("我的打卡");
        String[] mTitles = {"参与的打卡", "发表的日记 "};
        ArrayList<Fragment> mFragments = new ArrayList<>();
        ParticipatingPunchFragment participatingPunchFragment = ParticipatingPunchFragment.newInstance(1);
        PublishedDiaryFragment publishedDiaryFragment = PublishedDiaryFragment.newInstance();
        mFragments.add(participatingPunchFragment);
        mFragments.add(publishedDiaryFragment);
        MyHomeAdapter mAdapter = new MyHomeAdapter(getSupportFragmentManager(), mFragments);
        vpMyClockIn.setAdapter(mAdapter);
        vpMyClockIn.setCurrentItem(0);
        vpMyClockIn.setOffscreenPageLimit(0);
        stlMyClockIn.setViewPager(vpMyClockIn, mTitles);
        stlMyClockIn.updateTabSelection(0);
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


    @OnClick(R.id.iv_common_back)
    public void onClick() {
        finish();
    }
}
