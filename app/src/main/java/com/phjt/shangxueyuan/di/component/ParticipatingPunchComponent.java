package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ParticipatingPunchModule;
import com.phjt.shangxueyuan.mvp.contract.ParticipatingPunchContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.ParticipatingPunchFragment;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:21
 */

@FragmentScope
@Component(modules = ParticipatingPunchModule.class, dependencies = AppComponent.class)
public interface ParticipatingPunchComponent {
    void inject(ParticipatingPunchFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ParticipatingPunchComponent.Builder view(ParticipatingPunchContract.View view);

        ParticipatingPunchComponent.Builder appComponent(AppComponent appComponent);

        ParticipatingPunchComponent build();
    }
}