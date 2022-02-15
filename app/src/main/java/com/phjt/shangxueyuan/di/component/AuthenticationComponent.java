package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.AuthenticationModule;
import com.phjt.shangxueyuan.mvp.contract.AuthenticationContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.AuthenticationActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/11 15:02
 */

@ActivityScope
@Component(modules = AuthenticationModule.class, dependencies = AppComponent.class)
public interface AuthenticationComponent {
    void inject(AuthenticationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AuthenticationComponent.Builder view(AuthenticationContract.View view);

        AuthenticationComponent.Builder appComponent(AppComponent appComponent);

        AuthenticationComponent build();
    }
}