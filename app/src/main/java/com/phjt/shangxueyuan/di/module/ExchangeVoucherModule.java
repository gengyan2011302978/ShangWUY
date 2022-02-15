package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ExchangeVoucherContract;
import com.phjt.shangxueyuan.mvp.model.ExchangeVoucherModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/11/24 14:11
 */

@Module
public abstract class ExchangeVoucherModule {

    @Binds
    abstract ExchangeVoucherContract.Model bindExchangeVoucherModel(ExchangeVoucherModel model);
}