package com.phjt.shangxueyuan.mvp.presenter;

import android.annotation.SuppressLint;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.ForgetPasswordContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.LogUtils;

import javax.inject.Inject;


/**
 * @author: Created by Template
 * date: 03/27/2020 13:16
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordContract.Model, ForgetPasswordContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public ForgetPasswordPresenter(ForgetPasswordContract.Model model, ForgetPasswordContract.View rootView) {
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
    public void sendCode(String phone, int codeType) {
        mModel.sendCode(phone, codeType).compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(baseBean -> {
                    if (baseBean.isOk()) {
                        mRootView.sendVerficationSuccess();
                    } else {
                        mRootView.sendVerficationFailed(baseBean.msg);
                    }
                }, throwable -> LogUtils.e("send===" + throwable.toString()));

    }

    @SuppressLint("CheckResult")
    public void bindingPhone(String mobile, String openId, String code, String imgUrl, String userName,String unionid) {
        mModel.bindingPhone(mobile, openId, code, imgUrl, userName,unionid).compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(tokenBeanBaseBean -> {
                    if (tokenBeanBaseBean.isOk() && tokenBeanBaseBean.data != null) {
                        mRootView.loginSuccess(tokenBeanBaseBean.data);
                    } else {
                        mRootView.loginFailed(tokenBeanBaseBean.msg);
                    }
                }, throwable -> LogUtils.e("weChatLogin===" + throwable.toString()));

    }

    @SuppressLint("CheckResult")
    public void validateCode(String mobile, String code, int codeType) {
        mModel.validateCode(mobile, code ,codeType).compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(tokenBeanBaseBean -> {
                    if (tokenBeanBaseBean.isOk()) {
                        mRootView.validateCodeSuccess();
                    } else {
                        mRootView.validateCodeFailed(tokenBeanBaseBean.msg);
                    }
                }, throwable -> LogUtils.e("validatecode===" + throwable.toString()));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
