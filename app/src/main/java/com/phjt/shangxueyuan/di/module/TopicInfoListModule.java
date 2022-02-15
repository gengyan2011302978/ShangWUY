package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.TopicInfoListContract;
import com.phjt.shangxueyuan.mvp.model.TopicInfoListModel;


/**
 * @author: Created by GengYan
 * date: 11/03/2020 18:23
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class TopicInfoListModule {

    @Binds
    abstract TopicInfoListContract.Model bindTopicInfoListModel(TopicInfoListModel model);
}