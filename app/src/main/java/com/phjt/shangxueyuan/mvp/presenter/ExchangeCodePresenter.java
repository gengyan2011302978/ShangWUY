package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.ExchangeCodeContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.LogUtils;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 11:00
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ExchangeCodePresenter extends BasePresenter<ExchangeCodeContract.Model, ExchangeCodeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public ExchangeCodePresenter(ExchangeCodeContract.Model model, ExchangeCodeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void toChange(String code) {
        mModel.toChange(code)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.toChangeSuccess(baseBean);
                        } else {
                            mRootView.toChangefaile(baseBean.msg);
                        }
                    }
                });
    }
}
