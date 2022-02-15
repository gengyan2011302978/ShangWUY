package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.AboutModule;
import com.phjt.shangxueyuan.mvp.contract.AboutContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.AboutActivity;


@ActivityScope
@Component(modules = AboutModule.class, dependencies = AppComponent.class)
public interface AboutComponent {
    void inject(AboutActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AboutComponent.Builder view(AboutContract.View view);

        AboutComponent.Builder appComponent(AppComponent appComponent);

        AboutComponent build();
    }
}