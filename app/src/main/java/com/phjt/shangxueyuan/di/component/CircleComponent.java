package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.CircleModule;
import com.phjt.shangxueyuan.mvp.contract.CircleContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.CircleFragment;


/**
 * @author: Created by GengYan
 * date: 10/19/2020 10:24
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = CircleModule.class, dependencies = AppComponent.class)
public interface CircleComponent {
    void inject(CircleFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CircleComponent.Builder view(CircleContract.View view);

        CircleComponent.Builder appComponent(AppComponent appComponent);

        CircleComponent build();
    }
}