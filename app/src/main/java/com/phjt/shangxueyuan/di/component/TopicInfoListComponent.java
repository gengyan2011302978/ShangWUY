package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.TopicInfoListModule;
import com.phjt.shangxueyuan.mvp.contract.TopicInfoListContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.TopicInfoListFragment;


/**
 * @author: Created by GengYan
 * date: 11/03/2020 18:23
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = TopicInfoListModule.class, dependencies = AppComponent.class)
public interface TopicInfoListComponent {
    void inject(TopicInfoListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TopicInfoListComponent.Builder view(TopicInfoListContract.View view);

        TopicInfoListComponent.Builder appComponent(AppComponent appComponent);

        TopicInfoListComponent build();
    }
}