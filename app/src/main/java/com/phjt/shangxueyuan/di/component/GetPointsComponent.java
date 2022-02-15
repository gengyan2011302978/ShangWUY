package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.GetPointsModule;
import com.phjt.shangxueyuan.mvp.contract.GetPointsContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.GetPointsFragment;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:08
 */

@FragmentScope
@Component(modules = GetPointsModule.class, dependencies = AppComponent.class)
public interface GetPointsComponent {
    void inject(GetPointsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GetPointsComponent.Builder view(GetPointsContract.View view);

        GetPointsComponent.Builder appComponent(AppComponent appComponent);

        GetPointsComponent build();
    }
}