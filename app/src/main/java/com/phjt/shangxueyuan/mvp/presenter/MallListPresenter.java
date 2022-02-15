package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MallCommodityBean;
import com.phjt.shangxueyuan.mvp.contract.MallListContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 16:22
 * @description :
 */
@FragmentScope
public class MallListPresenter extends BasePresenter<MallListContract.Model, MallListContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public MallListPresenter(MallListContract.Model model, MallListContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 商品列表
     *
     * @param commodityType
     * @param currentPage
     * @param pageSize
     * @param isRefresh
     */
    public void getMallCommodityList(int commodityType, int currentPage, int pageSize, boolean isRefresh) {
        mModel.getMallCommodityList(commodityType, currentPage, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<MallCommodityBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseBean<BaseListBean<MallCommodityBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseListBean<MallCommodityBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                int totalPage = baseListBean.getTotalPage();
                                List<MallCommodityBean> commodityBeanList = baseListBean.getRecords();
                                if (isRefresh) {
                                    mRootView.showCommodityRefresh(commodityBeanList);

                                    if (commodityBeanList == null || commodityBeanList.isEmpty()) {
                                        mRootView.showEmptyView();
                                    }
                                } else {
                                    mRootView.showCommodityLoadMore(commodityBeanList);
                                }
                                if (currentPage < totalPage) {
                                    mRootView.canLoadMore();
                                } else {
                                    mRootView.cannotLoadMore();
                                }
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 兑换记录
     *
     * @param currentPage
     * @param pageSize
     */
    public void getExchangeRecordList(int currentPage, int pageSize, boolean isRefresh) {
        mModel.getExchangeRecordList(currentPage, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<MallCommodityBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseBean<BaseListBean<MallCommodityBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseListBean<MallCommodityBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                int totalPage = baseListBean.getTotalPage();
                                List<MallCommodityBean> commodityBeanList = baseListBean.getRecords();
                                if (isRefresh) {
                                    mRootView.showCommodityRefresh(commodityBeanList);

                                    if (commodityBeanList == null || commodityBeanList.isEmpty()) {
                                        mRootView.showEmptyView();
                                    }
                                } else {
                                    mRootView.showCommodityLoadMore(commodityBeanList);
                                }
                                if (currentPage < totalPage) {
                                    mRootView.canLoadMore();
                                } else {
                                    mRootView.cannotLoadMore();
                                }
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}