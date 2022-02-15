package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ExchangeVoucherModule;
import com.phjt.shangxueyuan.mvp.contract.ExchangeVoucherContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ExchangeVoucherActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/11/24 14:11
 */

@ActivityScope
@Component(modules = ExchangeVoucherModule.class, dependencies = AppComponent.class)
public interface ExchangeVoucherComponent {
    void inject(ExchangeVoucherActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExchangeVoucherComponent.Builder view(ExchangeVoucherContract.View view);

        ExchangeVoucherComponent.Builder appComponent(AppComponent appComponent);

        ExchangeVoucherComponent build();
    }
}