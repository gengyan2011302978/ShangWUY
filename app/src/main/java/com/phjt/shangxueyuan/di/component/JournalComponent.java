package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.JournalModule;
import com.phjt.shangxueyuan.mvp.contract.JournalContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.JournalActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/18 14:49
 */

@ActivityScope
@Component(modules = JournalModule.class, dependencies = AppComponent.class)
public interface JournalComponent {
    void inject(JournalActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        JournalComponent.Builder view(JournalContract.View view);

        JournalComponent.Builder appComponent(AppComponent appComponent);

        JournalComponent build();
    }
}