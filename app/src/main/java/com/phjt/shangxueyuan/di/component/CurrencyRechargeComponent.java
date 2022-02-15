package com.phjt.shangxueyuan.di.component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.di.module.CurrencyRechargeModule;
import com.phjt.shangxueyuan.mvp.contract.CurrencyRechargeContract;
import com.phjt.shangxueyuan.mvp.ui.activity.CurrencyRechargeActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/25 09:39
 * @description :
 */
@ActivityScope
@Component(modules = CurrencyRechargeModule.class, dependencies = AppComponent.class)
public interface CurrencyRechargeComponent {

    void inject(CurrencyRechargeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CurrencyRechargeComponent.Builder view(CurrencyRechargeContract.View view);

        CurrencyRechargeComponent.Builder appComponent(AppComponent appComponent);

        CurrencyRechargeComponent build();
    }
}