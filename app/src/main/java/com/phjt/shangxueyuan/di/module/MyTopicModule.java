package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MyTopicContract;
import com.phjt.shangxueyuan.mvp.model.MyTopicModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 10:10
 */

@Module
public abstract class MyTopicModule {

    @Binds
    abstract MyTopicContract.Model bindMyTopicModel(MyTopicModel model);
}