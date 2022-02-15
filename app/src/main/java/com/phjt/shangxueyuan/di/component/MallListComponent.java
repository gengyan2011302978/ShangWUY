package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.di.module.MallListModule;
import com.phjt.shangxueyuan.mvp.contract.MallListContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.MallListFragment;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 16:22
 * @description :
 */
@FragmentScope
@Component(modules = MallListModule.class, dependencies = AppComponent.class)
public interface MallListComponent {

    void inject(MallListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MallListComponent.Builder view(MallListContract.View view);

        MallListComponent.Builder appComponent(AppComponent appComponent);

        MallListComponent build();
    }
}