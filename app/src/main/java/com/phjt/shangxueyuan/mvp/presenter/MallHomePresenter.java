package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.mvp.contract.MallHomeContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 14:43
 * @description :
 */
@ActivityScope
public class MallHomePresenter extends BasePresenter<MallHomeContract.Model, MallHomeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public MallHomePresenter(MallHomeContract.Model model, MallHomeContract.View rootView) {
        super(model, rootView);
    }

    public void getUserAssetsInfo() {
        mModel.getUserAssetsInfo()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UserAssetsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseBean<UserAssetsBean> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.showUserAssets(baseBean.data);
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
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