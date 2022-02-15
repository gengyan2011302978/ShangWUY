package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.TrainingCommentDetailModule;
import com.phjt.shangxueyuan.mvp.contract.TrainingCommentDetailContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.TrainingCommentDetailActivity;


/**
 * @author: Created by GengYan
 * date: 02/04/2021 11:17
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = TrainingCommentDetailModule.class, dependencies = AppComponent.class)
public interface TrainingCommentDetailComponent {
    void inject(TrainingCommentDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TrainingCommentDetailComponent.Builder view(TrainingCommentDetailContract.View view);

        TrainingCommentDetailComponent.Builder appComponent(AppComponent appComponent);

        TrainingCommentDetailComponent build();
    }
}