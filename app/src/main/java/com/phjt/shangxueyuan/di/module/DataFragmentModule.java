package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.DataFragmentContract;
import com.phjt.shangxueyuan.mvp.model.DataFragmentModel;


/**
 * @author: Created by GengYan
 * date: 06/05/2020 18:12
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class DataFragmentModule {

    @Binds
    abstract DataFragmentContract.Model bindDataFragmentModel(DataFragmentModel model);
}