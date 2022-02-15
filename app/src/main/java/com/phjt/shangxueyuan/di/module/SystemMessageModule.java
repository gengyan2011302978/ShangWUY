package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.SystemMessageContract;
import com.phjt.shangxueyuan.mvp.model.SystemMessageModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/20 10:52
 */

@Module
public abstract class SystemMessageModule {

    @Binds
    abstract SystemMessageContract.Model bindSystemMessageModel(SystemMessageModel model);
}