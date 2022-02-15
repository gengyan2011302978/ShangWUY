package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MobileLoginContract;
import com.phjt.shangxueyuan.mvp.model.MobileLoginModel;


/**
 * @author: Created by Template
 * date: 03/30/2020 10:42
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class MobileLoginModule {

    @Binds
    abstract MobileLoginContract.Model bindMobileLoginModel(MobileLoginModel model);
}