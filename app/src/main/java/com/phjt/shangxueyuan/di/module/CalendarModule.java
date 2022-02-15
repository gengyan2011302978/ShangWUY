package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.CalendarContract;
import com.phjt.shangxueyuan.mvp.model.CalendarModel;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 15:14
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class CalendarModule {

    @Binds
    abstract CalendarContract.Model bindCalendarModel(CalendarModel model);
}