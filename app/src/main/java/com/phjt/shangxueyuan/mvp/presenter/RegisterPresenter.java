package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.RegisterBean;
import com.phjt.shangxueyuan.mvp.contract.RegisterContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.LogUtils;


/**
 * @author: Created by Template
 * date: 03/27/2020 10:20
 * company: 普华集团
 * description: register page presenter
 */
@ActivityScope
public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public RegisterPresenter(RegisterContract.Model model, RegisterContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    /**
     * 滑块验证
     *
     * @param csessionid csessionid
     * @param sig        sig
     * @param token      token
     * @param scene      scene
     */
    public void sliderValidation(String csessionid, String sig, String token, String scene,String mobile) {
        mModel.sliderValidation(csessionid, sig, token, scene, mobile)
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


    public void userRegister(String mobile, String verificationCode, String password) {
        mModel.userRegister(mobile, verificationCode, password)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            try {
                                String json = baseBean.data.toString();
                                if (!TextUtils.isEmpty(json)) {

                                    LogUtils.d("token>>>", json);
                                    mRootView.registerSuccess(json);
                                } else {
                                    mRootView.registerFailed(baseBean.msg);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                mRootView.registerFailed(baseBean.msg);
                            }


                        } else {
                            mRootView.registerFailed(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.registerFailed(e.getMessage());
                    }

                });
    }

    public void getVerificationCode(String mobile, int verificationcodeTypeReg) {
        mModel.getVerificationCode(mobile, verificationcodeTypeReg)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getVerificationCodeSuccess();
                        } else {
                            mRootView.getVerificationCodeFailed(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.getVerificationCodeFailed(e.getMessage());
                    }

                });
    }
}
