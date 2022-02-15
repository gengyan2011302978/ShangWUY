package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;

import com.phjt.shangxueyuan.bean.MessageListBean;
import com.phjt.shangxueyuan.di.component.DaggerSystemMessageComponent;
import com.phjt.shangxueyuan.mvp.contract.SystemMessageContract;
import com.phjt.shangxueyuan.mvp.presenter.SystemMessagePresenter;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.mvp.ui.adapter.SystemMessageAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
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
 * description :系统消息
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/20 10:52
 */
public class SystemMessageActivity extends BaseActivity<SystemMessagePresenter> implements SystemMessageContract.View, OnRefreshLoadMoreListener {

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
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private View footerView;
    private SystemMessageAdapter mAdapter;

    private List<MessageListBean.RecordsBean> mList = new ArrayList<>();
    /**
     * 分页
     */
    private int pageNo = 1;
    private int pageSize = 10;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerSystemMessageComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_system_message;
    }

    @Override
    protected void onResume() {
        super.onResume();
        UmengUtil.umengCount(this, ConstantUmeng.MINE_CLICK_MESSAGE_STATION);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("系统消息");
        srlList.setOnRefreshLoadMoreListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(layoutManager);
        mAdapter = new SystemMessageAdapter(this, mList);
        rvList.setAdapter(mAdapter);
        footerView = LayoutInflater.from(this).inflate(R.layout.item_invitation_list_foot, null);
        mAdapter.addFooterView(footerView);
        footerView.setVisibility(View.GONE);

        if (mPresenter != null) {
            mPresenter.getListMessage(1, pageNo, pageSize, true);
        }

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.relat_item) {
                MessageListBean.RecordsBean recordsBean = mAdapter.getData().get(position);
                String messageClassify = String.valueOf(recordsBean.getMessageClassify());
                if (TextUtils.isEmpty(messageClassify)) {
                    return;
                }
                if ("0".equals(messageClassify) || "3".equals(messageClassify) || "4".equals(messageClassify)||"27".equals(messageClassify)) {
                    Intent intent = new Intent(SystemMessageActivity.this, MessageDetalActivity.class);
                    intent.putExtra(Constant.MESSAGE_TYPE, 1);
                    intent.putExtra(Constant.MESSAGE_ID, recordsBean.getMessageId());
                    startActivity(intent);
                }else if("35".equals(messageClassify)){
                    //问答详情
                    Intent intent = new Intent(SystemMessageActivity.this, CheckTheAnswerActivity.class);
                    intent.putExtra(Constant.QUESTION_ID,recordsBean.getOtherId());
                    startActivity(intent);
                }
            }
        });
    }

    @OnClick(R.id.iv_common_back)
    public void onViewClicked(View view) {
        if (view.getId() == R.id.iv_common_back) {
            finish();
        }
    }

    @Override
    public void getRefresh(List<MessageListBean.RecordsBean> bean) {
        mList = bean;
        mAdapter.setNewData(bean);
        mAdapter.setEmptyView(R.layout.empty_layout, rvList);
        stopRefreshAndLoadMore();
    }

    @Override
    public void getLoadMore(List<MessageListBean.RecordsBean> bean) {
        mList = bean;
        mAdapter.addData(bean);
        stopRefreshAndLoadMore();
    }

    @Override
    public void canLoadMore() {
        if (srlList != null) {
            srlList.setEnableLoadMore(true);
        }
        if (footerView != null) {
            footerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (srlList != null) {
            srlList.setEnableLoadMore(false);
        }
        if (footerView != null) {
            footerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (srlList != null) {
            srlList.finishRefresh();
            srlList.finishLoadMore();
        }
    }

    @Override
    public void getLoadFail() {
        stopRefreshAndLoadMore();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (null != mPresenter) {
            pageNo += 1;
            //1 系统通知 2 课程提醒 3 互动提醒 4 活动公告
            mPresenter.getListMessage(1, pageNo, pageSize, false);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        requestData();
    }

    /**
     * 请求数据
     */
    private void requestData() {
        if (null != mPresenter) {
            pageNo = 1;
            /**
             * 消息类型（	1 系统通知 2 课程提醒 3 互动提醒 4 活动公告）
             */
            mPresenter.getListMessage(1, pageNo, pageSize, true);
        }
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
        TipsUtil.showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }


}
