package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.di.module.PutQuestionsToModule;
import com.phjt.shangxueyuan.mvp.contract.PutQuestionsToContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.PutQuestionsToActivity;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 14:32
 * @description :
 */
@ActivityScope
@Component(modules = PutQuestionsToModule.class, dependencies = AppComponent.class)
public interface PutQuestionsToComponent {

    void inject(PutQuestionsToActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PutQuestionsToComponent.Builder view(PutQuestionsToContract.View view);

        PutQuestionsToComponent.Builder appComponent(AppComponent appComponent);

        PutQuestionsToComponent build();
    }
}