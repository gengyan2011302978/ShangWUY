package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.CourseLocalPlayContract;


/**
 * @author: Created by GengYan
 * date: 09/07/2020 09:20
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class CourseLocalPlayPresenter extends BasePresenter<CourseLocalPlayContract.Model, CourseLocalPlayContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CourseLocalPlayPresenter(CourseLocalPlayContract.Model model, CourseLocalPlayContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
