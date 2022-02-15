package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.RegisterContract;
import com.phjt.shangxueyuan.mvp.model.RegisterModel;


/**
 * @author: Created by Template
 * date: 03/27/2020 10:20
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class RegisterModule {

    @Binds
    abstract RegisterContract.Model bindRegisterModel(RegisterModel model);
}