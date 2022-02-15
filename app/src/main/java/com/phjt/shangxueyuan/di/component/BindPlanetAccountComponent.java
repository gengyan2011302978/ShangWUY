package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.BindPlanetAccountModule;
import com.phjt.shangxueyuan.mvp.contract.BindPlanetAccountContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.BindPlanetAccountActivity;


/**
 * @author: Created by Template
 * date: 12/28/2020 13:47
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = BindPlanetAccountModule.class, dependencies = AppComponent.class)
public interface BindPlanetAccountComponent {
    void inject(BindPlanetAccountActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BindPlanetAccountComponent.Builder view(BindPlanetAccountContract.View view);

        BindPlanetAccountComponent.Builder appComponent(AppComponent appComponent);

        BindPlanetAccountComponent build();
    }
}