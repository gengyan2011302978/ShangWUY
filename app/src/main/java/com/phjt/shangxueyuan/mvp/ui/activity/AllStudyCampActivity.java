package com.phjt.shangxueyuan.mvp.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerAllStudyCampComponent;
import com.phjt.shangxueyuan.mvp.contract.AllStudyCampContract;
import com.phjt.shangxueyuan.mvp.presenter.AllStudyCampPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyDownloadAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.StudyCampFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/08/19 17:12
 * @description :
 */
public class AllStudyCampActivity extends BaseActivity<AllStudyCampPresenter> implements AllStudyCampContract.View {
    @BindView(R.id.tv_common_left)
    TextView tvCommonLeft;
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;
    @BindView(R.id.ic_common_right)
    ImageView icCommonRight;
    @BindView(R.id.tv_common_right_collection)
    TextView tvCommonRightCollection;
    @BindView(R.id.ic_common_right_sweep)
    ImageView icCommonRightSweep;
    @BindView(R.id.ic_common_right_collect)
    ImageView icCommonRightCollect;
    @BindView(R.id.ic_common_right_collection)
    ImageView icCommonRightCollection;
    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.stl_topic)
    SlidingTabLayout stlTopic;
    @BindView(R.id.vp_topic)
    ViewPager vpTopic;
    private StudyCampFragment studyCampActivity, studyCampActivityt, studyCampActivitt;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAllStudyCampComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_all_topic;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("学习营");
        String[] titles = {"全部", "信息化运营官", "新营销架构师"};
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<Fragment> fragments = new ArrayList<>();
        studyCampActivity = StudyCampFragment.newInstance("");
        studyCampActivityt = StudyCampFragment.newInstance("1");
        studyCampActivitt = StudyCampFragment.newInstance("2");


        fragments.add(studyCampActivity);
        fragments.add(studyCampActivityt);
        fragments.add(studyCampActivitt);
        MyDownloadAdapter myDownloadAdapter = new MyDownloadAdapter(fragmentManager, fragments);
        vpTopic.setAdapter(myDownloadAdapter);
        vpTopic.setOffscreenPageLimit(2);
        stlTopic.setViewPager(vpTopic, titles);
        vpTopic.setCurrentItem(getIntent().getIntExtra("studyType", 0));
        stlTopic.setCurrentTab(getIntent().getIntExtra("studyType", 0));

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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