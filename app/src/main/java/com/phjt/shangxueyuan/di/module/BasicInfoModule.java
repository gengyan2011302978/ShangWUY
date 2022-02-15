package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.BasicInfoContract;
import com.phjt.shangxueyuan.mvp.model.BasicInfoModel;


@Module
public abstract class BasicInfoModule {

    @Binds
    abstract BasicInfoContract.Model bindBasicInfoModel(BasicInfoModel model);
}