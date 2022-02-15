package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.bean.WithdrawalRecordBean;
import com.phjt.shangxueyuan.di.component.DaggerWithdrawalRecordComponent;
import com.phjt.shangxueyuan.mvp.contract.WithdrawalRecordContract;
import com.phjt.shangxueyuan.mvp.presenter.WithdrawalRecordPresenter;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWalletActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.WithdrawalDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.WithdrawalRecordAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.NumberUtil;
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
 * date: 08/04/2020 11:22
 * company: 普华集团
 * description: 提现记录
 */
public class WithdrawalRecordFragment extends BaseFragment<WithdrawalRecordPresenter> implements WithdrawalRecordContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.tv_withdrawal_amount_count)
    TextView mTvAmountCount;
    @BindView(R.id.srl_withdrawal)
    SmartRefreshLayout mSrlWithdrawal;
    @BindView(R.id.rv_withdrawal)
    RecyclerView mRvWithdrawal;

    private int currentPage;
    public  final int PAGE_SIZE = 10;

    private WithdrawalRecordAdapter withdrawalAdapter;
    private View emptyView;

    public static WithdrawalRecordFragment newInstance() {
        Bundle args = new Bundle();
        WithdrawalRecordFragment fragment = new WithdrawalRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWithdrawalRecordComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_withdrawal_record, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mSrlWithdrawal.setOnRefreshLoadMoreListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvWithdrawal.setLayoutManager(layoutManager);
        List<WithdrawalRecordBean> recordBeanList = new ArrayList<>();
        withdrawalAdapter = new WithdrawalRecordAdapter(mContext, recordBeanList);
        mRvWithdrawal.setAdapter(withdrawalAdapter);

        emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        withdrawalAdapter.setEmptyView(emptyView);
        emptyView.setVisibility(View.GONE);

        refreshData();

        withdrawalAdapter.setOnItemClickListener((adapter, view, position) -> {
            WithdrawalRecordBean withdrawalRecordBean = (WithdrawalRecordBean) adapter.getData().get(position);
            if (withdrawalRecordBean != null) {
                Intent intent = new Intent(mContext, WithdrawalDetailActivity.class);
                intent.putExtra(Constant.BUNDLE_WITHDRAWAL_ID, withdrawalRecordBean.getId());
                startActivity(intent);
            }
        });
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        mSrlWithdrawal.autoRefresh();
    }

    public void showWithdrawalAmount(double withdrawalAmount) {
        if (mTvAmountCount != null) {
            mTvAmountCount.setText(String.format("已累计提现：%s", NumberUtil.getStrDouble(withdrawalAmount)));
        }
    }

    @Override
    public void showAmountCount(boolean isShow) {
        if (mTvAmountCount != null) {
            mTvAmountCount.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage = 1;
            mPresenter.getWithdrawalRecordList(currentPage, PAGE_SIZE, true);
        }
        //每次刷新时，刷新已提现总额
        refreshWalletMoney();
    }

    private void refreshWalletMoney() {
        if (getActivity() instanceof MyWalletActivity) {
            MyWalletActivity myWalletActivity = (MyWalletActivity) getActivity();
            myWalletActivity.getWalletMoneys();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage += 1;
            mPresenter.getWithdrawalRecordList(currentPage, PAGE_SIZE, false);
        }
    }

    @Override
    public void showWithdrawalRecordRefresh(List<WithdrawalRecordBean> withdrawalRecordBeans) {
        if (withdrawalAdapter != null) {
            withdrawalAdapter.setNewData(withdrawalRecordBeans);
        }
    }

    @Override
    public void showWithdrawalRecordLoadMore(List<WithdrawalRecordBean> withdrawalRecordBeans) {
        if (withdrawalAdapter != null) {
            withdrawalAdapter.addData(withdrawalRecordBeans);
        }
    }

    @Override
    public void canLoadMore() {
        if (mSrlWithdrawal != null) {
            mSrlWithdrawal.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (mSrlWithdrawal != null) {
            mSrlWithdrawal.setEnableLoadMore(false);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (mSrlWithdrawal != null) {
            mSrlWithdrawal.finishRefresh();
            mSrlWithdrawal.finishLoadMore();
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
