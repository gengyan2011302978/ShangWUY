package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerLearnInfoComponent;
import com.phjt.shangxueyuan.mvp.contract.LearnInfoContract;
import com.phjt.shangxueyuan.mvp.presenter.LearnInfoPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyFragmentPagerAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.ReceiveUseLearnFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 06/22/2021 10:24
 * company: 普华集团
 * description: 模版请保持更新
 */
public class LearnInfoActivity extends BaseActivity<LearnInfoPresenter> implements LearnInfoContract.View {

    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private String[] mTitles;
    ReceiveUseLearnFragment receiveUseLearnFragment, receiveUseLearnFragmentt;
    private List<Fragment> mFragments = new ArrayList<>();
    private MyFragmentPagerAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLearnInfoComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_learn_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTitles = new String[]{
                "获得学豆",
                "消耗学豆"};
        receiveUseLearnFragment = ReceiveUseLearnFragment.newInstance(1);
        receiveUseLearnFragmentt = ReceiveUseLearnFragment.newInstance(2);
        mFragments.add(receiveUseLearnFragment);
        mFragments.add(receiveUseLearnFragmentt);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        viewpager.setAdapter(mAdapter);
        viewpager.setOffscreenPageLimit(1);
        tabLayout.setViewPager(viewpager);
        tabLayout.setCurrentTab(0);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_common_back)
    public void onViewClicked() {
    }
}
