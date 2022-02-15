package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.TopicTimeContract;
import com.phjt.shangxueyuan.mvp.model.TopicTimeModel;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:43
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class TopicTimeModule {

    @Binds
    abstract TopicTimeContract.Model bindTopicTimeModel(TopicTimeModel model);
}