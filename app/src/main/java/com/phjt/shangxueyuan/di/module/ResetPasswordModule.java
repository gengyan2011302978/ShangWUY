package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ResetPasswordContract;
import com.phjt.shangxueyuan.mvp.model.ResetPasswordModel;


/**
 * @author: Created by Template
 * date: 03/27/2020 13:40
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ResetPasswordModule {

    @Binds
    abstract ResetPasswordContract.Model bindResetPasswordModel(ResetPasswordModel model);
}