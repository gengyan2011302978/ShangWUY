package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MyClockInContract;
import com.phjt.shangxueyuan.mvp.model.MyClockInModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:01
 */

@Module
public abstract class MyClockInModule {

    @Binds
    abstract MyClockInContract.Model bindMyClockInModel(MyClockInModel model);
}