package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.TrainingPlayContract;
import com.phjt.shangxueyuan.mvp.model.TrainingPlayModel;


/**
 * @author: Created by GengYan
 * date: 01/20/2021 11:57
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class TrainingPlayModule {

    @Binds
    abstract TrainingPlayContract.Model bindTrainingPlayModel(TrainingPlayModel model);
}