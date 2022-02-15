package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.di.module.AgentModule;
import com.phjt.shangxueyuan.mvp.contract.AgentContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.AgentActivity;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/09/09 18:01
 * @description :
 */
@ActivityScope
@Component(modules = AgentModule.class, dependencies = AppComponent.class)
public interface AgentComponent {

    void inject(AgentActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AgentComponent.Builder view(AgentContract.View view);

        AgentComponent.Builder appComponent(AppComponent appComponent);

        AgentComponent build();
    }
}