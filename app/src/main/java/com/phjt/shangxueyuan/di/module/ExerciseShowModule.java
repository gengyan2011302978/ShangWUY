package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ExerciseShowContract;
import com.phjt.shangxueyuan.mvp.model.ExerciseShowModel;


/**
 * @author: Created by GengYan
 * date: 03/11/2021 11:13
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ExerciseShowModule {

    @Binds
    abstract ExerciseShowContract.Model bindExerciseShowModel(ExerciseShowModel model);
}