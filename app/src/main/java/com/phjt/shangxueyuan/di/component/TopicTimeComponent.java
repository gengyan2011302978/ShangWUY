package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.TopicTimeModule;
import com.phjt.shangxueyuan.mvp.contract.TopicTimeContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.TopicTimeFragment;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:43
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = TopicTimeModule.class, dependencies = AppComponent.class)
public interface TopicTimeComponent {
    void inject(TopicTimeFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TopicTimeComponent.Builder view(TopicTimeContract.View view);

        TopicTimeComponent.Builder appComponent(AppComponent appComponent);

        TopicTimeComponent build();
    }
}