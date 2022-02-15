package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyClockInModule;
import com.phjt.shangxueyuan.mvp.contract.MyClockInContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MyClockInActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:01
 */

@ActivityScope
@Component(modules = MyClockInModule.class, dependencies = AppComponent.class)
public interface MyClockInComponent {
    void inject(MyClockInActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyClockInComponent.Builder view(MyClockInContract.View view);

        MyClockInComponent.Builder appComponent(AppComponent appComponent);

        MyClockInComponent build();
    }
}