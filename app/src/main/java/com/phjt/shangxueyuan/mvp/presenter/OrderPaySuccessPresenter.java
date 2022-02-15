package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.OrderPaySuccessContract;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/21 14:32
 * @description :
 */
@ActivityScope
public class OrderPaySuccessPresenter extends BasePresenter<OrderPaySuccessContract.Model, OrderPaySuccessContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public OrderPaySuccessPresenter(OrderPaySuccessContract.Model model, OrderPaySuccessContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}