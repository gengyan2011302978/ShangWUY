package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.TrainingCampDetailModule;
import com.phjt.shangxueyuan.mvp.contract.TrainingCampDetailContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.TrainingCampDetailActivity;


/**
 * @author: Created by GengYan
 * date: 01/18/2021 13:53
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = TrainingCampDetailModule.class, dependencies = AppComponent.class)
public interface TrainingCampDetailComponent {
    void inject(TrainingCampDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TrainingCampDetailComponent.Builder view(TrainingCampDetailContract.View view);

        TrainingCampDetailComponent.Builder appComponent(AppComponent appComponent);

        TrainingCampDetailComponent build();
    }
}