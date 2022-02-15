package com.phjt.shangxueyuan.di.module;

import dagger.Binds;
import dagger.Module;

import com.phjt.shangxueyuan.mvp.contract.WjjAuthenticationContract;
import com.phjt.shangxueyuan.mvp.model.WjjAuthenticationModel;


@Module
public abstract class WjjAuthenticationModule {

    @Binds
    abstract WjjAuthenticationContract.Model bindAjjAuthenticationModel(WjjAuthenticationModel model);
}