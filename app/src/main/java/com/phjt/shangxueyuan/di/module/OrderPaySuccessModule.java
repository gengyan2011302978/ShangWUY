package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.phjt.shangxueyuan.mvp.contract.OrderPaySuccessContract;
import com.phjt.shangxueyuan.mvp.model.OrderPaySuccessModel;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/21 14:32
 * @description :
 */
@Module
//构建OrderPaySuccessModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class OrderPaySuccessModule {

    @Binds
    abstract OrderPaySuccessContract.Model bindOrderPaySuccessModel(OrderPaySuccessModel model);
}