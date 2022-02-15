package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.di.module.SelectedAnswersModule;
import com.phjt.shangxueyuan.mvp.contract.SelectedAnswersContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.SelectedAnswersFragment;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 14:27
 * @description :
 */
@FragmentScope
@Component(modules = SelectedAnswersModule.class, dependencies = AppComponent.class)
public interface SelectedAnswersComponent {

    void inject(SelectedAnswersFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SelectedAnswersComponent.Builder view(SelectedAnswersContract.View view);

        SelectedAnswersComponent.Builder appComponent(AppComponent appComponent);

        SelectedAnswersComponent build();
    }
}