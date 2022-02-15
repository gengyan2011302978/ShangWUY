package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.WithdrawalDetailContract;
import com.phjt.shangxueyuan.mvp.model.WithdrawalDetailModel;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 17:05
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class WithdrawalDetailModule {

    @Binds
    abstract WithdrawalDetailContract.Model bindWithdrawalDetailModel(WithdrawalDetailModel model);
}