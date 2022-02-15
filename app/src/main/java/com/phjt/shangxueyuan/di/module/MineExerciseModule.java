package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MineExerciseContract;
import com.phjt.shangxueyuan.mvp.model.MineExerciseModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/03/17 10:24
 */

@Module
public abstract class MineExerciseModule {

    @Binds
    abstract MineExerciseContract.Model bindMineExerciseModel(MineExerciseModel model);
}