package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyPointsModule;
import com.phjt.shangxueyuan.mvp.contract.MyPointsContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MyPointsActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 10:54
 */

@ActivityScope
@Component(modules = MyPointsModule.class, dependencies = AppComponent.class)
public interface MyPointsComponent {
    void inject(MyPointsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyPointsComponent.Builder view(MyPointsContract.View view);

        MyPointsComponent.Builder appComponent(AppComponent appComponent);

        MyPointsComponent build();
    }
}