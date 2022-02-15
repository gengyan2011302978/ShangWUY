package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.phjt.shangxueyuan.mvp.contract.AgentContract;
import com.phjt.shangxueyuan.mvp.model.AgentModel;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/09/09 18:01
 * @description :
 */
@Module
//构建AgentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class AgentModule {

    @Binds
    abstract AgentContract.Model bindAgentModel(AgentModel model);
}