package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.PunchClockListContract;
import com.phjt.shangxueyuan.mvp.model.PunchClockListModel;


/**
 * @author: Created by GengYan
 * date: 02/05/2021 11:30
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class PunchClockListModule {

    @Binds
    abstract PunchClockListContract.Model bindPunchClockListModel(PunchClockListModel model);
}