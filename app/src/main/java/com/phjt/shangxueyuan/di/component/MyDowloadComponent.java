package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyDowloadModule;
import com.phjt.shangxueyuan.mvp.contract.MyDowloadContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MyDowloadActivity;


/**
 * @author: Created by Template
 * date: 06/02/2020 10:34
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = MyDowloadModule.class, dependencies = AppComponent.class)
public interface MyDowloadComponent {
    void inject(MyDowloadActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyDowloadComponent.Builder view(MyDowloadContract.View view);

        MyDowloadComponent.Builder appComponent(AppComponent appComponent);

        MyDowloadComponent build();
    }
}