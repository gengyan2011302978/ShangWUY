package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MallCommodityBean;
import com.phjt.shangxueyuan.di.component.DaggerMallListComponent;
import com.phjt.shangxueyuan.mvp.contract.MallListContract;
import com.phjt.shangxueyuan.mvp.presenter.MallListPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.MallCommodityAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_TITLE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_URL;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 16:22
 * @description : 商城-商品列表
 */
public class MallListFragment extends BaseFragment<MallListPresenter> implements MallListContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.stl_mall)
    SmartRefreshLayout mStlMall;
    @BindView(R.id.rv_mall)
    RecyclerView mRvMall;

    /**
     * 空页面
     */
    private View emptyView;
    private int currentPage;
    public final int PAGE_SIZE = 10;

    /**
     * 商品类型（0虚拟商品，1实物商品）
     * -1 为兑换记录
     */
    private int mCommodityType;
    private MallCommodityAdapter mCommodityAdapter;

    /**
     * 对用户是否可见
     */
    private boolean isVisibleToUser;

    public static MallListFragment newInstance(int commodityType) {
        Bundle args = new Bundle();
        args.putInt(Constant.BUNDLE_MALL_COMMODITY_TYPE, commodityType);
        MallListFragment fragment = new MallListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMallListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mall_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mStlMall.setOnRefreshLoadMoreListener(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCommodityType = bundle.getInt(Constant.BUNDLE_MALL_COMMODITY_TYPE);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvMall.setLayoutManager(layoutManager);
        List<MallCommodityBean> commodityBeans = new ArrayList<>();
        mCommodityAdapter = new MallCommodityAdapter(mContext, commodityBeans);
        mRvMall.setAdapter(mCommodityAdapter);
        mCommodityAdapter.setExchangeRecord(mCommodityType == -1);

        emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        mCommodityAdapter.setEmptyView(emptyView);
        emptyView.setVisibility(View.GONE);

        mCommodityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MallCommodityBean commodityBean = (MallCommodityBean) adapter.getData().get(position);
                Intent intent = new Intent(mContext, MyWebViewActivity.class);
                intent.putExtra(BUNDLE_WEB_TITLE, "商品详情");
                intent.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_MALL_COMMODITY_DETAIL) + "?id=" + commodityBean.getId());

                intent.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, commodityBean.getId());
                intent.putExtra(Constant.BUNDLE_ORDER_NAME, commodityBean.getCommodityName());
                intent.putExtra(Constant.BUNDLE_ORDER_REALPRICE, String.valueOf(commodityBean.getStudyCoin()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        refreshMallData();
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshMallData();
    }

    private void refreshMallData() {
        if (mStlMall != null && isVisibleToUser) {
            onRefresh(mStlMall);
        }
    }

    @Override
    public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage = 1;
            if (mCommodityType != -1) {
                mPresenter.getMallCommodityList(mCommodityType, currentPage, PAGE_SIZE, true);
            } else {
                mPresenter.getExchangeRecordList(currentPage, PAGE_SIZE, true);
            }
        }
    }


    @Override
    public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage += 1;
            if (mCommodityType != -1) {
                mPresenter.getMallCommodityList(mCommodityType, currentPage, PAGE_SIZE, false);
            } else {
                mPresenter.getExchangeRecordList(currentPage, PAGE_SIZE, false);
            }
        }
    }

    @Override
    public void showCommodityRefresh(List<MallCommodityBean> commodityBeanList) {
        if (mCommodityAdapter != null) {
            mCommodityAdapter.setNewData(commodityBeanList);
        }
    }

    @Override
    public void showCommodityLoadMore(List<MallCommodityBean> commodityBeanList) {
        if (mCommodityAdapter != null) {
            mCommodityAdapter.addData(commodityBeanList);
        }
    }

    @Override
    public void canLoadMore() {
        if (mStlMall != null) {
            mStlMall.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (mStlMall != null) {
            mStlMall.setEnableLoadMore(false);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (mStlMall != null) {
            mStlMall.finishRefresh();
            mStlMall.finishLoadMore();
        }
    }

    @Override
    public void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
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
        stopRefreshAndLoadMore();
    }

    @Override
    public void showMessage(@NonNull String message) {
        TipsUtil.show(message);
    }
}