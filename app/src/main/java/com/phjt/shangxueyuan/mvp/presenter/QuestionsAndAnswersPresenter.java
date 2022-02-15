package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.ScreenLabelBean;
import com.phjt.shangxueyuan.bean.ScreenlBean;
import com.phjt.shangxueyuan.mvp.contract.QuestionsAndAnswersContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 10:56
 * @description :
 */
@ActivityScope
public class QuestionsAndAnswersPresenter extends BasePresenter<QuestionsAndAnswersContract.Model, QuestionsAndAnswersContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public QuestionsAndAnswersPresenter(QuestionsAndAnswersContract.Model model, QuestionsAndAnswersContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 筛选
     */
    public void getRealmSelectList() {
        mModel.getRealmSelectList()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<ScreenlBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<ScreenlBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadDataSuccess(bean.data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

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