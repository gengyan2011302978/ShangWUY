package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyTopicModule;
import com.phjt.shangxueyuan.mvp.contract.MyTopicContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MyTopicActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 10:10
 */

@ActivityScope
@Component(modules = MyTopicModule.class, dependencies = AppComponent.class)
public interface MyTopicComponent {
    void inject(MyTopicActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyTopicComponent.Builder view(MyTopicContract.View view);

        MyTopicComponent.Builder appComponent(AppComponent appComponent);

        MyTopicComponent build();
    }
}