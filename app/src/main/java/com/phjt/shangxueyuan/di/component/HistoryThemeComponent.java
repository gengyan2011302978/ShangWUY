package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.HistoryThemeModule;
import com.phjt.shangxueyuan.mvp.contract.HistoryThemeContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.HistoryThemeActivity;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 17:08
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = HistoryThemeModule.class, dependencies = AppComponent.class)
public interface HistoryThemeComponent {
    void inject(HistoryThemeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HistoryThemeComponent.Builder view(HistoryThemeContract.View view);

        HistoryThemeComponent.Builder appComponent(AppComponent appComponent);

        HistoryThemeComponent build();
    }
}