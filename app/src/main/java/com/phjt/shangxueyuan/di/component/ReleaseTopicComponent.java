package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ReleaseTopicModule;
import com.phjt.shangxueyuan.mvp.contract.ReleaseTopicContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ReleaseTopicActivity;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:28
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = ReleaseTopicModule.class, dependencies = AppComponent.class)
public interface ReleaseTopicComponent {
    void inject(ReleaseTopicActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ReleaseTopicComponent.Builder view(ReleaseTopicContract.View view);

        ReleaseTopicComponent.Builder appComponent(AppComponent appComponent);

        ReleaseTopicComponent build();
    }
}