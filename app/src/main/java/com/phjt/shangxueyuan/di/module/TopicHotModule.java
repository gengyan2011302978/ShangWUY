package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.TopicHotContract;
import com.phjt.shangxueyuan.mvp.model.TopicHotModel;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:42
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class TopicHotModule {

    @Binds
    abstract TopicHotContract.Model bindTopicHotModel(TopicHotModel model);
}