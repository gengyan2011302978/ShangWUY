package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.phjt.shangxueyuan.mvp.contract.AllStudyCampContract;
import com.phjt.shangxueyuan.mvp.model.AllStudyCampModel;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/08/19 17:12
 * @description :
 */
@Module
//构建AllStudyCampModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class AllStudyCampModule {

    @Binds
    abstract AllStudyCampContract.Model bindAllStudyCampModel(AllStudyCampModel model);
}