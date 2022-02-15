package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.TrainingCatalogContract;
import com.phjt.shangxueyuan.mvp.model.TrainingCatalogModel;


/**
 * @author: Created by GengYan
 * date: 01/19/2021 16:26
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class TrainingCatalogModule {

    @Binds
    abstract TrainingCatalogContract.Model bindTrainingCatalogModel(TrainingCatalogModel model);
}