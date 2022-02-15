package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.di.component.DaggerMyCollectionComponent;
import com.phjt.shangxueyuan.mvp.contract.MyCollectionContract;
import com.phjt.shangxueyuan.mvp.presenter.MyCollectionPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyHomeAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.MyCollectionsFragment;
import com.phjt.shangxueyuan.utils.TipsUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Roy
 * date:    2020/03/27
 * company: 普华集团
 * description: 我的收藏
 */
public class MyCollectionActivity extends BaseActivity<MyCollectionPresenter> implements MyCollectionContract.View, ViewPager.OnPageChangeListener {

    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.ic_common_right_collection)
    ImageView icCommonRight;

    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_common_right_collection)
    TextView tvCommonRight;

    @BindView(R.id.stl_my_collection)
    SlidingTabLayout mStlStudy;
    @BindView(R.id.vp_my_collection)
    ViewPager mVpStudy;

    private MyCollectionsFragment studentNameFragment;
    private MyCollectionsFragment subjectFragment;
    private MyCollectionsFragment specialColumnFragment;
    private int mPage = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerMyCollectionComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_collection;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("我的收藏");
        tvCommonRight.setText("取消");
        tvCommonRight.setTextColor(ContextCompat.getColor(this, R.color.color_4C7CFB));
        icCommonRight.setVisibility(View.GONE);
        tvDelete.setEnabled(false);
        String[] mTitles = {"课程", "专栏", "专题"};
        ArrayList<Fragment> mFragments = new ArrayList<>();
        studentNameFragment = MyCollectionsFragment.newInstance(0);
        specialColumnFragment = MyCollectionsFragment.newInstance(2);
        subjectFragment = MyCollectionsFragment.newInstance(1);
        mFragments.add(studentNameFragment);
        mFragments.add(specialColumnFragment);
        mFragments.add(subjectFragment);

        MyHomeAdapter mAdapter = new MyHomeAdapter(getSupportFragmentManager(), mFragments);
        mVpStudy.setAdapter(mAdapter);
        mVpStudy.setCurrentItem(0);
        mVpStudy.setOffscreenPageLimit(0);
        mStlStudy.setViewPager(mVpStudy, mTitles);
        mStlStudy.updateTabSelection(0);
        mVpStudy.addOnPageChangeListener(this);
    }

    @OnClick({R.id.ic_common_right_collection, R.id.tv_common_right_collection})
    public void onClickCommonRight(View view) {
        switch (view.getId()) {
            case R.id.ic_common_right_collection:
                tvDelete.setVisibility(View.VISIBLE);
                icCommonRight.setVisibility(View.GONE);
                tvCommonRight.setVisibility(View.VISIBLE);
                if (0 == mPage) {
                    studentNameFragment.setEdit(true);
                } else if (1 == mPage) {
                    specialColumnFragment.setEdit(true);
                } else if (2 == mPage) {
                    subjectFragment.setEdit(true);
                }

                break;
            case R.id.tv_common_right_collection:
                tvCommonRight.setVisibility(View.GONE);
                icCommonRight.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.GONE);

                tvDelete.setEnabled(false);
                tvDelete.setBackgroundResource(R.drawable.shape_bg_register);

                if (0 == mPage) {
                    studentNameFragment.setEdit(false);
                } else if (1 == mPage) {
                    specialColumnFragment.setEdit(false);
                }else if (2 == mPage) {
                    subjectFragment.setEdit(false);
                }
                break;
            default:
                break;
        }
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_delete:
                if (0 == mPage) {
                    studentNameFragment.setDelete();
                } else  if (1 == mPage) {
                    specialColumnFragment.setDelete();
                }else  if (2 == mPage) {
                    subjectFragment.setDelete();
                }
                break;
            default:
                break;
        }
    }

    public void loadFavoriteEdit() {
        tvCommonRight.setVisibility(View.GONE);
        icCommonRight.setVisibility(View.VISIBLE);
        icCommonRight.setEnabled(true);
        tvDelete.setVisibility(View.GONE);
        tvDelete.setEnabled(false);
        tvDelete.setBackgroundResource(R.drawable.shape_bg_register);
    }

    /**
     * 设置编辑按钮点击
     */
    public void setEditClick(boolean click) {
        if (click) {
            tvDelete.setEnabled(true);
            tvDelete.setBackgroundResource(R.drawable.shape_btn_login);
        } else {
            tvDelete.setEnabled(false);
            tvDelete.setBackgroundResource(R.drawable.shape_bg_register);
        }
    }

    /**
     * 设置编辑按钮显示
     */
    public void setEdit(boolean visibility) {
        if (visibility) {
            icCommonRight.setVisibility(View.VISIBLE);
            icCommonRight.setImageResource(R.drawable.ic_edit);
            icCommonRight.setEnabled(true);
        }else {
            visibility();
        }
    }

    /**
     * 设置编辑按钮隐藏
     */
    public void visibility() {
        icCommonRight.setVisibility(View.GONE);
        icCommonRight.setEnabled(false);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mPage = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public void setMayEnabled(boolean enabled) {
        if (enabled) {
            icCommonRight.setVisibility(View.VISIBLE);
        } else {
            icCommonRight.setVisibility(View.GONE);

        }
    }

    @Override
    public void showLoading() {
//        LoadingViewUtil.getInstance().show();
    }

    @Override
    public void hideLoading() {
//        LoadingViewUtil.getInstance().hide();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipsUtil.showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
