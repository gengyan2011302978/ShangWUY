package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.CouponContract;
import com.phjt.shangxueyuan.mvp.model.CouponModel;


/**
 * @author: Created by GengYan
 * date: 11/24/2020 11:40
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class CouponModule {

    @Binds
    abstract CouponContract.Model bindCouponModel(CouponModel model);
}