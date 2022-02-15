package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.AllTopicContract;
import com.phjt.shangxueyuan.mvp.model.AllTopicModel;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:29
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class AllTopicModule {

    @Binds
    abstract AllTopicContract.Model bindAllTopicModel(AllTopicModel model);
}