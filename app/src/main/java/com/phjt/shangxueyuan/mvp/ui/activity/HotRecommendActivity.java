package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseItemBean;
import com.phjt.shangxueyuan.di.component.DaggerHotRecommendComponent;
import com.phjt.shangxueyuan.mvp.contract.HotRecommendContract;
import com.phjt.shangxueyuan.mvp.presenter.HotRecommendPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.AuditionCourseAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :Roy
 * description :热门推荐
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/27 17:21
 */
public class HotRecommendActivity extends BaseActivity<HotRecommendPresenter> implements HotRecommendContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.rv_hot_recommend)
    RecyclerView rvHotRecommend;
    @BindView(R.id.srl_hot_recommend)
    SmartRefreshLayout srlHotRecommend;
    private int pageNo = 1;
    private final int PAGE_SIZE = 10;
    private View mEmptyView;
    private AuditionCourseAdapter adapter;
    public final int COURSE_REQUEST_CODE = 100;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerHotRecommendComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_hot_recommend;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("热门推荐");
        srlHotRecommend.setOnRefreshLoadMoreListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvHotRecommend.setLayoutManager(layoutManager);
        adapter = new AuditionCourseAdapter(this, null);
        rvHotRecommend.setAdapter(adapter);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        ImageView imageNodata = mEmptyView.findViewById(R.id.image_nodata);
        TextView tvNodata = mEmptyView.findViewById(R.id.tv_nodata);
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        loadData(true);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            CourseItemBean itemBean = (CourseItemBean) adapter.getData().get(position);
            Intent intent = new Intent(HotRecommendActivity.this, CourseDetailActivity.class);
            intent.putExtra(Constant.BUNDLE_COURSE_ID, itemBean.getId());
            intent.putExtra(Constant.BUNDLE_COURSE_NAME, itemBean.getName());
            startActivityForResult(intent, COURSE_REQUEST_CODE);

        });
    }


    /**
     * 刷新观看人数
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COURSE_REQUEST_CODE) {
            pageNo = 1;
            loadData(true);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        stopRefreshAndLoadMore();
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
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo += 1;
        loadData(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        loadData(true);
    }

    private void loadData(boolean isReFresh) {
        if (mPresenter != null) {
            mPresenter.getRecommendList(pageNo, PAGE_SIZE, isReFresh);
        }
    }

    @OnClick(R.id.iv_common_back)
    public void onClick() {
        finish();
    }

    @Override
    public void showAuditionCourseRefresh(List<CourseItemBean> itemBeanList) {
        if (adapter != null) {
            adapter.setNewData(itemBeanList);
        }
    }

    @Override
    public void showAuditionCourseLoadMore(List<CourseItemBean> itemBeanList) {
        if (adapter != null) {
            adapter.addData(itemBeanList);
        }
    }

    @Override
    public void canLoadMore() {
        if (srlHotRecommend != null) {
            srlHotRecommend.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (srlHotRecommend != null) {
            srlHotRecommend.setEnableLoadMore(false);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (srlHotRecommend != null) {
            srlHotRecommend.finishRefresh();
            srlHotRecommend.finishLoadMore();
        }
    }


    @Override
    public void showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }
}
