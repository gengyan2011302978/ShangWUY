package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.VipExchangeCodeBean;
import com.phjt.shangxueyuan.bean.event.ExchangeCode;
import com.phjt.shangxueyuan.di.component.DaggerVipExchangeComponent;
import com.phjt.shangxueyuan.mvp.contract.VipExchangeContract;
import com.phjt.shangxueyuan.mvp.presenter.VipExchangePresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.VipExchangeCodeAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 08/06/2020 09:51
 * company: 普华集团
 * description: 模版请保持更新
 */
public class VipExchangeFragment extends BaseFragment<VipExchangePresenter> implements VipExchangeContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.rv_download_video)
    RecyclerView rvDownloadVideo;
    @BindView(R.id.srf_study)
    SmartRefreshLayout srfStudy;
    VipExchangeCodeAdapter vipExchangeCodeAdapter;
    private int pageNo = 1;
    List<VipExchangeCodeBean.RecordsBean> list = new ArrayList<>();

    public static VipExchangeFragment newInstance() {
        VipExchangeFragment fragment = new VipExchangeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerVipExchangeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vip_exchange, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        vipExchangeCodeAdapter = new VipExchangeCodeAdapter(list);
        rvDownloadVideo.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvDownloadVideo.setAdapter(vipExchangeCodeAdapter);
        srfStudy.setOnRefreshLoadMoreListener(this);
        requestData(true);
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
        return super.onCreateView(inflater, container, savedInstanceState);
    }



    public void requestData(boolean isRefresh) {
        if (mPresenter != null) {
            mPresenter.getRefreshList(pageNo, 10, isRefresh);
        }

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo += 1;
        requestData(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        requestData(true);
    }

    @Override
    public void loadRefreshDataSuccess(VipExchangeCodeBean beans, int pageNo, boolean isRefresh) {
        srfStudy.setEnableLoadMore(pageNo < beans.getTotalPage());
        list = beans.getRecords();
        if (isRefresh) {
            vipExchangeCodeAdapter.setNewData(new ArrayList<>());
            if (beans != null && beans.getRecords().size() > 0) {
                vipExchangeCodeAdapter.setNewData(beans.getRecords());
            } else if (beans != null && beans.getRecords().size() == 0) {
                vipExchangeCodeAdapter.setEmptyView(R.layout.empty_exchangevip_layout, rvDownloadVideo);
            } else {
                srfStudy.setEnableLoadMore(false);
                vipExchangeCodeAdapter.setEmptyView(R.layout.empty_exchangevip_layout, rvDownloadVideo);
            }
            srfStudy.finishRefresh();
        } else {
            if (beans != null && beans.getRecords().size() > 0) {
                vipExchangeCodeAdapter.addData(beans.getRecords());
            } else {
                srfStudy.setEnableLoadMore(false);
            }
            srfStudy.finishLoadMore();
        }
    }

    @Override
    public void loadRefreshDataFailure() {
        srfStudy.finishRefresh();
        srfStudy.finishLoadMore();
        vipExchangeCodeAdapter.setEmptyView(R.layout.empty_exchangevip_layout, rvDownloadVideo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(ExchangeCode exchangeCode) {
        pageNo = 1;
        requestData(true);
    }

}
