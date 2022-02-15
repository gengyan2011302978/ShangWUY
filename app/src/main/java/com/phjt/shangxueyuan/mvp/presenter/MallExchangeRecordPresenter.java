package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.MallExchangeRecordContract;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/23 13:42
 * @description :
 */
@ActivityScope
public class MallExchangeRecordPresenter extends BasePresenter<MallExchangeRecordContract.Model, MallExchangeRecordContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public MallExchangeRecordPresenter(MallExchangeRecordContract.Model model, MallExchangeRecordContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}