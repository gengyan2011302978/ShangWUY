package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.TrainingPlayModule;
import com.phjt.shangxueyuan.mvp.contract.TrainingPlayContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.TrainingPlayActivity;


/**
 * @author: Created by GengYan
 * date: 01/20/2021 11:57
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = TrainingPlayModule.class, dependencies = AppComponent.class)
public interface TrainingPlayComponent {
    void inject(TrainingPlayActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TrainingPlayComponent.Builder view(TrainingPlayContract.View view);

        TrainingPlayComponent.Builder appComponent(AppComponent appComponent);

        TrainingPlayComponent build();
    }
}