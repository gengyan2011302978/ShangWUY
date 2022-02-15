package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.RankingContract;
import com.phjt.shangxueyuan.mvp.model.RankingModel;


/**
 * @author: Created by GengYan
 * date: 01/29/2021 13:57
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class RankingModule {

    @Binds
    abstract RankingContract.Model bindRankingModel(RankingModel model);
}