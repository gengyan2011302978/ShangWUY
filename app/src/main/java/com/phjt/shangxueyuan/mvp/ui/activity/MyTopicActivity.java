package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.di.component.DaggerMyTopicComponent;
import com.phjt.shangxueyuan.mvp.contract.MyTopicContract;
import com.phjt.shangxueyuan.mvp.presenter.MyTopicPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyTopicAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :
 * description :我的话题
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 10:10
 */
public class MyTopicActivity extends BaseActivity<MyTopicPresenter> implements MyTopicContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.rv_my_topic)
    RecyclerView rvMyTopic;
    @BindView(R.id.srl_my_topics)
    SmartRefreshLayout srlMyTopics;


    private int pageNo = 1;
    public  final int PAGE_SIZE = 10;
    private MyTopicAdapter adapter;
    private View mEmptyView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerMyTopicComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_topic;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("我的话题");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMyTopic.setLayoutManager(layoutManager);
        adapter = new MyTopicAdapter(this);
        rvMyTopic.setAdapter(adapter);
        srlMyTopics.setOnRefreshLoadMoreListener(this);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);

        loadData(true);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            List<MyTopicBean> data = adapter.getData();
            Intent intent = new Intent(MyTopicActivity.this, TopicInfoActivity.class);
            intent.putExtra(Constant.BUNDLE_TOPIC_ID, String.valueOf(data.get(position).getId()));
            startActivity(intent);
        });
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

    @OnClick({R.id.iv_common_back, R.id.tv_common_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            default:
                break;
        }
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
            mPresenter.getTopicList(pageNo, PAGE_SIZE, isReFresh);
        }
    }

    @Override
    public void LoadSuccess(BaseListBean<MyTopicBean> bean, boolean isRefresh) {
        if (bean!= null) {
            srlMyTopics.setEnableLoadMore(pageNo < bean.getTotalPage());
            if (isRefresh) {
                adapter.setNewData(new ArrayList<>());
                if (bean.getRecords().size() > 0) {
                    adapter.setNewData(bean.getRecords());
                } else if (bean.getRecords().size() == 0) {
                    if (adapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                } else {
                    srlMyTopics.setEnableLoadMore(false);
                    if (adapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                }
                srlMyTopics.finishRefresh();
            } else {
                if (bean.getRecords().size() > 0) {
                    adapter.addData(bean.getRecords());
                } else {
                    srlMyTopics.setEnableLoadMore(false);
                }
                srlMyTopics.finishLoadMore();
            }
        }
    }

    @Override
    public void LoadFailed(boolean isReFresh) {
        if (srlMyTopics != null) {
            srlMyTopics.finishRefresh();
            srlMyTopics.finishLoadMore();
        }
        if (adapter != null && mEmptyView != null&& isReFresh) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }
}
