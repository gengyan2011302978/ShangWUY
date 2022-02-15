package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;

import com.phjt.shangxueyuan.mvp.contract.SubordinateAgentsContract;
import com.phjt.shangxueyuan.mvp.model.SubordinateAgentsModel;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/09/09 18:15
 * @description :
 */
@Module
//构建SubordinateAgentsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class SubordinateAgentsModule {

    @Binds
    abstract SubordinateAgentsContract.Model bindSubordinateAgentsModel(SubordinateAgentsModel model);
}