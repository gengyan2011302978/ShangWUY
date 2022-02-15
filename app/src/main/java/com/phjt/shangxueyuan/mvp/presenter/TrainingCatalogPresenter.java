package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.TrainingCatalogContract;


/**
 * @author: Created by GengYan
 * date: 01/19/2021 16:26
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class TrainingCatalogPresenter extends BasePresenter<TrainingCatalogContract.Model, TrainingCatalogContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public TrainingCatalogPresenter(TrainingCatalogContract.Model model, TrainingCatalogContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
