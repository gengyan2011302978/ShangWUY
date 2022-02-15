package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.PunchClockListModule;
import com.phjt.shangxueyuan.mvp.contract.PunchClockListContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.PunchClockListActivity;


/**
 * @author: Created by GengYan
 * date: 02/05/2021 11:30
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = PunchClockListModule.class, dependencies = AppComponent.class)
public interface PunchClockListComponent {
    void inject(PunchClockListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PunchClockListComponent.Builder view(PunchClockListContract.View view);

        PunchClockListComponent.Builder appComponent(AppComponent appComponent);

        PunchClockListComponent build();
    }
}