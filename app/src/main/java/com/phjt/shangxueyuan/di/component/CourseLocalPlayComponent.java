package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.CourseLocalPlayModule;
import com.phjt.shangxueyuan.mvp.contract.CourseLocalPlayContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseLocalPlayActivity;


/**
 * @author: Created by GengYan
 * date: 09/07/2020 09:20
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = CourseLocalPlayModule.class, dependencies = AppComponent.class)
public interface CourseLocalPlayComponent {
    void inject(CourseLocalPlayActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CourseLocalPlayComponent.Builder view(CourseLocalPlayContract.View view);

        CourseLocalPlayComponent.Builder appComponent(AppComponent appComponent);

        CourseLocalPlayComponent build();
    }
}