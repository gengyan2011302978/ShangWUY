package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ChangeSecurityPasswordModule;
import com.phjt.shangxueyuan.mvp.contract.ChangeSecurityPasswordContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ChangeSecurityPasswordActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/12 09:54
 */

@ActivityScope
@Component(modules = ChangeSecurityPasswordModule.class, dependencies = AppComponent.class)
public interface ChangeSecurityPasswordComponent {
    void inject(ChangeSecurityPasswordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChangeSecurityPasswordComponent.Builder view(ChangeSecurityPasswordContract.View view);

        ChangeSecurityPasswordComponent.Builder appComponent(AppComponent appComponent);

        ChangeSecurityPasswordComponent build();
    }
}