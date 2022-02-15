package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyCoursesModule;
import com.phjt.shangxueyuan.mvp.contract.MyCoursesContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MyCoursesActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:00
 */

@ActivityScope
@Component(modules = MyCoursesModule.class, dependencies = AppComponent.class)
public interface MyCoursesComponent {
    void inject(MyCoursesActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyCoursesComponent.Builder view(MyCoursesContract.View view);

        MyCoursesComponent.Builder appComponent(AppComponent appComponent);

        MyCoursesComponent build();
    }
}