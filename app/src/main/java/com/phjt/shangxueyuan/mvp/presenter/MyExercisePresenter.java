package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.MyExerciseContract;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/03/11 13:54
 */

@ActivityScope
public class MyExercisePresenter extends BasePresenter<MyExerciseContract.Model, MyExerciseContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public MyExercisePresenter(MyExerciseContract.Model model, MyExerciseContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mErrorHandler = null;
    }
}
