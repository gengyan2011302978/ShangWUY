package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MyColumnContract;
import com.phjt.shangxueyuan.mvp.model.MyColumnModel;


/**
 * @author: Created by GengYan
 * date: 12/09/2020 17:27
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class MyColumnModule {

    @Binds
    abstract MyColumnContract.Model bindMyColumnModel(MyColumnModel model);
}