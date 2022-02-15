package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerReceiveUseLearnComponent;
import com.phjt.shangxueyuan.mvp.contract.ReceiveUseLearnContract;
import com.phjt.shangxueyuan.mvp.presenter.ReceiveUseLearnPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.LearnInforAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 06/22/2021 10:38
 * company: 普华集团
 * description: 模版请保持更新
 */
public class ReceiveUseLearnFragment extends BaseFragment<ReceiveUseLearnPresenter> implements ReceiveUseLearnContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.rv_learn)
    RecyclerView rvLearn;
    LearnInforAdapter learnInforAdapter;
    Unbinder unbinder;
    @BindView(R.id.srf_study)
    SmartRefreshLayout srfStudy;
    private View mEmptyView;

    public static ReceiveUseLearnFragment newInstance(int type) {
        ReceiveUseLearnFragment fragment = new ReceiveUseLearnFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerReceiveUseLearnComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_receive_use_learn, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        learnInforAdapter = new LearnInforAdapter(null);
        rvLearn.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLearn.setAdapter(learnInforAdapter);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        learnInforAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);

        srfStudy.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}
