package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CheckTheAnswerBean;
import com.phjt.shangxueyuan.mvp.contract.CheckTheAnswerContract;
import com.phjt.shangxueyuan.utils.RxUtils;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 11:05
 * @description :
 */
@ActivityScope
public class CheckTheAnswerPresenter extends BasePresenter<CheckTheAnswerContract.Model, CheckTheAnswerContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CheckTheAnswerPresenter(CheckTheAnswerContract.Model model, CheckTheAnswerContract.View rootView) {
        super(model, rootView);
    }



    /**
     * 查询精选解答列表信息
     */
    public void getUserQuestion(String answerId) {
        mModel.getUserQuestion(answerId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<CheckTheAnswerBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<CheckTheAnswerBean> bean) {
                        if (bean.isOk()) {
                            mRootView.checkTheAnswerSuccess(bean.data);
                        } else {
                            mRootView.checkTheAnswerFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.checkTheAnswerFail();
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