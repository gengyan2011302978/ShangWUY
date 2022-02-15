package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.HotRecommendContract;
import com.phjt.shangxueyuan.mvp.model.HotRecommendModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/27 17:21
 */

@Module
public abstract class HotRecommendModule {

    @Binds
    abstract HotRecommendContract.Model bindHotRecommendModel(HotRecommendModel model);
}