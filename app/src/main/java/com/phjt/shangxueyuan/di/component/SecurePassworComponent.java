package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.SecurePassworModule;
import com.phjt.shangxueyuan.mvp.contract.SecurePassworContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.SecurePasswordActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/12 11:18
 */

@ActivityScope
@Component(modules = SecurePassworModule.class, dependencies = AppComponent.class)
public interface SecurePassworComponent {
    void inject(SecurePasswordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SecurePassworComponent.Builder view(SecurePassworContract.View view);

        SecurePassworComponent.Builder appComponent(AppComponent appComponent);

        SecurePassworComponent build();
    }
}