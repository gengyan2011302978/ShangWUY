package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MyLearnCurrencyContract;
import com.phjt.shangxueyuan.mvp.model.MyLearnCurrencyModel;


/**
 * @author: Created by GengYan
 * date: 06/21/2021 16:43
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class MyLearnCurrencyModule {

    @Binds
    abstract MyLearnCurrencyContract.Model bindMyLearnCurrencyModel(MyLearnCurrencyModel model);
}