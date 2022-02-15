package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.HomePagerContract;
import com.phjt.shangxueyuan.mvp.model.HomePagerModel;


/**
 * @author: Created by Template
 * date: 03/24/2020 14:09
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class HomePagerModule {

    @Binds
    abstract HomePagerContract.Model bindHomePagerModel(HomePagerModel model);
}