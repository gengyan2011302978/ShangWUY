package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyColumnModule;
import com.phjt.shangxueyuan.mvp.contract.MyColumnContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MyColumnActivity;


/**
 * @author: Created by GengYan
 * date: 12/09/2020 17:27
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = MyColumnModule.class, dependencies = AppComponent.class)
public interface MyColumnComponent {
    void inject(MyColumnActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyColumnComponent.Builder view(MyColumnContract.View view);

        MyColumnComponent.Builder appComponent(AppComponent appComponent);

        MyColumnComponent build();
    }
}