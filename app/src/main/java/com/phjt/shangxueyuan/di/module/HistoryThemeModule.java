package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.HistoryThemeContract;
import com.phjt.shangxueyuan.mvp.model.HistoryThemeModel;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 17:08
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class HistoryThemeModule {

    @Binds
    abstract HistoryThemeContract.Model bindHistoryThemeModel(HistoryThemeModel model);
}