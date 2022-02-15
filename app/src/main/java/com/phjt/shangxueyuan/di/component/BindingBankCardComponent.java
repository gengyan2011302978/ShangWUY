package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.BindingBankCardModule;
import com.phjt.shangxueyuan.mvp.contract.BindingBankCardContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.BindingBankCardActivity;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 17:26
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = BindingBankCardModule.class, dependencies = AppComponent.class)
public interface BindingBankCardComponent {
    void inject(BindingBankCardActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BindingBankCardComponent.Builder view(BindingBankCardContract.View view);

        BindingBankCardComponent.Builder appComponent(AppComponent appComponent);

        BindingBankCardComponent build();
    }
}