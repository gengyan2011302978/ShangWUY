package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MineContract;
import com.phjt.shangxueyuan.mvp.model.MineModel;


@Module
public abstract class MineModule {

    @Binds
    abstract MineContract.Model bindMineModel(MineModel model);
}