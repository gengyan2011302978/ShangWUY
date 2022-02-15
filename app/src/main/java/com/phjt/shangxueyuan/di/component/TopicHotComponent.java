package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.TopicHotModule;
import com.phjt.shangxueyuan.mvp.contract.TopicHotContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.TopicHotFragment;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:42
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = TopicHotModule.class, dependencies = AppComponent.class)
public interface TopicHotComponent {
    void inject(TopicHotFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TopicHotComponent.Builder view(TopicHotContract.View view);

        TopicHotComponent.Builder appComponent(AppComponent appComponent);

        TopicHotComponent build();
    }
}