package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.WithdrawalRecordContract;
import com.phjt.shangxueyuan.mvp.model.WithdrawalRecordModel;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 11:22
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class WithdrawalRecordModule {

    @Binds
    abstract WithdrawalRecordContract.Model bindWithdrawalRecordModel(WithdrawalRecordModel model);
}