package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ExerciseDoContract;
import com.phjt.shangxueyuan.mvp.model.ExerciseDoModel;


/**
 * @author: Created by GengYan
 * date: 03/16/2021 09:46
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ExerciseDoModule {

    @Binds
    abstract ExerciseDoContract.Model bindExerciseDoModel(ExerciseDoModel model);
}