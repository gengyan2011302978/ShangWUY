package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.AuditionContract;
import com.phjt.shangxueyuan.mvp.model.AuditionModel;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 10:49
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class AuditionModule {

    @Binds
    abstract AuditionContract.Model bindAuditionModel(AuditionModel model);
}