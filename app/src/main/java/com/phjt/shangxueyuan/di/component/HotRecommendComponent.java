package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.HotRecommendModule;
import com.phjt.shangxueyuan.mvp.contract.HotRecommendContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.HotRecommendActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/27 17:21
 */

@ActivityScope
@Component(modules = HotRecommendModule.class, dependencies = AppComponent.class)
public interface HotRecommendComponent {
    void inject(HotRecommendActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HotRecommendComponent.Builder view(HotRecommendContract.View view);

        HotRecommendComponent.Builder appComponent(AppComponent appComponent);

        HotRecommendComponent build();
    }
}