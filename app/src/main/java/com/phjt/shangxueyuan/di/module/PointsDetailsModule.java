package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.PointsDetailsContract;
import com.phjt.shangxueyuan.mvp.model.PointsDetailsModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:09
 */

@Module
public abstract class PointsDetailsModule {

    @Binds
    abstract PointsDetailsContract.Model bindPointsDetailsModel(PointsDetailsModel model);
}