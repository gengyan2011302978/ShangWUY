package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.di.module.SubordinateAgentsModule;
import com.phjt.shangxueyuan.mvp.contract.SubordinateAgentsContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.SubordinateAgentsFragment;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/09/09 18:15
 * @description :
 */
@FragmentScope
@Component(modules = SubordinateAgentsModule.class, dependencies = AppComponent.class)
public interface SubordinateAgentsComponent {

    void inject(SubordinateAgentsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SubordinateAgentsComponent.Builder view(SubordinateAgentsContract.View view);

        SubordinateAgentsComponent.Builder appComponent(AppComponent appComponent);

        SubordinateAgentsComponent build();
    }
}