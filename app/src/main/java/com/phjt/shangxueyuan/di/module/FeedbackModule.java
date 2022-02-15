package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.FeedbackContract;
import com.phjt.shangxueyuan.mvp.model.FeedbackModel;


@Module
public abstract class FeedbackModule {

    @Binds
    abstract FeedbackContract.Model bindFeedbackModel(FeedbackModel model);
}