package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MyExerciseContract;
import com.phjt.shangxueyuan.mvp.model.MyExerciseModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/03/11 13:54
 */

@Module
public abstract class MyExerciseModule {

    @Binds
    abstract MyExerciseContract.Model bindMyExerciseModel(MyExerciseModel model);
}