package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ExerciseListContract;
import com.phjt.shangxueyuan.mvp.model.ExerciseListModel;


/**
 * @author: Created by GengYan
 * date: 03/22/2021 16:26
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ExerciseListModule {

    @Binds
    abstract ExerciseListContract.Model bindExerciseListModel(ExerciseListModel model);
}