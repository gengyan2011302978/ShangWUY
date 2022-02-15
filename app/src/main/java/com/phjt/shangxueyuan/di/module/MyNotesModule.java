package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MyNotesContract;
import com.phjt.shangxueyuan.mvp.model.MyNotesModel;


@Module
public abstract class MyNotesModule {

    @Binds
    abstract MyNotesContract.Model bindMyNotesModel(MyNotesModel model);
}