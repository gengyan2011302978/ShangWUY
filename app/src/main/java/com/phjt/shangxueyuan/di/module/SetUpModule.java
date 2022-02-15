package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.SetUpContract;
import com.phjt.shangxueyuan.mvp.model.SetUpModel;


@Module
public abstract class SetUpModule {

    @Binds
    abstract SetUpContract.Model bindSetUpModel(SetUpModel model);
}