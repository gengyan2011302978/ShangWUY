package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.MobileLoginContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by Template
 * date: 03/30/2020 10:42
 * company: 普华集团
 * description: login by mobile
 */
@ActivityScope
public class MobileLoginPresenter extends BasePresenter<MobileLoginContract.Model, MobileLoginContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public MobileLoginPresenter(MobileLoginContract.Model model, MobileLoginContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void doLogin(String mobile, String password) {
        mModel.doLogin(mobile, password)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.doLoginSuccess(String.valueOf(baseBean.data));
                        } else {
                            mRootView.doLoginFailed(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //mRootView.doLoginFailed(e.getMessage());
                    }

                });
    }

    public void weChatLogin(String mOpenId,String unionid) {
        mModel.loginByWeChat(mOpenId,unionid).compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> tokenBeanBaseBean) {
                        if (tokenBeanBaseBean != null) {
                            if (tokenBeanBaseBean.isOk() && tokenBeanBaseBean.data != null) {
                                mRootView.loginSuccess(tokenBeanBaseBean.data);
                            } else {
                                mRootView.loginFailedByWx(tokenBeanBaseBean.msg);
                            }
                        }
                    }
                });
    }
}
