package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.SetUpModule;
import com.phjt.shangxueyuan.mvp.contract.SetUpContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.SetUpActivity;


@ActivityScope
@Component(modules = SetUpModule.class, dependencies = AppComponent.class)
public interface SetUpComponent {
    void inject(SetUpActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SetUpComponent.Builder view(SetUpContract.View view);

        SetUpComponent.Builder appComponent(AppComponent appComponent);

        SetUpComponent build();
    }
}