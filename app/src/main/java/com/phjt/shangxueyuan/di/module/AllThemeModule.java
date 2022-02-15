package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.AllThemeContract;
import com.phjt.shangxueyuan.mvp.model.AllThemeModel;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 16:23
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class AllThemeModule {

    @Binds
    abstract AllThemeContract.Model bindAllThemeModel(AllThemeModel model);
}