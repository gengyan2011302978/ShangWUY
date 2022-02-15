package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.BindOtherAccountContract;
import com.phjt.shangxueyuan.mvp.model.BindOtherAccountModel;


/**
 * @author: Created by Template
 * date: 12/28/2020 17:51
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class BindOtherAccountModule {

    @Binds
    abstract BindOtherAccountContract.Model bindBindOtherAccountModel(BindOtherAccountModel model);
}