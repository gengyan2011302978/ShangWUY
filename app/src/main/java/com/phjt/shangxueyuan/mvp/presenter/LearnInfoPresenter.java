package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.LearnInfoContract;


/**
 * @author: Created by GengYan
 * date: 06/22/2021 10:24
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class LearnInfoPresenter extends BasePresenter<LearnInfoContract.Model, LearnInfoContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public LearnInfoPresenter(LearnInfoContract.Model model, LearnInfoContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
