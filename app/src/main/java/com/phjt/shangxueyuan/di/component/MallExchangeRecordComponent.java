package com.phjt.shangxueyuan.di.component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.di.module.MallExchangeRecordModule;
import com.phjt.shangxueyuan.mvp.contract.MallExchangeRecordContract;
import com.phjt.shangxueyuan.mvp.ui.activity.MallExchangeRecordActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/23 13:42
 * @description :
 */
@ActivityScope
@Component(modules = MallExchangeRecordModule.class, dependencies = AppComponent.class)
public interface MallExchangeRecordComponent {

    void inject(MallExchangeRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MallExchangeRecordComponent.Builder view(MallExchangeRecordContract.View view);

        MallExchangeRecordComponent.Builder appComponent(AppComponent appComponent);

        MallExchangeRecordComponent build();
    }
}