package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.bean.IncomeRecordBean;
import com.phjt.shangxueyuan.di.component.DaggerIncomeRecordComponent;
import com.phjt.shangxueyuan.mvp.contract.IncomeRecordContract;
import com.phjt.shangxueyuan.mvp.presenter.IncomeRecordPresenter;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.mvp.ui.adapter.IncomeRecordAdapter;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 10:56
 * company: 普华集团
 * description: 收入记录
 */
public class IncomeRecordFragment extends BaseFragment<IncomeRecordPresenter> implements IncomeRecordContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.srl_income)
    SmartRefreshLayout mSrlIncome;
    @BindView(R.id.rv_income)
    RecyclerView mRvIncome;

    private int currentPage;
    public final int PAGE_SIZE = 10;

    private IncomeRecordAdapter mIncomeAdapter;
    private View emptyView;

    public static IncomeRecordFragment newInstance() {
        Bundle args = new Bundle();
        IncomeRecordFragment fragment = new IncomeRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerIncomeRecordComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income_record, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mSrlIncome.setOnRefreshLoadMoreListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvIncome.setLayoutManager(layoutManager);
        List<IncomeRecordBean> incomeRecordBeans = new ArrayList<>();
        mIncomeAdapter = new IncomeRecordAdapter(mContext, incomeRecordBeans);
        mRvIncome.setAdapter(mIncomeAdapter);

        emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        mIncomeAdapter.setEmptyView(emptyView);
        emptyView.setVisibility(View.GONE);

        mSrlIncome.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage = 1;
            mPresenter.getIncomeRecordList(currentPage, PAGE_SIZE, true);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage += 1;
            mPresenter.getIncomeRecordList(currentPage, PAGE_SIZE, false);
        }
    }

    @Override
    public void showIncomeRecordRefresh(List<IncomeRecordBean> incomeRecordBeanList) {
        if (mIncomeAdapter != null) {
            mIncomeAdapter.setNewData(incomeRecordBeanList);
        }
    }

    @Override
    public void showIncomeRecordLoadMore(List<IncomeRecordBean> incomeRecordBeanList) {
        if (mIncomeAdapter != null) {
            mIncomeAdapter.addData(incomeRecordBeanList);
        }
    }

    @Override
    public void canLoadMore() {
        if (mSrlIncome != null) {
            mSrlIncome.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (mSrlIncome != null) {
            mSrlIncome.setEnableLoadMore(false);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (mSrlIncome != null) {
            mSrlIncome.finishRefresh();
            mSrlIncome.finishLoadMore();
        }
    }

    @Override
    public void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
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
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipsUtil.show(message);
    }
}
