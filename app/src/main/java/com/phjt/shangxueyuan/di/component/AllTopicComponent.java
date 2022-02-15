package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.AllTopicModule;
import com.phjt.shangxueyuan.mvp.contract.AllTopicContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.AllTopicActivity;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:29
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = AllTopicModule.class, dependencies = AppComponent.class)
public interface AllTopicComponent {
    void inject(AllTopicActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AllTopicComponent.Builder view(AllTopicContract.View view);

        AllTopicComponent.Builder appComponent(AppComponent appComponent);

        AllTopicComponent build();
    }
}