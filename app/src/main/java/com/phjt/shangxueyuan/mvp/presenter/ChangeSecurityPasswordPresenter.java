package com.phjt.shangxueyuan.mvp.presenter;


import android.annotation.SuppressLint;

import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.ChangeSecurityPasswordContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phsxy.utils.LogUtils;

/**
 * Created by Template
 *
 * @author :Roy
 * description :修改安全密码
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/12 09:54
 */

@ActivityScope
public class ChangeSecurityPasswordPresenter extends BasePresenter<ChangeSecurityPasswordContract.Model, ChangeSecurityPasswordContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public ChangeSecurityPasswordPresenter(ChangeSecurityPasswordContract.Model model, ChangeSecurityPasswordContract.View rootView) {
        super(model, rootView);
    }


    @SuppressLint("CheckResult")
    public void modifyPassword(String mobile, String originalPwd, String newPassword, String repeatNewPwd, String verificationCode,int type) {
        mModel.modifyPassword( mobile,  originalPwd,  newPassword,  repeatNewPwd,  verificationCode, type).compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(tokenBeanBaseBean -> {
                    if (tokenBeanBaseBean.isOk() ) {
                        mRootView.modifySuccess();
                    } else {
                        mRootView.modifyFailed();
                        TipsUtil.show(tokenBeanBaseBean.msg);
                    }
                }, throwable -> LogUtils.e("weChatLogin===" + throwable.toString()));

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mErrorHandler = null;
    }
}
