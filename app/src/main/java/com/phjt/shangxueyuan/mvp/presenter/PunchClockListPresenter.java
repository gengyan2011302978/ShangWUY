package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.PunchClockListContract;


/**
 * @author: Created by GengYan
 * date: 02/05/2021 11:30
 * company: 普华集团
 * description: 打开列表页
 */
@ActivityScope
public class PunchClockListPresenter extends BasePresenter<PunchClockListContract.Model, PunchClockListContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public PunchClockListPresenter(PunchClockListContract.Model model, PunchClockListContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
