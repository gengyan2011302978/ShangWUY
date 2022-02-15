package com.phjt.shangxueyuan.mvp.ui.fragment;

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
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.InvitationRecordBean;
import com.phjt.shangxueyuan.di.component.DaggerMyInvitationComponent;
import com.phjt.shangxueyuan.mvp.contract.MyInvitationContract;
import com.phjt.shangxueyuan.mvp.presenter.MyInvitationPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.InvitationRecordAdapter;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 08/28/2020 10:41
 * company: 普华集团
 * description: 我的邀请
 */
public class MyInvitationFragment extends BaseFragment<MyInvitationPresenter> implements MyInvitationContract.View, OnRefreshListener {

    @BindView(R.id.rv_invitation)
    RecyclerView mRvInvitation;
    @BindView(R.id.srl_invitation)
    SmartRefreshLayout mSrlInvitation;

    TextView mTvInvitationNum;

    private InvitationRecordAdapter recordAdapter;
    private View emptyView;

    public static MyInvitationFragment newInstance() {
        Bundle args = new Bundle();
        MyInvitationFragment fragment = new MyInvitationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMyInvitationComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_invitation, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mSrlInvitation.setOnRefreshListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvInvitation.setLayoutManager(layoutManager);
        List<InvitationRecordBean> recordBeans = new ArrayList<>();
        recordAdapter = new InvitationRecordAdapter(mContext, recordBeans);
        mRvInvitation.setAdapter(recordAdapter);

        View headerView = LayoutInflater.from(mContext).inflate(R.layout.my_invitation_header, null);
        mTvInvitationNum = headerView.findViewById(R.id.tv_invitation_num);
        recordAdapter.setHeaderView(headerView);

        emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        recordAdapter.setEmptyView(emptyView);
        emptyView.setVisibility(View.GONE);

        recordAdapter.setHeaderAndEmpty(true);

        if (mPresenter != null) {
            mPresenter.getInvitationRecordList();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            mPresenter.getInvitationRecordList();
        }
    }

    @Override
    public void showInvitationRecord(List<InvitationRecordBean> invitationRecordBeans) {
        if (recordAdapter != null) {
            recordAdapter.setNewData(invitationRecordBeans);
        }
    }

    @Override
    public void showInvitationedNum(int num) {
        if (mTvInvitationNum != null) {
            mTvInvitationNum.setText(String.valueOf(num));
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
        if (mSrlInvitation != null) {
            mSrlInvitation.finishRefresh();
        }
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
