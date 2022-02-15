package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.mzmedia.utils.String_Utils;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerPointsDetailsComponent;
import com.phjt.shangxueyuan.mvp.contract.PointsDetailsContract;
import com.phjt.shangxueyuan.mvp.presenter.PointsDetailsPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyHomeAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.GetPointsFragment;
import com.phjt.shangxueyuan.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :
 * description :积分明细
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:09
 */
public class PointsDetailsActivity extends BaseActivity<PointsDetailsPresenter> implements PointsDetailsContract.View, ViewPager.OnPageChangeListener {
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_my_pointse_num)
    TextView tvPointseNum;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.share_titile)
    ConstraintLayout shareTitile;
    @BindView(R.id.vp_my_points)
    ViewPager vpMyPoints;
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tab_my_pointse_detail)
    SlidingTabLayout tabMyPointseDetail;
    private String[] mTitles;
    private GetPointsFragment getDetail;
    private GetPointsFragment consumeFragment;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerPointsDetailsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_points_details;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getIntent().getStringExtra("detail_type").equals("bocc")){
            tvCommonTitle.setText("BOCC明细");
            mTitles = new String[]{"获得BOCC", "消耗BOCC"};
            tvDetailTitle.setText("BOCC");
            getDetail = GetPointsFragment.newInstance(1,1);
            consumeFragment = GetPointsFragment.newInstance(2,1);
        }else {
            tvCommonTitle.setText("学豆明细");
            mTitles = new String[]{"获得学豆", "消耗学豆"};
            tvDetailTitle.setText("学豆");
            getDetail = GetPointsFragment.newInstance(1,2);
            consumeFragment = GetPointsFragment.newInstance(2,2);
        }
        tvCommonTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
        ivCommonBack.setImageResource(R.drawable.back_white);

        shareTitile.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        ImmersionBar.with(PointsDetailsActivity.this).fitsSystemWindows(true)
                .statusBarColor(R.color.color_FF237EFB)
                .statusBarDarkFont(true)
                .init();

        ArrayList<Fragment> mFragments = new ArrayList<>();

        mFragments.add(getDetail);
        mFragments.add(consumeFragment);
        MyHomeAdapter mAdapter = new MyHomeAdapter(getSupportFragmentManager(), mFragments);
        vpMyPoints.setAdapter(mAdapter);
        vpMyPoints.setCurrentItem(0);
        vpMyPoints.setOffscreenPageLimit(2);
        vpMyPoints.addOnPageChangeListener(this);

        tabMyPointseDetail.setViewPager(vpMyPoints, mTitles);
        tabMyPointseDetail.updateTabSelection(0);
        String userIntegral = getIntent().getStringExtra(Constant.USER_INTEGRAL);
        tvPointseNum.setText(String_Utils.linearNmber(userIntegral));
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
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @OnClick(R.id.iv_common_back)
    public void onClick() {
        finish();
    }
}
