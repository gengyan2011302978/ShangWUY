package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.PublishDynamicModule;
import com.phjt.shangxueyuan.mvp.contract.PublishDynamicContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.PublishDynamicActivity;


/**
 * @author: Created by GengYan
 * date: 11/04/2020 16:18
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = PublishDynamicModule.class, dependencies = AppComponent.class)
public interface PublishDynamicComponent {
    void inject(PublishDynamicActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PublishDynamicComponent.Builder view(PublishDynamicContract.View view);

        PublishDynamicComponent.Builder appComponent(AppComponent appComponent);

        PublishDynamicComponent build();
    }
}