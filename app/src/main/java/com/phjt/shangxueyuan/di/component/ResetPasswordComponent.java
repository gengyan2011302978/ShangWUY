package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ResetPasswordModule;
import com.phjt.shangxueyuan.mvp.contract.ResetPasswordContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ResetPasswordActivity;


/**
 * @author: Created by Template
 * date: 03/27/2020 13:40
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = ResetPasswordModule.class, dependencies = AppComponent.class)
public interface ResetPasswordComponent {
    void inject(ResetPasswordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ResetPasswordComponent.Builder view(ResetPasswordContract.View view);

        ResetPasswordComponent.Builder appComponent(AppComponent appComponent);

        ResetPasswordComponent build();
    }
}