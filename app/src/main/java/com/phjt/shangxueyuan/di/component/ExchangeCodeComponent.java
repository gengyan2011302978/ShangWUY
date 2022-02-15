package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ExchangeCodeModule;
import com.phjt.shangxueyuan.mvp.contract.ExchangeCodeContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ExchangeCodeActivity;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 11:00
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = ExchangeCodeModule.class, dependencies = AppComponent.class)
public interface ExchangeCodeComponent {
    void inject(ExchangeCodeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExchangeCodeComponent.Builder view(ExchangeCodeContract.View view);

        ExchangeCodeComponent.Builder appComponent(AppComponent appComponent);

        ExchangeCodeComponent build();
    }
}