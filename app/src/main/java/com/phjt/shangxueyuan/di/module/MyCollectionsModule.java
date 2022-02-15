package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MyCollectionsContract;
import com.phjt.shangxueyuan.mvp.model.MyCollectionsModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/08/26 09:53
 */

@Module
public abstract class MyCollectionsModule {

    @Binds
    abstract MyCollectionsContract.Model bindMyCollectionsModel(MyCollectionsModel model);
}