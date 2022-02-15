package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.ReceiveUseLearnContract;


/**
 * @author: Created by GengYan
 * date: 06/22/2021 10:38
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class ReceiveUseLearnPresenter extends BasePresenter<ReceiveUseLearnContract.Model, ReceiveUseLearnContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public ReceiveUseLearnPresenter(ReceiveUseLearnContract.Model model, ReceiveUseLearnContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
