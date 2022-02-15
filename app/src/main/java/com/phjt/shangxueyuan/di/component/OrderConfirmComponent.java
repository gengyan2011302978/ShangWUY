package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.di.module.OrderConfirmModule;
import com.phjt.shangxueyuan.mvp.contract.OrderConfirmContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.OrderConfirmActivity;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 15:51
 * @description :
 */
@ActivityScope
@Component(modules = OrderConfirmModule.class, dependencies = AppComponent.class)
public interface OrderConfirmComponent {

    void inject(OrderConfirmActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OrderConfirmComponent.Builder view(OrderConfirmContract.View view);

        OrderConfirmComponent.Builder appComponent(AppComponent appComponent);

        OrderConfirmComponent build();
    }
}