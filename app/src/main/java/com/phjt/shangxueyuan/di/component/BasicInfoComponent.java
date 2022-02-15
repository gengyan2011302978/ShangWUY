package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.BasicInfoModule;
import com.phjt.shangxueyuan.mvp.contract.BasicInfoContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.BasicInfoActivity;


@ActivityScope
@Component(modules = BasicInfoModule.class, dependencies = AppComponent.class)
public interface BasicInfoComponent {
    void inject(BasicInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BasicInfoComponent.Builder view(BasicInfoContract.View view);

        BasicInfoComponent.Builder appComponent(AppComponent appComponent);

        BasicInfoComponent build();
    }
}