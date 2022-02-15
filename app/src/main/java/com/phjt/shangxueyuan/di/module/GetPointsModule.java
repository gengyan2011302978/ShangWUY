package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.GetPointsContract;
import com.phjt.shangxueyuan.mvp.model.GetPointsModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:08
 */

@Module
public abstract class GetPointsModule {

    @Binds
    abstract GetPointsContract.Model bindGetPointsModel(GetPointsModel model);
}