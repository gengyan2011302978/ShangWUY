package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.IncomeRecordBean;
import com.phjt.shangxueyuan.mvp.contract.IncomeRecordContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 10:56
 * company: 普华集团
 * description: 收入记录
 */
@FragmentScope
public class IncomeRecordPresenter extends BasePresenter<IncomeRecordContract.Model, IncomeRecordContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public IncomeRecordPresenter(IncomeRecordContract.Model model, IncomeRecordContract.View rootView) {
        super(model, rootView);
    }

    public void getIncomeRecordList(int currentPage, int pageSize, boolean isRefresh) {
        mModel.getIncomeRecordList(currentPage, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<IncomeRecordBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<IncomeRecordBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseListBean<IncomeRecordBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                int totalPage = baseListBean.getTotalPage();
                                List<IncomeRecordBean> recordList = baseListBean.getRecords();

                                if (isRefresh) {
                                    if (recordList != null && !recordList.isEmpty()) {
                                        mRootView.showIncomeRecordRefresh(recordList);
                                        mRootView.canLoadMore();
                                    } else {
                                        mRootView.showEmptyView();
                                    }
                                } else {
                                    mRootView.showIncomeRecordLoadMore(recordList);
                                }

                                if (currentPage < totalPage) {
                                    mRootView.canLoadMore();
                                } else {
                                    mRootView.cannotLoadMore();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.showEmptyView();
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
