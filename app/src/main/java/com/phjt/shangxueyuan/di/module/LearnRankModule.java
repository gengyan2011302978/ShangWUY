package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.LearnRankContract;
import com.phjt.shangxueyuan.mvp.model.LearnRankModel;


/**
 * @author: Created by GengYan
 * date: 06/22/2021 11:23
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class LearnRankModule {

    @Binds
    abstract LearnRankContract.Model bindLearnRankModel(LearnRankModel model);
}