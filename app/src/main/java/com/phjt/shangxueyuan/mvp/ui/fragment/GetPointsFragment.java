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
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.PointsDetailBean;
import com.phjt.shangxueyuan.di.component.DaggerGetPointsComponent;
import com.phjt.shangxueyuan.mvp.contract.GetPointsContract;
import com.phjt.shangxueyuan.mvp.presenter.GetPointsPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.GetPointsAdapter;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * Created by Template
 *
 * @author :
 * description :获得积分
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:08
 */

public class GetPointsFragment extends BaseFragment<GetPointsPresenter> implements GetPointsContract.View, OnRefreshLoadMoreListener {
    public static final String TYPE = "type_id";
    public static final String DETAIL_TYPE = "detail_type";

    @BindView(R.id.rv_get_points)
    RecyclerView rvGetPoints;
    @BindView(R.id.srl_get_points)
    SmartRefreshLayout srlGetPoints;

    private int mType;
    private int pageSize = 10;
    private int pageNo = 1;
    private GetPointsAdapter adapter;
    private View mEmptyView;
    private View footerView;
    private int detailType;


    /**
     * 提供静态工厂方法 避免在实例化是利用构造方法初始化对象
     *
     * @return 对应Fragment实例
     */
    public static GetPointsFragment newInstance(int type,int detailType) {
        Bundle args = new Bundle();
        GetPointsFragment fragment = new GetPointsFragment();
        args.putInt(TYPE, type);
        args.putInt(DETAIL_TYPE, detailType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGetPointsComponent //Dagger 编译时生成代码,报错先请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_get_points, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt(TYPE);
            detailType = bundle.getInt(DETAIL_TYPE);
        }

        loadData(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvGetPoints.setLayoutManager(layoutManager);
        adapter = new GetPointsAdapter(getActivity());
        rvGetPoints.setAdapter(adapter);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        TextView tvNodata = mEmptyView.findViewById(R.id.tv_nodata);
        if (1 == mType) {
            if (1 == detailType){
                tvNodata.setText("暂无BOCC，快去赚取BOCC吧~");
            }else {
                tvNodata.setText("暂无学豆，快去赚取学豆吧~");
            }

        } else {
            if (1 == detailType){
                tvNodata.setText("暂无消耗BOCC");
            }else {
                tvNodata.setText("暂无消耗学豆");
            }

        }
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        footerView = LayoutInflater.from(getActivity()).inflate(R.layout.item_invitation_list_foot, null);
        adapter.setFooterView(footerView);
        adapter.setType(mType);
        footerView.setVisibility(View.GONE);
        srlGetPoints.setOnRefreshLoadMoreListener(this);
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

    private void loadData(boolean isRefresh) {
        if (mPresenter != null) {
            mPresenter.getIntegralRecord(mType,detailType, pageNo, pageSize, isRefresh);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void loadIntegralRecordSuccess(BaseListBean<PointsDetailBean> bean, boolean isRefresh) {
        srlGetPoints.setEnableLoadMore(pageNo < bean.getTotalPage());
        if (isRefresh) {
            adapter.setNewData(new ArrayList<>());
            if (bean.getRecords().size() > 0) {
                adapter.setType(mType);
                adapter.setNewData(bean.getRecords());
            } else if (bean.getRecords().size() == 0) {
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            } else {
                srlGetPoints.setEnableLoadMore(false);
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }

            }
            srlGetPoints.finishRefresh();
        } else {
            if (bean.getRecords().size() > 0) {
                adapter.addData(bean.getRecords());
                adapter.setType(mType);
            } else {
                srlGetPoints.setEnableLoadMore(false);
            }
            srlGetPoints.finishLoadMore();
        }
        adapter.setTotalCount(bean.getTotalCount());
        if (pageNo < bean.getTotalPage()) {
        } else {
            if (footerView != null) {
                footerView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void loadIntegralRecordFailure() {
        if (null != srlGetPoints) {
            srlGetPoints.finishRefresh();
            srlGetPoints.finishLoadMore();
        }
        if (null != adapter && mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setData(@Nullable Object data) {

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
        TipsUtil.show(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
