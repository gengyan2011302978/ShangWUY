package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MessageDetalModule;
import com.phjt.shangxueyuan.mvp.contract.MessageDetalContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MessageDetalActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/28 09:35
 */

@ActivityScope
@Component(modules = MessageDetalModule.class, dependencies = AppComponent.class)
public interface MessageDetalComponent {
    void inject(MessageDetalActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MessageDetalComponent.Builder view(MessageDetalContract.View view);

        MessageDetalComponent.Builder appComponent(AppComponent appComponent);

        MessageDetalComponent build();
    }
}