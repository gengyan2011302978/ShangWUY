package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MyPointsContract;
import com.phjt.shangxueyuan.mvp.model.MyPointsModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 10:54
 */

@Module
public abstract class MyPointsModule {

    @Binds
    abstract MyPointsContract.Model bindMyPointsModel(MyPointsModel model);
}