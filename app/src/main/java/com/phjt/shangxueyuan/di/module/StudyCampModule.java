package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.StudyCampContract;
import com.phjt.shangxueyuan.mvp.model.StudyCampModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/23 09:41
 */

@Module
public abstract class StudyCampModule {

    @Binds
    abstract StudyCampContract.Model bindStudyCampModel(StudyCampModel model);
}