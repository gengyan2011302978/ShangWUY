package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.SystemMessageModule;
import com.phjt.shangxueyuan.mvp.contract.SystemMessageContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.SystemMessageActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/20 10:52
 */

@ActivityScope
@Component(modules = SystemMessageModule.class, dependencies = AppComponent.class)
public interface SystemMessageComponent {
    void inject(SystemMessageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SystemMessageComponent.Builder view(SystemMessageContract.View view);

        SystemMessageComponent.Builder appComponent(AppComponent appComponent);

        SystemMessageComponent build();
    }
}