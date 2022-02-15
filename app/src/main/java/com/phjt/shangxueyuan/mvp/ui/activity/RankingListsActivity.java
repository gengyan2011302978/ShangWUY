package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mzmedia.utils.String_Utils;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.IntegralRankingBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.di.component.DaggerRankingListsComponent;
import com.phjt.shangxueyuan.mvp.contract.RankingListsContract;
import com.phjt.shangxueyuan.mvp.presenter.RankingListsPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.RankingListsAdapter;
import com.phjt.view.roundImg.RoundedImageView;
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
 * @author :
 * description :积分排行榜
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:19
 */
public class RankingListsActivity extends BaseActivity<RankingListsPresenter> implements RankingListsContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.rv_ranking_list)
    RecyclerView rvRankingList;
    @BindView(R.id.srl_ranking_list)
    SmartRefreshLayout srlRankingList;
    @BindView(R.id.iv_head)
    RoundedImageView ivHead;
    @BindView(R.id.tv_nickName)
    TextView tvNickName;
    @BindView(R.id.tv_credit_item)
    TextView tvCreditItem;
    @BindView(R.id.tv_number_item)
    TextView tvNumber;
    @BindView(R.id.cl_my_number)
    ConstraintLayout clNberumber;


    private RankingListsAdapter adapter;
    private View footerView;
    private View mEmptyView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerRankingListsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ranking_lists;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("BOCC排行榜");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRankingList.setLayoutManager(layoutManager);
        adapter = new RankingListsAdapter(this);
        rvRankingList.setAdapter(adapter);
        srlRankingList.setEnableRefresh(false);
        srlRankingList.setEnableLoadMore(false);
        footerView = View.inflate(this, R.layout.item_list_footer, null);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        footerView.setVisibility(View.GONE);
//        adapter.setFooterView(footerView);


        if (mPresenter != null) {
            mPresenter.getIntegralRanking();
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
    public void loadSuccess(List<IntegralRankingBean> bean) {
        if (bean.size() > 0) {
            AppImageLoader.loadResUrl(bean.get(0).getPhoto(), ivHead, R.drawable.iv_mine_avatar);
            tvNickName.setText(bean.get(0).getNickName());
            tvCreditItem.setText(String_Utils.linearNmber(bean.get(0).getUserBocc()));
            tvNumber.setText("NO." + bean.get(0).getRowNo());

            bean.remove(0);
            adapter.setNewData(bean);
            clNberumber.setVisibility(View.VISIBLE);
            footerView.setVisibility(View.VISIBLE);
        } else {
            if (adapter != null && mEmptyView != null) {
                mEmptyView.setVisibility(View.VISIBLE);
            }
            clNberumber.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadFailure() {
        if (adapter != null && mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        clNberumber.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_common_back)
    public void onClick() {
        finish();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }


}
