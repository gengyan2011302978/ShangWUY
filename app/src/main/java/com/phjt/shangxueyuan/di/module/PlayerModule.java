package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.PlayerContract;
import com.phjt.shangxueyuan.mvp.model.PlayerModel;


/**
 * @author: Created by Template
 * date: 03/25/2020 14:40
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class PlayerModule {

    @Binds
    abstract PlayerContract.Model bindPlayerModel(PlayerModel model);
}