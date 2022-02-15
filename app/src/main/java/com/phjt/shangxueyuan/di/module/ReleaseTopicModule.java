package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ReleaseTopicContract;
import com.phjt.shangxueyuan.mvp.model.ReleaseTopicModel;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:28
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ReleaseTopicModule {

    @Binds
    abstract ReleaseTopicContract.Model bindReleaseTopicModel(ReleaseTopicModel model);
}