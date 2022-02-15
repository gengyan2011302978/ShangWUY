package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.TrainingCampDetailContract;
import com.phjt.shangxueyuan.mvp.model.TrainingCampDetailModel;


/**
 * @author: Created by GengYan
 * date: 01/18/2021 13:53
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class TrainingCampDetailModule {

    @Binds
    abstract TrainingCampDetailContract.Model bindTrainingCampDetailModel(TrainingCampDetailModel model);
}