package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ScreenlBean;
import com.phjt.shangxueyuan.mvp.contract.PutQuestionsToContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 14:32
 * @description :
 */
@ActivityScope
public class PutQuestionsToPresenter extends BasePresenter<PutQuestionsToContract.Model, PutQuestionsToContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public PutQuestionsToPresenter(PutQuestionsToContract.Model model, PutQuestionsToContract.View rootView) {
        super(model, rootView);
    }



    /**
     * 筛选
     */
    public void getRealmSelectList(String teacherId) {
        mModel.getRealmSelectList(teacherId)
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


    /**
     * 发布问题
     */
    public void sendQuestion(String tutorId, String title, String content, String realmId, int isOpen,String questionImg,int payType, int quantity) {
        mModel.sendQuestion(tutorId, title, content, realmId, isOpen,questionImg,payType,quantity)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            mRootView.sendQuestionSuccess();
                        } else {
                            mRootView.sendQuestionFail(bean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.sendQuestionFail(e.getMessage());
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