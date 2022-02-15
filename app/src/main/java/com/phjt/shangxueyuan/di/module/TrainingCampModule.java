package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.TrainingCampContract;
import com.phjt.shangxueyuan.mvp.model.TrainingCampModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:14
 */

@Module
public abstract class TrainingCampModule {

    @Binds
    abstract TrainingCampContract.Model bindTrainingCampModel(TrainingCampModel model);
}