package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ApplyWithdrawalContract;
import com.phjt.shangxueyuan.mvp.model.ApplyWithdrawalModel;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 11:37
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ApplyWithdrawalModule {

    @Binds
    abstract ApplyWithdrawalContract.Model bindApplyWithdrawalModel(ApplyWithdrawalModel model);
}