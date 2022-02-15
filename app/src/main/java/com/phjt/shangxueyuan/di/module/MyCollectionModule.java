package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MyCollectionContract;
import com.phjt.shangxueyuan.mvp.model.MyCollectionModel;


@Module
public abstract class MyCollectionModule {

    @Binds
    abstract MyCollectionContract.Model bindMyCollectionModel(MyCollectionModel model);
}