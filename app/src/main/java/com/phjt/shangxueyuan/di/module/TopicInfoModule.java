package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.TopicInfoContract;
import com.phjt.shangxueyuan.mvp.model.TopicInfoModel;


/**
 * @author: Created by GengYan
 * date: 11/03/2020 15:08
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class TopicInfoModule {

    @Binds
    abstract TopicInfoContract.Model bindTopicInfoModel(TopicInfoModel model);
}