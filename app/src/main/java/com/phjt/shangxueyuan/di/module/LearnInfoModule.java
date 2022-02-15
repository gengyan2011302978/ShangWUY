package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.LearnInfoContract;
import com.phjt.shangxueyuan.mvp.model.LearnInfoModel;


/**
 * @author: Created by GengYan
 * date: 06/22/2021 10:24
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class LearnInfoModule {

    @Binds
    abstract LearnInfoContract.Model bindLearnInfoModel(LearnInfoModel model);
}