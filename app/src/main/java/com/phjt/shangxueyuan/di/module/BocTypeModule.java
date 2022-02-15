package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.BocTypeContract;
import com.phjt.shangxueyuan.mvp.model.BocTypeModel;


/**
 * @author: Created by GengYan
 * date: 10/27/2020 13:53
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class BocTypeModule {

    @Binds
    abstract BocTypeContract.Model bindBocTypeModel(BocTypeModel model);
}