package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.ParticipatingPunchBean;
import com.phjt.shangxueyuan.mvp.contract.ParticipatingPunchContract;
import com.phjt.shangxueyuan.utils.RxUtils;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:21
 */

@FragmentScope
public class ParticipatingPunchPresenter extends BasePresenter<ParticipatingPunchContract.Model, ParticipatingPunchContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public ParticipatingPunchPresenter(ParticipatingPunchContract.Model model, ParticipatingPunchContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取我参与的打卡列表
     */
    public void getMyPunchCardList(int currentPage, int pageSize, boolean isReFresh) {
        mModel.getMyPunchCardList(currentPage, pageSize)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<ParticipatingPunchBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<ParticipatingPunchBean>> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.loadSuccess(integerBaseBean.data, isReFresh);
                        } else {
                            mRootView.loadFailed(isReFresh);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadFailed(isReFresh);
                    }
                });
    }

    /**
     * 课程、训练营  打卡列表
     */
    public void getPunchClockList(String couId, String couType, int currentPage, int pageSize, boolean isRefresh) {
        mModel.getPunchClockList(couId, couType, currentPage, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<ParticipatingPunchBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<ParticipatingPunchBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.loadSuccess(baseBean.data, isRefresh);
                        } else {
                            mRootView.loadFailed(isRefresh);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadFailed(isRefresh);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mErrorHandler = null;
    }
}
