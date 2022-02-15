package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ReceiveUseLearnContract;
import com.phjt.shangxueyuan.mvp.model.ReceiveUseLearnModel;


/**
 * @author: Created by GengYan
 * date: 06/22/2021 10:38
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ReceiveUseLearnModule {

    @Binds
    abstract ReceiveUseLearnContract.Model bindReceiveUseLearnModel(ReceiveUseLearnModel model);
}