package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.PlayerContract;


/**
 * @author: Created by Template
 * date: 03/25/2020 14:40
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class PlayerPresenter extends BasePresenter<PlayerContract.Model, PlayerContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public PlayerPresenter(PlayerContract.Model model, PlayerContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
