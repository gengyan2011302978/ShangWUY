package com.phjt.shangxueyuan.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.ResetPasswordContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.LogUtils;

import javax.inject.Inject;


/**
 * @author: Created by Template
 * date: 03/27/2020 13:40
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ResetPasswordPresenter extends BasePresenter<ResetPasswordContract.Model, ResetPasswordContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public ResetPasswordPresenter(ResetPasswordContract.Model model, ResetPasswordContract.View rootView) {
        super(model, rootView);
    }

    @SuppressLint("CheckResult")
    public void resetPassword(String phone, String newPassWord) {
        mModel.resetPassword(phone, newPassWord).compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(baseBean -> {
                    if (baseBean.isOk()) {
                        mRootView.submitSuccess();
                    } else {
                        mRootView.submitFailed(baseBean.msg);
                    }
                }, throwable -> LogUtils.e("send===" + throwable.toString()));

    }

    @SuppressLint("CheckResult")
    public void changePassword(String phone, String newPassWord) {
        mModel.changePassword(phone, newPassWord).compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(baseBean -> {
                    if (baseBean.isOk()) {
                        mRootView.submitSuccess();
                    } else {
                        mRootView.submitFailed(baseBean.msg);
                    }
                }, throwable -> LogUtils.e("send===" + throwable.toString()));

    }

    public void outLogin(Context context) {
        mModel.outLogin()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {

                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            mRootView.loadOutLoginSuccess();
                        }else {
                            mRootView.loadOutLoginFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadOutLoginFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
