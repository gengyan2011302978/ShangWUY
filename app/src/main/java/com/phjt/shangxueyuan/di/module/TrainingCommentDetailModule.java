package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.TrainingCommentDetailContract;
import com.phjt.shangxueyuan.mvp.model.TrainingCommentDetailModel;


/**
 * @author: Created by GengYan
 * date: 02/04/2021 11:17
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class TrainingCommentDetailModule {

    @Binds
    abstract TrainingCommentDetailContract.Model bindTrainingCommentDetailModel(TrainingCommentDetailModel model);
}