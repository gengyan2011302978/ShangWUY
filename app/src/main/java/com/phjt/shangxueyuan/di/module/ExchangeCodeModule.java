package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ExchangeCodeContract;
import com.phjt.shangxueyuan.mvp.model.ExchangeCodeModel;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 11:00
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ExchangeCodeModule {

    @Binds
    abstract ExchangeCodeContract.Model bindExchangeCodeModel(ExchangeCodeModel model);
}