package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.AboutContract;
import com.phjt.shangxueyuan.mvp.model.AboutModel;


@Module
public abstract class AboutModule {

    @Binds
    abstract AboutContract.Model bindAboutModel(AboutModel model);
}