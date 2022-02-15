package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MobileLoginModule;
import com.phjt.shangxueyuan.mvp.contract.MobileLoginContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MobileLoginActivity;


/**
 * @author: Created by Template
 * date: 03/30/2020 10:42
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = MobileLoginModule.class, dependencies = AppComponent.class)
public interface MobileLoginComponent {
    void inject(MobileLoginActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MobileLoginComponent.Builder view(MobileLoginContract.View view);

        MobileLoginComponent.Builder appComponent(AppComponent appComponent);

        MobileLoginComponent build();
    }
}