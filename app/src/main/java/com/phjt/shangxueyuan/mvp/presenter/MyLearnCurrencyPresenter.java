package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.MyLearnCurrencyContract;


/**
 * @author: Created by GengYan
 * date: 06/21/2021 16:43
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class MyLearnCurrencyPresenter extends BasePresenter<MyLearnCurrencyContract.Model, MyLearnCurrencyContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyLearnCurrencyPresenter(MyLearnCurrencyContract.Model model, MyLearnCurrencyContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
