package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MyDowloadContract;
import com.phjt.shangxueyuan.mvp.model.MyDowloadModel;


/**
 * @author: Created by Template
 * date: 06/02/2020 10:34
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class MyDowloadModule {

    @Binds
    abstract MyDowloadContract.Model bindMyDowloadModel(MyDowloadModel model);
}