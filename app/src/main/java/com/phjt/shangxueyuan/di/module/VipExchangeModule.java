package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.VipExchangeContract;
import com.phjt.shangxueyuan.mvp.model.VipExchangeModel;


/**
 * @author: Created by GengYan
 * date: 08/06/2020 09:51
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class VipExchangeModule {

    @Binds
    abstract VipExchangeContract.Model bindVipExchangeModel(VipExchangeModel model);
}