package com.phjt.shangxueyuan.di.component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.di.module.ForgetPasswordModule;
import com.phjt.shangxueyuan.mvp.contract.ForgetPasswordContract;
import com.phjt.shangxueyuan.mvp.ui.activity.ForgetPasswordActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * @author: Created by Template
 * date: 03/27/2020 13:16
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = ForgetPasswordModule.class, dependencies = AppComponent.class)
public interface ForgetPasswordComponent {
    void inject(ForgetPasswordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ForgetPasswordComponent.Builder view(ForgetPasswordContract.View view);

        ForgetPasswordComponent.Builder appComponent(AppComponent appComponent);

        ForgetPasswordComponent build();
    }
}