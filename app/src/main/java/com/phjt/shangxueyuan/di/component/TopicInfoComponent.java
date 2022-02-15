package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.TopicInfoModule;
import com.phjt.shangxueyuan.mvp.contract.TopicInfoContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.TopicInfoActivity;


/**
 * @author: Created by GengYan
 * date: 11/03/2020 15:08
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = TopicInfoModule.class, dependencies = AppComponent.class)
public interface TopicInfoComponent {
    void inject(TopicInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TopicInfoComponent.Builder view(TopicInfoContract.View view);

        TopicInfoComponent.Builder appComponent(AppComponent appComponent);

        TopicInfoComponent build();
    }
}