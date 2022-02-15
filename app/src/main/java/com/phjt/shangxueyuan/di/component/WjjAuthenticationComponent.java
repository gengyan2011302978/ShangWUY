package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.WjjAuthenticationModule;
import com.phjt.shangxueyuan.mvp.contract.WjjAuthenticationContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.WjjAuthenticationActivity;


@ActivityScope
@Component(modules = WjjAuthenticationModule.class, dependencies = AppComponent.class)
public interface WjjAuthenticationComponent {
    void inject(WjjAuthenticationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WjjAuthenticationComponent.Builder view(WjjAuthenticationContract.View view);

        WjjAuthenticationComponent.Builder appComponent(AppComponent appComponent);

        WjjAuthenticationComponent build();
    }
}