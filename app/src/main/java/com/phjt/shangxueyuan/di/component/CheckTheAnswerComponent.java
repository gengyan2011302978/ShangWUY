package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.di.module.CheckTheAnswerModule;
import com.phjt.shangxueyuan.mvp.contract.CheckTheAnswerContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.CheckTheAnswerActivity;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 11:05
 * @description :
 */
@ActivityScope
@Component(modules = CheckTheAnswerModule.class, dependencies = AppComponent.class)
public interface CheckTheAnswerComponent {

    void inject(CheckTheAnswerActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CheckTheAnswerComponent.Builder view(CheckTheAnswerContract.View view);

        CheckTheAnswerComponent.Builder appComponent(AppComponent appComponent);

        CheckTheAnswerComponent build();
    }
}