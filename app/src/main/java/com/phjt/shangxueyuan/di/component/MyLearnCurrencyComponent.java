package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyLearnCurrencyModule;
import com.phjt.shangxueyuan.mvp.contract.MyLearnCurrencyContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MyLearnCurrencyActivity;


/**
 * @author: Created by GengYan
 * date: 06/21/2021 16:43
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = MyLearnCurrencyModule.class, dependencies = AppComponent.class)
public interface MyLearnCurrencyComponent {
    void inject(MyLearnCurrencyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyLearnCurrencyComponent.Builder view(MyLearnCurrencyContract.View view);

        MyLearnCurrencyComponent.Builder appComponent(AppComponent appComponent);

        MyLearnCurrencyComponent build();
    }
}