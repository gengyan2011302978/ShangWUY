package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.CalendarModule;
import com.phjt.shangxueyuan.mvp.contract.CalendarContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.CalendarActivity;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 15:14
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = CalendarModule.class, dependencies = AppComponent.class)
public interface CalendarComponent {
    void inject(CalendarActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CalendarComponent.Builder view(CalendarContract.View view);

        CalendarComponent.Builder appComponent(AppComponent appComponent);

        CalendarComponent build();
    }
}