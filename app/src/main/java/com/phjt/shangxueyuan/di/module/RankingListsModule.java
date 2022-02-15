package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.RankingListsContract;
import com.phjt.shangxueyuan.mvp.model.RankingListsModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:19
 */

@Module
public abstract class RankingListsModule {

    @Binds
    abstract RankingListsContract.Model bindRankingListsModel(RankingListsModel model);
}