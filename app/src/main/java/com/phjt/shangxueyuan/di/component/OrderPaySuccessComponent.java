package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.di.module.OrderPaySuccessModule;
import com.phjt.shangxueyuan.mvp.contract.OrderPaySuccessContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.OrderPaySuccessActivity;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/21 14:32
 * @description :
 */
@ActivityScope
@Component(modules = OrderPaySuccessModule.class, dependencies = AppComponent.class)
public interface OrderPaySuccessComponent {

    void inject(OrderPaySuccessActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OrderPaySuccessComponent.Builder view(OrderPaySuccessContract.View view);

        OrderPaySuccessComponent.Builder appComponent(AppComponent appComponent);

        OrderPaySuccessComponent build();
    }
}