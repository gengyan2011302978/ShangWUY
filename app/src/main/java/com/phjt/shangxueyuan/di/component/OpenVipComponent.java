package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.OpenVipModule;
import com.phjt.shangxueyuan.mvp.contract.OpenVipContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.OpenVipActivity;


/**
 * @author: Created by Template
 * date: 03/27/2020 15:37
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = OpenVipModule.class, dependencies = AppComponent.class)
public interface OpenVipComponent {
    void inject(OpenVipActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OpenVipComponent.Builder view(OpenVipContract.View view);

        OpenVipComponent.Builder appComponent(AppComponent appComponent);

        OpenVipComponent build();
    }
}