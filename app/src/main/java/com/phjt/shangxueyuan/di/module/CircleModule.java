package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.CircleContract;
import com.phjt.shangxueyuan.mvp.model.CircleModel;


/**
 * @author: Created by GengYan
 * date: 10/19/2020 10:24
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class CircleModule {

    @Binds
    abstract CircleContract.Model bindCircleModel(CircleModel model);
}