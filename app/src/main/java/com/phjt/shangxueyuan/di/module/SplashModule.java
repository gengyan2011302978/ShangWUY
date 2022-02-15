package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.SplashContract;
import com.phjt.shangxueyuan.mvp.model.SplashModel;


/**
 * @author: Created by Template
 * date: 03/25/2020 18:00
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class SplashModule {

    @Binds
    abstract SplashContract.Model bindSplashModel(SplashModel model);
}