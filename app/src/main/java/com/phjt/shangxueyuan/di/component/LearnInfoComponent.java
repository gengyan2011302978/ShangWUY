package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.LearnInfoModule;
import com.phjt.shangxueyuan.mvp.contract.LearnInfoContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.LearnInfoActivity;


/**
 * @author: Created by GengYan
 * date: 06/22/2021 10:24
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = LearnInfoModule.class, dependencies = AppComponent.class)
public interface LearnInfoComponent {
    void inject(LearnInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LearnInfoComponent.Builder view(LearnInfoContract.View view);

        LearnInfoComponent.Builder appComponent(AppComponent appComponent);

        LearnInfoComponent build();
    }
}