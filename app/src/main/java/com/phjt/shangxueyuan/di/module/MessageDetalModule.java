package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MessageDetalContract;
import com.phjt.shangxueyuan.mvp.model.MessageDetalModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/28 09:35
 */

@Module
public abstract class MessageDetalModule {

    @Binds
    abstract MessageDetalContract.Model bindMessageDetalModel(MessageDetalModel model);
}