package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.PublishDynamicContract;
import com.phjt.shangxueyuan.mvp.model.PublishDynamicModel;


/**
 * @author: Created by GengYan
 * date: 11/04/2020 16:18
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class PublishDynamicModule {

    @Binds
    abstract PublishDynamicContract.Model bindPublishDynamicModel(PublishDynamicModel model);
}