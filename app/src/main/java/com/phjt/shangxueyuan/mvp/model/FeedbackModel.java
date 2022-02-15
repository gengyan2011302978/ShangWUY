package com.phjt.shangxueyuan.mvp.model;


import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.FeedbackContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


@ActivityScope
public class FeedbackModel extends BaseModel implements FeedbackContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public FeedbackModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean> submitFeedback(String feedbackContent, String feedbackImg) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).submitFeedback(feedbackContent, feedbackImg);
    }
}