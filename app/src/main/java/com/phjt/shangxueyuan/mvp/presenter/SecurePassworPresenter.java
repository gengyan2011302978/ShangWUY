package com.phjt.shangxueyuan.mvp.presenter;


import android.annotation.SuppressLint;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.SecurePassworContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phsxy.utils.LogUtils;

/**
 * Created by Template
 *
 * @author :
 * description :找回安全密码
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/12 11:18
 */

@ActivityScope
public class SecurePassworPresenter extends BasePresenter<SecurePassworContract.Model, SecurePassworContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public SecurePassworPresenter(SecurePassworContract.Model model, SecurePassworContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 滑块验证
     *
     * @param csessionid csessionid
     * @param sig        sig
     * @param token      token
     * @param scene      scene
     */
    public void sliderValidation(String csessionid, String sig, String token, String scene,String mobile,int codeType) {
        mModel.sliderValidation(csessionid, sig, token, scene,mobile,codeType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.sliderValidationSuccess();
                        } else {
                            mRootView.sliderValidationFailed(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.sliderValidationFailed(e.getMessage());
                    }

                });
    }


    @SuppressLint("CheckResult")
    public void modifyPassword(String mobile, String originalPwd, String newPassword, String repeatNewPwd, String verificationCode,int type) {
        mModel.modifyPassword( mobile,  originalPwd,  newPassword,  repeatNewPwd,  verificationCode, type).compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(tokenBeanBaseBean -> {
                    if (tokenBeanBaseBean.isOk()) {
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
