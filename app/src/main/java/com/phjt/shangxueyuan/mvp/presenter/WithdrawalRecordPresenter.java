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
import com.phjt.shangxueyuan.bean.WithdrawalRecordBean;
import com.phjt.shangxueyuan.mvp.contract.WithdrawalRecordContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 11:22
 * company: 普华集团
 * description: 提现记录
 */
@FragmentScope
public class WithdrawalRecordPresenter extends BasePresenter<WithdrawalRecordContract.Model, WithdrawalRecordContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public WithdrawalRecordPresenter(WithdrawalRecordContract.Model model, WithdrawalRecordContract.View rootView) {
        super(model, rootView);
    }

    public void getWithdrawalRecordList(int currentPage, int pageSize, boolean isRefresh) {
        mModel.getWithdrawalRecordList(currentPage, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<WithdrawalRecordBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<WithdrawalRecordBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseListBean<WithdrawalRecordBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                int totalPage = baseListBean.getTotalPage();
                                List<WithdrawalRecordBean> recordList = baseListBean.getRecords();

                                if (isRefresh) {
                                    if (recordList != null && !recordList.isEmpty()) {
                                        mRootView.showWithdrawalRecordRefresh(recordList);
                                        mRootView.canLoadMore();
                                        mRootView.showAmountCount(true);
                                    } else {
                                        mRootView.showEmptyView();
                                        mRootView.showAmountCount(false);
                                    }
                                } else {
                                    mRootView.showWithdrawalRecordLoadMore(recordList);
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
