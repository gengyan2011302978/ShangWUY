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
import com.phjt.shangxueyuan.di.component.DaggerMyExerciseComponent;
import com.phjt.shangxueyuan.mvp.contract.MyExerciseContract;
import com.phjt.shangxueyuan.mvp.presenter.MyExercisePresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyHomeAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.MineExerciseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :Roy
 * description :我的作业-全部/已提交/未提交
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/03/11 13:54
 */
public class MyExerciseActivity extends BaseActivity<MyExercisePresenter> implements MyExerciseContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.stl_my_exercise)
    SlidingTabLayout stlMyExercise;
    @BindView(R.id.vp_my_exercise)
    ViewPager vpMyExercise;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerMyExerciseComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_exercise;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("作业列表");
        String[] mTitles = {"全部", "已提交", "未提交"};
        ArrayList<Fragment> mFragments = new ArrayList<>();
        MineExerciseFragment allExerciseFragment = MineExerciseFragment.newInstance(-1);
        MineExerciseFragment alreadyExerciseFragment = MineExerciseFragment.newInstance(1);
        MineExerciseFragment unExerciseFragment = MineExerciseFragment.newInstance(0);
        mFragments.add(allExerciseFragment);
        mFragments.add(alreadyExerciseFragment);
        mFragments.add(unExerciseFragment);
        MyHomeAdapter mAdapter = new MyHomeAdapter(getSupportFragmentManager(), mFragments);
        vpMyExercise.setAdapter(mAdapter);
        vpMyExercise.setCurrentItem(0);
        vpMyExercise.setOffscreenPageLimit(0);
        stlMyExercise.setViewPager(vpMyExercise, mTitles);
        stlMyExercise.updateTabSelection(0);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_common_back)
    public void onClick() {
        finish();
    }
}
