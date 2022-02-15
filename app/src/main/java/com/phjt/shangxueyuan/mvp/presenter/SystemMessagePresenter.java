package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MessageListBean;
import com.phjt.shangxueyuan.mvp.contract.SystemMessageContract;
import com.phjt.shangxueyuan.utils.RxUtils;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/20 10:52
 */

@ActivityScope
public class SystemMessagePresenter extends BasePresenter<SystemMessageContract.Model, SystemMessageContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public SystemMessagePresenter(SystemMessageContract.Model model, SystemMessageContract.View rootView) {
        super(model, rootView);
    }

    public void getListMessage( int type,int pageNo, int pageSize, boolean isRefresh) {
        mModel.getListMessage(type, pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<MessageListBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<MessageListBean> bean) {
                        if (bean.isOk()) {
                            if (isRefresh) {
                                mRootView.getRefresh(bean.data.getRecords());
                                mRootView.canLoadMore();
                            } else {
                                mRootView.getLoadMore(bean.data.getRecords());
                            }
                            if (pageNo >= bean.data.getTotalPage()) {
                                mRootView.cannotLoadMore();
                            } else {
                                mRootView.canLoadMore();
                            }
                        } else {
                            mRootView.getLoadFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.getLoadFail();
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
