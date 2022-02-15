package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.StudyModule;
import com.phjt.shangxueyuan.mvp.contract.StudyContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.StudyFragment;


/**
 * @author: Created by Template
 * date: 05/07/2020 14:09
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = StudyModule.class, dependencies = AppComponent.class)
public interface StudyComponent {
    void inject(StudyFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StudyComponent.Builder view(StudyContract.View view);

        StudyComponent.Builder appComponent(AppComponent appComponent);

        StudyComponent build();
    }
}