package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.RegisterModule;
import com.phjt.shangxueyuan.mvp.contract.RegisterContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.RegisterActivity;


/**
 * @author: Created by Template
 * date: 03/27/2020 10:20
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = RegisterModule.class, dependencies = AppComponent.class)
public interface RegisterComponent {
    void inject(RegisterActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RegisterComponent.Builder view(RegisterContract.View view);

        RegisterComponent.Builder appComponent(AppComponent appComponent);

        RegisterComponent build();
    }
}