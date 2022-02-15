package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ViewRecordContract;
import com.phjt.shangxueyuan.mvp.model.ViewRecordModel;


/**
 * @author: Created by Template
 * date: 03/27/2020 17:38
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ViewRecordModule {

    @Binds
    abstract ViewRecordContract.Model bindViewRecordModel(ViewRecordModel model);
}