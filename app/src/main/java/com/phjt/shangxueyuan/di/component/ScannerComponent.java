package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ScannerModule;
import com.phjt.shangxueyuan.mvp.contract.ScannerContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ScannerActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/17 09:45
 */

@ActivityScope
@Component(modules = ScannerModule.class, dependencies = AppComponent.class)
public interface ScannerComponent {
    void inject(ScannerActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ScannerComponent.Builder view(ScannerContract.View view);

        ScannerComponent.Builder appComponent(AppComponent appComponent);

        ScannerComponent build();
    }
}