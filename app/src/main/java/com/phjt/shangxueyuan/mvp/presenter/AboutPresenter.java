package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.AboutContract;


@ActivityScope
public class AboutPresenter extends BasePresenter<AboutContract.Model, AboutContract.View> {

    @Inject
    AppManager mAppManager;

    @Inject
    public AboutPresenter(AboutContract.Model model, AboutContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;

    }
}
